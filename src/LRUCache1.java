import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cdx0312
 * 2018/4/3
 * 继承LinkedHashMap实现
 */
public class LRUCache1<K, V> extends LinkedHashMap<K, V> {
    //缓存的大小
    private int cacheSize;

    //初始化时保证LinkedHashMap的初始大小要大于CacheSize+1，否则会发生扩容
    public LRUCache1(int cacheSize) {
        super(16, 0.75f, true);
        this.cacheSize = cacheSize;
    }

    /**
     * 重写该方法，当连表的size大于CacheSize时删除最老元素
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > cacheSize;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, V> entry : entrySet()) {
            sb.append(String.format("%s:%s ", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LRUCache1<String, String> lruCache1 = new LRUCache1<>(6);
        for (int i = 0; i < 6; i++) {
            lruCache1.put(i+"", i * i + "");
        }
        System.out.println(lruCache1.toString());
        lruCache1.put("s", "s");
        System.out.println(lruCache1.toString());
        lruCache1.put("2", "2222");
        System.out.println(lruCache1.toString());
        lruCache1.get(1+"");
        System.out.println(lruCache1.toString());
    }
}
