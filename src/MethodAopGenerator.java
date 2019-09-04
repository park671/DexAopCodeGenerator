import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class MethodAopGenerator {

    public static final String DirPath = "C:\\Users\\pake.yc\\Desktop\\隐私管控v2", SimpleName = "SimpleClazzName.txt", FullName = "FullClazzName.txt";

    static Map<String, String> simpleToFull = new HashMap<>();

    public static void initMap() throws FileNotFoundException {
        Scanner simpleReader = new Scanner(new FileInputStream(new File(DirPath, SimpleName)));
        Scanner fullReader = new Scanner(new FileInputStream(new File(DirPath, FullName)));
        while (simpleReader.hasNext() && fullReader.hasNext()) {
            simpleToFull.put(simpleReader.nextLine(), fullReader.nextLine());
        }
    }

    public static void main(String[] args) {

        try {
            initMap();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StringBuilder finalConfig = new StringBuilder();
        Set<String> mapNotFound = new HashSet<>();
        Set<String> proxyName = new HashSet<>();

        Scanner reader = null;
        try {
            reader = new Scanner(new FileInputStream(new File("C:\\Users\\pake.yc\\Desktop\\隐私管控v2", "Methods.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner clazzReader = null;
        try {
            clazzReader = new Scanner(new FileInputStream(new File("C:\\Users\\pake.yc\\Desktop\\隐私管控v2", "Class.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Set<String> keyword = new HashSet<>();
        keyword.add("public");
        keyword.add("private");
        keyword.add("protect");
        keyword.add("static");
        keyword.add("final");
        keyword.add("synchronized");
        keyword.add("volatile");
        keyword.add("abstract");

        /**
         * 0 - return value
         * 1 - method name
         * 2 - start args
         * 3 - end
         */
        int status = 0;

        while (reader.hasNext()) {
            status = 0;
            String method = reader.nextLine();
            String clazz = clazzReader.nextLine();
            method = method.trim();
            method = method.replace("(", " ( ");
            method = method.replace(",", " , ");
            method = method.replace(")", " ) ");
            System.out.println("method = " + method);

            String[] parts = method.split(" ");
            AnsBean bean = new AnsBean();

            StringBuilder arg = new StringBuilder("");

            boolean isArgType = true;
            boolean complete = false;
            boolean firstArg = true;

            for (String item : parts) {
                item = item.trim();
                if (item.isEmpty()) continue;
                System.out.println(item);
                if (keyword.contains(item)) {
                    if (item.equals("static")) {
                        bean.setStatic(true);
                    }
                } else {
                    switch (status) {
                        case 0:
                            if (!item.isEmpty()) {
                                if (item.contains("...")) {
                                    item = item.replace("...", "[]");
                                }
                                boolean isArray = false;
                                if (item.contains("[]")) {
                                    item = item.replace("[]", "");
                                    isArray = true;
                                }
                                if (simpleToFull.get(item) != null && !simpleToFull.get(item).isEmpty()) {
                                    item = simpleToFull.get(item);
                                    if (isArray) {
                                        item += "[]";
                                    }
                                } else {
                                    mapNotFound.add(item);
                                }
                            }
                            bean.setReturnType(item);
                            status++;
                            break;
                        case 1:
                            bean.setName(clazz + "." + item);
                            status++;
                            break;
                        case 2:
                            if (item.equals(",") || item.equals("(") || item.isEmpty()) {
                                break;
                            } else if (item.equals(")")) {
                                status++;
                                complete = true;
                                bean.setConfigArgList(arg.toString());
                                break;
                            }
                            if (isArgType) {
                                if (!firstArg) {
                                    arg.append(",");
                                } else {
                                    firstArg = false;
                                }
                                if (!item.isEmpty()) {
                                    if (item.contains("...")) {
                                        item = item.replace("...", "[]");
                                    }
                                    boolean isArray = false;
                                    if (item.contains("[]")) {
                                        item = item.replace("[]", "");
                                        isArray = true;
                                    }
                                    if (simpleToFull.get(item) != null && !simpleToFull.get(item).isEmpty()) {
                                        item = simpleToFull.get(item);
                                        if (isArray) {
                                            item += "[]";
                                        }
                                    } else {
                                        mapNotFound.add(item);
                                    }
                                }
                                arg.append(item);
                            }
                            isArgType = !isArgType;
                            break;
                        default:
                            complete = true;
                    }
                    if (complete) {
                        bean.setConfigArgList(arg.toString());
                        break;
                    }
                }
            }
            String itemConfig = ConfigFileGenerator.generateCode(bean);
            System.out.println(itemConfig);
            finalConfig.append(itemConfig).append("\n");
            String stub = "proxyMethod=";
            proxyName.add(itemConfig.substring(itemConfig.indexOf(stub) + stub.length()));
        }//While
        try {
            StringBuilder config = new StringBuilder();
            StringBuilder mapNotFoundStringBuilder = new StringBuilder();
            for (String item : mapNotFound) {
                mapNotFoundStringBuilder.append(item).append("\n");
            }
            for (String item : proxyName) {
                config.append("\"").append(item).append("\",\n");
            }
            FileUtils.writeStringToFile(new File("C:\\Users\\pake.yc\\Desktop\\隐私管控v2", "generator.java"), config.toString(), "utf-8");
            FileUtils.writeStringToFile(new File("C:\\Users\\pake.yc\\Desktop\\隐私管控v2", "h5js_thirdpart.cfg"), finalConfig.toString(), "utf-8");
            FileUtils.writeStringToFile(new File(DirPath, "notFound.txt"), mapNotFoundStringBuilder.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
