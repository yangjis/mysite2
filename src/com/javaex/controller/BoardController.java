package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.Paging;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		rq.setCharacterEncoding("UTF-8");
		
		BoardDao dao = new BoardDao();
		
		String action = rq.getParameter("action");
		if("list".equals(action)) {
			
			int pg = Integer.parseInt(rq.getParameter("pg"));
			
			Paging pgVo = new Paging(5, 5,dao.allpag(),pg);
			
			List<BoardVo> bList = dao.list(pgVo.getWriting_Start(), pgVo.getWriting_End());
			
			rq.setAttribute("pg", pgVo);
			rq.setAttribute("bList", bList);
			
			WebUtil.forword(rq, rs,"/WEB-INF/views/board/list.jsp");
			
		}else if("read".equals(action)) {
			BoardVo boardVo = dao.getBoard(Integer.parseInt(rq.getParameter("no")));
			rq.setAttribute("getBoard", boardVo);
			WebUtil.forword(rq, rs, "/WEB-INF/views/board/read.jsp");
			
		}else if("modifyForm".equals(action)) {
			BoardVo boardVo = dao.getBoard(Integer.parseInt(rq.getParameter("no")));
			rq.setAttribute("getBoard", boardVo);
			WebUtil.forword(rq, rs, "/WEB-INF/views/board/modifyForm.jsp");
		
		}else if("update".equals(action)) {
			int no = Integer.parseInt(rq.getParameter("no"));
			String title = rq.getParameter("title");
			String content = rq.getParameter("content");
			int user_no = Integer.parseInt(rq.getParameter("user_no"));
			
			dao.update(new BoardVo(no, title, content, user_no));
			WebUtil.redirect(rq, rs, "/mysite2/board?action=list&pg=1");
		
		}else if("writeForm".equals(action)) {
			WebUtil.forword(rq, rs, "/WEB-INF/views/board/writeForm.jsp");
		
		}else if("insert".equals(action)) {
			int user_no = Integer.parseInt(rq.getParameter("user_no"));
			String title = rq.getParameter("title");
			String content = rq.getParameter("content");
			dao.insert(new BoardVo(title, content, user_no));
			
			WebUtil.redirect(rq, rs, "/mysite2/board?action=list&pg=1");
		
		}else if("delete".equals(action)) {
			int no = Integer.parseInt(rq.getParameter("no"));
			int user_no = Integer.parseInt(rq.getParameter("user_no"));
			
			dao.delete(no, user_no);
			WebUtil.redirect(rq, rs, "/mysite2/board?action=list&pg=1");
			
		}else if("search".equals(action)) {
			String keyword = rq.getParameter("keyword");
			List<BoardVo> bList = dao.search(keyword);
			rq.setAttribute("bList", bList);
			WebUtil.forword(rq, rs,"/WEB-INF/views/board/list.jsp");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}