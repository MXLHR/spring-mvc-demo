package com.xianlei.jdk8action;

import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;

public class StreamBbd {
	
	/*
	 * 
	//Returns a Collector that accumulates elements into a Map whose keys and values are the result of applying the provided mapping functions to the input elements.
	 */
	interface Collectors {
		static <T,K,U> Collector<T,?,Map<K,U>>	toMap(
				Function<? super T,? extends K> keyMapper,
				Function<? super T,? extends U> valueMapper,
				BinaryOperator<U> mergeFunction) {
			
			
			return null;
		}
		/*
		 * T = Map<String, Object> , k=String , U=Integer
		 * <Map<String, Object>, String, Integer> 
		 * Collector<Map<String, Object>, ?, Map<String, Integer>> 
		 * java.util.stream.Collectors.toMap(
		 * Function<? super Map<String, Object>, ? extends String> keyMapper,
		 * Function<? super Map<String, Object>, ? extends Integer> valueMapper,
		 * BinaryOperator<Integer> mergeFunction)
		 * 
		 */
	}
	
	
	public void test(){
        // 统计各个级别的人员数 在这之前添加的重点人或关注对象
        List<Map<String, Object>> keyPeople = null;
        // 转换为Ai，Bi的映射
        Map<String, Integer> keyPeopleMap = keyPeople.parallelStream().collect(Collectors.toMap(
                (entry) -> entry.get("TYPE").toString(),
                (entry) -> Integer.parseInt(entry.get("TOTAL").toString()),
                (oldValue, newValue) -> newValue
        ));
		
	}
}
