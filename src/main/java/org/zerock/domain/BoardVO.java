package org.zerock.domain; //domain 패키지는 객체를 담당

import java.util.Date;

import lombok.Data;

@Data //getter, setter, toString, equals, 생성자 등을 관여한다.
public class BoardVO {
	//tbl_board에 있는 객체를 담당
	private long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;

}
