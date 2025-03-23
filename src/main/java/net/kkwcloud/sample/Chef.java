package net.kkwcloud.sample;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;

@Component 	// 스프링이 객체 관리하게 한다. 
@Data		// DTO 관리용 lombok
//@AllArgsConstructor // 모든 필드값을 활용하는 생성자 만듬
public class Chef {
	
	private String name ;
	private int age;
	private Date regDate ;
}
