package kr.seoil.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.seoil.service.BoardService;
import kr.seoil.util.HttpUtil;
import kr.seoil.vo.BoardVO;

public class ArticleAccessManageController implements Controller {
	private static final int MESSAGE_COUNT_PER_PAGE = 10;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String admin = (String)session.getAttribute("userAdmin");
		if(admin == null) {
			request.setAttribute("result", -1);
			HttpUtil.forward(request, response, "/manage/admin_access_only_page.jsp");
			return;
		}	
		String pageNumber =request.getParameter("page");
		String bId = request.getParameter("bId");
		String bReg_date = request.getParameter("bReg_date");
		String btnValue = request.getParameter("btnValue");
		String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		
		int result = -1;
		int currentPageNumber=1;
		

		BoardService service = BoardService.getInstance();

		
		if(pageNumber != null){
			currentPageNumber = Integer.parseInt(pageNumber);
		}
		
		
		if(bId == null || bId.equals("")) {
			BoardPageController articles = getBoardLists(currentPageNumber, searchType, searchText);
			request.setAttribute("articles", articles);
			HttpUtil.forward(request, response,"/manage/article_access_manage.jsp");
		}
		else if(btnValue != null && btnValue.equals("access")) {
			result = service.setArticleAccess(Integer.parseInt(bId), bReg_date);
			request.setAttribute("result", result);
			HttpUtil.forward(request, response,"/manage/article_access_ok.jsp");
			return;
		}
		else if(btnValue != null && btnValue.equals("defer")) {
			result = service.setArticleDefer(Integer.parseInt(bId), bReg_date);
			request.setAttribute("result", result);
			HttpUtil.forward(request, response,"/manage/article_defer_ok.jsp");
			return;
		}
		
	}
	
	private BoardPageController getBoardLists(int currentPageNumber, String searchType, String searchText) {
		BoardPageController articles=null;
		BoardService service = BoardService.getInstance();
		ArrayList<BoardVO> allArticles = null;

		int firstRow = 0, endRow=0;
		int messageTotalCount =0; 
		System.out.println("페이지 컨트롤러 들어옴");
		
		if(searchText =="" || searchText == null) { //전체
			messageTotalCount = service.selectAllBoardListExceptCount();
			System.out.println("총 메시지 수"+messageTotalCount);
			if(messageTotalCount>0) {
				System.out.println("메시지 존재함");
				firstRow = (currentPageNumber - 1)*MESSAGE_COUNT_PER_PAGE+1;
				endRow = firstRow + MESSAGE_COUNT_PER_PAGE -1;
				System.out.println(firstRow+endRow);
				allArticles = service.getAllBoardListExcept(firstRow, endRow);
			}else {
				currentPageNumber = 1;
			}
		}else {
			messageTotalCount = service.selectAdminSearchCount(searchType, searchText);
			System.out.println("select mypage총 메시지 수"+messageTotalCount);
			if(messageTotalCount>0) {
				System.out.println("메시지 존재함2");
				firstRow = (currentPageNumber - 1)*MESSAGE_COUNT_PER_PAGE+1;
				endRow = firstRow + MESSAGE_COUNT_PER_PAGE -1;
				System.out.println("firstRow+endRow222"+firstRow+endRow);
				allArticles= service.getAdminSearch(firstRow, endRow, searchType, searchText);
			}else {
				currentPageNumber = 1;
			}
		}
		
		articles = new BoardPageController(allArticles, 
				messageTotalCount, currentPageNumber, 
				MESSAGE_COUNT_PER_PAGE, firstRow, endRow, searchType, searchText);
		return articles;
	}
	
}	
