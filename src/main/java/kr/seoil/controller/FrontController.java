package kr.seoil.controller;

import java.io.*;
import javax.servlet.*;
import java.util.*;
import javax.servlet.http.*;

public class FrontController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	String charset = null;
	HashMap<String, Controller> list = null;
	
	@Override
	public void init(ServletConfig sc) throws ServletException{
		charset = sc.getInitParameter("charset");
		list = new HashMap<String, Controller>();
		list.put("/home.do", new HomeController());
		
		//기사 관리(보여주기)
		list.put("/news.do", new BoardListController()); // 기사 리스트 보기
		list.put("/satirical.do", new BoardListController()); // 모든 만평 보기
		list.put("/boardContent.do", new BoardContentViewController()); //기사 내부 보기
		list.put("/boardPreview.do", new BoardContentPreviewController()); //미리보기 기사 내부
		list.put("/reportContent.do", new ReportContentViewController()); //기사 내부 보기
		list.put("/pdfViewList.do", new PdfViewListController()); //학보 목록
		list.put("/userReport.do", new NewsReportController()); //기사제보함
		//기사 검색(보여주기)
		list.put("/mainSearch.do", new MainSearchController()); //메인화면 기사 검색
		
		//기사 검색(관리자,기자)
		list.put("/mypageManage.do", new MypageManageController()); //내 기사 관리
		//기사 관리(작성, 수정)
		list.put("/contentupload.do", new BoardUploadController()); //기사 작성
		list.put("/selectbox.do", new SelectController());
		list.put("/contentupdateupload.do", new BoardUpdateController()); //기사 수정
		list.put("/imgupload.do", new ImgUploadController());
		//기사 관리(관리자)
		list.put("/articleManage.do", new ArticleManageController()); //관리자가 승인된 기사 관리
		list.put("/articleAccessManage.do", new ArticleAccessManageController()); //관리자가 승인 전 기사 관리 & 전체 기사 검색
		
		//기자 관리(가입-관리자 관리)
		list.put("/userJoin.do", new UserJoinController());
		list.put("/userLogin.do", new UserLoginController());
		list.put("/userManage.do", new UserManageController());
		list.put("/tempUserManage.do", new TempUserManageController());
		
		//광고 관리(작성, 수정, 관리 - 관리자)
		list.put("/advertiseManage.do", new AdvertiseManageController()); //광고 검색 및 보기
		list.put("/advertiseupdateupload.do", new AdvertiseUpdateController()); //광고 수정
		list.put("/advertiseupload.do", new AdvertiseUploadController()); //광고 등록
		//팝업 관리(관리자)
		list.put("/popupRegister.do", new PopupUploadController()); //팝업 등록
		list.put("/popupmanage.do", new PopupManageController()); // 팝업 관리
		list.put("/popupupdateupload.do", new PopupUpdateController()); //팝업 수정
		//지면 학보 관리(관리자)
		list.put("/pdfRegister.do", new PdfUploadController()); //학보 등록
		list.put("/pdfManage.do", new PdfManageController()); //학보 관리
		
		//기사 제보 관리(관리자)
		list.put("/reportmanage.do", new ReportManageController()); // 기사 제보 관리
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException{
		request.setCharacterEncoding(charset);
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length());
		Controller subController = list.get(path);
		subController.execute(request, response);
	}
	
}
