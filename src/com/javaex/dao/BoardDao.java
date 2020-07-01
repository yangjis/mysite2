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

			String query = "select b.no no, b.title title, b.content content, b.hit hit, TO_CHAR(b.reg_date, 'YY/MM/DD HH24:mi') reg_date, b.user_no user_no, u.name name from board b,(select name, no from users)u where b.user_no = u.no(+) order by reg_date desc";

			pstmt = conn.prepareStatement(query); 

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				int user_no = rs.getInt("user_no");
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

			String query = "select b.no no, b.title title, b.content content, b.hit hit, b.reg_date reg_date, b.user_no user_no, u.name name from board b,(select name, no from users)u where b.user_no = u.no(+) and b.no = ?";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				int user_no = rs.getInt("user_no");
				String name = rs.getString("name");
				
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setUser_no(user_no);
				vo.setName(name);
			}
			
			String query1 = "update board set hit = hit + 1 where no = ?";
			pstmt = conn.prepareStatement(query1);
			pstmt.setInt(1, boardNo);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		
		return vo;
	}
	
	public int update(BoardVo vo) {
		int count = 0;
		getConnection();

		try {

			String query = "update board set title = ?, content = ?, reg_date = sysdate where no=? and user_no=?"; 

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, vo.getTitle()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, vo.getContent()); // ?(물음표) 중 3번째, 순서중요
			pstmt.setInt(3, vo.getNo());
			pstmt.setInt(4, vo.getUser_no()); // ?(물음표) 중 4번째, 순서중요

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println(count + "건 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}
	
	public int insert(BoardVo vo) {
		int count = 0;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "insert into board values(seq_board_no.nextval, ?, ?, 0, sysdate, ?)"; 

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, vo.getTitle()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, vo.getContent()); // ?(물음표) 중 2번째, 순서중요
			pstmt.setInt(3, vo.getUser_no()); // ?(물음표) 중 3번째, 순서중요

			count = pstmt.executeUpdate(); // 쿼리문 실행


		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
	
	public int delete(int no, int user_no) {
		
		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "delete from board where no = ? and user_no = ?"; 
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setInt(1, no);// ?(물음표) 중 1번째, 순서중요
			pstmt.setInt(2, user_no);

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println(count + "건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
		
	}

	public List<BoardVo> search(String keyword){
		List<BoardVo> personList = new ArrayList<BoardVo>();

		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
			String query = "select no, title, content, hit, reg_date, user_no from board where title like ?";

		
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setString(1, '%' + keyword + '%');
			
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				int user_no = rs.getInt("user_no");

				BoardVo vo = new BoardVo(no, title, content, hit, reg_date, user_no);
				personList.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return personList;

	}
	
	public int count() {
		int count = 0;
		getConnection();
		
		try {

			String query = "SELECT rn, no, title, name, hit, reg_date, user_no FROM (SELECT rownum rn, no, title, name, hit, reg_date, user_no FROM (SELECT b.no no, b.title title, u.name name, b.hit hit, to_char(b.reg_date,'yyyy-mm-dd hh24:mi') reg_date, b.user_no user_no FROM board b, users u where b.user_no = u.no order by no desc)) where rn >= 5 and rn <= 10;";

			pstmt = conn.prepareStatement(query); 

			count = pstmt.executeUpdate(); 


		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
}
