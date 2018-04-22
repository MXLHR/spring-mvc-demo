package com.xianlei.jdk8action;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import com.xianlei.jdk8action.domain.Dish;

public class StreamAction {

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
	 * Stream API 的使用
	 * 
	 * 引入流
	 * 如何创建和操作数字流，比如生成一个偶数流，或者勾股数流。
	 * 如何从不同的源（如文件）创建流。
	 * 如何生成一个具有无穷多元素的流。
	 * 
	 * 使用硫
	 * 流可以用来表达复杂数据处理查询。
	 * 如筛选、切片、查找、匹配、映射、归约。
	 * 
	 * 遗漏的
	 * 3.5 类型检查、类型腿短以及限制
	 * 5.5 付出实践：交易员和交易
	 * 5.6.3 数值流的应用：勾股数
	 * 测验5.4 斐波拉契元组序列
	 * 
	 * 
	 * 用流收集数据
	 * Collectors类创建和使用收集器
	 * 将数据流归约为一个值
	 * 汇总：归约的特殊情况
	 * 数据分组和分区
	 * 开发自己定义的自定义收集器
	 */
	
	/*
	 * 流简介
	 * java8中的集合支持一个新的stream方法，它会返回一个流（java.util.stream.Stream）
	 * 		其他方法获得流？利用数值范围或从I/O资源生成流元素。
	 * 
	 * 流的定义：从支持数据处理操作的源生成的元素序列
	 * 元素序列 --流提供了一个借口可以访问元素类型的一组有序值。
	 * 	 		集合是数据结构，它的主要目的是以特定的 时间/空间复杂度 存储和访问元素
	 * 源 -- 流会使用一个提供数据的源。如集合、数组、I/O 等。
	 * 		注意：从有序集合生成流时会保留原有的顺序。 有列表生成的流与其元素顺序一致。
	 * 数据处理操作 -- 如filter\map\reduce\find\match\sor  流操作可以顺序执行也可以并行。 类似于数据库的操作，以及函数式编程语言中的常用操作。
	 * 流水线 -- 很多流操作本身会返回一个流，这样多个操作就可以链接起来，形成一个大的流水线。 
	 * 			流水线的操作可以看作是对数据源进行数据库式的查询。
	 * 内部迭代-- 流的迭代是在内部进行的，集合是显示迭代。
	 * 
	 */
	
//	@Test
	public void test1() {
		//three High Caloric Dish names            predicate 谓词 用于返回一个boolean值
		
		// Stream<T> filter(Predicate<? super T> predicate) 
		Predicate<? super Dish> predicate = d -> d.getCalories() > 300;
		
		//<R> Stream<R> map(Function<? super T,? extends R> mapper)
		
		Function<? super Dish,? extends String> mapper = Dish :: getName;
		
		List<String> names = menu.stream() //从menu中获得流（菜肴列表）     
				.filter(predicate)   //首先选出高热量的菜肴 等价于 d -> d.getCalories() > 300
				.map(Dish :: getName)  //获得菜名  等价于 (e)->e.getName()
				.limit(3) //限制3条
				.collect(Collectors.toList()); //将结果存在另一个list中。
		
		System.out.println(names);
		/*
		 * 总结：
		 *  数据源（菜肴列表）给流提供了一个元素序列。
		 *  然后对流应用了一系列数据处理操作：filter、map、limit、collect
		 *  除了collect之外，所有这些操作都会返回另一个流，这样他们就可以接成一条流水线。 于是就可以看作对源的一个查询。
		 *  最后，collect操作开始处理流水线，并返回结果。 在调用collect之前，没有任何结果产生。
		 *  
		 *  collect将流转换成其他形式，像变魔术。第6章会将collect的工作原理。
		 *  
		 *  查找热量最高的三道菜的菜名。
		 *  菜单（数据源）-> 筛选(filter) -> 提取（map) -> 截断(limit)
		 * 
		 */
		
	}
	
	/*
	 * 4.3 流与集合
	 * Java现有的集合概念和新的流的概念都提供了接口，来配合代表元素型有序值的数据接口。
	 * 有序，的
	 * 
	 * 集合与流的差异就是在什么时候进行计算
	 * 集合是一个内存中的数据结构，它包含数据结果中目前所有的值
	 * 
	 * 流u能添加和删除元素，其元素则是按需计算的。
	 * 流只能遍历一次
	 */
	List<String> title = Arrays.asList("JAVA8","IN","ACTION");
	@Test
	public void test2() {
		Stream<String> s = title.stream();
		s.forEach(System.out::println);
//		s.forEach(System.out::println);//只能遍历一次，这是会报错的
	}
	/*** 注意：forEach是一个繁华void的终端操作 */
	@Test
	public void test3() {
		//内部迭代
		menu.stream().forEach(m->System.out.println(m.getName()));
	}
	/** count是一个繁华void的终端操作 */
	@Test
	public void test4() {
		Long count = menu.stream().filter(d -> d.getCalories() > 300).distinct().limit(3).count();
		System.out.println("count终端操作:{}"+count);
	}
	/**
	 * 小结：
	 * 		    (T,R)->R  Function<T,R>
	 * 中间操作
	 * filter   T->boolean  Predicate<T>
	 * map      T->R        Function<T,R>
	 * limit   
	 * sorted   (T,T)->int  Comparator<T>
	 * distinct
	 * 终端操作
	 * forEach   消费流中的每个元素并对其应用Lambda，返回void
	 * count     返回流中元素的个数
	 * collect   将流归约成一个集合
	 */
	
	/**
	 * 第5章 使用流
	 * 主要内容
	 * 筛选、切片和匹配
	 * 查找、匹配和归约
	 * 使用数值范围等数值流
	 * 从多个源创建流
	 * 无限流
	 */
	@Test
	public void test_1() {
		//内部迭代boolean  筛选是素的菜肴
		List<Dish> dishs = menu.stream().filter(Dish::isVegetarian).collect(toList());
		//遍历
		dishs.stream().forEach(d->System.out.println(d.toString()));
		//统计
		Long counts =  dishs.stream().count();
		System.out.println("counts:{}"+counts);
	}
	/** 筛选各异的元素  distinct 去重 */
	@Test
	public void test_2() {
		List<Integer> numbers = Arrays.asList(1,2,3,4,5,2,3,1,2);
		numbers.stream().filter(i-> i%2 == 0)
		.distinct()
		.forEach(System.out::println);
	}
	/** 
	 * limit(n)截短流  返回一个不超过给定长度的流。
	 * 如果流时有序的，则返回前n个元素。
	 * 如果源是Set无序的，则结果无序
	 */
	/**
	 * skip(n) 跳过元素
	 * 如果流中元素不足n个，则返回一个空流
	 * 
	 */
	/*
	 * 映射
	 * 比如在sql里从表中选择一列
	 * Stream API 也通过 map 和 flatMap 方法提供了类似的工具。
	 * 
	 */
	@Test
	public void test_3() {
		//给定一个单词列表，你要返回另一个列表，显示每个单词中有几个字母。
		
		Function<? super String,? extends Integer> mapper  = s -> s.length();
		List<Integer> nums = title.stream().map(mapper).collect(toList());
		List<Integer> nums2 = title.stream().map(String::length).collect(toList());
		nums.stream().forEach(System.out::println);
	}
	/**
	 * 流的扁平化，
	 * 你已经看到如何用map方法返回列表中每个单词的长度了。扩展一下，对于一张单词表，如何返回一张列表，
	 * 列出里面各不相同的字符呢？
	 * 例如{"hello","world"} 返回 {h,e,l,o,w,r,d}
	 * 
	 * words.stream().map(w->w.split(""))
	 * 			     .distinct()
	 * 				 .collect(toList());
	 * 问题： 传递map方法的Lambdawi每个单词返回一了个String[]数组，因此，map返回的流实际上是Stream<String[]>类型的。
	 * 然后你真正需要的是Stream<String>的字符流。  该怎么办呢？
	 * 
	 * 使用flatMap可以解决这个问题！
	 */
	public void test_4 () {
		//首先需要一个字符流 Arrays.stream()
		String[] array = {"goodbye","world"};
		Stream<String> wordStream = Arrays.stream(array);
		title.stream().map(s->s.split(""))
		.map(Arrays::stream)//错误：：先把单词转换成了一个字符数组，然后把每个数组变成了一个独立的流。   所以得到的是一个流的列表
		.distinct().collect(toList());
		
		/*
		 * 解决方案，用flatMap
		 */
		List<String> result = title.stream().map(w->w.split(""))
				.flatMap(Arrays::stream)/** 生成了多个流，扁平化为一个流 */
				.distinct().collect(toList());
		
	}
	
	/**
	 * 流的查找和匹配
	 * 常见操作：
	 * allMatch、anyMatch、noneMatch、findFirst、findAny
	 * 
	 * anyMatch  boolean 检查谓词是否至少匹配一个元素  -> menu.stream().anyMatch(Dish::isVegetarian)
	 * allMatch  boolean 是否所有元素都能匹配给定的谓词 ->  menu.stream().allMatch(d->d.getCalories()>1000)
	 * noneMatch boolean 是否没有任何元素匹配给定的谓词 ->  menu.stream().noneMatch(d->d.getCalories()>=1000)
	 * 
	 * findAny  T  返回当前流中的任意元素  常与filter结合-> menu.stream().filter(Dish::isVegetarian).findAny()
	 * 
	 * Optional简介
	 * Optional<T> 是一个容器类，代表一个值存在或者不存在。 findAny可能什么元素也没有找到。 
	 * 引入Optional<T>，就不用返回容易出问题的null了。   -》 避免Null检查相关的bug
	 * Optional里面可以迫使你显示地检查是否尽或处理值不存在的方法
	 * idPresent()  Optinal包含值的是否返回true,否则返回false
	 * ifPresent(Consumer<T> block) 值存在的时候执行给定的代码块
	 * T get() 值存在时返回值，否则抛出NoSuchElement 异常
	 * T orElse(T other) 值存在时返回值，否则返回一个默认值。 
	 * 
	 * 只要找到一个元素，就返回结果。   与limit一样，不用处理所有的元素。   
	 * 
	 */
	  public void test_5() {
		  menu.stream().filter(Dish::isVegetarian).findAny() //返回一个Optional<Dish>
		  			   .ifPresent(d -> System.out.println(d.getName())); //如果值存在就执行代码，否则什么都不做
		  
	  }
	  //查找地一个元素 findFirst
	  
	  /**
	   * 归约 （将流归约成一个值）
	   * 
	   * 目前为止，你见过的终端操作是： boolean、Optional、Collect将流中的所有元素组合成一个list 等
	   * 本节将如何把一个流中的元素组合起来，使用reduce操作来表达更复杂的查询
	   * 比如，计算菜单中的总卡路里
	   * 或者   菜单中卡路里最高的菜是哪一个
	   * 此类查询需要把流中所有元素反复结合起来，得到一个值。  这样的查询可以被归类为 归约操作。
	   * 
	   */
	  //元素求和
	  List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9);
	  public void test_6 () {
		  int sum = 0;
		  for (int x : numbers) {
			  sum += x;
		  }
		  //之前的归约，通过反复使用加法，把一个数字列表归约成了一个数字
		  //reduce 
		  int identity = 0; //总和变量的初始值
		  BinaryOperator<Integer> bo = (a,b)->a+b; // extends BiFunction   --> R apply(T t, U u)
		  int sum2 = numbers.stream().reduce(identity, bo);
		  int sum3 = numbers.stream().reduce(0, (a,b)->a+b);  
		  //reduce接受两个参数，一个是初始值identity, 一个是Lambda
		  //代码优化
		  int sum4 = numbers.stream().reduce(0, Integer::sum);
		  
		  //如果Numbers可能为空，需要用到Optional<Integer> 返回值
		  Optional<Integer> sum5 = numbers.stream().reduce(Integer::sum);
		  
	  }
	  
	  /*
	   * 最大值和最小值
	   * 
	   * 原来，值要用归约就可以计算最大值和最小值了
	   * 如何利用reduce来计算流中的最大值和最小值？
	   * 
	   * 
	   */
	public void test_7() {
		Optional<Integer> max = numbers.stream().reduce(Integer::max);// 计算最大值
		Optional<Integer> min = numbers.stream().reduce(Integer::min);// 计算最小值
		Optional<Integer> min2 = numbers.stream().reduce((x, y) -> x <= y ? x : y);// 计算最小值
	}
	
	/**实际操作：交易员和交易的八个查询 */
	
	/*
	 * 数值流
	 * 
	 * 原始类型流特化流接口
	 * IntStream、DoubleStream、LongStream
	 * 分别将流中的元素特化为int\long\double,从而避免了暗含的装箱成本
	 * 每个流都有 Sum,max 等常用数归约方法
	 * 
	 * 
	 */
	public void test_8() {
		//之前的求和
		int calories = menu.stream()
				.map(Dish::getCalories)//返回的是Stream<String>
				.reduce(0, Integer::sum);
		//改进
		int calories2 = menu.stream()
				.mapToInt(Dish::getCalories)//返回的是IntStream,  
				.sum();
		
		//转换回对象流 
		IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
		//使用box方法
		Stream<Integer> stream = intStream.boxed(); //将数值流转换为一般流Stream;
		
		//默认值OptionalInt
		//如何计算IntStream中的最大元素？  Optional可以表示值存在或者不存在的。Optional可以用Integer\String等参考类型来参数化。
		//对于三种原始流特化，分别有一个OptionalInt\OptionalDouble\OptionalLong
		OptionalInt maxCalories = menu.stream().mapToInt(Dish::getCalories).max();
		//如果没有最大值可以指定默认值
		int max = maxCalories.orElse(1);
		
		//数值范围。
		//IntStream \ LongStream  的静态方法 range 、rangeClosed  这两个方法
		IntStream evenNumbers = IntStream.rangeClosed(1, 100)//表示数值范围【1,100】
				.filter(n -> n%2 ==0); //一个1到100的偶数
		
	    System.out.println(evenNumbers.count());//终端操作
	    //IntStream.range(1,100)  则结果将会是49， 因为range不包含结束值。
	    
	    //勾股数
	    //....
	}
	
	/**
	 * 构建流、
	 * 现在，流对于表达数据处理查询 ， 你已经能够使用Stream方法从集合生成流了。
	 * 此外，我们还介绍了如何根据数值范围创建流。    但创建流的方式还有许多，  如值序列，数组，文件 等
	 * 
	 */
	public void test_9() {
		//由值创建流  Stream.of
		Stream<String> stream = Stream.of("JAVA8","REDIS","NEO4J");
		stream.map(String::toLowerCase).forEach(System.out::println);
		
		//empty 的流
		Stream<String> emStream = Stream.empty();
		
		//由数组创建流
		int[] numbers = {1,2,3,4,5,11,22,33,44};
		
		int sum = Arrays.stream(numbers)//IntStream
				.sum();
		//由文件生成流
		
		/*
		 * JAVA中用于处理文件等I/O操作的NIO API 已更新，以便于利用Stream API
		 * java.nio.file.Files中有很多静态方法都会返回一个流。  如 Files.lines 返回指定文件中的各行构成的字符串流
		 * 
		 */
		long uniqueWords = 0 ;
		try(//其中的每个元素都是一行， 
				Stream<String> lines
				= Files.lines(Paths.get("data.txt"), Charset.defaultCharset())){
			/** 注意如何使用floadMap生成一个扁平的单词流，而不是给每一行生成以个单词流 */
			uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))//生成单词流
					.distinct().count();
		}catch(IOException e) {
			//打开文件异常
		}
		
		//函数生成流，创建无限流。
		//两个静态方法：Stream.iterate 和 Stream.generate
		//这两个操作可以创建无限流
		Stream.iterate(0, n -> n+2)//(初始值，UnaryOperator<t>)
		.limit(10).forEach(System.out::println);
		
		
		
		
		
		
		
		
		
	}
	
	
	
}
