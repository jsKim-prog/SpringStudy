package org.zerock.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor //=new ReplyPageDTO(replyCnt, list)
@Getter
public class ReplyPageDTO { //service에서 List<ReplyVO>와 댓글 수를 같이 전달하기 위한 DTO
	
	private int replyCnt; //댓글 개수
	private List<ReplyVO> list; //댓글 리스트

}
