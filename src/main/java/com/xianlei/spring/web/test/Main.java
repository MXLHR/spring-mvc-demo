package com.xianlei.spring.web.test;

/**
 * 测试Web项目通常不需要启动项目，我们需要一些Servlet相关的模拟对象。
 * 比如: MockMVC、MockHttpServletRequest、MockHttpServletResponse、MockHttpSession等。
 * 
 * 在Spring里，我们使用@WebAppConfiguration指定加载的ApplicationContext是一个WebApplicationContext
 * 
 * 可能许多人，包括我自己以前觉得测试没什么用，自己启动一下，点点弄弄，就是我们前面的例子一样不也是测试的吗？
 * 但现实是残酷的。
 * 现实开发中，我们是先有需求的，也就是说先知道我们想要的是什么样的，然后按照我们想要的样子去开发。
 * TDD(Test Driven Development ,测试驱动开发 )，
 * 设计人员按照需求先写一个自己预期结果的测试用例，这个测试用例刚开始肯定是失败的测试，随着不断的编码和重构，
 * 最终让测试用例通过测试，这样才能保证软件的质量和可控性。
 * 
 * 下面的示例是借助JUnit和 Spring TestContext framework，分别演示普通页面转向形控制器和RestController进行测试
 * 
 * 
 * 1.依赖spring-test、junit
 * 2.演示服务  com.xianlei.spring.web.test.service.UserService
 * 3.测试用例，在src/test/java
 * @author Xianlei
 *
 */
public class Main {
	
	

}
