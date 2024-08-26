package org.zerock.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.zerock.domain.Ticket;

import com.google.gson.Gson;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import oracle.jdbc.proxy.annotation.Post;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"}) //참고할 파일 , front도 봐야 하므로 servlet-context.xml도 넣어줌
@Log4j2
@WebAppConfiguration //front 영역 테스트용 (web.xml 대체)
public class SampleControllerTests {
	@Setter(onMethod_ = @Autowired)
	private WebApplicationContext ctx; //tomcat 대타
	
	private MockMvc mocMvc; //크롬대타
	
	@Before //import org.junit.Before; 구동 전에 선행해야 되는 코드 작성
	public void setup() {
		this.mocMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
		//api : front 없이 테스트용
	}
	
	@Test
	public void testConvert() throws Exception{
		//post 메서드 테스트는 url로 안됨
		Ticket ticket = new Ticket(); //티켓 빈객체 생성
		ticket.setTno(312);
		ticket.setOwner("kkw");
		ticket.setGrade("VIP"); //객체생성 완료
		String jsonStr = new Gson().toJson(ticket); //구글에서 만든 변환기로 json으로 변경
		log.info("구글에서 제공하는 json 변환기" + jsonStr);	
		//구글에서 제공하는 json 변환기{"tno":312,"owner":"kkw","grade":"VIP"}
		
		mocMvc.perform(post("/sample/ticket")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr)).andExpect(status().is(200));
		// SampleController.convert() 메서드 실행Ticket(tno=312, owner=kkw, grade=VIP)
	}

}
