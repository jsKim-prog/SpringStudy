package org.zerock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //모든 필드값을 파라미터로 하는 생성자 = new SampleVO(mno, firstName, lastName);
@NoArgsConstructor //기본생성자 = new SampleVO();
public class SampleVO {
	// RESTController의 객체 처리용
	
	//필드
	private Integer mno;
	private String firstName;
	private String lastName;
	
	//생성자
	
	//메서드

}
