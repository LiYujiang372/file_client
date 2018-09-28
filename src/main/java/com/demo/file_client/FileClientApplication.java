package com.demo.file_client;

import org.springframework.beans.BeansException;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class FileClientApplication {

	public static void main(String[] args) throws BeansException, InterruptedException {
	    SpringApplicationBuilder builder = new SpringApplicationBuilder(FileClientApplication.class);
	    //设置headless, spring boot默认阻止awt
	    builder.headless(false).web(WebApplicationType.NONE).run(args);
	}
}
