package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

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

	private void close() {
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
	
	public int insert(UserVo vo) {
		int count = 0;
		getConnection();
		
		try {

			String query = "insert into users VALUES(seq_users_no.nextval, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(query); 

			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getName()); 
			pstmt.setString(4, vo.getGender());
			
			count = pstmt.executeUpdate(); 


		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
	
	public UserVo login(String uid, String u_password) {
		getConnection();
		UserVo vo = new UserVo();
		
		try {

			String query = "select no, id, name, gender from users where id = ? and password = ?";

			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, uid);
			pstmt.setString(2, u_password);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String id = rs.getString("id");
				String gender = rs.getString("gender");
				
				vo.setNo(no);
				vo.setName(name);
				vo.setId(id);
				vo.setGender(gender);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		
		return vo;
	}
	
	public int update(UserVo vo) {
		int count = 0;
		getConnection();

		try {

			String query = "update users set name = ?, gender = ?, password = ? where no=?"; 

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, vo.getName()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, vo.getGender()); // ?(물음표) 중 3번째, 순서중요
			pstmt.setString(3, vo.getPassword());
			pstmt.setInt(4, vo.getNo()); // ?(물음표) 중 4번째, 순서중요

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println(count + "건 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}
	

}
