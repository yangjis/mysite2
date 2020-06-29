package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestVo;

public class BoardDao {

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
	
	public List<BoardVo> list(){
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		
		getConnection();
		try {

			String query = "select b.no boardNo, b.title title, b.content content, b.hit hit, b.reg_date reg_date, u.no userNo, u.name name from board b,(select name, no from users)u where b.no = u.no(+)";

			pstmt = conn.prepareStatement(query); 

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("boardNo");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				int user_no = rs.getInt("userNo");
				String name = rs.getString("name");

				BoardVo boardVo = new BoardVo(no, title, content, hit, reg_date, user_no, name);
				boardList.add(boardVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return boardList;
	}
	
	public BoardVo getBoard(int boardNo) {
		getConnection();
		
		BoardVo vo = new BoardVo();
		
		try {

			String query = "select b.no boardNo, b.title title, b.content content, b.hit+1 hit, b.reg_date reg_date, u.no userNo, u.name name from board b,(select name, no from users)u where b.no = u.no(+) and b.no = ?";

			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("boardNo");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				int user_no = rs.getInt("userNo");
				String name = rs.getString("name");
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setUser_no(user_no);
				vo.setName(name);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		
		return vo;
	}
	
}
