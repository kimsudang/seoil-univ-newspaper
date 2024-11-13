package kr.seoil.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.seoil.service.BoardService;
import kr.seoil.util.HttpUtil;
import kr.seoil.vo.BoardVO;

public class BoardContentPreviewController implements Controller {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		BoardService service = BoardService.getInstance();
		String idxno = request.getParameter("idxno");
		String tempdate= idxno.substring(0, 12); 
		int bId = Integer.parseInt(idxno.substring(12)); 
		
		ArrayList<BoardVO> recentArticles = service.getRecentBoardList();
		ArrayList<BoardVO> studentColumnArticle = service.getBoardList(1,1,"19");
		ArrayList<BoardVO> professorColumnArticle = service.getBoardList(1,1,"20");
		ArrayList<BoardVO> editorialArticle = service.getBoardList(1,2,"11");
		String bStdID = request.getParameter("id");
		Timestamp bReg_date = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		Date d;
		try {
			d = sdf.parse(tempdate);
			bReg_date = new Timestamp(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		BoardVO boardContent = service.getBoardDetailPreviewContent(bId, bReg_date, bStdID);
		request.setAttribute("recentArticles", recentArticles);
		request.setAttribute("studentColumnArticle", studentColumnArticle);
		request.setAttribute("professorColumnArticle", professorColumnArticle);
		request.setAttribute("editorialArticle", editorialArticle);
		request.setAttribute("boardContent", boardContent);
		HttpUtil.forward(request, response,"/board/board_content.jsp");
	}
}
