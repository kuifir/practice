package com.kuifir.normal.collectionTopic;

import java.util.*;

/**
 * Java集合类库中的{@link java.util.WeakHashMap}持有的是弱引用。这个类使得创建规范映射更容易了。
 * 在这样的映射中，对于某个特定的值，我们只创建一个实例，以便更节省存储空间。
 * 当程序需要这个值时，它就在该映射中查找现有的对象并使用它（而不是从头创建）。
 * 该映射可以在其初始化过程中构造这些值，但更有可能按需创建。
 * <p></p>
 * 这是一种节省存储空间的技术，因此{@link java.util.WeakHashMap}允许垃圾收集器自动清理不用的值，这一点非常方便。
 * 放在WeakHashMap中的键和值不需要任何特殊处理，它们会自动被映射包在{@link java.lang.ref.WeakReference}中,
 * 当这个键不再使用时，它就可以被垃圾收集器清理了。
 *
 * @author kuifir
 * @date 2023/6/10 17:05
 * @see java.util.WeakHashMap
 * @see java.lang.ref.WeakReference
 * @see com.kuifir.normal.reference.References
 */
public class CanonoicalMapping {
    static void showKeys(Map<String, String> m) {
        // 显示排序后的键
        List<String> keys = new ArrayList<>(m.keySet());
        Collections.sort(keys);
        System.out.println(keys);
    }

    public static void main(String[] args) {
        int size = 100;
        String[] savedKeys = new String[size];
        WeakHashMap<String, String> map = new WeakHashMap<>();
        for (int i = 0; i < size; i++) {
            String key = String.format("%03d", i);
            String value = Integer.toString(i);
            if (i % 3 == 0) {
                // 当作真正的引用来保存
                savedKeys[i] = key;
            }
            map.put(key, value);
        }
        showKeys(map);
        System.gc();
        showKeys(map);
    }
}
