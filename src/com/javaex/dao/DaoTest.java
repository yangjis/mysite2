package com.javaex.dao;

import com.javaex.vo.UserVo;
import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestVo;

public class DaoTest {

	public static void main(String[] args) {

		/* 회원테스트 */
//		UserDao dao = new UserDao();
		//UserVo vo = new UserVo("ekseks3358","1234","양지선","female");
		//System.out.println(dao.insert(vo));
//		System.out.println(dao.login("1111", "1234").toString());
//		System.out.println(dao.update(new UserVo(2, "ekseks", "1234","수정양지선","male")));
		
		/* 방명록테스트 */
//		GuestVo vo = new GuestVo("양지선", "1234", "양지선입니다.");
//		GuestDao dao = new GuestDao();
//		System.out.println(dao.addList(vo));
		
		
		/* 게시판 테스트 */
//		BoardDao dao = new BoardDao();
//		System.out.println(dao.list().toString());
//		System.out.println(dao.getBoard(1));
//		System.out.println(dao.update(new BoardVo(1,"변경 수정","내용 수정",1)));
//		System.out.println(dao.insert(new BoardVo("안녕하세요","이것은 내용입니다.",1)));
//		System.out.println(dao.delete(44,1));
		
		/* 페이징테스트 */
//		System.out.println(dao.list(1,5));
//		System.out.println(dao.allpag());
	}

}
