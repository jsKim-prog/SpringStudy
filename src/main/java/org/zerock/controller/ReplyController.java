package org.zerock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;
//import org.zerock.service.BoardService;
import org.zerock.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController //REST로 응답함 -> view(jsp)가 아닌 json, xml로 나옴
@RequestMapping("/replies") // http://localhost:80/replies/??
@Log4j2
@AllArgsConstructor //new ReplyController(ReplyService);
public class ReplyController { //Rest 방식의 컨트롤러로 구현 + Ajax 처리함
	
	private ReplyService service;
	//private BoardService bservice;
	
	@PostMapping(value = "/new", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE) //입력값은 json으로
	// http://localhost:80/replies/new
	public ResponseEntity<String> create(@RequestBody ReplyVO vo){
		//리턴은 200 | 500 으로 처리된다.
		log.info("ReplyVO 객체 json 입력값 : " + vo); //파라미터로 넘어온 값 출력테스트
		int insertCount = service.register(vo); //sql 처리 후의 결과값이 1 | 0 이 나옴
		log.info("service + mapper 처리결과 : " + insertCount); //service + mapper 처리결과 : 1
				
		return insertCount==1 ? new ResponseEntity<>("success", HttpStatus.OK) //200 정상
				:new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500서버 오류
		//삼항연산자나 if로 리턴을 할 때 정상처리인지 오류값인지를 전달해야 한다.
		//200 OK 556 ms 175 B

	}
	
	//http://localhost:80/replies/pages/11/1 -> xml
	//http://localhost:80/replies/pages/11/1.json -> json
	@GetMapping(value = "/pages/{bno}/{page}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<ReplyVO>> getList(@PathVariable("page") int page, @PathVariable("bno") Long bno){
		log.info("ReplyController.getList() 메서드 실행. ");
		log.info("찾을 번호 : " + bno);
		log.info("페이지 번호 : " + page);
		//찾을 번호 : 11
		 //INFO  org.zerock.controller.ReplyController(getList52) - 페이지 번호 : 1
		 //INFO  org.zerock.controller.ReplyController(getList55) - 페이지 Criteria : Criteria(pageNum=1, amount=10, type=null, keyword=null)
		
		Criteria cri = new Criteria(page, 10); //현재 페이지와 리스트 개수를 전달
		log.info("페이지 Criteria : " + cri);
		
		return new ResponseEntity<>(service.getList(cri, bno), HttpStatus.OK); //200 정상
	}
	
	//http://localhost:80/replies/4 -> xml
	//http://localhost:80/replies/4.json -> json	
	@GetMapping(value = "/{rno}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno){
		log.info("ReplyController.get() 메서드 실행. ");
		log.info("찾을rno  : " + rno);
		return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
		//{"rno":4,"bno":8,"reply":"댓글8","replyer":"kkw","replyDate":1724723528000,"updateDate":1724723528000}
		//200 OK 967 ms
	}
	
	//http://localhost:80/replies/4 
	@DeleteMapping(value = "/{rno}", produces = MediaType.TEXT_PLAIN_VALUE) //json으로 나올 필요가 없음
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno){
		log.info("ReplyController.remove() 메서드 실행. ");
		log.info("삭제할 rno  : " + rno);
		return service.remove(rno)==1 ? new ResponseEntity<>("success", HttpStatus.OK) //200 정상
				:new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		
		// success
		//200 OK 468 ms

	}
	
	//http://localhost:80/replies/5
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, value = "/{rno}", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno){
		//(이미 폼(form)에 있는 값, 수정할 번호)
		vo.setRno(rno); //이미 가지고 있는 객체에 url의 rno 값을 넣음
		log.info("ReplyController.modify() 메서드 실행. ");
		log.info("수정할 객체  : " + vo);
		service.modify(vo);
		
		return service.modify(vo)==1? 
				new ResponseEntity<>("success", HttpStatus.OK) //200 정상
				:new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); //500 서버오류
	}

}
