package kr.seoil.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.seoil.service.BoardService;
import kr.seoil.service.PdfService;
import kr.seoil.util.HttpUtil;
import kr.seoil.vo.BoardVO;
import kr.seoil.vo.PdfVO;

public class PdfViewListController implements Controller {
	PdfService service = PdfService.getInstance();	
	private static final int MESSAGE_COUNT_PER_PAGE = 10;
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String pdf_no = request.getParameter("pdf");
		String publish_no = request.getParameter("publish");
		System.out.println("pdf+publish: "+pdf_no+"|" +publish_no);

		String pdfViewer = null;
		BoardService bservice = BoardService.getInstance();
		ArrayList<BoardVO> recentArticles = bservice.getRecentBoardList();
		ArrayList<BoardVO> studentColumnArticle = bservice.getBoardList(1,1,"19");
		ArrayList<BoardVO> professorColumnArticle = bservice.getBoardList(1,1,"20");
		ArrayList<BoardVO> editorialArticle = bservice.getBoardList(1,2,"11");
		
		String pageNumber = request.getParameter("page");
		PdfService service = PdfService.getInstance();

		int currentPageNumber=1;
		
		if(pageNumber != null){
			currentPageNumber = Integer.parseInt(pageNumber);
		}
		
		if(pdf_no == null || pdf_no.equals("")) {
			PdfPageController articles = getBoardLists(currentPageNumber);
			request.setAttribute("articles", articles);
			request.setAttribute("recentArticles", recentArticles);
			request.setAttribute("studentColumnArticle", studentColumnArticle);
			request.setAttribute("professorColumnArticle", professorColumnArticle);
			request.setAttribute("editorialArticle", editorialArticle);
			HttpUtil.forward(request, response,"/board/pdf_list.jsp");
			return;
		}else {
			pdfViewer = service.getFilePath(Integer.parseInt(pdf_no), Integer.parseInt(publish_no));
			System.out.println(pdfViewer);
			pdfViewer = pdfViewer.replace("/", "\\");
			//String pdfViewer = request.getSession().getServletContext().getRealPath("/")+pdfViewer;
			System.out.println(pdfViewer);
			request.setAttribute("articles", pdfViewer);
			HttpUtil.forward(request, response,"/board/pdf_viewer.jsp");
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
