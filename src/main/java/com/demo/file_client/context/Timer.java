package com.demo.file_client.context;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Timer {
	
	private static Logger logger = LoggerFactory.getLogger(Timer.class);
	
	@Pointcut(value = "@annotation(com.demo.file_client.context.annotation.LogExecuteTime)")
	public void logExecuteTime() {
		
	}
	
	@Around(value = "logExecuteTime()")
	public Object around(ProceedingJoinPoint point) {
		long a = System.currentTimeMillis();
		Object result = null;
		try {
			result = point.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		long b = System.currentTimeMillis();
		logger.info("{}.{}()--耗时:{}ms", point.getSignature().getDeclaringTypeName(),
				point.getSignature().getName(), (b - a));
		return result;
	}

}
