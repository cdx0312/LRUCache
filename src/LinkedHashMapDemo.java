import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cdx0312
 * 2018/4/4
 */
public class LinkedHashMapDemo {
    public static void main(String[] args) {
        Map<Integer,String> map = new LinkedHashMap<>();
        for (int i = 1; i < 7; i++) {
            map.put(i, "" + i);
//            System.out.println(map.toString());
        }
        System.out.println(map.toString());
//        map.remove(6);
//        System.out.println(map.toString());
//        map.remove(3);
//        System.out.println(map.toString());
        map.get(3);
        map.get(6);
        System.out.println(map);

        Map<Integer,String> orderMap = new LinkedHashMap<Integer, String >(16,0.75f, true);
        for (int i = 1; i < 7; i++) {
            orderMap.put(i, "" + i);
        }
        System.out.println(orderMap.toString());
        orderMap.get(3);
        System.out.println(orderMap);
        orderMap.get(1);
        System.out.println(orderMap);
    }
}
