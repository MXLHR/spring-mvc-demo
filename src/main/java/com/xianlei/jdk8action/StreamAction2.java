package com.xianlei.jdk8action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import static java.util.Comparator.comparingInt;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transaction;

import org.junit.Test;

import com.xianlei.jdk8action.domain.Dish;
import static java.util.stream.Collectors.*;

/**
 * @author stron
 *
 */
public class StreamAction2 {
	
	final static List<Dish> menu = Arrays.asList(
			new Dish("pork", false, 800, Dish.Type.MEAT),  //猪肉
			new Dish("beef", false, 700, Dish.Type.MEAT), //牛肉
			new Dish("chicken", false, 400, Dish.Type.MEAT), //鸡肉
			new Dish("french fries", true, 530, Dish.Type.OTHER), //炸薯条
			new Dish("rice", true, 350, Dish.Type.OTHER), //白米饭
			new Dish("season fruit", true, 120, Dish.Type.OTHER), //季节水果
			new Dish("pizza", true, 550, Dish.Type.OTHER), //比萨
			new Dish("prawns", false, 300, Dish.Type.FISH), //吓
			new Dish("salmon", false, 450, Dish.Type.FISH) //三文鱼
			);
	
	/**
	 * 第6章 用流来收集数据
	 * 
	 * 1)用Collectors类创建和使用收集器
	 * 2)将数据归约为一个值
	 * 3)汇总：归约的特殊情况
	 * 4)数据分组和分区
	 * 5)开发自己的自定义收集器
	 * 
	 */
	/*
	 * collect是终端操作,主要是用来把Stream中的所有元素结合成一个List或其他类型
	 * reduce一样可以接收各种做法为参数，归约
	 * Collection\
	 * Collector
	 * Collectors
	 * 
	 * 
	 */
	List<Transaction> transactions = new ArrayList<Transaction>();
	@Test
	public void test1() {
		Map<Currency,List<Transaction>> trans = new HashMap<>();
//		trans = transactions.stream().collect(Collectors.groupingBy(Transaction:getCurrency()))
		
		long howManyDishes = menu.stream().count();
		
		/***重点  Collectors.maxBy(Comparator<T>)*/
		//查找流中的最大值和最小值 Collectors.maxBy()  minBy()
		Comparator<Dish> dc = Comparator.comparingInt(Dish::getCalories);
		Optional<Dish> most = menu.stream().collect(Collectors.maxBy(dc));
		/***Comparator Collectors.maxBy(Comparator<T>)  */
		
		//汇总， 求平均数 Collectors.averagingInt
		double avgCalories = menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
		
		Map<String,String> map = new HashMap<String,String>();
		
		new HashMap<String,String>(){
			{
				put("key", "value");
				put("key", "value");
			}
		};
		
		 List<String> list = new ArrayList<String>(){{  
		     add("string1");  
		     add("string2");  
		     //some other add() code......  
		     add("stringN");  
		 }};  
		 ArrayList<String> list2 = new ArrayList(Arrays.asList("Tom", "Jerry", "Mike"));  
		/*
		 * 到目前为止，你已经看到了如何使用收集器来给流中的元素计数，根据属性找到最大值和最小值，以及计算总和 和平均值。
		 * 不过有时候，你可能想要得到两个或更多这样的结果，而且你希望一次操作就可以完成
		 * 
		 * 那么久可以使用summarizingInt公车返回的收集器了。例如
		 * 一次sum操作 数出菜单中元素的个数，并得到菜肴热量综合、平均值、最大值、最小智
		 * 
		 */
		IntSummaryStatistics menuStatistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
		//这个收集器会吧所有这些信息收集到IntSummaryStatistics类里，它提供了方便的取值getter方法来返回结果。
		System.out.println(menuStatistics);
		long count = menuStatistics.getCount();
		double ave = menuStatistics.getAverage();
		int max = menuStatistics.getMax();
		int min = menuStatistics.getMin();
		long sum = menuStatistics.getSum();
		
		/*
		 * 连接字符串
		 * joining工厂方法返回的收集器会把流中每一个对象应用toString方法得到的所有字符串连接成一个字符串。
		 * 这意味着你把菜单中所有菜肴的名称拼接起来
		 * 
		 */
		String shortMenu = menu.stream().map(Dish::getName).collect(Collectors.joining());
		System.out.println("joining在内部使用了StringBuilder把字符串追加起来：{}"+shortMenu);
		String shortMenu2 = menu.stream().map(Dish::getName).collect(Collectors.joining(", "));
		System.out.println("Collectors.joining(\", \")："+shortMenu2);
		
		
		//----------------------------------------------------------
		
		int totalCalories = menu.stream().collect(Collectors
				.summingInt(Dish::getCalories));
		/*
		 * Collectors.reducing 工厂是所有这些特殊情况的一般化
		 * 
		 */
		int totalCalories2 = menu.stream().collect(Collectors
				.reducing(0, Dish::getCalories, (i,j)->i+j));
		//第三个参数是一个BinaryOperator，将连个项目累积成一个同类型的值
		int totalCalories3 = menu.stream().collect(Collectors
				.reducing(0, Dish::getCalories, Integer::sum)); //初始值，转换函数，累积函数
		//另一种方法，不适用收集器
		int totalCalories4 = menu.stream().map(Dish::getCalories)
				.reduce(Integer::sum).get(); 	//返回的是Optional<Integer>
		//第5中方法，最大热量是多少、 总和、最小、平均  都可以
		Optional<Integer> totalCalories5 = menu.stream()
				.map(Dish::getCalories)
				.collect(Collectors.reducing(Integer::max));
		//IntStream的使用
		int totalCalories6 = menu.stream().mapToInt(Dish::getCalories).sum();
		
		/**
		 * 根据情况选择最佳方案。
		 * 
		 * 要计算菜单的总热量，我们更倾向于最后一个解决方案，使用IntStream,因为它最简明，易读,性能最好（避免自动拆箱）。
		 * 
		 * 
		 */
		
		
		
		//----------------------------------------------------------
		
		//最大热量的菜 reducing    (d1,d2)->d1.getCalories() > d2.getCalories() ? d1 : d2)
		Optional<Dish> mostCaloriesDish = menu.stream().collect(Collectors
				.reducing((d1,d2)->d1.getCalories() > d2.getCalories() ? d1 : d2));
		//等价于
		
		//最大热量的菜 Comparator.comparingInt(Dish::getCalories) 是个比较器
		Optional<Dish> calorMostDish = menu.stream().collect(Collectors
				.maxBy(Comparator.comparingInt(Dish::getCalories)));
		
		System.out.println("Collectors.reducing:{},{} ==》"+totalCalories2+","+totalCalories3);
				
		//生成以个Map,键是calories, 值是相同calories的列表
		Map<Integer, List<Dish>> calorieDishlist = menu.stream().collect(Collectors
				.groupingBy(Dish::getCalories));
		
		
	}
	
	public enum Level {DIET,NORMAL,FAT}
	
	/**
	 * 分组
	 */
	@Test
	public void test2() {
		//把热量小鱼400c 的化为低热量DIETE, 400-700 划为普通NORMAL， 大于700的化为FAT
		Map<Level, List<Dish>> levelGroup = menu.stream().collect(Collectors.groupingBy(
				d -> d.getCalories() <= 400 ? Level.DIET : d.getCalories() <= 700 ? Level.NORMAL : Level.FAT));
		//多级分组
		//先根据Dish.Type分组，再根据热量分组
		Map<Dish.Type,Map<Level,List<Dish>>> levelGroup2 = menu.stream().collect(Collectors
				.groupingBy(Dish::getType, Collectors
						.groupingBy(d -> d.getCalories() <= 400 ? Level.DIET : d.getCalories() <= 700 ? Level.NORMAL : Level.FAT)));
		System.out.println("一级分组：{}==》"+levelGroup.toString());
		System.out.println("二级分组：{}==》"+levelGroup2.toString());
		
		//进一步扩展，数一数每类菜有多少
		Map<Dish.Type,Long> typesCount = menu.stream().collect(Collectors
				.groupingBy(Dish::getType, Collectors
						.counting()));
		
		System.out.println("二级分组(统计)：{}==>"+typesCount.toString());
		
		//再进一步， 最高热量分组 maxBy
		Map<Dish.Type,Optional<Dish>> mostc = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors
				.maxBy(Comparator.comparingInt(Dish::getCalories))));
		System.out.println("二级分组(maxBy),该类型的最高热量：{}==>"+mostc.toString());
		//分组操作的Map结果中的每个值上包装的Otional没什么用。所以你想把它去掉。
		/*
		 * 如果菜单中没有某一类型的Dish , 这个类型就不会对应一个Optioal。 empty()值，而且根本不会出现在Map的键中
		 * groupingBy收集器只有在应用分组条件后，第一次在流中找到某个键对应的元素时才会把键加入分组Map中。
		 * 这意味着Optional包装器在这里不是很有用，因为它不会仅仅因为它是归约收集器的返回类型二表达一个最终可能不存在却意外的值。
		 * 
		 * 把收集器返回的结果转换为另一种类型，可以使用Collectors.collectingAndThen
		 * 
		 */
		Map<Dish.Type,Dish> mostc2 = menu.stream()
				.collect(groupingBy(Dish::getType, 
				collectingAndThen(maxBy(comparingInt(Dish::getCalories)),//要转换的收集器（包装后的收集器）
						Optional::get//转换成函数
						)));
		
		/*
		 * 用summinInt 对每组的热量进行求和
		 */
		Map<Dish.Type,Integer> totalCaloriesBytype =
				menu.stream().collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
		/*
		 * 这样就可以接收特定类型元素的收集器适应不同类型的对象。
		 * 对于每种类型的Dish,菜单中都有哪些caloricLevel。 我们可以把groupingBy和Mapping收集器结合
		 * Map<City, Set<String>> lastNamesByCity
         = people.stream().collect(groupingBy(Person::getCity,
                                              mapping(Person::getLastName, toSet())));
		 */
		Map<Dish.Type,Set<Level>> cl = menu.stream().collect(
				groupingBy(Dish::getType,mapping(d -> d.getCalories() <= 400 
				? Level.DIET : d.getCalories() <= 700 ? Level.NORMAL : Level.FAT,toSet())));
		Map<Dish.Type,Set<Level>> c2 = menu.stream().collect(
				groupingBy(Dish::getType,mapping(d -> d.getCalories() <= 400 
				? Level.DIET : d.getCalories() <= 700 ? Level.NORMAL : Level.FAT,toCollection(HashSet::new))));
//				Map<Dish.Type,Set<Level>> c2 = menu.stream().collect(
//						groupingBy,(Dish::getType
//								,(d -> d.getCalories() <= 400 ? Level.DIET : d.getCalories() <= 700 ? Level.NORMAL : Level.FAT)
//								);
		 /**通过toCollection(HashSet::new)  可以指定map的类型 */
		
		/*
		 * 分区
		 * 分区是分组的特殊情况，有一个谓词作为分类函数，返回一个布尔值的函数。
		 * 返回的map类型是  Map<Boolean,List<Dish>> map , true 是一组， false 是一组
		 */
		/** 函数 partitioningBy */
		Map<Boolean,List<Dish>> partMenu = menu.stream().collect(partitioningBy(Dish::isVegetarian));
		//获取
		List<Dish> veDishes = partMenu.get(true);
		//等于 filter筛选，把结果收集到另外一个list
		List<Dish> vdishes = menu.stream().filter(Dish::isVegetarian).collect(toList());
		
		/*
		 * 分区的优势
		 * 分区的好处在于保留了分区函数返回true或false的两套流元素列表。
		 * 要得到非素食Dish的List， 可以使用两个筛选操作来访问partMenuMap 中false键的值。
		 * 
		 */
		//二级收集
		Map<Boolean,Map<Dish.Type,List<Dish>>> partitionMenuMap2 = menu.stream().collect(
				partitioningBy(Dish::isVegetarian,groupingBy(Dish::getType))
				);
		/*
		 * {
		 * 	false={MEAT=[pork, beef, chicken], FISH=[prawns, salmon]},
		 *  true={OTHER=[french fries, rice, season fruit, pizza]}
		 * }
		 */
		
		System.out.println("分区的优势："+partitionMenuMap2.toString());
		/*
		 * 这样分区产生的素食流和  非素食流  ，分别按照类型 对菜肴进行分组，得到了一个二级map，
		 * 可以重用签名的代码来找到素食和非素食中热量最高的菜。
		 * 与6.3.1节 类似。  
		 * partitioningBy(predicate,  Collector<? super Dish, Object, Dish> downstream)
		 * 
		 * maxBy(Comparator<? super Dish> comparator)
		 */
		/**关键字 partitioningBy,collectingAndThen,maxBy,comparingInt,Optional */
		Map<Boolean,Dish> mostMap =
				menu.stream().collect(partitioningBy(Dish::isVegetarian,collectingAndThen(
						maxBy(comparingInt(Dish::getCalories)), Optional::get)));
		
		/*
		 * 我们已经看到了Collector接口中实现的许多收集器，如toList，groupingBy.
		 * 这表示你可以为Collector接口提供自己的实现。从而自由地创建自定义归约操作。
		 * 
		 * public interface Collector<T,A,R>{
		 * 	   Supplier<A> supplier();  建立新的容器
		 *     BiConsumer<A,T> accumulator();  将元素添加到结果容器
		 *     Function<A,R>  finisher();  对结果容器应用最终转换
		 *     BinaryOperator<A> combiner();  合并两个结果容器  例如list.addAll(list2)
		 *     Set<Characteristice> characteristice(); 
		 * }
		 */
		
		/**
		 *  开发你自己收集器来获得更好的性能！！
		 */
	}
	
	/**
	 * 第7章 并行数据处理与性能
	 * 
	 * 1.用并行流并行处理数据
	 * 2.并行流的性能分析
	 * 3.分之/合并框架
	 * 4.分割流 Spliterator
	 * 
	 */
	/*
	 * 在前面三章中，Stream接口而已让你以声明式方式处理数据集。
	 * 
	 * 了解并行流如何工作很重要
	 * 
	 * 给定数字n, 返回从1到n的总和
	 */
	@Test
	public void test3() {
		System.out.println(sequentialSum(5));
	}
	public static long sequentialSum(long n) {
		return Stream.iterate(1L, i -> i+1).limit(n).reduce(0L, Long::sum);
	}
	public static long parallelSum(long n) {
		return Stream.iterate(1L, i -> i+1).limit(n).parallel()//转化为并行流
				.reduce(0L, Long::sum);
		/*
		 * 调用parallel()方法后的所有操作都是并行执行。
		 * filter..
		 * sequetial
		 * map
		 * reduce   
		 * parallel //最后一次调用会影响整个流水线表示流水线会并行执行。因为是最后调用的它。
		 */
	}
	
	
	
	
	
	

}
