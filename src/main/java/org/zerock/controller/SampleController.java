package org.zerock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.SampleVO;
import org.zerock.domain.Ticket;

import lombok.extern.log4j.Log4j2;

@Log4j2 // Log4j2.xml 필수
@RequestMapping("/sample") // http://localhost:80/sample/???
@RestController // REST 방식의 컨트롤러로 동작
public class SampleController {
	// REST : Representational State Transfer -> uri를 통해 Method 가 반응한다.
	// Method는 get(select) / post(insert) / put(update) / delete(delete) 로 세분화됨
	// @RequestBody : 요청에 대한 처리(JSON 데이터를 원하는 타입으로 바인딩 처리)
	// @ResponseBody : 응답에 대한 처리(jsp로 view 처리가 아님 -> xml, json으로 보냄)
	// @PathVariable : URL(URI, 주소, 경로)에 있는 값을 파라미터로 추출
	// @CrossOrigin : AJAX(프론트)의 크로스 도메인 문제를 해결

	@GetMapping(value = "gettext", produces = "text/plain; charset=UTF-8") // http://localhost:80/sample/gettext
	// produces(생산하다) : 데이터 타입변환용
	public String getText() {
		log.info("SampleController.getText() 메서드 실행");
		return "안녕하세요 REST controller 입니다."; // **view의 경로로 return하지 않고 화면에 바로 return 값을 띄운다.
	}

	@GetMapping(value = "/getSampleVO", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }) // {Json 출력, xml 출력}
	public SampleVO getSample() {

		return new SampleVO(123, "김", "기원");
	}

	// http://localhost:80/sample/getSample -> xml(기본값)
	// http://localhost:80/sample/getSample.json -> json
	@GetMapping(value = "/getSample") // produces 생략 가능
	public SampleVO getSample2() {

		return new SampleVO(312, "MBC", "아카데미");
	}

	@GetMapping(value = "/getList") // http://localhost:80/sample/getList.json
	public List<SampleVO> getlist() {
		return IntStream.range(1, 10).mapToObj(i -> new SampleVO(i, i + "firstName", i + "lastName"))
				.collect(Collectors.toList());
		// IntStream.range(1, 10) : 정수 타입의 숫자를 1~10까지 반복생성용
		// [{"mno":1,"firstName":"1firstName","lastName":"1lastName"},
		// {"mno":2,"firstName":"2firstName","lastName":"2lastName"},
		// {"mno":3,"firstName":"3firstName","lastName":"3lastName"},
		// {"mno":4,"firstName":"4firstName","lastName":"4lastName"},
		// {"mno":5,"firstName":"5firstName","lastName":"5lastName"},
		// {"mno":6,"firstName":"6firstName","lastName":"6lastName"},
		// {"mno":7,"firstName":"7firstName","lastName":"7lastName"},
		// {"mno":8,"firstName":"8firstName","lastName":"8lastName"},
		// {"mno":9,"firstName":"9firstName","lastName":"9lastName"}]
	}

	@GetMapping(value = "/getMap") // http://localhost:80/sample/getMap.json
	public Map<String, SampleVO> getMap() {
		Map<String, SampleVO> map = new HashMap<String, SampleVO>();
		map.put("kkw", new SampleVO(312, "MBC아카데미", "클라우드풀스텍"));
		map.put("mgs", new SampleVO(312, "MBC아카데미", "클라우드풀스텍-반장"));
		map.put("ysy", new SampleVO(312, "MBC아카데미", "클라우드풀스텍-1조장"));
		map.put("ysh", new SampleVO(312, "MBC아카데미", "클라우드풀스텍-2조장"));
		map.put("lyh", new SampleVO(312, "MBC아카데미", "클라우드풀스텍-3조장"));
		return map;
		// {"kkw":{"mno":312,"firstName":"MBC아카데미","lastName":"클라우드풀스텍"},
		// "mgs":{"mno":312,"firstName":"MBC아카데미","lastName":"클라우드풀스텍-반장"},
		// "lyh":{"mno":312,"firstName":"MBC아카데미","lastName":"클라우드풀스텍-3조장"},
		// "ysy":{"mno":312,"firstName":"MBC아카데미","lastName":"클라우드풀스텍-1조장"},
		// "ysh":{"mno":312,"firstName":"MBC아카데미","lastName":"클라우드풀스텍-2조장"}}
	}

	// RestController의 단점 : 넘어온 값이 정상값인지 비정상값인지 알려줄 필요가 있다. **-> 넘어온 값들이 모두 문자이기 때문
	// ResposeEntity : 데이터와 함께 http 헤더에 상태값(200, 400, 500) 등을 같이 전달

	@GetMapping(value = "/check", params = { "height", "weight" })
	// http://localhost:80/sample/check.json?height=140&weight=80 -> 502 에러
	// http://localhost:80/sample/check.json?height=160&weight=80 -> 200ok
	public ResponseEntity<SampleVO> check(Double height, Double weight) {
		// height가 150 보다 작으면 오류로 판단-> 502 오류 값을 보낼 것!
		SampleVO vo = new SampleVO(312, "" + height, "" + weight); // ""+ 는 String 처리용
		ResponseEntity<SampleVO> result = null;
		if (height < 150) { // url을 통해 넘어온 heigh 값이 150보다 작으면
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo); // BAD_GATEWAY : 502 오류
		} else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo); // HttpStatus.OK : 200 상태(정상)
		}
		return result;
	}

	// @PathVariable -> RestController에는 최대한 많은 정보를 가지고 데이터를 처리하려고 한다.
	// http://localhost:80/sample/{sno}/page/{pno}/amount/{amount} **-> 페이징 처리, =을
	// 쓰지 않고 변수로 처리
	@GetMapping("/product/{cat}/{pid}") // http://localhost:80/sample/product/happy/312.json **-> {cat}=happy, {pid}=312
	public String[] getPath(@PathVariable("cat") String cat, @PathVariable("pid") Integer pid) {
		return new String[] { "category:" + cat, "productid:" + pid };
		// ["category:happy","productid:312"]
	}

	// @RequestBody -> 전달된 요청(Request)의 내용(Body)를 이용해서 해당 파라미터의 타입으로 변환 요구함
	// 외부 API가 JSON을 이용해서 값을 달라고 요청을 하면 (들어오는 값이 JSON) 처리해야 함
	
	@PostMapping("/ticket") // http://localhost:80/sample/ticket
	//@PostMapping은 url을 통해서 테스트가 불가능함(Junit에서 하던지, postman을 이용함)
	public Ticket convert(@RequestBody Ticket ticket) {
		log.info("SampleController.convert() 메서드 실행 " + ticket); //toString 처리됨
		return ticket;
	}
	
	

}
