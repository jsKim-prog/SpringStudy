package org.zerock.service;

import org.springframework.stereotype.Service;

@Service
public class SampleServiceImpl implements SampleService { // AOP Test 위한 service 계층(구현 클래스)

	@Override
	public Integer doAdd(String str1, String str2) throws Exception {
		// 받은 두 개의 문자를 더해서 리턴
		return Integer.parseInt(str1) + Integer.parseInt(str2);
	}

}
