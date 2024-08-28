package org.zerock.service;

import java.util.List;

import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;

public interface ReplyService { //구현클래스 필수  ReplyServiceImpl
	
	public int register(ReplyVO vo);  //댓글 등록
	
	public ReplyVO get(Long rno); //댓글 한 개 가져오기
	
	public int modify(ReplyVO vo); //댓글(객체) 수정 후 int 리턴
	
	public int remove(Long rno); //댓글 한개 삭제 후 int 리턴
	
	public List<ReplyVO> getList(Criteria cri, Long bno); //게시글의 번호를 이용해 모든 댓글을 리스트로 출력
	
	public ReplyPageDTO getListPage(Criteria cri, Long bno); //bno를 받아서 댓글 개수와 리스트를 전달한다.

}
