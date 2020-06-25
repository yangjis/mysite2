package com.javaex.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		rq.setCharacterEncoding("UTF-8");
		String action = rq.getParameter("action");
		
		if("joinForm".equals(action)) {
			System.out.println(action);
			
			WebUtil.forword(rq, rs, "/WEB-INF/views/user/joinForm.jsp");
			
		}else if("join".equals(action)){
			System.out.println(action);
			
			String id = rq.getParameter("id");
			String password = rq.getParameter("password");
			String name = rq.getParameter("name");
			String gender = rq.getParameter("gender");
			
			UserDao dao = new UserDao();
			dao.insert(new UserVo(id, password, name, gender));
			
			WebUtil.forword(rq, rs, "/WEB-INF/views/user/joinOK.jsp");
			
		}else if("loginForm".equals(action)) {
			System.out.println(action);
			WebUtil.forword(rq, rs, "/WEB-INF/views/user/loginForm.jsp");
			
		}else if("login".equals(action)) {
			System.out.println(action);
			
			String id = rq.getParameter("id");
			String password = rq.getParameter("password");
			
			UserDao dao = new UserDao();
			UserVo authVo = dao.login(id, password);
			
			if(authVo.getId() == null) { //로그인 실패
				System.out.println("로그인 실패");
				WebUtil.redirect(rq, rs, "/mysite2/user?action=loginForm&result=fail");
				
			}else {
				//로그인성공
				//세션영역에 값을 추가
				HttpSession session = rq.getSession();
				session.setAttribute("authUser", authVo);
				
				WebUtil.redirect(rq, rs, "/mysite2/main");
			}
			
		}else if("logout".equals(action)) {
			System.out.println("logout");
			HttpSession session = rq.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(rq, rs, "/mysite2/main");
		}else if("modifyForm".equals(action)) {
			System.out.println(action);
			WebUtil.forword(rq, rs, "/WEB-INF/views/user/modifyForm.jsp");
			
		}else if("modify".equals(action)) {
			System.out.println(action);
			
			int no = Integer.parseInt(rq.getParameter("no"));
			String id = rq.getParameter("id");
			String inputPassword = rq.getParameter("password");
			String name = rq.getParameter("name");
			String gender = rq.getParameter("gender");
			
			UserDao dao = new UserDao();
			UserVo authVo = new UserVo(no, id, inputPassword,name,gender);
			dao.update(authVo);
			
			HttpSession session = rq.getSession();
			session.setAttribute("authUser", authVo);
			WebUtil.redirect(rq, rs, "/mysite2/main");
			
			
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
