package org.zerock.mapper; //DB와 영속성을 가진 패키지

import java.util.List;

//import org.apache.ibatis.annotations.Select;
import org.zerock.domain.BoardVO;

public interface BoardMapper {
	//interface 로 선언한 이유는 추상메서드와 xml을 결합하여 구현클래스를 사용하는 mybatis
	//xml을 생성할 때는 resources 안의 폴더를 계층별로 만들고 파일명을 interface와 같이 xml을 생성
	
	//interface의 자체적인 추상메서드를 활용
	//@Select("select * from tbl_board where bno>0") // where bno>0 -> bno가 pk라 인덱싱이 되어 있어 빠름
	public List<BoardVO> getList(); // 인터페이스 안에는 추상메서드 임
	// 리턴은  List<BoardVO> 이므로 배열 안쪽에 객체가 BoardVO로 완성됨
	
	// insert 1 - board 삽입용 코드
	public void insert(BoardVO board);
	
	// insert 2 -삽입할 번호를 먼저 파악 후 게시물 등록
	public void insertSelectKey(BoardVO board);
	
	// read : 게시물의 번호를 받아 객체를 출력한다.
	public BoardVO read(Long bno);
	
	//update : 게시물의 번호를 받아 객체를 수정한다.
	public int update(BoardVO boardVO);
	
	//delete : 게시물의 번호를 받아 객체를 삭제한다.
	public int delete(Long bon);
	

}
