
package com.xianlei.spring.web.config;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import com.xianlei.spring.web.model.User;

/**
 * 自定义消息转换器
 * @author Xianlei
 *
 */
public class MyMessageConverter extends AbstractHttpMessageConverter<User> {

	public MyMessageConverter() {
		super(new MediaType("application","x-wisely",Charset.forName("UTF-8")));
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}
	
	//重写 readInternal房阿发，处理请求的数据。 转换成User对象
	@Override
	protected User readInternal(Class<? extends User> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		String body = StreamUtils.copyToString(inputMessage.getBody(),Charset.forName("UTF-8"));
		String[] bodyArr = body.split("-");
		
		User user = new User();
		user.setId(bodyArr[0]);
		user.setName(bodyArr[1]);
		
		return user;
	}
	
	//重写writeInternal方法，处理如何输出数据到response
	@Override
	protected void writeInternal(User t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		String out = "hello : "+ t.getId() +" - " + t.getName();
		outputMessage.getBody().write(out.getBytes());
		
	}


}
