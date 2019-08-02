import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProxyToPermission {
    public static Map<String, Set<String>> proxyPermission = new HashMap<>();

    public static void generateCode(AnsBean ansBean, JsonBean jsonBean) {

        String proxyName = ansBean.getName() + "_proxy";
        proxyName = proxyName.replace('$', '_');
        proxyName = proxyName.replace('.', '_');

        Set<String> proxySet = proxyPermission.get(proxyName);
        if(proxySet == null){
            proxySet = new HashSet<>();
            proxySet.add(jsonBean.getPermissionName());
            proxyPermission.put(proxyName, proxySet);
        }else{
            proxySet.add(jsonBean.getPermissionName());
        }

//        stringBuilder.append(f);
//        stringBuilder.append("\", \"");
//        stringBuilder.append(jsonBean.getPermissionName());
//        stringBuilder.append("\");");

    }

}
