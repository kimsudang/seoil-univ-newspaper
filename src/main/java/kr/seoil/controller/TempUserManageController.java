package kr.seoil.controller;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

import kr.seoil.util.HttpUtil;
import kr.seoil.vo.TempVO;
import kr.seoil.service.TempService;



public class TempUserManageController implements Controller {
	private static final int MESSAGE_COUNT_PER_PAGE = 10;
	TempService service = TempService.getInstance();
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

		String pageNumber = request.getParameter("page");
		
		int currentPageNumber=1;
		
		if(pageNumber != null){
			currentPageNumber = Integer.parseInt(pageNumber);
		}
		
		if(btnValue == "" || btnValue == null){
			TempUserPageController articles = getBoardLists(currentPageNumber);
			request.setAttribute("articles", articles);
			HttpUtil.forward(request, response, "/manage/tempuser_manage.jsp");
			return;
		
		}else if(btnValue.equals("delete")){
			int result =service.tempDelete(stdId);
			request.setAttribute("result", result);
			HttpUtil.forward(request, response, "/manage/access_del_ok.jsp");
			return;
			
		}else if(btnValue.equals("access")) {
			int result = service.tempAccess(stdId);
			request.setAttribute("result", result);
			HttpUtil.forward(request, response, "/manage/access_acc_ok.jsp");
			return;
		}
	}
	
	private TempUserPageController getBoardLists(int currentPageNumber) {
		TempUserPageController articles=null;

		int messageTotalCount = service.selectAllTempCount();
		int firstRow = 0, endRow=0;
		
		ArrayList<TempVO> userList = null;
		
		if(messageTotalCount>0) {
			firstRow = (currentPageNumber - 1)*MESSAGE_COUNT_PER_PAGE+1;
			endRow = firstRow + MESSAGE_COUNT_PER_PAGE -1;
			userList = service.getAllTemp(firstRow, endRow);
		}else {
			currentPageNumber = 1;
		}

		articles = new TempUserPageController(userList, 
				messageTotalCount, currentPageNumber, 
				MESSAGE_COUNT_PER_PAGE, firstRow, endRow);
		return articles;
	}

}
