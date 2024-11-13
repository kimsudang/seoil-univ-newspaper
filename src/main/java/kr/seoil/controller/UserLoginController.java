package kr.seoil.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import kr.seoil.util.HttpUtil;

public class UserLoginController implements Controller {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		String stdId = request.getParameter("userStdID");
		String passwd = request.getParameter("userPW");
		HttpSession session = request.getSession();
		String alreadylogin = (String)session.getAttribute("userStdID");
		
		if(alreadylogin != null) {
			request.setAttribute("already", "error");
			HttpUtil.forward(request, response, "/admin/check_login.jsp");
			return;
		}else {
			if(stdId =="" || passwd == null || passwd =="" || stdId == null) {
				HttpUtil.forward(request, response,"/admin/login.jsp");
			}else {
				request.setAttribute("userStdID",stdId);
				request.setAttribute("userPW",passwd);
				HttpUtil.forward(request, response,"/admin/check_login.jsp");
				return;
			}
		}
	}

}
