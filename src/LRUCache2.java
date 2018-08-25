import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by cdx0312
 * 2018/4/3
 * LinkedHashMap的委托方法
 */
public class LRUCache2<K, V> {
    //缓存大小
    private final int CACHE_SIZE;
    //默认加载因子
    private final float DEFAULT_LOAD_FACTOR = 0.75f;
    //缓存容器
    private LinkedHashMap<K, V> map;

    //初始化过程中,传入缓存的大小，根据缓存大小和默认factor来计算
    // LinkedHashMap的初始容量，确保其不会产生扩容操作。
    // 从而完成了map容器的初始化内容。
    public LRUCache2(int cacheSize) {
        this.CACHE_SIZE = cacheSize;
        int capacity = (int)Math.ceil(CACHE_SIZE / DEFAULT_LOAD_FACTOR) + 1;
        map = new LinkedHashMap<K,V>(capacity, DEFAULT_LOAD_FACTOR, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > CACHE_SIZE;
            }
        };
    }

    /**
     * 多线程put方法，需要加锁
     * @param key
     * @param value
     */
    public synchronized void put (K key, V value) {
        map.put(key, value);
    }

    /**
     * 多线程get方法，需要加锁
     * @param key
     * @return
     */
    public synchronized V get(K key) {
        return map.get(key);
    }

    /**
     * 多线程remove方法，需要加锁
     * @param key
     */
    public synchronized void remove(K key) {
        map.remove(key);
    }

    /**
     * 多线程getAll方法，需要加锁
     * @return
     */
    public synchronized Set<Map.Entry<K, V>> getAll() {
        return map.entrySet();
    }

    /**
     * 多线程size方法，需要加锁
     * @return
     */
    public synchronized int size() {
        return map.size();
    }

    /**
     * 多线程clear方法，需要加锁
     */
    public synchronized void clear() {
        map.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : map.entrySet()) {
            sb.append(String.format(" %s:%s ", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LRUCache2<Integer, String> lruCache2 = new LRUCache2<>(5);
        for (int i = 0; i < 5; i++) {
            lruCache2.put(i,"" + i);
        }
        System.out.println(lruCache2.toString());
        lruCache2.put(5,"5");
        System.out.println(lruCache2.toString());
        lruCache2.get(2);
        System.out.println(lruCache2.toString());
        lruCache2.put(6,"6");
        System.out.println(lruCache2.toString());
        lruCache2.remove(4);
        System.out.println(lruCache2.toString());
    }
}
