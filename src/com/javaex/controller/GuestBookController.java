package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;

/**
 * Servlet implementation class GuestBookController
 */
@WebServlet("/guestBook")
public class GuestBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		rq.setCharacterEncoding("UTF-8");
		System.out.println("guestBook");
		
		String action = rq.getParameter("action");
		if("list".equals(action)) {
			GuestDao dao = new GuestDao();
			List<GuestVo> gList = dao.getList();
			
			rq.setAttribute("gList", gList);
			
			WebUtil.forword(rq, rs,"/WEB-INF/views/guestBook/addList.jsp");
			
		}else if("addList".equals(action)) {
			String name = rq.getParameter("name");
			String password = rq.getParameter("pass");
			String content = rq.getParameter("content");
			
			GuestVo vo = new GuestVo(name, password, content);
			GuestDao dao = new GuestDao();
			dao.addList(vo);
			
			WebUtil.redirect(rq, rs,"/mysite2/guestBook?action=list");
			
		}else if("delete".equals(action)) {
			RequestDispatcher rd = rq.getRequestDispatcher("/WEB-INF/views/guestBook/delete.jsp");
			rd.forward(rq, rs);
			
		}else if("deleteAction".equals(action)) {
			String no0 = rq.getParameter("no");
			int no = Integer.parseInt(no0);
			String inputNum = rq.getParameter("pass");
			
			GuestDao dao = new GuestDao();
			dao.delete(no, inputNum);
			
			WebUtil.redirect(rq, rs, "/mysite2/guestBook?action=list");
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
