import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class ClassPermissionCheck {

    public static final String
            DirPath = "C:\\Users\\pake.yc\\Desktop\\ans",
            DexAopFileName = "DexAOP.cfg";

    public static String key = "super=", nextKey = "^proxyMethod=";

    public static String
            dexAopConfigString = "";

    public static void main(String[] args) throws Exception {
        dexAopConfigString = FileUtils.readFileToString(new File(DirPath, DexAopFileName), "utf-8");

        List<AnsBean> list = LoadFromAns.getAnsList();

        int preIndex = 0;
        Set<String> result = new HashSet<>();
        Set<String> finalResult = new HashSet<>();
        while ((preIndex = dexAopConfigString.indexOf(key, preIndex + 1)) != -1) {
            int nextAttrIndex = dexAopConfigString.indexOf(nextKey, preIndex);
            String clazzName = dexAopConfigString.substring(preIndex + key.length(), nextAttrIndex);
            result.add(clazzName);
        }

        for (AnsBean ansBean : list) {
            String thisConfig = ConfigFileGenerator.generateCode(ansBean);
            preIndex = thisConfig.indexOf(key, preIndex + 1);
            int nextAttrIndex = thisConfig.indexOf(nextKey, preIndex);
            String clazzName = thisConfig.substring(preIndex + key.length(), nextAttrIndex);

            if (result.contains(clazzName)) {
                finalResult.add(clazzName);
            }
        }

        System.out.println("fianl = " + finalResult.size());
        System.out.println("result = " + result.size());

        for(String clazzName : finalResult){
            System.out.println(clazzName);
        }

    }

}
