package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class GuestDao {
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	public List<GuestVo> getList() {
		List<GuestVo> personList = new ArrayList<GuestVo>();

		getConnection();

		try {

			String query = "select no, name, password, content, reg_date from guestBook";

			pstmt = conn.prepareStatement(query); 

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String reg_date = rs.getString("reg_date");

				GuestVo guestVo = new GuestVo(no, name, password, content, reg_date);
				personList.add(guestVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return personList;

	}
	
	
	
	public int addList(GuestVo gVo) {
		int count = 0;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "insert into guestBook values(seq_guest_no.nextval,?, ?, ?, sysdate)"; 

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, gVo.getName()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, gVo.getPassword()); // ?(물음표) 중 2번째, 순서중요
			pstmt.setString(3, gVo.getContent()); // ?(물음표) 중 3번째, 순서중요

			count = pstmt.executeUpdate(); // 쿼리문 실행


		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
	
	public int delete(int no, String inputNum) {
		
		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "delete from guestBook where no = ? and password = ?"; 
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setInt(1, no);// ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, inputNum);

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println(count + "건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
		
	}

}
