package org.zerock.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
// Java Config
// @ContextConfiguration(classes = {org.zerock.config.RootConfig.class} )
@Log4j2
public class ReplyMapperTests {

	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;

	@Test
	public void testMapper() {
		
		log.info(mapper); 
		//INFO  org.zerock.mapper.ReplyMapperTests(testMapper30) - org.apache.ibatis.binding.MapperProxy@4d27d9d
	}
	
	@Test
	public void testCreate() {
		ReplyVO vo = new ReplyVO();
		vo.setBno(11L);
		vo.setReply("Mapper 댓글 테스트");
		vo.setReplyer("Mapper");
		
		mapper.insert(vo);		
	}
	
	@Test
	public void testRead() {
		Long targetRno = 10L;
		ReplyVO vo = mapper.read(targetRno);
		log.info(targetRno+"번 댓글 : "+vo);
		//3번 댓글 : ReplyVO(rno=3, bno=9, reply=댓글9, replyer=kkw, replyDate=Tue Aug 27 10:52:07 KST 2024, updateDate=Tue Aug 27 10:52:07 KST 2024)
		//10번 댓글 : ReplyVO(rno=10, bno=8, reply=댓글8, replyer=kkw, replyDate=Tue Aug 27 10:52:18 KST 2024, updateDate=Tue Aug 27 10:52:18 KST 2024)
	}
	
	@Test
	public void testUpdate() {
		Long targetRno = 10L;
		ReplyVO vo = mapper.read(targetRno); //10번 객체를 먼저 가져온다.
		vo.setReply("mapper로 수정한 댓글");
		int count = mapper.update(vo); //수정된 객체를 넣고 결과 받기
		log.info("수정된 결과 개수: " + count); //- 수정된 결과 개수: 1
		log.info("수정된 객체 : " + vo);
		//- 수정된 객체 : ReplyVO(rno=10, bno=8, reply=mapper로 수정한 댓글, replyer=kkw, replyDate=Tue Aug 27 10:52:18 KST 2024, updateDate=Tue Aug 27 10:52:18 KST 2024)
	}
		
	@Test
	public void testDelete() {
		Long targetRno = 8L;
		log.info("delete 결과 : "+ mapper.delete(targetRno));
		//INFO  org.zerock.mapper.ReplyMapperTests(testDelete58) - delete 결과 : 1		
	}
	
	//번호를 이용한 객체가 리스트로 나옴
	@Test
	public void testList() {
		Criteria cri = new Criteria();
		log.info("Criteria : "+cri);
		//Criteria : Criteria(pageNum=1, amount=10, type=null, keyword=null)
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 10L);
		replies.forEach(reply -> log.info(reply));
	}
	
	//댓글 리스트 테스트(페이지번호, bno 넘겨 페이지 받기)
	@Test
	public void testList2() {
		Criteria cri = new Criteria(1, 3);
		List<ReplyVO> replise = mapper.getListWithPaging(cri, 11L);
		replise.forEach(reply -> log.info(reply));
	}


}
