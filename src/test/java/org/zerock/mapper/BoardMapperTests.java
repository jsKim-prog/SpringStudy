package org.zerock.mapper;

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
public class BoardMapperTests { // 테스트용 코드
	@Setter(onMethod_ = @Autowired) // 생성자 자동주입
	private BoardMapper mapper;

	@Test // 메서드별로 테스트 Junit
	public void testGetList() {
		mapper.getList().forEach(board -> log.info(board));
	}

	@Test // 보드 객체 삽입용 테스트
	public void testInsert() {
		BoardVO boardVO = new BoardVO(); // 빈 객체 생성
		boardVO.setTitle("mapper로 만든 제목");
		boardVO.setContent("mapper로 만든 내용");
		boardVO.setWriter("mapper사용자"); // 객체에 내용 삽입 완료

		mapper.insert(boardVO);

		log.info("입력된 객체 : " + boardVO);

		// BoardVO(bno=0, title=mapper로 만든 제목, content=mapper로 만든 내용, writer=mapper사용자,
		// regdate=null, updateDate=null)
		// DB에서 작업된 영역들은 0, null로 표시
	}

	@Test
	public void testInsertSelectKey() {
		BoardVO boardVO = new BoardVO();

		boardVO.setTitle("번호생성 먼저 제목");
		boardVO.setContent("번호생성 먼저 내용");
		boardVO.setWriter("번호생성 먼저 작성자");

		mapper.insertSelectKey(boardVO);

		log.info("입력된 객체 : " + boardVO);
		// BoardVO(bno=9, title=번호생성 먼저 제목, content=번호생성 먼저 내용, writer=번호생성 먼저 작성자,
		// regdate=null, updateDate=null)
	}

	@Test
	public void testRead() {
		BoardVO boardVO = mapper.read(5L);
		log.info("출력객체 : " + boardVO);
	}

	@Test
	public void testUpdate() {
		BoardVO boardVO = new BoardVO();

		boardVO.setBno(5L); // 찾을 번호
		boardVO.setTitle("수정한 제목");
		boardVO.setContent("수정한 내용");
		boardVO.setWriter("수정자");

		int count = mapper.update(boardVO);
		log.info("수정된 개수 : " + count);
		log.info("수정된 객체 : " + boardVO);
	}

	@Test
	public void testDelete() {
		log.info("삭제한 개수 : " + mapper.delete(8L));

	}

}
