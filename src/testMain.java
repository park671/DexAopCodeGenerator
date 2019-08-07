import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class testMain {

    public static String key = "returnType=", nextKey = "^static=";

    static Map<String, Integer> typeCount = new HashMap<>();

    static class Bean {
        String type;
        Integer count;
    }

    public static void main(String[] args) {
        try {
            String config = FileUtils.readFileToString(new File(Main.DirPath, Main.ConfigFileName), "utf-8");
            int foundIndex = 0;
            while ((foundIndex = config.indexOf(key, foundIndex + 1)) != -1) {
                int nextAttrIndex = config.indexOf(nextKey, foundIndex);
                String type = config.substring(foundIndex + key.length(), nextAttrIndex);
                if (typeCount.get(type) == null) {
                    typeCount.put(type, 1);
                } else {
                    typeCount.put(type, typeCount.get(type) + 1);
                }
                //System.out.println(foundIndex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Bean> list = new ArrayList<>();
        int totalCount = 0;
        for (Map.Entry<String, Integer> item : typeCount.entrySet()) {
            //System.out.println(item.getKey() + ", " + item.getValue());
            Bean bean = new Bean();
            bean.type = item.getKey();
            bean.count = item.getValue();
            totalCount += bean.count;
            list.add(bean);
        }
        Collections.sort(list, new Comparator<Bean>() {
            @Override
            public int compare(Bean o1, Bean o2) {
                return o2.count - o1.count;
            }
        });
        for (Bean bean : list) {
            System.out.println(bean.type + ", " + bean.count);
        }
        System.out.println("\n total = " + totalCount);
    }
}
