import java.util.HashMap;

/**
 * Created by cdx0312
 * 2018/4/3
 * 链表+HashMap实现
 */
public class LRUCache3<K, V> {
    //缓存容量
    private final int CACHE_SIZE;
    //链表头
    private Entry head;
    //链表尾
    private Entry tail;
    //HashMap容器
    private HashMap<K, Entry<K, V>> hashMap;

    /**
     * 初始化HashMap容器和cacheSize
     * @param cacheSize 容量
     */
    public LRUCache3(int cacheSize) {
        this.CACHE_SIZE = cacheSize;
        hashMap = new HashMap<>();
    }

    /**
     * put方法，加锁
     * @param key
     * @param value
     */
    public synchronized void put (K key, V value) {
        Entry entry = getEntry(key);
        //hashmap中不存在key对应的entry时，新增
        if (entry == null) {
            //判断是否需要删除最老的节点
            if (hashMap.size() >= CACHE_SIZE) {
                hashMap.remove(tail.key);
                removeLast();
            }
            entry = new Entry();
            entry.key = key;
        }
        //修改
        entry.value = value;
        moveToFirst(entry);
        hashMap.put(key, entry);
    }

    /**
     * get方法，多线程加锁
     * @param key
     * @return
     */
    public synchronized V get(K key) {
        Entry<K, V> entry = hashMap.get(key);
        if (entry == null)
            return null;
        moveToFirst(entry);
        return entry.value;
    }

    /**
     * 删除
     * @param key
     */
    public synchronized void remove(K key) {
        Entry entry = getEntry(key);
        //从链表中删除
        if (entry != null) {
            if (entry.pre != null)
                entry.pre.next = entry.next;
            if (entry.next != null)
                entry.next.pre = entry.pre;
            if (entry == head)
                head = entry.next;
            if (entry == tail)
                tail = entry.pre;
        }
        //从HashMap中删除
        hashMap.remove(key);
    }

    //调整entry到链表头
    private void moveToFirst(Entry entry) {
        if (entry == head)
            return;
        if (entry.pre!= null)
            entry.pre.next = entry.next;
        if (entry.next != null)
            entry.next.pre = entry.pre;
        if (head == null || tail == null) {
            head = tail = entry;
            return;
        }
        entry.next = head;
        head.pre = entry;
        head = entry;
        entry.pre = null;
    }

    //移除链表尾的元素
    private void removeLast() {
        if (tail != null) {
            tail = tail.pre;
            if (tail == null)
                head = null;
            else
                tail.next = null;
        }
    }

    //从hashmap中获取key对应的Entry
    private Entry<K, V> getEntry(K key) {
        return hashMap.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Entry entry = head;
        while (entry != null) {
            sb.append("").append(entry.key).append(":").append(entry.value).append("  ");
            entry = entry.next;
        }
        return sb.toString();
    }

    /**
     * Entry内部类，双链表实现
     * @param <K>
     * @param <V>
     */
    class Entry<K,V> {
        public Entry pre;
        public Entry next;
        public K key;
        public V value;
    }

    public static void main(String[] args) {
        LRUCache3<Integer, String> lruCache3 = new LRUCache3<>(5);
        for (int i = 0; i < 5; i++) {
            lruCache3.put(i,"" + i);
        }
        System.out.println(lruCache3.toString());
        lruCache3.put(5,"5");
        System.out.println(lruCache3.toString());
        lruCache3.get(2);
        System.out.println(lruCache3.toString());
        lruCache3.put(6,"6");
        System.out.println(lruCache3.toString());
        lruCache3.remove(4);
        System.out.println(lruCache3.toString());
    }
}
