package net.kkwcloud.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TodoDTO {
	private String title; //할일
	@DateTimeFormat(pattern = "yyyy/MM/dd") //2024-08-14 형식으로 입력시 오류발생
	private Date dueDate; //적용시간
	private boolean check; //완료여부

}
