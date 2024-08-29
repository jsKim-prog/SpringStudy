package org.zerock.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Aspect
@Log4j2
@Component // Aop와 상관없지만 스프링에서 bean으로 인식하기 위해서
public class LogAdvice {
	// 관심사의 로그기록
	// xml이 아니라 @만을 이용하여 AOP 설정

	// @Before : BeforeAdvice 구현
	// execution : 접근제한자와 클래스의 메서드 지정
	// * org...: *=접근제한자, .SampleService*.*(..) : *=클래스/메서드 이름
	// 첫 번째 접근제한자와 클래스 사이 띄어쓰기 필수!!
	@Before("execution(* org.zerock.service.SampleService*.*(..))") // Pointcut
	public void logBefore() {
		log.info("========");
		// INFO  org.zerock.aop.LogAdvice(logBefore22) - ========
	}

	//파라미터 파악하여 로그찍기
	@Before("execution(* org.zerock.service.SampleService*.doAdd(String,String)) && args(str1, str2)")
	//args(srt1, str2) : 아래 logBeforeWithParam 메서드의 파라미터 설정
	public void logBeforeWithParam(String str1, String str2) {
		log.info("str1:" + str1);
		log.info("str2:" + str2);
		// INFO  org.zerock.aop.LogAdvice(logBeforeWithParam29) - str1:123
		// INFO  org.zerock.aop.LogAdvice(logBeforeWithParam30) - str2:456
		// INFO  org.zerock.service.SampleServiceTests(testAdd31) - 579
	}
	
	//예외발생 후 문제찾기
	@AfterThrowing(pointcut = "execution(* org.zerock.service.SampleService*.*(..))", throwing = "exception")
	public void logExcetion(Exception exception) {
		log.info("예외발생!!!............");
		log.info("exception:"+ exception);		
		//INFO  org.zerock.aop.LogAdvice(logExcetion41) - 예외발생!!!............
		//INFO  org.zerock.aop.LogAdvice(logExcetion42) - exception:java.lang.NumberFormatException: For input string: "ak3"
	}
	
	//AOP를 활용한 구체적인 처리(@Around / ProceedingJointPoint)
	@Around("execution(* org.zerock.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		long start = System.currentTimeMillis();
		log.info("Target:" + pjp.getTarget());
		log.info("Param:" + Arrays.toString(pjp.getArgs()));
		
		Object result = null;
		try {
			result = pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		log.info("Time : " + (end - start));
		
		return result;
		
//		INFO  org.zerock.aop.LogAdvice(logTime55) - Target:org.zerock.service.SampleServiceImpl@2b98b3bb
//		INFO  org.zerock.aop.LogAdvice(logTime56) - Param:[123, 456]
//		INFO  org.zerock.aop.LogAdvice(logBefore27) - ========
//		INFO  org.zerock.aop.LogAdvice(logBeforeWithParam35) - str1:123
//		INFO  org.zerock.aop.LogAdvice(logBeforeWithParam36) - str2:456
//		INFO  org.zerock.aop.LogAdvice(logTime66) - Time : 55
//		INFO  org.zerock.service.SampleServiceTests(testAdd31) - 579
		// @Around -> @Before -> @Around(시간기록) 순으로 실행
	}


	
}
