package com.xianlei.jdk8;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.WeakHashMap;

enum Season {
	SPRING, SUMMER, FALL, WINTER
}

class A {
	int count;

	public A(int count) {
		this.count = count;
	}

	// 根据count值判断两个对象是否相等
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj != null && obj.getClass() == A.class) {
			A a = (A) obj;
			return this.count == a.count;
		}
		return false;
	}

	// 根据count值来判断hashcode值
	public int hashcode() {
		return this.count;
	}
}

class B {
	// 重写equals()方法，B对象与任何对象通过equals()方法比较都返回true
	public boolean equals(Object obj) {
		return true;
	}
}

/*
 * 下面代码定义了一个R类，该类重写了equals()方法，并实现了Comparable()接口
 * 所以可以使用该R对象作为TreeMap的key,该TreeMap使用自然排序
 */
class R implements Comparable {
	int count;

	public R(int count) {
		this.count = count;
	}

	public String toString() {
		return "R[count:" + count + "]";
	}

	// 根据count来判断两个对象是否相等
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj != null && obj.getClass() == R.class) {
			R r = (R) obj;
			return this.count == r.count;
		}
		return false;
	}

	// 根据count属性值来判断两个对象的大小
	public int compareTo(Object arg0) {
		R r = (R) arg0;
		return count > r.count ? 1 : count < r.count ? -1 : 0;
	}
}

public class Map1 {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Map map = new HashMap();
		// 成对放入多个key-value对
		map.put("计算机导论", 101);
		map.put("高级算法分析", 111);
		map.put("敏捷开发", 121);
		map.put("网页设计", 131);
		// 多次放入的key-value中的value可以重复
		map.put("疯狂java讲义", 99);
		// 放入重复的key时，新的value会覆盖原有的value
		// 如果新的value覆盖了原有的value，该方法返回被覆盖的value
		System.out.println(map.put("网页设计", 99));
		System.out.println(map);
		// 判断是否包含指定的key
		System.out.println("是否包含值为 数据库技术key：" + map.containsKey("数据库技术"));
		// 判断是否包含指定value
		System.out.println("判断是否包含值为99 value:" + map.containsValue(111));
		// 获取map集合的所有key组成的集合，通过遍历key来实现遍历所有的key-value对
		for (Object key : map.keySet()) {
			// map.get(key)方法获取指定key对应的value
			System.out.println(key + "-->" + map.get(key));
		}
		map.remove("敏捷开发");
		System.out.println(map);
		System.out.println("--------Java8新增的Map方法-------");
		// 尝试替换key为"云计算"的value,由于原map中木有对应的key
		// 因此map没有改变，不会添加新的key-value对
		map.replace("云计算", 98);
		System.out.println(map);
		// 使用原value与传入参数计算出来的结果覆盖原有的value
		map.merge("高级算法分析", 10, (oldVal, parm) -> (Integer) oldVal + (Integer) parm);
		System.out.println(map);// 可以看到"高级算法分析"的value值增加了10
		// 当key为"java web"对应的value为null或者不存在时，使用计算的结果作为新的value
		map.computeIfAbsent("java web", (key) -> ((String) key).length());
		System.out.println(map);// 可以看到map中添加了java web=8这组key-value对
		// 当key为java web对应的value存在时，使用计算的结果作为新的value
		map.computeIfPresent("java web", (key, value) -> (Integer) value * (Integer) value);
		System.out.println(map);// map中的java web=8变成了java web=64

		System.out.println("\n-------Java改进的HashMap和HashTable实现类-------");
		// 试图将两个key为null值的key-value对放入HashMap中,不会引发异常
		map.put(null, null);
		map.put(null, null);
		// 将一个value为null值的key-value对放入HashMap中
		map.put("a", null);
		// 输出map
		System.out.println(map);

		System.out.println("\n----Hashtable判断key,value相等的标准----");
		Hashtable ht = new Hashtable();
		ht.put(new A(6000), "编程之美");
		ht.put(new A(87666), "高级算法设计");
		ht.put(new A(3211), new B());
		System.out.println(ht);
		// 只要两个对象通过equals()方法返回true
		// Hashtable就认为他们是相等value
		// 由于Hashtable中有一个B对象
		// 它与任何对象通过equals()方法比较都相等，所以下面输出true
		System.out.println(ht.containsValue("测试字符串"));
		// 只要两个A对象的count相等，它们通过equals()方法返回true，且hashcode值相等
		// Hashtable即认为它们是相同的key，所以下面输出true
		System.out.println(ht.containsKey(new A(3211)));
		// 下面语句可以删除最后一个key-value对
		ht.remove(new A(3211));
		System.out.println(ht);
		System.out.println("\n-------使用可变对象作为HashMap的key-------");
		/*
		 * 与HashSet类似的，如果使用可变对象作为HashMap、Hashtable的key,并且修改了作为key
		 * 的可变对象，则也可能出现与HashSet类似的情形：程序再也无法准确访问到Map中被修改过的key
		 * 尽量不要使用可变对象作为HashMap,Hashtable的key,如果必须使用，尽量不要修改作为key的可变对象
		 */
		map.clear();
		map.put(new A(6999), "海量存储");
		map.put(new A(87653), "云计算");
		System.out.println(map);
		// 获得与Hashtable的key set集合对应的Iterator迭代器
		Iterator it = map.keySet().iterator();
		// 取出map中的第一个key，并修改它的count值
		A first = (A) it.next();
		first.count = 8888;
		// 输出
		System.out.println(map);
		// 只能删除没有被修改过的key对应的key-value对
		map.remove(new A(87653));
		System.out.println(map);
		// 无法获取剩下的value
		System.out.println(map.get(new A(6999)));
		System.out.println(map.get(new A(87653)));
		/*
		 * LinkedHashMap可以记住key-value对的添加顺序
		 */
		System.out.println("\n------LinekedHashMap实现类------");
		LinkedHashMap scores = new LinkedHashMap();
		scores.put("语文", 77);
		scores.put("英语", 68);
		scores.put("数学", 79);
		// 调用forEach()方法遍历scores里的所有key-value对
		scores.forEach((key, value) -> System.out.println(key + "-->" + value));

		System.out.println("\n------使用Properties读写属性文件------");
		Properties props = new Properties();
		props.setProperty("username", "Yafeng");
		props.setProperty("password", "123456");
		// 将properties中的key-value对保存到a.ini文件中
		props.store(new FileOutputStream("a.ini"), "comment line");
		// 新建一个Properties对象
		Properties props2 = new Properties();
		// 向props2中添加属性
		props2.setProperty("gender", "man");
		// 将a.ini文件中的key-value对追加到props2中
		props2.load(new FileInputStream("a.ini"));
		System.out.println(props2);
		/*
		 * TreeMap中的key-value对是有序的，所以增加了访问 第一个，前一个，后一个，最后一个key-value对的方法
		 * 并提供了几个从TreeMap中截取子TreeMap的方法
		 */
		System.out.println("\n-------TreeMap------");
		TreeMap tm = new TreeMap();
		tm.put(new R(3), "操作系统");
		tm.put(new R(-5), "计算机系统结构");
		tm.put(new R(9), "数据结构");
		System.out.println(tm);
		// 返回该TreeMap的第一个Entry对象
		System.out.println(tm.firstEntry());
		// 返回该TreeMap的最后一个key值
		System.out.println(tm.lastKey());
		// 返回该TreeMap的比new R(2)大的最小key值
		System.out.println(tm.higherKey(new R(2)));
		// 返回该TreeMap的比new R(2)小的最大key值
		System.out.println(tm.lowerEntry(new R(2)));
		// 返回该TreeMap的子TreeMap
		System.out.println(tm.subMap(new R(-1), new R(19)));

		/*
		 * WeakHashMap中的每个key对象只持对实际对象的弱引用，因此 当垃圾回收了该key所对应的实际对象之后，WeakHashMap会自动
		 * 删除该key对应的key-value对。 PS:如果需要使用WeakHashMap的key来保留对象的弱引用，
		 * 则不要让该key所引用的对象具有任何引用，否则将失去WeakHashMap的意义
		 */
		System.out.println("---------WeakHashMap实现类----------");
		WeakHashMap whm = new WeakHashMap();
		// 向WeakHashMap中添加三个key-value对
		// 三个key都是匿名字符串对象
		whm.put(new String("语文"), new String("优秀"));
		whm.put(new String("数学"), new String("良好"));
		whm.put(new String("外语"), new String("优秀"));
		// 向WeakHashMap中添加一个key-value对
		// 该key是一个系统缓存的字符串对象
		whm.put("java", new String("优秀"));
		// 输出whm对象，将看到4个key-value对
		System.out.println(whm);
		// 通知系统立即进行垃圾回收
		System.gc();
		System.runFinalization();
		// 在通常情况下，将只会看到一个key-value对
		System.out.println(whm);
		/*
		 * IdentityHashMap实现类当且仅当两个key严格相等(key1==key2)时， IdentityHashMap才认为两个key相等
		 * 而普通的HashMap只要key1和key2通过equals()方法比较返回true，且 它们的hashcode()相等即可
		 */
		System.out.println("\n---------IdentityHashMap-----------");
		IdentityHashMap ihm = new IdentityHashMap();
		// 下面将会向IdentityHashMap对象中添加两个key-value对
		ihm.put(new String("语文"), 89);
		ihm.put(new String("数学"), 78);
		/*
		 * 下面两行代码只会向IdentityHashMa对象中添加一个key-value
		 * 下面key-value对的key都是字符串直接量，而且它们的字符串序列完全相同 Java使用常量池来管理字符串直接量，因此它们通过==比较返回true
		 * IdentityHashMap会认为它们是同一个key,故只有一次添加成功
		 */
		ihm.put("Java", 88);
		ihm.put("Java", 88);
		System.out.println(ihm);

		/*
		 * EnumMap类中的所有key都必须是单个枚举类的枚举值 创建EnumMap时必须显式或隐式指定它对应的枚举类
		 */
		System.out.println("\n---------EnumMap-----------");
		// 创建EnumMap对象，该EnumMap的所有key都是Season枚举类的枚举值
		EnumMap enumMap = new EnumMap(Season.class);
		// 向该EnumMap中添加两个key-value对后，
		// 这两个key-value对将会以Season枚举值的自然顺序排序
		enumMap.put(Season.WINTER, "漫天飞雪");
		enumMap.put(Season.FALL, "秋高气爽");
		System.out.println(enumMap);

	}
}
