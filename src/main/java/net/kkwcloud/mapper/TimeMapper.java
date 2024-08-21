package net.kkwcloud.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
	// mybatis는 쿼리문 처리를 xml + interface로 만든다.
	// interface에는 호출할 메서드 명만 기술함
	// xml에는 같은 이름으로 쿼리를 만든다.
	
	// c 추상 method
	
	// r 추상 method
	public String getTimeXML(); //resources/net/kkwcloud/mapper/TimeMapper.xml 의 쿼리를 실행
	
	// u 추상 method
	
	// d 추상 method
	
	// xml을 꼭 사용하지 않아도 된다.
	@Select("select sysdate from dual")
	public String getTime();

}
