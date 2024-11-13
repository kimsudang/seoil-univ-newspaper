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

public class MypageManageController implements Controller {
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
		String pageNum =request.getParameter("page");
		String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		String bId = request.getParameter("bId");
		String bStdID = (String)session.getAttribute("userStdID");
		
		int currentPageNumber=1;
		
		if(pageNum != null){
			currentPageNumber = Integer.parseInt(pageNum);
		}
		
		if(bId == null || bId.equals("")) {
			BoardPageController articles = getBoardLists(currentPageNumber, bStdID, searchType, searchText);
			request.setAttribute("articles", articles);
			request.setAttribute("bStdID", bStdID);
			HttpUtil.forward(request, response,"/manage/mypage_manage.jsp");
		}
	}
	
	private BoardPageController getBoardLists(int currentPageNumber, String bStdID, String searchType, String searchText) {
		BoardPageController articles=null;
		BoardService service = BoardService.getInstance();
		ArrayList<BoardVO> allArticles = null;

		int firstRow = 0, endRow=0;
		int messageTotalCount =0; 
		System.out.println("페이지 컨트롤러 들어옴");
		
		if(searchText =="" || searchText == null) {
			messageTotalCount = service.selectUserCount(bStdID);
			System.out.println("총 메시지 수"+messageTotalCount);
			if(messageTotalCount>0) {
				System.out.println("메시지 존재함");
				firstRow = (currentPageNumber - 1)*MESSAGE_COUNT_PER_PAGE+1;
				endRow = firstRow + MESSAGE_COUNT_PER_PAGE -1;
				System.out.println(firstRow+endRow);
				allArticles = service.getUserAllBoard(firstRow, endRow, bStdID);
			}else {
				currentPageNumber = 1;
			}

		}else {
			messageTotalCount = service.selectMyPageSearchCount(bStdID, searchType, searchText);
			System.out.println("select mypage총 메시지 수"+messageTotalCount);
			if(messageTotalCount>0) {
				System.out.println("메시지 존재함2");
				firstRow = (currentPageNumber - 1)*MESSAGE_COUNT_PER_PAGE+1;
				endRow = firstRow + MESSAGE_COUNT_PER_PAGE -1;
				System.out.println("firstRow+endRow222"+firstRow+endRow);
				allArticles= service.getMyPageSearch(firstRow, endRow, bStdID, searchType, searchText);
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
