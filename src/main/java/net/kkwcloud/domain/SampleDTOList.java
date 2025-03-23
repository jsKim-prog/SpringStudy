package net.kkwcloud.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SampleDTOList {
	//필드
	private List<SampleDTO> list;
	
	//생성자
	public SampleDTOList() {
		list = new ArrayList<>(); //List<SampleDTO> list=new ArrayList<SampleDTO>();
	}

}
