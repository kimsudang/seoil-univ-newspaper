package kr.seoil.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.seoil.service.BoardService;
import kr.seoil.vo.BoardVO;
import kr.seoil.util.HttpUtil;

public class BoardListController implements Controller {
	private static final int MESSAGE_COUNT_PER_PAGE = 10;
	BoardService service = BoardService.getInstance();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String pageNumber = request.getParameter("page");
		String bCategory = request.getParameter("category");
		System.out.println(bCategory);
		String section = request.getParameter("section");
		ArrayList<BoardVO> recentArticles = service.getRecentBoardList();
		ArrayList<BoardVO> studentColumnArticle = service.getBoardList(1,1,"19");
		ArrayList<BoardVO> professorColumnArticle = service.getBoardList(1,1,"20");
		ArrayList<BoardVO> editorialArticle = service.getBoardList(1,2,"11");
		int currentPageNumber=1;
		
		if(pageNumber != null){
			currentPageNumber = Integer.parseInt(pageNumber);
		}
		
		BoardPageController articles = getBoardLists(currentPageNumber, bCategory);
		request.setAttribute("recentArticles", recentArticles);
		request.setAttribute("studentColumnArticle", studentColumnArticle);
		request.setAttribute("professorColumnArticle", professorColumnArticle);
		request.setAttribute("editorialArticle", editorialArticle);
		request.setAttribute("articles", articles);
		
		if(section != null && !section.equals("")) {
			HttpUtil.forward(request, response,"/board/satirical_list.jsp");
		}else if(bCategory == "14" || bCategory.equals("14")){
			BoardVO boardContent = service.getInfoContent(bCategory);
			request.setAttribute("boardContent", boardContent);
			HttpUtil.forward(request, response,"/board/board_content.jsp");
		}else if(bCategory == "16" || bCategory.equals("16")){
			HttpUtil.forward(request, response,"/board/board_report_form.jsp");
		}else {
			request.setAttribute("bCategory", bCategory);
			HttpUtil.forward(request, response,"/board/board_list.jsp");
		}
	}
	
	private BoardPageController getBoardLists(int currentPageNumber, String bCategory) {
		BoardPageController articles=null;
		
		int messageTotalCount = service.selectCount(bCategory);
		int firstRow = 0, endRow=0;
		
		ArrayList<BoardVO> articleList = null;
		
		if(messageTotalCount>0) {
			firstRow = (currentPageNumber - 1)*MESSAGE_COUNT_PER_PAGE+1;
			endRow = firstRow + MESSAGE_COUNT_PER_PAGE -1;
			articleList = service.getBoardList(firstRow, endRow, bCategory);
		}else {
			currentPageNumber = 1;
		}
		
		articles = new BoardPageController(articleList, 
				messageTotalCount, currentPageNumber, 
				MESSAGE_COUNT_PER_PAGE, firstRow, endRow);
		return articles;
	}


}
