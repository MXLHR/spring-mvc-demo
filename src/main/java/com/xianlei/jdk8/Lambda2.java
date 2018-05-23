package com.xianlei.jdk8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import org.junit.Test;


/*
 * 每一个lambda都能够通过一个特定的接口，与一个给定的类型进行匹配。
 * 一个所谓的函数式接口必须要有且仅有一个抽象方法声明。
 * 每个与之对应的lambda表达式必须与抽象方法的声明相匹配。
 * 由于默认方法不是抽象的，因此你可以在你的函数式借口里 任意添加默认方法。
 * 
 * 任意只包含一个抽象方法的接口，我们都可以用来做成lambda表达式。
 * 为了让你定义的接口满足要求，你应当在接口上加上@FunctionalInterface标注。
 * 编译器会注意到这个标注，如果你的接口中定义了第二个抽象方法的话，编译器会抛出异常。
 * 
 * 总结：函数式接口只有一个抽象方法声明。
 *      每个lambda表达式必须与抽象方法的声明相匹配。
 *      可以增加任意默认方法。
 *      只有一个抽象方法的函数式接口应当加上@FunctionalInterface标注
 */
@FunctionalInterface
interface Converter<F,T>{
	T convert(F from);
}

/*
 * java8允许你通过::关键字获取方法或者构造函数的引用。
 * Coverter<String,Integer> converter = (from) -> Integer.valueOf(from);
 * Coverter<String,Integer> converter2 = Integer::valueOf
 * 这就是引用了一个静态方法。
 * 
 */
class Something {
	String startsWith(String s) {
		return String.valueOf(s.charAt(0));
	}
}
/*
 * lambda 范围 ：与匿名对象的方式类似，能够范围外部区域的局部final变量，以及成员变量和静态变量。
 */
public class Lambda2 {
	
	
	
	@Test
	public void testReference() {
		//通过Pserson::new 来创建一个Person类构造函数的引用。
		//java编辑器会自动地选择合适的构造函数来匹配PersonFactory.create函数的签名，
		//并选择正确的构造函数形式。
		PersonFactory<Person> personFactory = Person::new;
		Person person = personFactory.create("Ray", "Xian");
		System.out.println(person.toString());
	}
	
	public void staticReference() {
		Something something = new Something();
		Converter<String,String> converter = something::startsWith;
		String converted = converter.convert("Java");
		System.out.println(converted);
	}
	
//	@Test
	public void functionalInterface() {
		//(T,R)->R   Function(T,r)
		Converter<String,Integer> converter = (from) -> Integer.valueOf(from);
		Integer converted = converter.convert("123");
		System.out.println(converted);
		
//		改写
		Function<String,Integer> function =  Integer::valueOf;
		Integer ab = function.apply("123");
		
		//代码优化，可以通过静态方法引用
		Converter<String,Integer> converter2 = Integer::valueOf;
		Integer converted2 = converter.convert("123");
//		(converted2)->System.out::println(e);
		System.out.println(converted2);
		
		final int num = 1;
		Converter<Integer,String> sc = (from)->String.valueOf(from+num);
		sc.convert(2);
		//--->
		Converter<Integer,String> scc =String::valueOf;
	}
//	@Test
	public void comparator() {
		List<String> names = Arrays.asList("petter","anna","mike","xenia");
		//jdk8之前的排序  对字符串列表
		Collections.sort(names, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return b.compareTo(a);
			}
		});
		//jdk8 lambda表达式
		Collections.sort(names, (String a,String b)->{
			return b.compareTo(a);
		});
		//简写
		Collections.sort(names, (a,b)-> b.compareTo(a));
	}
	@Test
	public void compartorInt() {
		List<Integer> intlist = Arrays.asList(1,23,4,2,56);
		System.out.println(intlist);
		Collections.sort(intlist, Integer::compare);
		System.out.println(intlist);
	}
}

//看看是如何使用::关键字引用构造函数的。
interface PersonFactory<P extends Person>{
	P create (String firstName,String lastName);
}

class Person {
	String firstName;
	String lastName;
	public Person() {
	}
	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
	
}
