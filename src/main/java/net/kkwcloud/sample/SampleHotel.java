package net.kkwcloud.sample;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@ToString 	// lombok이 객체출력을 문자열로
@Getter		// 게터만 생성
//@AllArgsConstructor // 필드에 있는 모든 값을 이용해서 생성자를 만듬.
//@NoArgsConstructor 	// 기본생성자용 new SampleHotel();
//@RequiredArgsConstructor // @NonNull이나 final이 붙은 필드만 생성자값으로 넣음(커스텀)
public class SampleHotel {

	//@NonNull
	private Chef chef;
	private/* final */String HotelName; 
	private Date HotelAge ; 
	
//	public SampleHotel(Chef chef) {  // SampleHotel sampleHotel = new SampleHotel(chef);
//		this.chef = chef;
//		// 생성자 -> 객체 생성시 Chef를 만듬
//	}
}
