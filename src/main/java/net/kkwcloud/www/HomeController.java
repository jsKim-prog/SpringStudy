package net.kkwcloud.www;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

/**
 * Handles requests for the application home page.
 */

@Data // dto 객체 관리용
@Controller // 분기를 담당
@Log4j2 // System.out.println->log로 콘솔 출력(파일로 보관 가능) ->**jsp 필터처리(log보관)
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET) // http://localhost:포트/ 요청시 응답하는 메서드
	public String home(Locale locale, Model model) { // Model model request 영역처럼 도는 스프링용 메모리 영역

		// System.out.println->log로 콘솔 출력(파일로 보관 가능)
		log.info("Log4j2........................");
		log.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate); // model 영역에 값을 생성하여 넣는다.

		return "home"; // spring의 controller 역할을 담당하는 servlet-context.xml에 가서 처리
		// <beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		// <beans:property name="prefix" value="/WEB-INF/views/" />
		// <beans:property name="suffix" value=".jsp" />
		// 결론 : /WEB-INF/views/home.jsp 로 완성
	}

}
