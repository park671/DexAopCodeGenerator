import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;


/**
 * Create by youngpark 2019/7/2
 * 对checkStyle的扫描结果，和伙伴的json 进行解析
 * 生成DexAOP所需要的代码
 */

public class Main {

    public static final String DirPath = "C:\\Users\\pake.yc\\Desktop\\ans",
            SuccessFileName = "Success.txt",
            FailFileName = "Fail.txt",
            ConfigFileName = "dex_aop.cfg",
            JavaFileName = "dex_aop.java",
            PermissionDexAopFileName = "dex_aop_permission.cfg",
            ProxyToPermissionFileName = "proxy_permission";

    public static void main(String[] args) {
        System.out.println("开始生成...");

        System.out.println("读取CheckStyle...");
        List<JsonBean> jsonList = LoadFromJson.getJsonList();
        System.out.println("读取json...");
        List<AnsBean> ansList = LoadFromAns.getAnsList();

        String finalMethodString = "";
        String notFoundString = "";
        String DexAopString = "";
        String DexAopJavaString = "";
        String DexAopReqPerString = "";

        System.out.println("开始生成...");
        StringBuilder ProxyToPermissionString = new StringBuilder("Map<String, String> proxyPermission = new HashMap<>();\n");

        for (int i = 0; i < jsonList.size(); i++) {
            JsonBean a = jsonList.get(i);
            String[] methodNames = a.getMethods();

            finalMethodString += "\n" + a.getPermissionName() + ":\n\n";
            notFoundString += "\n" + a.getPermissionName() + ":\n\n";

            for (int j = 0; j < methodNames.length; j++) {
                String nameFromJson = methodNames[j];
                String _nameFromJson = nameFromJson.replace('$', '.');
                boolean found = false, inner = false;

                Iterator<AnsBean> iterator = ansList.iterator();
                while (iterator.hasNext()) {
                    AnsBean ansBean = iterator.next();
                    String nameFromAns = ansBean.getName();
                    if (nameFromAns.contains("$")) {
                        inner = true;
                    }
                    String _nameFromAns = nameFromAns.replace('$', '.');
                    if (_nameFromJson.equals(_nameFromAns)) {
                        found = true;
                        ProxyToPermission.generateCode(ansBean, a);
                        //System.out.println("Found Name = " + ansBean);
                        String oneConfig = ConfigFileGenerator.generateCode(ansBean);
                        if (!DexAopString.contains(oneConfig)) {
                            DexAopString += oneConfig + "\n";
                        }
                        String oneJavaCode = JavaCodeGenerator.generateJavaCode(ansBean);
                        if (!DexAopJavaString.contains(oneJavaCode)) {
                            DexAopJavaString += oneJavaCode + "\n";
                        }
                        finalMethodString += "\t" + ansBean + "\n";
                    }
//                    else if (_nameFromAns.contains("equestPermission")) {
//                        String oneConfig = ConfigFileGenerator.generateCode(ansBean);
//                        DexAopReqPerString += oneConfig + "\n";
//                    }
                }
                if (!found) {
                    //System.err.println("Not Found Name = " + nameFromJson);
                    notFoundString += "\t" + nameFromJson + "\n";
                }
            }
        }
        System.out.println("construct list...");
        for (Map.Entry<String, Set<String>> item : ProxyToPermission.proxyPermission.entrySet()) {
            String proxyName = item.getKey();
            Set<String> proxyPermissions = item.getValue();
            ProxyToPermissionString.append("proxyPermission.put(\"").append(proxyName).append("\", \"");
            boolean first = true;
            for (String permission : proxyPermissions) {
                if (first) {
                    first = false;
                } else {
                    ProxyToPermissionString.append("|");
                }
                ProxyToPermissionString.append(permission);
            }
            ProxyToPermissionString.append("\");\n");
        }
        System.out.println("write File...");
        try {
//            FileUtils.writeStringToFile(new File(DirPath, PermissionDexAopFileName), DexAopReqPerString, "utf-8");
            FileUtils.writeStringToFile(new File(DirPath, SuccessFileName), finalMethodString, "utf-8");
            FileUtils.writeStringToFile(new File(DirPath, FailFileName), notFoundString, "utf-8");
            FileUtils.writeStringToFile(new File(DirPath, ConfigFileName), DexAopString, "utf-8");
            FileUtils.writeStringToFile(new File(DirPath, JavaFileName), DexAopJavaString, "utf-8");
            FileUtils.writeStringToFile(new File(DirPath, ProxyToPermissionFileName), ProxyToPermissionString.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("完成。");
    }

}
