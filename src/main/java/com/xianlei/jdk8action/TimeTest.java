package com.xianlei.jdk8action;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class TimeTest {
	
	@Test
	public void test() {
		
		//获取当前日期时间
		LocalDateTime now = LocalDateTime.now();
		System.out.println(now);
		
		//将当前日期时间减去两套
		LocalDateTime minusDays = now.minusDays(2);
		System.out.println(minusDays);
		
		//将当前日期的时间加上五天
		LocalDateTime plusDays = now.plusDays(5);
		System.out.println(plusDays);
		
		//输出当前日期时间的年份
		System.out.println(now.getYear());
		
		//构造一个指定日期时间的对象
		 LocalDateTime dateTime = LocalDateTime.of(2016, 10, 23, 8, 20);
		 System.out.println(dateTime);
	}
	
	@Test
	public void test1() {
	    // 获取当前时间的时间戳
	    Instant instant = Instant.now();
	    // 因为中国在东八区，所以这句输出的时间跟我的电脑时间是不同的
	    System.out.println(instant);

	    // 既然中国在东八区，则要偏移8个小时，这样子获取到的时间才是自己电脑的时间
	    OffsetDateTime dateTime = instant.atOffset(ZoneOffset.ofHours(8));
	    System.out.println(dateTime);

	    // 转换成毫秒，如果是当前时间的时间戳，结果跟System.currentTimeMillis()是一样的
	    long milli = instant.toEpochMilli();
	    System.out.println(milli);
	}
	//时间间隔
	@Test
	public void test2() {
	    LocalTime start = LocalTime.now();
	    try {
	        //让线程睡眠3s
	        Thread.sleep(3000);
	    } catch (Exception e) {
	    }
	    LocalTime end = LocalTime.now();
	    //获取end和start的时间间隔
	    Duration duration = Duration.between(start, end);
	    //可能会输出PT3S或者输出PT3.001S，至于多出来的0.001秒其实就是除去线程睡眠时间执行计算时间间隔那句代码消耗的时间
	    System.out.println(duration);
	    
	    
				
	}
	//日期间隔
	@Test
	public void test3() {
	    //起始时间指定为2015年3月4日
	    LocalDate start = LocalDate.of(2015, 3, 4);
	    //终止时间指定为2017年8月23日
	    LocalDate end = LocalDate.of(2017, 8, 23);

	    Period period = Period.between(start, end);
	    //输出P2Y5M19D，Y代表年，M代表月，D代表日，说明start和end的日期间隔是2年5个月19天
	    System.out.println(period);
	}
	/*
	 * 以前我们用的日期格式化的类是java.text.SimpleDateFormat，
	 * Java 8 提供的日期格式化类是java.time.format.DateTimeFormatter，下面写个例子
	 */
	@Test
	public void test5() {
	    // 获取预定义的格式，DateTimeFormatter类预定了很多种格式
	    DateTimeFormatter dtf = DateTimeFormatter.BASIC_ISO_DATE;
	    // 获取当前日期时间
	    LocalDate now = LocalDate.now();
	    // 指定格式化器格式日期时间
	    String strNow = now.format(dtf);
	    System.out.println(strNow);

	    // 自定义格式
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
	    String strNow2 = now.format(formatter);
	    System.out.println(strNow2);

	    // 将字符串转换成日期
	    LocalDate date = LocalDate.parse(strNow2, formatter);
	    System.out.println(date);
	}
	
	@Test
	public void test7() {
	    Set<String> set = ZoneId.getAvailableZoneIds();
	    set.forEach(System.out::println);
	}
	
	@Test
	public void test8() {
	    //获取当前时区的日期时间
	    ZonedDateTime now = ZonedDateTime.now();
	    System.out.println(now);

	    //获取美国洛杉矶时区的日期时间
	    ZonedDateTime USANow = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));
	    System.out.println(USANow);
	}
}
