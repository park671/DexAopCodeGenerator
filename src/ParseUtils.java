import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by pake.yc on 2019/7/10.
 */

public class ParseUtils {

    public static Object parse(String clazzName, String val) {
        try {
            Class<?> clazz = Class.forName(clazzName);
            return parse(clazz, val);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object parse(Class<?> clazz, String val) {
        String typeName = clazz.getSimpleName();
        if(Character.isLowerCase(typeName.charAt(0))){
            typeName = Character.toUpperCase(typeName.charAt(0)) + typeName.substring(1);
            try {
                clazz = Class.forName("java.lang." + typeName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            Method parseMethod = clazz.getMethod("parse" + typeName, String.class);
            return parseMethod.invoke(null, val);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
