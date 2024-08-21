package net.kkwcloud.www;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;
import net.kkwcloud.domain.SampleDTO;
import net.kkwcloud.domain.SampleDTOList;
import net.kkwcloud.domain.TodoDTO;

@Controller // servlet-context.xml의 <context:component-scan base-package="net.kkwcloud.www"
			// /> 적용(spring 객체 등록)
@RequestMapping("/sample/*") // url mapping(web.xml -> servlet-context.xml->@RequestMapping)
@Log4j2
public class SampleController {
	// 필드

	// 생성자

	// 메서드
	// 파라미터의 변환 @InitBinder -> dto 객체에 DateTimeFormat 직접 적용하여 사용하지 않음
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(simpleDateFormat, false));
//	}

	// 경로생성, 전송방식
	@RequestMapping("") // http://localhost:80/sample/
	public void basic() { // 기본 경로 확인용
		log.info("SampleController.basic() 메서드 실행");
		// [/WEB-INF/views/sample.jsp]을(를) 찾을 수 없습니다.
	}

	@RequestMapping(value = "/basic", method = { RequestMethod.GET, RequestMethod.POST }) // http://localhost:80/sample/basic
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

	// 객체처리
	@GetMapping("/ex01") // http://localhost:80/sample/ex01
	public String ex01(SampleDTO sampleDTO) {
		log.info("ex01(SampleDTO sampleDTO) 메서드 실행" + sampleDTO);
		return "ex01"; // /WEB-INF/views/ex01.jsp

		// http://localhost:80/sample/ex01?name=kkk&age=30 ->View name 'ex01', model
		// {sampleDTO=SampleDTO(name=kkk, age=30)
	}

	@GetMapping("/ex02") // http://localhost:80/sample/ex02?id=kkk&name2=20
	public String ex02(@RequestParam("id") String name, @RequestParam("name2") int age) {
		// @RequestParam : 파라미터로 사용된 변수이름과 전달되는 파라미터의 이름이 다른 경우 유용(*name2를 name으로 한 경우는
		// 잘못된 요청 처리)
		// 타입이 지정되어 안전하다.
		log.info("name : " + name);
		log.info("age : " + age);

		return "ex02";
	}

	// 리스트, 배열처리
	@GetMapping("/ex02List") // http://localhost:80/sample/ex02List?ids=kkk&ids=112&ids=jd3
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		log.info("ids : " + ids);
		return "ex02List";
	}

	@GetMapping("/ex02Array") // http://localhost:80/sample/ex02Array?ids=111&ids=112&ids=jd3
	public String ex02Array(@RequestParam("ids") String[] ids) {
		log.info("Array ids : " + Arrays.toString(ids));
		return "ex02Array";
	}

	// 리스트, 배열처리(dto 사용)
	@GetMapping("/ex02Bean")
	// 전달하려는 url ->
	// http://localhost:80/sample/ex02Bean?list[0].name=kkk&list[0].age=40 ->[]가
	// 유효하지 않은 문자
	// url인코더로 변환후 전달 ->
	// http://localhost:80/sample/ex02Bean?list%5B0%5D.name%3Dkkk%26list%5B0%5D.age%3D40
	public String ex02Bean(SampleDTOList list) {
		log.info("list dtos : " + list); // list dtos : SampleDTOList(list=[SampleDTO(name=null, age=0)])
		return "ex02Bean";
	}

	// 파라미터의 변환(문자열->Date) : @InitBinder @DateTimeFormat
	@GetMapping("/initDate") // http://localhost:80/sample/initDate?title=study&dueDate=2024/08/14&check=true
	// [test]
	// http://localhost:80/sample/initDate?title=study&dueDate=2024/08/14&check=true
	// ->날짜형식 변경시 잘못된 요청
	public String initDate(TodoDTO todoDTO) {
		log.info("todo : " + todoDTO);
		return "initDate";
	}

	// model이용한 데이터 전송 : 객체, page 번호 받아서 보내기->model 객체 이용
	// http://localhost:80/sample/ex04 -> 값 없음으로 error
	// http://localhost:80/sample/ex04?name=kkw&age=32&page=2
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
		// int page : model로 보내지 않아 결과 안나옴
		// @ModelAttribute("page") -> int page 파라미터를 화면까지 전달
		log.info("dto : " + dto);
		log.info("page : " + page);
		return "/sample/ex04";
		// jsp 경로 지정 : http://localhost:80/sample/ex04.jsp
		// view : servlet-context.xml에서 담당 -> 실제경로 : /WEB-INF/views/sample/ex04.jsp
	}

	// Controller의 리턴 타입 : String(jsp파일명 = return 지정명.jsp), void(jsp파일명 = 해당
	// url.jsp), VO/DTO(JSON데이터 생성)
	// VO/DTO(JSON데이터 생성) 타입 실습
	// 1. pom.xml : jackson-databind 추가
	// 2. 메서드 활용한 객체전달->JSON 결과 확인
	@GetMapping("/ex06") // http://localhost:80/sample/ex06
	public @ResponseBody SampleDTO ex06() { // @ResponseBody SampleDTO : 응답바디 영역에 객체를 담아서 리턴
		log.info("/ex06 메서드 실행");
		SampleDTO dto = new SampleDTO();
		dto.setName("홍길동");
		dto.setAge(25);

		return dto; // {"name":"홍길동","age":25}
	}

	// 헤더 데이터 전달
	@GetMapping("/ex07") // http://localhost:80/sample/ex07
	public ResponseEntity<String> ex07() {
		log.info("/ex07 메서드 실행");
		String msg = "{\"name\":\"차은우\",\"age\":30}"; // json으로 만들 msg

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		return new ResponseEntity<String>(msg, header, HttpStatus.OK); // HttpHeaders.OK 200 정상 코드임을 보냄
	}

	// 파일업로드 처리
	@GetMapping("/exUpload") // http://localhost:80/sample/exUpload
	public void exUpload() {
		log.info("/exUpload 메서드 실행(파일업로드)......");
	}

	@PostMapping("/exUplodPost") // http://localhost:80/sample/exUplodPost
	public void exUplodPost(ArrayList<MultipartFile> files) { // 업로드 파일을 리스트로 받아 정보 출력
		files.forEach(file -> {
			log.info("--------------------");
			log.info("name : " + file.getName()); // 원본파일 이름 출력
			log.info("size : " + file.getSize()); // 파일크기
			log.info("toString : " + file.toString()); // toString 메서드 실행
		});
	}

}
