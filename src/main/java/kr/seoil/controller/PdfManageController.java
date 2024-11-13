package kr.seoil.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.seoil.service.PdfService;
import kr.seoil.util.HttpUtil;
import kr.seoil.vo.PdfVO;

public class PdfManageController implements Controller {
	private static final int MESSAGE_COUNT_PER_PAGE = 10;
	PdfService service = PdfService.getInstance();
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
		
		String fReg_date = request.getParameter("fReg_date");
		String btnValue = request.getParameter("btnValue");
		String pdf_no = request.getParameter("pdf_no");
		String pageNumber = request.getParameter("page");
		
		int result = -1;
		

		int currentPageNumber=1;
		
		if(pageNumber != null){
			currentPageNumber = Integer.parseInt(pageNumber);
		}
		
		if(btnValue == null || btnValue.equals("")) {
			PdfPageController articles = getBoardLists(currentPageNumber);
			request.setAttribute("articles", articles);
			HttpUtil.forward(request, response,"/manage/admin_pdf_manage.jsp");
			return;
		}
		else if(btnValue != null && btnValue.equals("delete")) {
			ArrayList<String> pdfFileDeleteList = service.getFileList(Integer.parseInt(pdf_no), fReg_date);
			if(pdfFileDeleteList.size() > 0) {	
				result = service.setPdfFileDel(Integer.parseInt(pdf_no), fReg_date); 
				if(result <= 0) {
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/pdf_delete_ok.jsp");
					return;
				} else {
					for (String path : pdfFileDeleteList) {
					 	File file = new File(request.getSession().getServletContext().getRealPath("/")+path);
						file.delete();
					}
					result = service.setWriteDelete(Integer.parseInt(pdf_no), fReg_date);
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/pdf_delete_ok.jsp");
					return;
				} 
			} else { 
				result = service.setWriteDelete(Integer.parseInt(pdf_no), fReg_date);
				request.setAttribute("result", result);
				HttpUtil.forward(request, response,"/manage/pdf_delete_ok.jsp");
				return;
			}
		}
	}
	

	private PdfPageController getBoardLists(int currentPageNumber) {
		PdfPageController articles=null;
		PdfService service = PdfService.getInstance();
		int messageTotalCount = service.selectAllPdfCount();
		int firstRow = 0, endRow=0;
		
		ArrayList<PdfVO> articleList = null;
		
		if(messageTotalCount>0) {
			firstRow = (currentPageNumber - 1)*MESSAGE_COUNT_PER_PAGE+1;
			endRow = firstRow + MESSAGE_COUNT_PER_PAGE -1;
			articleList = service.getAllPdfList(firstRow, endRow);
		}else {
			currentPageNumber = 1;
		}
		articles = new PdfPageController(articleList, 
				messageTotalCount, currentPageNumber, 
				MESSAGE_COUNT_PER_PAGE, firstRow, endRow);
		return articles;
	}
}
