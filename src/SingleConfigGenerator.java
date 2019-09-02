import java.util.Scanner;

public class SingleConfigGenerator {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.print("static:");
        while (reader.hasNext()) {
            AnsBean bean = new AnsBean();
            bean.setStatic(reader.nextLine().equals("true"));
            System.out.print("name:");
            bean.setName(reader.nextLine());
            System.out.print("arg:");
            bean.setConfigArgList(reader.nextLine());
            System.out.print("returnType:");
            bean.setReturnType(reader.nextLine());
            System.out.print("clazz:");
            String clazz = reader.nextLine();

            String methodName = bean.getName().substring(bean.getName().lastIndexOf(".") + 1);
            String a = "point=invoke^",
                    b = "method=" + methodName + "(" + bean.getConfigArgList() + ")" + "^",
                    c = "returnType=" + bean.getReturnType() + "^",
                    d = "static=" + bean.isStatic() + "^",
                    e = "super=" + clazz + "^";

            String f = "proxyMethod=" + bean.getName() + "_proxy";
            f = f.replace('$', '_');
            f = f.replace('.', '_');

            String finalConfig = a + b + c + d + e + f;
            System.out.println(finalConfig);
            System.out.print("static:");
        }
    }

}
