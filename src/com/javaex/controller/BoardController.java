package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
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
			List<BoardVo> bList = dao.list();
			rq.setAttribute("bList", bList);
			
			WebUtil.forword(rq, rs,"/WEB-INF/views/board/list.jsp");
		}else if("read".equals(action)) {
			BoardVo boardVo = dao.getBoard(Integer.parseInt(rq.getParameter("no")));
			rq.setAttribute("getBoard", boardVo);
			WebUtil.forword(rq, rs, "/WEB-INF/views/board/read.jsp");
		}else if("modifyForm".equals(action)) {
			WebUtil.forword(rq, rs, "/WEB-INF/views/board/modifyForm.jsp");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
