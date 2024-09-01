package org.zerock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mapper.Sample1Mapper;
import org.zerock.mapper.Sample2Mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SampleTxServiceImpl implements SampleTxService{
	@Setter(onMethod_ = @Autowired)
	private Sample1Mapper mapp1;
	@Setter(onMethod_ = @Autowired)
	private Sample2Mapper mapp2;
	
	

	@Transactional //트랜잭션 처리
	@Override
	public void addData(String value) {
		log.info("mappe1 실행............");
		mapp1.insertCol1(value);
		log.info("mappe2 실행............");
		mapp2.insertCol2(value);
		log.info("transaction 종료............");
		
	}

}
