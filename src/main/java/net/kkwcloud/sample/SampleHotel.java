package net.kkwcloud.sample;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
//import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@ToString //lombok이 객체출력을 문자열
@Getter //lombok이 getter만 생성
//@AllArgsConstructor //필드에 있는 모든 값을 이용해서 생성자를 만듦
//@NoArgsConstructor //기본생성자용
@RequiredArgsConstructor //@NonNull이나 final이 붙은 필드만 생성자 값으로 넣음(커스텀)

public class SampleHotel {
	@NonNull
	private Chef chef;
	private final String hotelName;
	private Date hotelAge; 
	
//	public SampleHotel(Chef chef) { //SampleHotel sampleHotel = new SampleHotel(chef);
//		//생성자 => 객체생성시 Chef를 만듦
//		this.chef=chef;
//	} //@AllArgsConstructor로 대체

}
