import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class AopToJava {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner reader = new Scanner(new FileInputStream(new File("C:\\Users\\pake.yc\\Desktop\\隐私管控v2", "船新版本DexAOP.cfg")));
        Set<String> proxyName = new HashSet<>();
        while (reader.hasNext()) {
            String configItem = reader.nextLine();
            String stub = "proxyMethod=";
            if (!configItem.contains(stub)) {
                continue;
            }
            proxyName.add(configItem.substring(configItem.indexOf(stub) + stub.length()));
            StringBuilder config = new StringBuilder();
            for(String item : proxyName){
                config.append("\"").append(item).append("\",\n");
            }
            try {
                FileUtils.writeStringToFile(new File("C:\\Users\\pake.yc\\Desktop\\隐私管控v2", "generator.java"), config.toString(), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
