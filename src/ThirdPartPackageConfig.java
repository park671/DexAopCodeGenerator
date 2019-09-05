import java.util.Scanner;

public class ThirdPartPackageConfig {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        StringBuilder finalConfig = new StringBuilder("^package=");
        boolean first = true;
        while (reader.hasNext()) {
            String packageName = reader.nextLine();
            if (packageName.equals("over")) {
                break;
            }
            if(first){
                first = false;
            }else{
                finalConfig.append(",");
            }
            finalConfig.append(packageName);
        }
        System.out.println(finalConfig.toString());
        StringBuilder config = new StringBuilder();
        while (reader.hasNext()) {
            String dexaop = reader.nextLine();
            config.append(dexaop).append(finalConfig).append("\n");
        }
        System.out.println(config.toString());
    }
}
