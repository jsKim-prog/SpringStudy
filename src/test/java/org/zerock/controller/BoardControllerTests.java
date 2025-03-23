package org.zerock.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"}) //참고할 파일 , front도 봐야 하므로 servlet-context.xml도 넣어줌
@Log4j2
@WebAppConfiguration //front 영역 테스트용 (web.xml 대체)
public class BoardControllerTests {
	@Setter(onMethod_ = @Autowired)
	private WebApplicationContext ctx; //tomcat 대타
	
	private MockMvc mocMvc; //크롬대타
	
	@Before //import org.junit.Before; 구동 전에 선행해야 되는 코드 작성
	public void setup() {
		this.mocMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
		//api : front 없이 테스트용
	}
	
	@Test //url과 결과를 처리해주는 테스트
	public void testlist() throws Exception{
		log.info(
				mocMvc.perform(MockMvcRequestBuilders.get("/board/list")) //url을
				.andReturn()										//결과를
				.getModelAndView()									//모델에
				.getModelMap()										//표형식
				);
	}
	
	@Test
	public void testRegister() throws Exception{
		String resultPage = mocMvc.perform(MockMvcRequestBuilders.post("/board/register")
				.param("title", "컨트롤러 테스트 제목")
				.param("content", "컨트롤러 테스트 내용")
				.param("writer", "컨트롤러"))
				.andReturn().getModelAndView()
				.getViewName(); //리턴값을 받아서 String 처리

		log.info("결과url : "+resultPage);
		//결과url : redirect:/board/list
	}
	
	@Test  //bno가 넘어가면 돌아오는 값은 객체
	public void testGet() throws Exception{		
		log.info("결과 : "+
				mocMvc.perform(MockMvcRequestBuilders.get("/board/get").param("bno", "6")) // http://localhost:80/board/get?bno=6
				.andReturn().getModelAndView()
				.getModelMap()); //select 결과는 getModelMap
		//결과 : {board=BoardVO(bno=6, title=테스트 제목6, content=테스트 내용6, writer=user00, regdate=Tue Aug 20 11:13:36 KST 2024, updateDate=Tue Aug 20 11:13:36 KST 2024)		
	}
	
	@Test
	public void testModify() throws Exception{
		String resultPage = mocMvc.perform(MockMvcRequestBuilders.post("/board/modify")
				.param("bno", "6")
				.param("title", "컨트롤러 수정 제목")
				.param("content", "컨트롤러 수정 내용")
				.param("writer", "컨수정자"))
				.andReturn().getModelAndView()
				.getViewName(); //리턴값을 받아서 String 처리

		log.info("결과url : "+resultPage);
		//결과url : redirect:/board/list
	}
	
	@Test
	public void testRemove() throws Exception{
		String resultPage = mocMvc.perform(MockMvcRequestBuilders.post("/board/remove")
				.param("bno", "7"))
				.andReturn().getModelAndView()
				.getViewName(); //리턴값을 받아서 String 처리

		log.info("결과url : "+resultPage);
		//결과url : redirect:/board/list
	}

}
