import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cdx0312
 * 2018/4/3
 * 继承实现FIFO缓存
 */
public class FIFOCache<K, V> extends LinkedHashMap<K, V>{

    //缓存的大小
    private int cacheSize;

    //初始化时保证LinkedHashMap的初始大小要大于CacheSize+1，否则会发生扩容
    public FIFOCache(int cacheSize) {
        super(16, 0.75f);
        this.cacheSize = cacheSize;
    }
    /**
     * 重写该方法，当连表的size大于CacheSize时删除最新插入的元素
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
        FIFOCache<String, String> f = new FIFOCache<>(6);
        for (int i = 0; i < 6; i++) {
            f.put(i+"", i * i + "");
        }
        System.out.println(f.toString());
        f.put("s", "s");
        System.out.println(f.toString());
        f.put("5", "2222");
        System.out.println(f.toString());
        f.get(1 + "");
        System.out.println(f.toString());
    }
}
