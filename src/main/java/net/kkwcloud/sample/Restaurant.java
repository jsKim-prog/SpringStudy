package net.kkwcloud.sample;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Setter;

@Component // spring이 관리하게 한다. -> 필수 : root-context.xml의 component-scan에 패키지 추가
@Data //lombok이 dto처럼 관리
public class Restaurant {
	//필드
	@Setter(onMethod_ = @Autowired) //자동으로 setChef() 를 컴파일시 생성한다.
	private Chef chef; //setChef(Chef)
	private String restaurantName;
	private Date openTime;
	private Date closeTime;
	//생성자
	
	//메서드
}
