public class ConfigFileGenerator {

    public static String generateCode(AnsBean bean) {

        String methodName = bean.getName().substring(bean.getName().lastIndexOf(".") + 1);
        String a = "point=invoke^",
                b = "method=" + methodName + "(" + bean.getConfigArgList() + ")" + "^",
                c = "returnType=" + bean.getReturnType() + "^",
                d = "static=" + bean.isStatic() + "^",
                e = "super=" + bean.getName().substring(0, bean.getName().lastIndexOf(".")) + "^";

        String f = "proxyMethod="+bean.getName()+"_proxy";
        f = f.replace('$', '_');
        f = f.replace('.', '_');

        String finalConfig = a + b + c + d + e + f;
        return finalConfig;
    }

}
