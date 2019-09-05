import java.util.*;

public class AlipayExportConfig {

    private static final String nameKey = "'name':";

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        Set<AnsBean> set = new TreeSet<>();
        Set<String> dexaopConfig = new TreeSet<>();

        while (reader.hasNext()) {
            String item = reader.nextLine();
            int index = item.indexOf(nameKey) + nameKey.length() + 1;
            int startIndex = item.indexOf("'", index);
            int endIndex = item.indexOf("'", startIndex + 1);
            String clazzName = item.substring(startIndex + 1, endIndex);
            AnsBean bean = new AnsBean();
            bean.setName(clazzName + ".getIntent");
            bean.setConfigArgList("");
            bean.setReturnType("android.content.Intent");
            bean.setStatic(false);
            bean.setArgList("");
            dexaopConfig.add(ConfigFileGenerator.generateCode(bean));
        }
        for (String dexaop : dexaopConfig) {
            System.out.println(dexaop.replace("^super=", "^class="));
        }
    }

}
