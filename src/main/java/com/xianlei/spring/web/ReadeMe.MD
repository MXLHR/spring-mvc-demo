
SpringMVC的基础知识和技术要点。
#2017.12.20 - 2017.12.26 学习整理
#阅读书籍《SpringBoot实战：JaveEE开发的颠覆者》

目录
第一部分 点睛Spring4.x
第二部分  点睛SpringMVC 4.x
第三部分  实战SpringBoot

下一步的学习规划：
1.springBoot项目环境搭建（eclipse\IDEA)

第一部分
第1章 Spring基础
第2章 Spring常用配置
第3章 Spring高级话题

第二部分
第4章 SpringMVC基础

阶段性知识总结：
Java配置Spring DI、
AOP、AOP动态代理发展历程、AspectJ注解式AOP、Scope、EL、commons-io、Bean的初始化和销毁、profile、
Application Event、Spring Aware 获取服务、TaskExecutor并发编程和@EnableAsync异步任务、@Scheduled计划任务、
条件注解@Conditional、MVC和三层架构的概念、SpringMVC项目依赖jar包、logback日志、常用注解、配置类、
静态资源映射、拦截器、ExceptionHandler全局异常处理、addViewControllers、文件上传、自定义HttpMessageConverter
服务器端推送技术SSE和Servert异步处理、Spring MVC 测试。

掌握不好的和重点归纳：
1.AOP和动态代理的发展使用
2.commons-io\commons-lang工具类库操作
3.Application Event
4.spring 定时任务
5.logback
6.文件上传和下周
7.SSE
8.MVC 测试

难度排名：
1.AOP和动态代理的发展使用
2.commons-io\commons-lang工具类库操作
6.文件上传和下周
8.MVC 测试





1.1 spring概述（发展简史、概述）
1.2 spring项目环境搭建（maven介绍、构建）
1.3 spring基础配置(依赖注入、Java配置、AOP)
	1.DI依赖注入 com.zhph.spring.di
	2.JAVA配置 @Configuration声明当前类是配置类，相当于xml;@Bean声明当前方法的返回值是一个Bean
		全局的配置使用JAVA配置，业务Bean使用注解配置（@Controller、@Repositoty、@Service、@Component）
	3.AOP:spring支持AspectJ的注解式切面编程
		1）使用@Aspect声明式一个切面
		2）使用@After\@Before\@Around定义advice,注解的value为拦截规则（切点）
		3）其中@After、@Before、@Around参数的拦截规则为切点PonintCut,切点修饰连接点JoinPoint
		4) 其中符合条件的每一个被拦截处为连接点JoinPoint
		5) 使用@EnableAspectJAutoProxy注解开启Spring对AspectJ代理的支持。
	4.自定义注解

2.1 Bean的Scope
	1.Scope描述的是Spring容器如何创建Bean的实例的。
		Singleton:单例。  Prototype:每次调用新建 一个Bean的实例。
		Request：Web项目中给每一个http request新建一个Bean的实例
		Session: Web项目中，給每一个http session新建一个Bean的实例。
2.2 Spring EL 和资源调用
	1.支持在xml和注解中使用EL表达式类似于JSP的EL表达式
	2.开发中经常涉及调用各种资源如普通文件\网址、配置文件、系统环境变量等 都可以使用EL实现资源注入。
	3.主要在注解@Value的参数中使用表达式。
		演示内容：注入普通字符；操作系统属性；运算结果；其他Bean的属性；文件内容；网址内容；属性文件
	4.引入Commons-io 简化文件相关操作。file 相关的操作。
2.3 Bean的初始化和销毁
	1.JAVA配置方式：使用@Bean的initMethod 和 DestoryMethod（xml : init-method和destory-method）
	2.注解方式：利用JSR-250注解的 @PostConstruct 和 @PreDestory。
2.4 Profile
	1.简述：Profile为在不同环境下适用不同的配置提供了支持。在不同情况下选择实例化不同的Bean.
	2.后置注册Bean配置类，不然会报Bean未定义的错误;刷新容器
2.5 事件（Application Event)
	1.自定义事件，继承ApplicationEvent ，super(resource);
	2.定义监听器  实现ApplicationListener接口，重写onApplicationEvent方法
	3.容器发布事件（发布-订阅模型） applicationContext.publishEvent DemoEvent(this,msg);

3.1 Spring Aware
	1.实现BeanNameAware、ResourceLoaderAware接口，获得Bean名称和资源加载的服务。
	2.实现ResourceLoaderAware 需要重写setResourceLoader;BeanNameAware重写setBeanName;
3.2 多线程
	1.Spring 通过任务执行器（TaskExecutor) 来实现多线程和并发编程
	2.配置类通过@EnableAsync开启对异步任务的支持
3.3 计划任务
	1.简述：通过配置@EnableScheduling开启对定时任务的支持，在方法上@Scheduled声明式一个定时任务。
		   配置值：fixedRate
3.4 条件注解@Conditional：根据满足特定的一个条件创建一个特定的Bean。 结合多态的写法，只有一个子类成为Bean
3.5 组合注解与元注解
3.6 @Enable* 常用注解和工作原理 :@EnableAspectJAutoProxy、@EnableAsync、@EnableScheduling、@EnableWebMvc
3.7 测试

4.1 Spring MVC 概述
	1.MVC的概念，Model+View+Controller(数据模型+视图+控制器)
	2.三层架构：展现层+应用层+数据访问层
4.2 Spring MVC 项目快速搭建
	1.依赖：
	    JavaEE:
		    javax.javaee-web-api
		    javax.servlet.jstl
		    javax.servlet.javax.servlet-api
		    javax.servlet.jsp.jsp-api
    	日志:  
    	    org.slf4j.slf4j-api
		    org.slf4j.jcl-over-slf4j
			ch.qos.logback.logback-classic
			ch.qos.logback.logback-core
			ch.qos.logback.logback-access
		对象和JSON/XML转换的支持:
			com.fasterxml.jackson.dataformat.jackson-dataformat-xml
		切面：
			org.aspectj.aspectjrt
			org.aspectj.aspectjweaver
		公用类：
			org.apache.commons.commons-io
			org.apache.commons.commons-fileupload
			org.apache.commons.commons-lang3
		JSR:
			javax.annotation.jsr250-api
	2.在src/main/resources/ 新建logback.xml
	3.配置类加@EnableWebMvc ,会开启默认的配置 viewResovler 、 MessaggeConverter等
	4.web配置，实现WebApplicationInitializer，注册Spring的容器和mvc的DispatcherServlet
	5.编写简单的控制器，发布项目导tomcat测试
4.3 Spring MVC 的常用注解
	1。@Controller
	2.@ResquestMapping produces可定制返回媒体类型和字符集
	3.@ResponseBody
	4.@RequestBody
	5.@PathVariable 接受路径参数，如/news/001,可接受001作为参数
	6.@RestController  直接返回对象，对象会自动转换为json
4.4 Spring MVC 的基本配置
	0.配置类MyMvcConfig继承WebMvcConfigurerAdapter
	1.静态资源映射. 重写addResourceHandlers方法
	2.拦截器配置
		1.自定义一个拦截器，继承HandlerInterceptorAdapter,重写preHandle、postHandle方法
		2.重写addInterceptors方法
	3.@ControllerAdvice： 控制器的全局配置
		1)注解了Controller的类的方法可使用@ExceptionHandler，@InitBinder,@ModelAttribute注解到该方法上，市全局的配置。
		@ExceptionHandler,用于全局处理控制器的异常.
		@InitBinder:用来设置WebDataBinder，用来自动绑定前台请求参数到Model中。
		@ModelAttribute,绑定键值对到Model中，
		
	4.其他配置
		1）ViewController:重写addViewControllers
		2）路径匹配参数配置。如果路径参数带.后面的值会被忽略，可以自定义是否被忽略
		3）更多配置查看WebMvcConfigurerAdapter类的API
4.4 Spring MVC 的高级配置
	1.文件上传与下载
	2.自定义HttpMessageConverter
	3.服务器端推送技术
		1）SSE（Server Send Event）
		2）Servlet 3.0+异步方法处理
4.6 Spring MVC 的测试
	1.MockMvc-模拟MVC对象


