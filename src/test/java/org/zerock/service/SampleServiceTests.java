package org.zerock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j2
public class SampleServiceTests { //AOP TEST
	@Setter(onMethod_ = @Autowired)
	private SampleService service;
	
	//proxy 객체 생성 여부 테스트
	@Test
	public void testClass() {
		log.info(service);
		log.info(service.getClass().getName());
		// INFO  org.zerock.service.SampleServiceTests(testClass22) - org.zerock.service.SampleServiceImpl@ce9b9a9
		// INFO  org.zerock.service.SampleServiceTests(testClass23) - com.sun.proxy.$Proxy35
	}
	
	//메서드 실행 테스트
	@Test
	public void testAdd() throws Exception{
		log.info(service.doAdd("123", "456"));
		// INFO  org.zerock.aop.LogAdvice(logBefore22) - ========
		// INFO  org.zerock.service.SampleServiceTests(testAdd31) - 579
	}
	
	//예외발생 테스트
	@Test
	public void testAddError() throws Exception{
		log.info(service.doAdd("123", "ak3"));
	}
	
	
	
	
	
	
	

}
