package kr.seoil.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.seoil.service.BoardService;
import kr.seoil.util.HttpUtil;
import kr.seoil.vo.BoardVO;

public class ArticleManageController implements Controller {
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
		String pageNumber = request.getParameter("page");
		String bId = request.getParameter("bId");
		String bReg_date = request.getParameter("bReg_date");
		String btnValue = request.getParameter("btnValue");
		String bMain = request.getParameter("bMain");
		String bAnnounce = request.getParameter("bAnnounce");
		String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		
		int result = -1;
		int currentPageNumber=1;
		int num = 0;
		
		if(pageNumber != null){
			currentPageNumber = Integer.parseInt(pageNumber);
		}
		
		BoardService service = BoardService.getInstance();
				
		if(bId == null || bId.equals("")) {
			BoardPageController articles = getBoardLists(currentPageNumber, searchType, searchText);
			request.setAttribute("articles", articles);
			HttpUtil.forward(request, response,"/manage/article_manage.jsp");
			return;
		}
		else if(btnValue != null && btnValue.equals("setMain")) {
			num = service.selectMainArticleCount();
			if(num == 0) {
				result = service.setMainArticleNone(Integer.parseInt(bId), bReg_date);
				request.setAttribute("result", result);
				HttpUtil.forward(request, response,"/manage/main_article_select_ok.jsp");
				return;
			}else if(num == 1) {
				result = service.setMainArticle(Integer.parseInt(bId), bReg_date);
				request.setAttribute("result", result);
				HttpUtil.forward(request, response,"/manage/main_article_select_ok.jsp");
				return;
			}else if(num >= 2){
				result = service.setMainArticleMult();
				if(result <= 0) {
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/main_article_select_ok.jsp");
					return;
				}else {
					result = service.setMainArticleNone(Integer.parseInt(bId), bReg_date);
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/main_article_select_ok.jsp");
					return;
				}
			}
		}
		else if(btnValue != null && btnValue.equals("setAnnounce")) {
			num = service.selectAnnounceArticleCount();
			if(num == 0) {
				result = service.setAnnounceArticleNone(Integer.parseInt(bId), bReg_date);
				request.setAttribute("result", result);
				HttpUtil.forward(request, response,"/manage/announce_article_select_ok.jsp");
				return;
			}else if(num == 1) {
				result = service.setAnnounceArticle(Integer.parseInt(bId), bReg_date);
				request.setAttribute("result", result);
				HttpUtil.forward(request, response,"/manage/announce_article_select_ok.jsp");
				return;
			}else if(num >= 2){
				result = service.setAnnounceArticleMult();
				if(result <= 0) {
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/announce_article_select_ok.jsp");
					return;
				}else {
					result = service.setAnnounceArticleNone(Integer.parseInt(bId), bReg_date);
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/announce_article_select_ok.jsp");
					return;
				}
			}
		}
		else if(btnValue != null && btnValue.equals("revise")) {
			if(Integer.parseInt(bMain) == 0 || Integer.parseInt(bAnnounce) == 0) {
				result = service.setArticleRevise(Integer.parseInt(bId), bReg_date);
				request.setAttribute("result", result);
				HttpUtil.forward(request, response,"/manage/article_revise_ok.jsp");
				return;
			}
		}
		else if(btnValue != null && btnValue.equals("delete")) {
			if(Integer.parseInt(bMain) == 0 || Integer.parseInt(bAnnounce) == 0){
				ArrayList<String> boardImageDeleteList = service.getImgPreviewList(Integer.parseInt(bId), bReg_date);
				if(boardImageDeleteList.size() > 0) {
					result = service.setArticleImageDel(Integer.parseInt(bId), bReg_date); 
					if(result <= 0) {
						request.setAttribute("result", result);
						HttpUtil.forward(request, response,"/manage/article_delete_ok.jsp");
						return;
					} else {
						for (String path : boardImageDeleteList) {
						 	File file = new File(request.getSession().getServletContext().getRealPath("/")+path);
							file.delete();
						}
						result = service.setArticleDelete(Integer.parseInt(bId), bReg_date);
						System.out.println(result);
						
						request.setAttribute("result", result);
						HttpUtil.forward(request, response,"/manage/article_delete_ok.jsp");
						return;
					} 
				}else { 
					result = service.setArticleDelete(Integer.parseInt(bId), bReg_date);
					System.out.println(result);
					if(result <= 0) {
						request.setAttribute("result", result);
						HttpUtil.forward(request, response,"/manage/article_delete_ok.jsp");
						return;
					}
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/article_delete_ok.jsp");
					return;
				}
			}
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
			messageTotalCount = service.selectAllBoardAllListCount();
			System.out.println("총 메시지 수"+messageTotalCount);
			if(messageTotalCount>0) {
				System.out.println("메시지 존재함");
				firstRow = (currentPageNumber - 1)*MESSAGE_COUNT_PER_PAGE+1;
				endRow = firstRow + MESSAGE_COUNT_PER_PAGE -1;
				System.out.println(firstRow+endRow);
				allArticles = service.getAllAccessBoardListExcept(firstRow, endRow);
			}else {
				currentPageNumber = 1;
			}
		}else {
			messageTotalCount = service.selectAccessSearchCount(searchType, searchText);
			System.out.println("select mypage총 메시지 수"+messageTotalCount);
			if(messageTotalCount>0) {
				System.out.println("메시지 존재함2");
				firstRow = (currentPageNumber - 1)*MESSAGE_COUNT_PER_PAGE+1;
				endRow = firstRow + MESSAGE_COUNT_PER_PAGE -1;
				System.out.println("firstRow+endRow222"+firstRow+endRow);
				allArticles= service.getAccessSearch(firstRow, endRow, searchType, searchText);
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
