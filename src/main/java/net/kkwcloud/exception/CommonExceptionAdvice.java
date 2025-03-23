package net.kkwcloud.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j2;

@ControllerAdvice //컨트롤러용 지원코드 -> 필수 : servlet-context.xml에 scan 코드를 넣음 <context:component-scan base-package="net.jscode.exception" />
@Log4j2
public class CommonExceptionAdvice {
	
	@ExceptionHandler(Exception.class) //Exception class 해당하는 예외 발생시 
	public String except(Exception ex, Model model) {
		log.error("except 메서드 실행..." + ex.getMessage()); //예외 메시지 콘솔 출력
		model.addAttribute("exception", ex); //model에 예외 정보(name, value) 담기
		log.error(model); //콘솔에 예외 메시지 출력
		
		return "error_page"; //error_page.jsp 프론트에 model을 통해 예외 메시지 보냄
	}
	
	//404에러 처리
	//**test시 /sample/.. 경로는 SampleController 가 무조건 동작하여 해당경로 제외하고 테스트 해야 함
	@ExceptionHandler(NoHandlerFoundException.class) //404 예외발생시
	public String handle404(NoHandlerFoundException ex) {
		return "custom404";  //custom404.jsp 
	}

}
