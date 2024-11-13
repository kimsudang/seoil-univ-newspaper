package kr.seoil.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.seoil.service.BoardService;
import kr.seoil.util.HttpUtil;
import kr.seoil.vo.BoardVO;

public class MainSearchController implements Controller {
	private static final int MESSAGE_COUNT_PER_PAGE = 10;
	BoardService service = BoardService.getInstance();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String searchText = request.getParameter("searchText");
		System.out.println("searchText: "+searchText);
		String pageNumber = request.getParameter("page");
		ArrayList<BoardVO> recentArticles = service.getRecentBoardList();
		ArrayList<BoardVO> studentColumnArticle = service.getBoardList(1,1,"19");
		ArrayList<BoardVO> professorColumnArticle = service.getBoardList(1,1,"20");
		ArrayList<BoardVO> editorialArticle = service.getBoardList(1,2,"11");
		
		int currentPageNumber=1;
		
		if(pageNumber != null){
			currentPageNumber = Integer.parseInt(pageNumber);
		}

		BoardPageController articles = getBoardLists(currentPageNumber, searchText);
		request.setAttribute("recentArticles", recentArticles);
		request.setAttribute("studentColumnArticle", studentColumnArticle);
		request.setAttribute("professorColumnArticle", professorColumnArticle);
		request.setAttribute("editorialArticle", editorialArticle);
		request.setAttribute("articles", articles);
		HttpUtil.forward(request, response,"/board/main_search.jsp");
	}
	
	private BoardPageController getBoardLists(int currentPageNumber, String searchText) {
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
				allArticles = service.getAllBoardAllListExcept(firstRow, endRow);
			}else {
				currentPageNumber = 1;
			}
		}else {
			messageTotalCount = service.selectMainSearchCount(searchText);
			System.out.println("select mypage총 메시지 수"+messageTotalCount);
			if(messageTotalCount>0) {
				System.out.println("메시지 존재함2");
				firstRow = (currentPageNumber - 1)*MESSAGE_COUNT_PER_PAGE+1;
				endRow = firstRow + MESSAGE_COUNT_PER_PAGE -1;
				System.out.println("firstRow+endRow222"+firstRow+endRow);
				allArticles= service.getMainSearch(firstRow, endRow, searchText);
			}else {
				currentPageNumber = 1;
			}

		}
		
		articles = new BoardPageController(allArticles, 
				messageTotalCount, currentPageNumber, 
				MESSAGE_COUNT_PER_PAGE, firstRow, endRow, searchText);
		return articles;
	}
	
}
