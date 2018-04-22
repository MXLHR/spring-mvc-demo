package com.xianlei.jdk8action;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

class Apple {
	private String color;
	private Integer weight;
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public Apple() {
	}
	public Apple(String color, int weight) {
		this.color = color;
		this.weight = weight;
	}
	
	
}

interface ApplePredicate {
	boolean filter (Apple a);
}

class AppleHeavyWeightPredicate implements ApplePredicate {
	@Override
	public boolean filter(Apple a) {
		return a.getWeight() > 500;
	}
}
class AppleGreenColorPredicate implements ApplePredicate {
	@Override
	public boolean filter(Apple a) {
		return a.getColor().equals("green");
	}
}


public class Action1 {
	List<Apple> inventory = Arrays.asList(
			new Apple("green",150),
			new Apple("red",501),
			new Apple("blue",400),
			new Apple("green",600)
		);
	
	//策略模式    在运行时选择一个算法，算法足就是ApplePredicate,实现类就是不同的策略
	public static List<Apple> filterApples(List<Apple> inventory,ApplePredicate p){
		List<Apple> result = new ArrayList<>();
		for(Apple apple : inventory) {
			if(p.filter(apple)) {
				result.add(apple);
			}
		}
		return result;
	}
	
	public void testApple () {
		//inventory 库存
		
		List<Apple> greenApples = filterApples(inventory,new AppleGreenColorPredicate());
		List<Apple> heavyApples = filterApples(inventory,new AppleHeavyWeightPredicate());
		//--------> filterApples方法的行为取决于你通过ApplePredicate对象传递的diamante，这就是把filterApples方法的行为参数化了！
	}
	
	//采用匿名内部类演进
	public void testInnor() {
		List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
			@Override
			public boolean filter(Apple a) {
				return a.getColor().equals("red");//筛选红苹果
			}
		});
	}
	//采用Lambda表达式
	public void testLambda() {
		List<Apple> result = filterApples(inventory, (Apple a)->"red".equals(a.getColor()));
		List<Apple> result2 = filterApples(inventory, apple->"red".equals(apple.getColor()));
		
		//采用list抽象化后，可以把filterobject 方法用在香蕉、桔子、Integer或者 String的列表上了。
		List<Apple> result3 = filterObject(inventory, apple -> "red".equals(apple.getClass()));
		
		List<Integer> numbers = Arrays.asList(1,2,3,4,5);
		List<Integer> result4 = filterObject(numbers, i -> i % 2 == 0);
		
		//巩固练习 排序Comparator接口
		// int compare (T o1, T o2);
		//所以我们可以随时创建Comparator接口的实现，用sort方法表现出不同的行为。
		inventory.sort(new Comparator<Apple>() {
			@Override
			public int compare(Apple a1, Apple a2) {
				return a1.getWeight().compareTo(a2.getWeight());
			}
		});
		
		inventory.sort((a1,a2)-> a1.getWeight().compareTo(a2.getWeight()));
	
	}
	//用Runable来执行代码块
	public void testRunable() {
		//线粗是轻量级的进程。它们自己执行一个代码块，怎么才能告诉线程要执行哪块代码？
		//
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("匿名内部类。执行了线程内部的代码");
			}
		});
		//lambda 表达式改进
		Thread t2 = new Thread(() -> System.out.println("Lambda。执行了线程内部的代码"));
		//（） -》 {}
		//lambda参数 -> { lambda主体 }
		
		//有效的Lambda表达式
//		(String s) -> s.length();//具有一个String类型的参数，并返回一个Int。
//		(Apple a) -> a.getWeight() > 150; //参数Apple类型， 返回boolean类型。 苹果的重量是否大于150g
		/*
		(int x,int y) -> {
			System.out.println("result");
			System.out.println(x+y);
		}  具有两个int类型的参数而没有返回值。 void 。  包含了多行语句
		*/
		
//		() -> 42 //没有参数，返回int 42
		
	}
	
	static void process(Runnable r) {
		r.run();
	}
	
	public void testFunctionInterface () {
		//函数式接口 + Lambda 的组合使用
		Runnable r1 = () -> System.out.println("hello Lambda 1 ");
		Runnable r2 = new Runnable() {
			public void run() {
				System.out.println("hello Inner class ");
			}
		};
		process(r1);
		process(r2);
		process(()-> {});//有效的，具有返回签名void
		process(()->System.out.println("Hello lambda 2"));
		
	}
	//第1步，记得行为参数化
	static String processFile () throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
			return br.readLine();
		} 
	}
	//第2步，定义一个函数式接口来传递行为
	@FunctionalInterface
	interface BufferedReaderProcessor {
		String process(BufferedReader b) throws IOException;
	}
	//第3步，执行一个行为
	public static String processFile(BufferedReaderProcessor p) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
			return p.process(br);
		} 
	}
	//第4步传递Lambda
	public void testExcuteAround() throws IOException {
		
		//你只能读文件的第一行，如果你想要返回头两行，甚至返回使用最频繁的词，那么久需要把processFile的行为参数化。
		//把行为传递给processFile, 以便它可以利用BufferedReader执行不同的行为。
		//Lambda  处理一行
		String oneLine = processFile((BufferedReader br)-> br.readLine());
		
		//Lambda  处理两行
		String twoLine = processFile((BufferedReader br)-> br.readLine()+br.readLine());
	}
	@FunctionalInterface
	interface PredicateMy<T>{
		boolean test (T t);
	}
	
	public static <T> List<T> filter(List<T> list,PredicateMy<T> p){
		List<T> results = new ArrayList<>();
		for(T s:list) {
			if(p.test(s)) {
				results.add(s);
			}
		}
		return results;
	}
	/**
	 * java.util.function.Predicate<T>
	 * static <T> Predicate<T>	isEqual(Object targetRef)
	 * boolean	test(T t)
	 */
	public void testPredicate() {
		List<String> listOfStrings = Arrays.asList("1","2","3","");
		PredicateMy<String> nonEmptyStringPredicateMy = (String s) -> !s.isEmpty();
		List<String> nonEmpty = filter(listOfStrings,nonEmptyStringPredicateMy);
		
	}
	
	public static <T> void forEach(List<T> list, java.util.function.Consumer<T> c) {
		for(T i :list) {
			c.accept(i);
		}
	}
	/**
	 * java.util.function.Consumer<T> 
	 * void	accept(T t)   如果需要访问类型T的对象，并对其执行某些操作，就可以使用这个接口。
	 * 比如可以用它来创建一个forEach()方法，接受一个Integers的列表，并对其中每个元素执行操作。
	 */
	public void testConsumer() {
		List<Integer> intList = Arrays.asList(1,2,3,4);
		forEach(intList, (Integer i) -> System.out.println(i)); //Lambda是Cosumer中的accept方法的实现
	}
	
	/**
	 * java.util.function.Function<T,R>  接口定义了apply方法，接受一个泛型T对象，并返回泛型R对象。
	 * R	apply(T t)
	 * 如果你需要定义一个Lambda,将输入对象的信息映射到输出，就可以使用到这个接口（比如提取苹果的重量，或把字符串映射为它的长度等）。
	 * 利用它来创建一个map犯法，以将一个String列表映射到包含每个String长度的Integer列表。
	 */
	public void testFunction() {
		List<Integer> list = map(Arrays.asList("lambdas","in","action"), 
							(String s) -> s.length()  //Lambda时Function中的apply方法的实现，返回int
						);
	}
	
	public static <T,R> List<R> map(List<T> list, java.util.function.Function<T, R> f){
		List<R> result = new ArrayList<>();
		for(T s: list) {
			result.add(f.apply(s));
		}
		return result;
	}
	
	/**
	 * 使用IntPredicate就避免了对值1000进行装箱操作，但要是用Predicate<Integer>就会把参数1000装箱，
	 * 装箱需要更多的内存，并需要额外的内存搜索来获取被包裹的原始值。
	 */
	public void testIntPredicate() {
		java.util.function.IntPredicate evenNumbers = (int i) -> i % 2 == 0; // 无装箱
		Predicate<Integer> oddNumbers = (Integer i) -> i % 2 == 0; // 装箱
		
	}
	
	/**
	 * 方法引用可以重复使用现有的方法的定义，并像Lambda一样传递它们。  在某些情况下，比使用Lambda表达式更容易读
	 * 方法引用主要有三类
	 * 1.指向静态方法的方法引用。 例如Integer的parseInt方法 == Integer::parseInt
	 * 2.指向任意类型实例方法的方法引用 。例如String的length方法 == Stirng::length
	 * 3.指向现有对象的实例方法的方法引用。局部变量ext::getValue
	 * 
	 * (String s) -> s.toUppeCase()  ==  String::toUpperCase.
	 * () -> ex.getValue()   == ex::getValue
	 * (Apple a） -> a.getWeight()  == Apple::getWeight
	 * (str,i) -> str.substring(i) == Stirng::substring
	 * 
	 * java.util.function.Supplier<T>
	 * T	get()
	 * 构造函数引用 ClassName::new
	 * 
	 * 如果构造函数的签名是Apple(Integer weight),那么它就社会Function接口的签名
	 * 
	 * 函数式接口
	 * 1.T->R  
	 * 2).(int,int)->int
	 * 3).T->void
	 * 4).()->T
	 * 5).(T,U)->R
	 * 
	 * 1)Function<T,R> . 用于将类型T的对象转换为类型R的对象。（比如Function<Apple,Integer>用来提取苹果的重量)
	 * 2)IntBinaryOperator 具有唯一一个抽象方法，applyAsInt,它代表的函数描述符是（int,int)->int
	 * 3)Consume<T> 具有为一个抽象方法accept,它代表的函数描述符是T->void
	 * 4)Supplier<T> 具有唯一一个抽象方法get()，它代表的函数描述符是()->T . 或者Callable<T> 的 call方法，代表（）->T
	 * 5)BiFunction<T,U,R>具有唯一一个抽象方法apple()，它代表的函数描述符是（T,U）-> R
	 * 
	 * Lambdas及函数式接口的例子
	 * 布尔表达式  (List<String> list) -> list.isEmpty()   Predicate<List<String>>
	 * 创建对象 ()->new Apple(10)      Supplier<Apple>
	 * 消费一个对象  (Apple a)->System.out.pringln(a.getWeight())    Consumer<Apple>
	 * 从对象中选择/提取  (String s ) -> s.length()    Function<String,Integer> 或者 ToIntFunction<String>
	 * 合并两个值       （int a,int b) -> a * b      IntBinaryOperator
	 * 比较两个值    （Apple a1, Apple a2 ) -> a1.getWeight().compareTo(a2.getWeight)     Compartor<Apple>
	 * 				BiFunction<Apple,Apple,Integer> 或者 ToIntBiFunction<Apple,Apple>      
	 * 
	 * JAVA8中常用的函数式接口
	 * Predicate<T> 谓词 T->boolean   Int\long\double
	 * Consumer<T> 消费者 T->void   Int\long\double
	 * Function<T,R> 	函数 T->R     Int\long\double 
	 * 						   IntToDouble\IntToLong 
	 *                         LongTodInt\LongTodouble
	 *                         DoubleToInt\DoubleToLong
	 *                         ToInt\ToLong\ToDoubleFunctin
	 * Supplier<T> 提供者  ()->T Int\long\double\boolean
	 * UnaryOperator<T> 一元操作  T->T  Int\long\double
	 * BinaryOperator<T> 二分查找 (T,T)->T  Int\long\double
	 * BiPredicate<L,R>  (L,R)->boolean
	 * BiConsumer<T,U>   (T,U)->void  ObjInt\ObjLong\ObjDouble
	 * BiFunction<T,U,R>  (T,U)->R  ToInt\ToDouble\ToLong
	 * 
	 */
	public void testBiPredicate() {
		BiPredicate<String, String> predicate = (a,b)->a.equals(b);
		predicate.test("2", "2");
	}
	
	public void testMethodIns() {
		inventory.sort((Apple a1,Apple a2)-> a1.getWeight().compareTo(a2.getWeight()));
		//-》方法引用改进
		inventory.sort(Comparator.comparing(Apple::getWeight));
		
		//构造函数引用    假设有一个构造函数没有参数，它适合Supplier的签名()->Apple
		Supplier<Apple> c1 = Apple::new;
		Apple apple = c1.get(); //调用Supplier的get方法将产生一个新的apple
		//等价于
		Supplier<Apple> c2 = () -> new Apple();
		Apple a2 = c2.get();
		
		//有参构造使用Function接口
//		Function<Integer,Apple> appleFunction = 
		List<Integer> weights = Arrays.asList(7,3,4,10);
		List<Apple> apples = mapTo(weights,(e)->new Apple("green",e));
		List<Apple> apples2 = mapTo(weights,(e)->new Apple());
		//如果有一个参数构造可以这样
//		List<Apple> apples3 = mapTo(weights,Apple::new); 
		
		//如果你有两个参数的构造函数那么久需要用BiFunction接口的签名
		BiFunction<String,Integer,Apple> c3 = Apple::new;
		Apple c4 = c3.apply("green",110);
		//等价于
		BiFunction<String,Integer,Apple> c5 = (color,weight)->new Apple(color,weight);
		Apple c6 = c5.apply("green", 120);
		
		
	}
	
	//map升级  
	static Map<String,BiFunction<String,Integer,Apple>> fruitMap = new HashMap<>();
	
	static {
		fruitMap.put("apple1", Apple::new);
		fruitMap.put("apple2", Apple::new);
		fruitMap.put("apple3", Apple::new);
		fruitMap.put("apple4", (color,weight)->new Apple(color,weight));
	}
	
	//获取map，调用apply()方法将提供所要求的Friut
	public Apple testFriut() {
		String fruit = "apple1"; 
		String color = "green";
		Integer weight = 200;
		return fruitMap.get(fruit.toLowerCase()).apply(color, weight);
	}
	
	public static List<Apple> mapTo(List<Integer> list , Function<Integer,Apple> f){
		List<Apple> result = new ArrayList<>();
		for(Integer e : list) {
			result.add(f.apply(e));
		}
		return result;
	}
	
	
	
	
	

	
	//List类型抽象化
	interface Predicate<T>{
		boolean select(T t);
	}
	static <T> List<T> filterObject(List<T> list, Predicate<T> p) {
		List<T> result = new ArrayList<>();
		for(T e : list) {
			if(p.select(e)) {
				result.add(e);
			}
		}
		return result;
	}
	
	/*
	 * 你想要写两个只有几行代码不同的方法，那现在你只需要把不同的那部分代码作为参数传递进去就可以了。
	 * 
	 * java8之前用匿名类实现行为参数化，
	 * inventory.sort(comparing(Apple::getWeight)
	 * 
	 * 1.1 节 讨论JAVA的演变过程和概念
	 * 1.2 节 介绍了为什么把代码传递给方法
	 */
	
	//filterApples(inventory,(Apple a)->"green".equals(a.getColor()))
	

}
