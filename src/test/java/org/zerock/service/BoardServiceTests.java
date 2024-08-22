package org.zerock.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j2
public class BoardServiceTests {

	@Setter(onMethod_ = @Autowired)
	private BoardService service; // setService(BoardService)

	@Test
	public void testExist() {
		// 객체생성 유무 판단용
		log.info(service); // interface를 필드로 생성했는데 서비스 객체를 실행하면 ..Impl이 붙은 class가 실행된다.
		assertNotNull(service);
	}

	@Test
	public void testRegister() {
		BoardVO board = new BoardVO();
		board.setTitle("service로 만든 제목");
		board.setContent("service로 만든 내용");
		board.setWriter("service 사용자");
		service.register(board);
		log.info("등록된 게시물 번호 : " + board.getBno());
	}

	@Test
	public void testGet() {
		log.info(service.get(1L));
	}

	@Test
	public void testGetList() {
		service.getlist().forEach(board -> log.info(board));
	}

	@Test
	public void testUpdate() {
		BoardVO board = service.get(1L);
		if (board == null) {
			log.info("찾는 객체가 없습니다.");
			return;
		}
		board.setTitle("Service에서 수정한 제목");
		log.info("Service에서 수정 메서드 결과 : " + service.modify(board)); // boolean
	}

	@Test
	public void testDelete() {
		log.info("삭제된 결과 : " + service.remove(2L));
	}

}
