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
public class SampleTxServiceTests { // Transaction Test

	@Setter(onMethod_ = @Autowired)
	private SampleTxService serviceTx;

	@Test
	public void testLong() {
		// 50byte< str < 500byte -> tbl1, 2에 insert
		String str = "Starry\r\n" + "Starry night\r\n" 
		+ "Paint your palette blue and grey\r\n"
		+ "Look out on summer's day";
		
		log.info(str.getBytes().length); //int로 문자열 길이 나옴
		serviceTx.addData(str);

	}

}
