import java.util.List;
import java.util.Scanner;

public class AnythingFromCheckStyle {

    public static void main(String[] args) {
        List<AnsBean> ansBeanList = LoadFromAns.getAnsList();
        System.out.println("就绪.");
        Scanner reader = new Scanner(System.in);
        while(reader.hasNext()){
            String name = reader.nextLine();
            for(AnsBean bean : ansBeanList){
                if(bean.getName().contains(name)){
                    String config = ConfigFileGenerator.generateCode(bean);
                    System.out.println(config);
                }
            }
            System.out.println("Over.");
        }
    }

}
