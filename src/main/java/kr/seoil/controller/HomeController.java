package kr.seoil.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.seoil.service.BoardService;
import kr.seoil.service.PopupService;
import kr.seoil.vo.BoardVO;
import kr.seoil.vo.PopupVO;
import kr.seoil.util.HttpUtil;


public class HomeController implements Controller {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		request.setCharacterEncoding("utf-8");

		BoardService service = BoardService.getInstance();
		PopupService popservice = PopupService.getInstance();
		
		ArrayList<BoardVO> recentArticles = service.getRecentBoardList();
		ArrayList<BoardVO> firstArticles = service.getBoardList(1,2,"1");
		ArrayList<BoardVO> secondArticles = service.getBoardList(1,2,"2");
		ArrayList<BoardVO> advertiseArticles = service.getBoardList(1,3,"18");
		ArrayList<BoardVO> studentColumnArticle = service.getBoardList(1,1,"19");
		ArrayList<BoardVO> professorColumnArticle = service.getBoardList(1,1,"20");
		ArrayList<BoardVO> editorialArticle = service.getBoardList(1,2,"11");
		ArrayList<BoardVO> satiricalArticle = service.getBoardList(1,1,"13");
		BoardVO mainArticle = service.getMainNews();
		
		ArrayList<PopupVO> popupArticle = popservice.getPeriodPopupList();
		
		request.setAttribute("recentArticles", recentArticles);
		request.setAttribute("firstArticles", firstArticles);
		request.setAttribute("secondArticles", secondArticles);
		request.setAttribute("advertiseArticles", advertiseArticles);
		request.setAttribute("studentColumnArticle", studentColumnArticle);
		request.setAttribute("professorColumnArticle", professorColumnArticle);
		request.setAttribute("editorialArticle", editorialArticle);
		request.setAttribute("satiricalArticle", satiricalArticle);
		request.setAttribute("popupArticle", popupArticle);
		request.setAttribute("mainArticle", mainArticle);
//		request.setAttribute("pdfArticle", pdfArticle);

		HttpUtil.forward(request, response,"/board/main.jsp");
		
	}
	
}
