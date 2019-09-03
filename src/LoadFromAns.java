import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class LoadFromAns {

    public static final String DirPath = "C:\\Users\\pake.yc\\Desktop\\ans", FileName = "MethodPermissions.txt";

    public static List<AnsBean> getAnsList() {
        List<AnsBean> contentList = new LinkedList<>();
        File methodFile = new File(DirPath, FileName);
        if (!methodFile.exists()) {
            System.err.println("CheckStyle文件：" + FileName + "未找到!");
            return null;
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(methodFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String content;
            while ((content = bufferedReader.readLine()) != null) {
                content = content.trim();
                String[] contents = content.split(" ");
                if (contents[0].isEmpty()) continue;
                if (contents[0].equals("static") || contents[0].equals("nonStatic")) {
                    AnsBean bean = new AnsBean();
                    bean.setStatic(contents[0].equals("static") ? true : false);
                    bean.setReturnType(contents[1].equals("T") ? "java.lang.Object" : contents[1]);
                    bean.setName(contents[2]);
                    String argList = "", configArgList = "";
                    boolean first = true;
                    for (int i = 4; i < contents.length - 1; i++) {
                        if (i % 2 == 0) {
                            if (first) first = false;
                            else configArgList += ",";
                            configArgList += contents[i];

                        }
                        argList += contents[i];
                    }
                    bean.setConfigArgList(configArgList);
                    bean.setArgList(argList);
                    contentList.add(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentList;
    }

}
