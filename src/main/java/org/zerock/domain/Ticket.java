package org.zerock.domain;

import lombok.Data;

@Data
public class Ticket {
	//URL을 통해 JSON 타입으로 받아 객체처리 테스트
	
	private int tno; //티켓번호
	private String owner; //티켓주인
	private String grade; //티켓랭크

}
