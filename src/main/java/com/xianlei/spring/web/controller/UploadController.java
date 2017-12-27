package com.xianlei.spring.web.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.zhph.common.util.DateUtil;

import ch.qos.logback.core.util.DatePatternToRegexUtil;

@Controller
public class UploadController {

	@RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
	public @ResponseBody String uploadFiles(@RequestParam("file") CommonsMultipartFile[] files) throws IOException {
		checkMultipartFiles(files);
		for (MultipartFile file : files ) {
			File outFile = new File("e:/upload/files/" + getFileName(file));
			FileUtils.writeByteArrayToFile(outFile, file.getBytes());
		}
		return "ok";
	}


	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String upload(MultipartFile file,HttpServletRequest request) throws IOException {
		checkMultipartFile(file);
		File outFile = new File("e:/upload/file/" + getFileName(file));
		FileUtils.writeByteArrayToFile(outFile, file.getBytes());
		return "ok";
	}
	
	private void checkMultipartFiles(CommonsMultipartFile[] files) {
		for(MultipartFile file : files){
			checkMultipartFile(file);
		}
	}
	
	private void checkMultipartFile(MultipartFile file) {
		if (file == null) {
			throw new NullPointerException("无法获取表单上传文件");
		}
	}

	private String getFileName(MultipartFile file){
		return  dateToString(new Date()) + "_" + file.getOriginalFilename();
	}

	private static String dateFormat = "yyyy-MM-dd";

	private static String dateTimeFormat = "yyyyMMddHHmmss";

	public String dateToString(Date date) {
		return dateToString(date, dateTimeFormat);
	}

	public static String dateToString(Date date, String format) {
		String target = null;
		if (date == null || "".equals(date)) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		target = simpleDateFormat.format(date);
		return target;
	}

}
