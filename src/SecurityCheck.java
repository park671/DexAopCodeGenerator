import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class SecurityCheck {

    public static String key = "method=", nextKey = "(", clazzKey = "super=", clazzNextKey = "^";

    static class DexAopBean {
        String name;
        String clazzName;
        String totalItem;
    }

    static class SecurityBean {
        String name;
    }

    public static void main(String[] args) {
        Scanner fileReader = null;
        try {
            fileReader = new Scanner(new FileInputStream(new File("C:\\Users\\pake.yc\\Desktop\\ans", "dex_aop.cfg")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<DexAopBean> dexAopBeanList = new ArrayList<>();
        while (fileReader.hasNext()) {
            String item = fileReader.nextLine();
            int foundIndex = 0;
            if ((foundIndex = item.indexOf(key)) != -1) {
                int nextAttrIndex = item.indexOf(nextKey, foundIndex);
                String name = item.substring(foundIndex + key.length(), nextAttrIndex);
                DexAopBean now = new DexAopBean();
                now.name = name;
                int clazzIndex = item.indexOf(clazzKey, foundIndex);
                now.clazzName = item.substring(clazzIndex + clazzKey.length(), item.indexOf(clazzNextKey, clazzIndex));
                now.totalItem = item;
                dexAopBeanList.add(now);
            }
        }
        for (DexAopBean a : dexAopBeanList) {
            System.out.println(a.name + " in " + a.clazzName);
        }

        System.out.println("*********************************");

        List<SecurityBean> securityBeanList = new ArrayList<>();
        Scanner reader = new Scanner(System.in);
        Set<String> finalDexAopList = new TreeSet<>();

        while (reader.hasNext()) {
            String item = reader.nextLine();
            String[] methodArgs = item.split(" ");
            boolean found = false;
            for (DexAopBean a : dexAopBeanList) {
                if (a.name.equals(methodArgs[1])) {
                    System.out.println("found : " + a.name + " in " + a.clazzName);
                    found = true;
                    finalDexAopList.add(a.totalItem);
                }
            }
            if (!found) {
                System.err.println("Not found:name = " + methodArgs[1] + ",  item = " + item);
            }
        }

        System.out.println("*********************************");

        for (String config : finalDexAopList) {
            System.out.println(config);
        }
    }
}
