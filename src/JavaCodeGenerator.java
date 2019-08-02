public class JavaCodeGenerator {
    public static final String a = "\"" ,
        b = "\",";

    public static String generateJavaCode(AnsBean bean) {
        String proxyName = "";
        proxyName = bean.getName()+"_proxy";
        proxyName = proxyName.replace('$', '_');
        proxyName = proxyName.replace('.', '_');
        String ans = a + proxyName + b;
        return ans;
    }
}
