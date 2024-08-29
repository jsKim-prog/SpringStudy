package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Service //스프링에서 서비스담당임을 알아감
@Log4j2
public class ReplyServiceImpl implements ReplyService{ //ReplyService 의 구현클래스
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardmapp;

	@Transactional //트랜잭션 처리 : 댓글등록(b_tb : cnt+1, r_tb : vo insert) 
	@Override
	public int register(ReplyVO vo) {
		log.info("ReplyServiceImpl.register() 메서드 실행"+vo);
		boardmapp.updateReplyCnt(vo.getBno(), 1); //댓글개수 업데이트(+1)
		return mapper.insert(vo);
	}

	@Override
	public ReplyVO get(Long rno) {
		log.info("ReplyServiceImpl.get() 메서드 실행"+ rno);
		return mapper.read(rno);
	}

	@Override
	public int modify(ReplyVO vo) {
		log.info("ReplyServiceImpl.modify() 메서드 실행"+ vo);
		return mapper.update(vo);
	}

	@Transactional //트랜잭션 처리 : 댓글삭제(b_tb : cnt-1, r_tb : rno delete) 
	@Override
	public int remove(Long rno) {
		log.info("ReplyServiceImpl.remove() 메서드 실행"+ rno);
		ReplyVO vo = mapper.read(rno); //rno로 객체 생성
		 boardmapp.updateReplyCnt(vo.getBno(), -1); //댓글 개수 업데이트(-1)
		return mapper.delete(rno);
	}

	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {
		log.info("ReplyServiceImpl.getList() 메서드 실행 게시물 번호:"+ bno);
		return mapper.getListWithPaging(cri, bno);
	}

	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {
		log.info("ReplyServiceImpl.getListPage() 메서드 실행/게시물 번호:"+ bno);
		return new ReplyPageDTO(mapper.getCountByBno(bno), mapper.getListWithPaging(cri, bno)) ;
	}

}
