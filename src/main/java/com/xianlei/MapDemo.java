package com.xianlei;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;

public class MapDemo {
	
	Map<String,Object> map = new HashMap();
	
	
	/**
	 * JDK1.8在线地址：https://blog.fondme.cn/apidoc/jdk-1.8-google/
	 * 本篇主要写Map方法测试。
	 * java.util.Interface Map<K,V>
	 */
	/*
	 * 默认方法
	 * default V	compute(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)
	尝试计算指定键的映射及其当前映射的值（如果没有当前映射， null ）。
	default V	computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction)
	如果指定的键尚未与值相关联（或映射到 null ），则尝试使用给定的映射函数计算其值，并将其输入到此映射中，除非 null 。
	default V	computeIfPresent(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)
	如果指定的密钥的值存在且非空，则尝试计算给定密钥及其当前映射值的新映射。
	default void	forEach(BiConsumer<? super K,? super V> action)
	对此映射中的每个条目执行给定的操作，直到所有条目都被处理或操作引发异常。
	default V	getOrDefault(Object key, V defaultValue)
	返回到指定键所映射的值，或 defaultValue如果此映射包含该键的映射。
	default V	merge(K key, V value, BiFunction<? super V,? super V,? extends V> remappingFunction)
	如果指定的键尚未与值相关联或与null相关联，则将其与给定的非空值相关联。
	default V	putIfAbsent(K key, V value)
	如果指定的键尚未与某个值相关联（或映射到 null ）将其与给定值相关联并返回 null ，否则返回当前值。
	default boolean	remove(Object key, Object value)
	仅当指定的密钥当前映射到指定的值时删除该条目。
	default V	replace(K key, V value)
	只有当目标映射到某个值时，才能替换指定键的条目。
	default boolean	replace(K key, V oldValue, V newValue)
	仅当当前映射到指定的值时，才能替换指定键的条目。
	default void	replaceAll(BiFunction<? super K,? super V,? extends V> function)
	将每个条目的值替换为对该条目调用给定函数的结果，直到所有条目都被处理或该函数抛出异常。
	 */
	public void size () {
		//If the map contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
		int size = map.size(); //  最大值Integer.MAX_VALUE
	}
	
	public boolean isEmpty () {
		return map.isEmpty(); // 是否为空
	}
	//boolean containsKey(Object key)
	public boolean containsKey(Object key) {
		//不允许null和optional类型 否则抛出NullPointerException，ClassCastException
		return map.containsKey(key);
	}
//	boolean containsValue(Object value)
	public boolean containsValue(Object value) {
		return map.containsValue(value);//如果包含一个或多个键返回true
	}
//  V get(Object key)
	public Object get (Object key) {
		return map.get(key);
	}
//	public void putAll (Map<? extends K,? extends V> m) {
//		map.putAll(m);
//	}
	//int size()
	//boolean isEmpty()
	//boolean containsKey(Object key)
	//boolean containsValue(Object value)
	//V get(Object key)
	//V put(K key, V value)
	//V remove(Object key)
	//void putAll(Map<? extends K,? extends V> m)
	//void clear()
	
	//Collection<V> values()
	//Set<Map.Entry<K,V>> entrySet()
	//boolean equals(Object o)   m1.entrySet().equals(m2.entrySet()) ，两个地图m1和m2代表相同的映射
	//int hashCode()
	//default V getOrDefault(Object key,V defaultValue)
	//default void forEach(BiConsumer<? super K,? super V> action)
	//default void replaceAll(BiFunction<? super K,? super V,? extends V> function)
	
	//BiConsumer消费者
//	public void forEach(BiConsumer<? super K,? super V> action) {
//		 for (Map.Entry<K, V> entry : map.entrySet())
//		     action.accept(entry.getKey(), entry.getValue());
//	}
	//BiFunction<T, U, R> 替换为R
//	public void replaceAll(BiFunction<? super K,? super V,? extends V> function) {
//		 for (Map.Entry<K, V> entry : map.entrySet())
//		     entry.setValue(function.apply(entry.getKey(), entry.getValue()));
//	}
	//default V putIfAbsent(K key,V value)
	//V v = map.get(key); 
	//if (v == null)
	//	  v = map.put(key, value); 
	//return v;  
	Hashtable hashtable = new Hashtable();
	TreeMap treeMap = new TreeMap();
	@Test
	public void putIfAbsent() { //如果值为NULL则put
		map.put("aa", "vaa");
		map.put("cc", null);
		Object value = map.putIfAbsent("aa", "baa");
		Object value2 = map.putIfAbsent("bb", "bbb");
		Object value3 = map.putIfAbsent("cc", "CCC"); 
		System.out.println(value);
		System.out.println(value2);
		System.out.println(value3);//hashMap允许Null值
		//hashtable不允许Null值
	}
	
	//default boolean remove(Object key,Object value)
	/*
	 *  if (map.containsKey(key) && Objects.equals(map.get(key), value)) {
		     map.remove(key);
		     return true;
		 } else
		     return false;
	 */
	//default boolean replace(K key,V oldValue,V newValue)
	/*
	 *  if (map.containsKey(key) && Objects.equals(map.get(key), value)) {
		     map.put(key, newValue);
		     return true;
		} else
		     return false;
	 */
	//default V replace(K key,V value)
	/*
	 *  if (map.containsKey(key)) {
		     return map.put(key, value);
		 } else
		     return null;
	 */
	//default V computeIfAbsent(K key,Function<? super K,? extends V> mappingFunction)
	/*
	 *  if (map.get(key) == null) {
		     V newValue = mappingFunction.apply(key);
		     if (newValue != null)
		         map.put(key, newValue);
		 }
	 */
	public void computeIfAbsent() {
		String key = "aa";
		 Function<? super String,? extends Object> mappingFunction = 
				 k -> k.toLowerCase();
		map.computeIfAbsent(key,  k -> k.toLowerCase());
	}
	//default V computeIfPresent(K key,
    //BiFunction<? super K,? super V,? extends V> remappingFunction)
	/*
	 *  if (map.get(key) != null) {
		     V oldValue = map.get(key);
		     V newValue = remappingFunction.apply(key, oldValue);
		     if (newValue != null)
		         map.put(key, newValue);
		     else
		         map.remove(key);
		 }
	 */
	
	//default V compute(K key,
//    BiFunction<? super K,? super V,? extends V> remappingFunction)
	/*
	 *  V oldValue = map.get(key);
		 V newValue = remappingFunction.apply(key, oldValue);
		 if (oldValue != null ) {
		    if (newValue != null)
		       map.put(key, newValue);
		    else
		       map.remove(key);
		 } else {
		    if (newValue != null)
		       map.put(key, newValue);
		    else
		       return null;
		 }
	 */
	//default V merge(K key,V value,
//    BiFunction<? super V,? super V,? extends V> remappingFunction)
	/*
	 V oldValue = map.get(key);
	 V newValue = (oldValue == null) ? value :
	              remappingFunction.apply(oldValue, value);
	 if (newValue == null)
	     map.remove(key);
	 else
	     map.put(key, newValue);
	 */
	public void merge (String key, String msg) {
		//merge(key, msg, String::concat) 合并，将指定的值和msg利用BiFunction函数合并成一个新的值。
		//compute
//		 map.merge(key, msg, String::concat);
//		 map.compute(key, (k, v) -> (v == null) ? msg : v.concat(msg)) 
//		 map.computeIfAbsent(key, k -> new Value(f(k)));  
//		   或者实现一个多值地图， Map<K,Collection<V>> ，每个键支持多个值：
//		 map.computeIfAbsent(key, k -> new HashSet<V>()).add(v); 
	}
	public void compute() {
	/*	compute() 方法
		通常情况下，我们从map中获取一个值，对它进行计算，然后再将它存放到map中。
		如果有并发存在，这个过程很复杂并且容易出错。Java8中，我们可以给新的compute()，
		computeIfAbsent()或computeIfPresent()方法传递一个BiFunction，
		并由Map实现来处理替换值的语义。*/
		map.put("A", "1");
		//compute a new value  for the existing key
		System.out.println(map.compute("A", (k,v) -> v == null ? 42 : 42 + Integer.parseInt(v.toString()) ));
		// add a new (key,value) pair
		System.out.println(map.compute("X", (k,v) -> v == null ? 42 : 42 + Integer.parseInt(v.toString()) ));
		//这对于ConcurrentHashMap是真的有用
		
		//foreach
		map.forEach((k, v) ->System.out.println(k + "=" + v));
		String key = "";
		String msg = "";
//		map.merge(key, msg, String::concat);
		String value = (String) map.get(key);
		if (value == null)
			map.put(key, msg);
		else
			map.put(key, value.concat(msg));
		
		//hashMap允许null值
		map.put("X", null);
		System.out.println(map.merge("X", null, (v1, v2) -> null));
		System.out.println(map);

		map.put("X", 1);
		System.out.println(map.merge("X", 1, (v1, v2) -> null));
		System.out.println(map);
		
		//表明一个值应该被删除，是应该从这样一个函数返回空值。但map被允许包含空值，这永远不能使用merge()将其插入到map
		map.getOrDefault(key, "123124");
		
	}
}
