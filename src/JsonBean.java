import java.util.Arrays;

public class JsonBean {
    private String PermissionName;
    private String[] Fields;
    private String[] Methods;

    public String getPermissionName() {
        return PermissionName;
    }

    public void setPermissionName(String permissionName) {
        PermissionName = permissionName;
    }

    public String[] getFields() {
        return Fields;
    }

    public void setFields(String[] fields) {
        Fields = fields;
    }

    public String[] getMethods() {
        return Methods;
    }

    public void setMethods(String[] methods) {
        Methods = methods;
    }

    @Override
    public String toString() {
        return "\n\nPermissionName = '" + PermissionName + '\'' +
                "\nFields = " + Arrays.toString(Fields) +
                "\nMethods = " + Arrays.toString(Methods);
    }
}
