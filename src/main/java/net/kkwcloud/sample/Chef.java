package net.kkwcloud.sample;

import java.sql.Date;

import org.springframework.stereotype.Component;

//import lombok.AllArgsConstructor;
import lombok.Data;

@Component // spring이 관리하게 한다.
@Data // lombok - dto관리용
//@AllArgsConstructor // Chef(String, int, Date)-> 모든 필드값을 활용하는 생성자 만듦
public class Chef {
	private String name;
	private int age;
	private Date regDate;

}
