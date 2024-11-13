package kr.seoil.controller;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

import kr.seoil.util.HttpUtil;
import kr.seoil.vo.ReportVO;
import kr.seoil.service.ReportService;



public class ReportManageController implements Controller {
	private static final int MESSAGE_COUNT_PER_PAGE = 10;
	ReportService service = ReportService.getInstance();
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
		String rId = request.getParameter("rId");

		String pageNumber = request.getParameter("page");
		
		int currentPageNumber=1;
		
		if(pageNumber != null){
			currentPageNumber = Integer.parseInt(pageNumber);
		}
		
		if(btnValue == "" || btnValue == null){
			ReportPageController articles = getBoardLists(currentPageNumber);
			request.setAttribute("articles", articles);
			HttpUtil.forward(request, response, "/manage/report_manage.jsp");
			return;
		
		}else if(btnValue.equals("delete")){
			int result =service.reportDelete(rId);
			request.setAttribute("result", result);
			HttpUtil.forward(request, response, "/manage/report_del_ok.jsp");
			return;
			
		}
	}
	
	private ReportPageController getBoardLists(int currentPageNumber) {
		ReportPageController articles=null;

		int messageTotalCount = service.selectAllReportCount();
		int firstRow = 0, endRow=0;
		
		ArrayList<ReportVO> userList = null;
		
		if(messageTotalCount>0) {
			firstRow = (currentPageNumber - 1)*MESSAGE_COUNT_PER_PAGE+1;
			endRow = firstRow + MESSAGE_COUNT_PER_PAGE -1;
			userList = service.getAllReport(firstRow, endRow);
		}else {
			currentPageNumber = 1;
		}

		articles = new ReportPageController(userList, 
				messageTotalCount, currentPageNumber, 
				MESSAGE_COUNT_PER_PAGE, firstRow, endRow);
		return articles;
	}

}
