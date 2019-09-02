import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Other {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("com.alipay.dexaop.");
        list.add("com.alipay.fusion.intercept.");
        list.add("java.");
        list.add("javax.");
        list.add("android.");
        list.add("com.android.");
        list.add("dalvik.");

        List<String> result = new ArrayList<>();
        Scanner reader = new Scanner(System.in);
        StringBuilder oneStack = new StringBuilder();
        while (reader.hasNext()) {
            String oneLine = reader.nextLine().trim();
            boolean write = true;
            for (String preSuffix : list) {
                if (oneLine.contains(preSuffix)) {
                    write = false;
                    break;
                }
            }
            if (write) {
                result.add(oneLine);
//                oneStack.append(oneLine).append("\n");
            }
        }
//        System.out.println(oneStack.toString());
        System.out.println(JSONArray.fromObject(result).toString());
    }
}
