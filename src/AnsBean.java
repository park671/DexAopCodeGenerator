public class AnsBean {
    private boolean isStatic;
    private String returnType;
    private String name;
    private String argList;
    private String configArgList;

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArgList() {
        return argList;
    }

    public void setArgList(String argList) {
        this.argList = argList;
    }

    public String getConfigArgList() {
        return configArgList;
    }

    public void setConfigArgList(String configArgList) {
        this.configArgList = configArgList;
    }

    @Override
    public String toString() {
        return (isStatic? "static" : "nonStatic") + " " +
                returnType + " " +
                name + " " +
                "(" + argList + ")";
    }
}
