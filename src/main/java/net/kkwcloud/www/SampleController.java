package net.kkwcloud.www;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;
import net.kkwcloud.domain.SampleDTO;
import net.kkwcloud.domain.SampleDTOList;
import net.kkwcloud.domain.TodoDTO;

@Controller //servlet-context.xml의 <context:component-scan base-package="net.kkwcloud.www" /> 적용(spring 객체 등록)
@RequestMapping("/sample/*") //url mapping(web.xml -> servlet-context.xml->@RequestMapping)
@Log4j2
public class SampleController {
	//필드
	
	//생성자
	
	//메서드
	//파라미터의 변환 @InitBinder -> dto 객체에 DateTimeFormat 직접 적용하여 사용하지 않음
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(simpleDateFormat, false));
//	}
	
	
	//경로생성, 전송방식
	@RequestMapping("") // http://localhost:80/sample/
	public void basic() { //기본 경로 확인용
		log.info("SampleController.basic() 메서드 실행");
		// [/WEB-INF/views/sample.jsp]을(를) 찾을 수 없습니다.
	}
	
	@RequestMapping(value = "/basic" , method = {RequestMethod.GET, RequestMethod.POST}) // http://localhost:80/sample/basic
	public void basicGet() {
		log.info("SampleController.basicGet() 메서드 실행");
	}
	
	@GetMapping("/getOnly") // http://localhost:80/sample/getOnly
	public void getOnly() {		
		log.info("SampleController.getOnly() 메서드 실행 - get방식");
	}
	
	@PostMapping("/postOnly") // http://localhost:80/sample/postOnly
	public void postOnly() {
		log.info("SampleController.postOnly() 메서드 실행 - post방식");
	}
	
	//객체처리
	@GetMapping("/ex01") // http://localhost:80/sample/ex01
	public String ex01(SampleDTO sampleDTO) {
		log.info("ex01(SampleDTO sampleDTO) 메서드 실행" + sampleDTO);
		return "ex01"; // /WEB-INF/views/ex01.jsp
		
		//http://localhost:80/sample/ex01?name=kkk&age=30 ->View name 'ex01', model {sampleDTO=SampleDTO(name=kkk, age=30)
	}
	@GetMapping("/ex02") // http://localhost:80/sample/ex02?id=kkk&name2=20
	public String ex02(@RequestParam("id") String name, @RequestParam("name2") int age) {
		//@RequestParam : 파라미터로 사용된 변수이름과 전달되는 파라미터의 이름이 다른 경우 유용(*name2를 name으로 한 경우는 잘못된 요청 처리)
		//타입이 지정되어 안전하다.
		log.info("name : "+name);
		log.info("age : " + age);
		
		return "ex02";
	}
	
	//리스트, 배열처리
	@GetMapping("/ex02List") // http://localhost:80/sample/ex02List?ids=kkk&ids=112&ids=jd3
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		log.info("ids : "+ids);
		return "ex02List";
	}
	@GetMapping("/ex02Array") // http://localhost:80/sample/ex02Array?ids=111&ids=112&ids=jd3
	public String ex02Array(@RequestParam("ids") String[] ids) {
		log.info("Array ids : "+Arrays.toString(ids));
		return "ex02Array";
	}
	
	//리스트, 배열처리(dto 사용)
	@GetMapping("/ex02Bean") 
	// 전달하려는 url ->  http://localhost:80/sample/ex02Bean?list[0].name=kkk&list[0].age=40  ->[]가 유효하지 않은 문자
	// url인코더로 변환후 전달 -> http://localhost:80/sample/ex02Bean?list%5B0%5D.name%3Dkkk%26list%5B0%5D.age%3D40
	public String ex02Bean(SampleDTOList list) {
		log.info("list dtos : " +list); //list dtos : SampleDTOList(list=[SampleDTO(name=null, age=0)])
		return "ex02Bean";
	}
	
	//파라미터의 변환(문자열->Date) : @InitBinder @DateTimeFormat
	@GetMapping("/initDate") // http://localhost:80/sample/initDate?title=study&dueDate=2024-08-14&check=true
	// [test] http://localhost:80/sample/initDate?title=study&dueDate=2024/08/14&check=true ->날짜형식 변경시 잘못된 요청
	public String initDate(TodoDTO todoDTO) {
		log.info("todo : "+todoDTO);
		return "initDate";
	}
}
