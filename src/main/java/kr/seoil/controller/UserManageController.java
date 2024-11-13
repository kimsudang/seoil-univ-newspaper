package kr.seoil.controller;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

import kr.seoil.util.HttpUtil;
import kr.seoil.vo.UserVO;

import kr.seoil.service.UserService;

public class UserManageController implements Controller {
	private static final int MESSAGE_COUNT_PER_PAGE = 10;
	UserService service = UserService.getInstance();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String admin = (String)session.getAttribute("userAdmin");
		if(admin == null) {
			request.setAttribute("result", -1);
			HttpUtil.forward(request, response, "/manage/admin_access_only_page.jsp");
			return;
		}
		String btnValue = request.getParameter("btnValue");
		String stdId = request.getParameter("stdId");
		String userAdmin = request.getParameter("userAdmin");
		String pageNumber = request.getParameter("page");
		
		int currentPageNumber=1;
		
		if(pageNumber != null){
			currentPageNumber = Integer.parseInt(pageNumber);
		}
		
		if(btnValue == "" || btnValue == null){
			UserPageController articles = getBoardLists(currentPageNumber);
			request.setAttribute("articles", articles);
			HttpUtil.forward(request, response, "/manage/user_manage.jsp");
			return;
			
		}else if(btnValue.equals("delete") && userAdmin.equals("1")){
			request.setAttribute("userAdmin", userAdmin);
			request.setAttribute("result", -1);
			HttpUtil.forward(request, response, "/admin/delete_check.jsp");
			return;
			
		}else if(btnValue.equals("delete") && userAdmin.equals("0")){
			int result =service.userDelete(stdId);
			request.setAttribute("userAdmin", userAdmin);
			request.setAttribute("result", result);
			HttpUtil.forward(request, response, "/admin/delete_check.jsp");
			return;
			
		}else if(btnValue.equals("entrust")) {
			int result = service.adminEntrust(stdId);
			request.setAttribute("result", result);
			request.setAttribute("userAdmin", userAdmin);
			HttpUtil.forward(request, response, "/admin/entrust_check.jsp");
			return;
		}
		
		
	}
	
	private UserPageController getBoardLists(int currentPageNumber) {
		UserPageController articles=null;

		int messageTotalCount = service.selectAllUserCount();
		int firstRow = 0, endRow=0;
		System.out.println("messageTotalCount: "+messageTotalCount);
		ArrayList<UserVO> userList = null;
		
		if(messageTotalCount>0) {
			firstRow = (currentPageNumber - 1)*MESSAGE_COUNT_PER_PAGE+1;
			endRow = firstRow + MESSAGE_COUNT_PER_PAGE -1;
			userList = service.getAllUser(firstRow, endRow);
		}else {
			currentPageNumber = 1;
		}
		
		articles = new UserPageController(userList, 
				messageTotalCount, currentPageNumber, 
				MESSAGE_COUNT_PER_PAGE, firstRow, endRow);
		return articles;
	}



}
