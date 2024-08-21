package net.kkwcloud.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

//필수 3가지 -> pom.xml에 spring-test 코드 필수
@RunWith(SpringJUnit4ClassRunner.class) //메서드 별로 테스트(우클릭->runas)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") //참조할 파일
@Log4j2 //log출력용
public class SampleTests {
	@Setter(onMethod_ = @Autowired) //setRestaurant(Restaurant)
	private Restaurant restaurant;  //Restaurant restaurant = new Restaurant(Restaurant);
	
	@Test //import org.junit.Test; 메서드 별로 테스트 진행됨(메서드명 블럭-> 우클릭-> Run As-> Junit)
	public void testExit() {
		assertNotNull(restaurant); //assertNotNull : 
		
		log.info(restaurant);
		log.info("-------------------");
		log.info(restaurant.getChef()); //restaurant 객체에 있는 Chef 필드를 가져와 출력
//		 INFO  net.kkwcloud.sample.SampleTests(testExit26) - Restaurant(chef=Chef(name=null, age=0, regDate=null), restaurantName=null, openTime=null, closeTime=null)
//		 INFO  net.kkwcloud.sample.SampleTests(testExit27) - -------------------
//		 INFO  net.kkwcloud.sample.SampleTests(testExit28) - Chef(name=null, age=0, regDate=null)
	}

}
