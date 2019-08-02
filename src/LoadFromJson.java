import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class LoadFromJson {

    private static final String DirPath = "C:\\Users\\pake.yc\\android-permissions\\HDocs",
            FileName = "权限名到api和字段映射表.json";

    public static List<JsonBean> getJsonList() {
        String jsonString = "empty";
        File jsonFile = new File(DirPath, FileName);
        if (!jsonFile.exists()) {
            System.err.println("文件不存在！！");
            return null;
        }
        try {
            jsonString = FileUtils.readFileToString(jsonFile, Charset.forName("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        List<JsonBean> allBeanList = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JsonBean jsonBean = new JsonBean();
            //权限名称
            jsonBean.setPermissionName(jsonObject.getString("name"));
            //常亮数组
            JSONArray constantsArray = jsonObject.getJSONArray("constants");
            String[] allConstants = new String[constantsArray.size()];
            for (int j = 0; j < constantsArray.size(); j++) {
                allConstants[j] = constantsArray.getString(j);
            }
            jsonBean.setFields(allConstants);
            //方法
            JSONArray methodsArray = jsonObject.getJSONArray("apis");
            String[] allMethod = new String[methodsArray.size()];
            for (int j = 0; j < methodsArray.size(); j++) {
                allMethod[j] = methodsArray.getString(j);
            }
            jsonBean.setMethods(allMethod);
            allBeanList.add(jsonBean);
        }
        return allBeanList;
    }
}
