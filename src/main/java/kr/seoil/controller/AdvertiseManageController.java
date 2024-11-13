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

public class AdvertiseManageController implements Controller {
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
		String bId = request.getParameter("bId");
		String bReg_date = request.getParameter("bReg_date");
		String pageNumber = request.getParameter("page");
		String bCategory = "18";
		String searchText = request.getParameter("searchText");
		String btnValue = request.getParameter("btnValue");
		
		int result = -1;
		int currentPageNumber=1;
		
		if(pageNumber != null){
			currentPageNumber = Integer.parseInt(pageNumber);
		}
		
		BoardService service = BoardService.getInstance();
		
		if(bId == null || bId.equals("")) {
			BoardPageController articles = getBoardLists(currentPageNumber, bCategory, searchText);
			request.setAttribute("articles", articles);
			HttpUtil.forward(request, response,"/manage/advertise_manage.jsp");
			return;
		}
		else if(btnValue != null && btnValue.equals("access")) {
			result = service.setArticleAccess(Integer.parseInt(bId), bReg_date);
			request.setAttribute("result", result);
			HttpUtil.forward(request, response,"/manage/advertise_access_ok.jsp");
			return;
		}
		else if(btnValue != null && btnValue.equals("delete")) {
			ArrayList<String> boardImageDeleteList = service.getImgPreviewList(Integer.parseInt(bId), bReg_date);
			if(boardImageDeleteList.size() > 0) {
				result = service.setArticleImageDel(Integer.parseInt(bId), bReg_date); 
				if(result <= 0) {
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/advertise_delete_ok.jsp");
					return;
				} else {
					for (String path : boardImageDeleteList) {
					 	File file = new File(request.getSession().getServletContext().getRealPath("/")+path);
						file.delete();
					}
					result = service.setArticleDelete(Integer.parseInt(bId), bReg_date);
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/advertise_delete_ok.jsp");
					return;
				} 
			}
			else { 
				result = service.setArticleDelete(Integer.parseInt(bId), bReg_date);
				request.setAttribute("result", result);
				HttpUtil.forward(request, response,"/manage/advertise_delete_ok.jsp");
				return;
			}
		}
	}
	
	private BoardPageController getBoardLists(int currentPageNumber, String bCategory, String searchText) {
		BoardPageController articles=null;
		BoardService service = BoardService.getInstance();
		ArrayList<BoardVO> allArticles = null;

		int firstRow = 0, endRow=0;
		int messageTotalCount =0; 
		System.out.println("페이지 컨트롤러 들어옴");
		
		if(searchText =="" || searchText == null) {
			messageTotalCount = service.selectAllCount(bCategory);
			System.out.println("총 메시지 수"+messageTotalCount);
			if(messageTotalCount>0) {
				System.out.println("메시지 존재함");
				firstRow = (currentPageNumber - 1)*MESSAGE_COUNT_PER_PAGE+1;
				endRow = firstRow + MESSAGE_COUNT_PER_PAGE -1;
				System.out.println(firstRow+endRow);
				allArticles = service.getAllAdvertise(firstRow, endRow, bCategory);
			}else {
				currentPageNumber = 1;
			}

		}else {
			messageTotalCount = service.selectAdvertiseSearchCount(bCategory, searchText);
			System.out.println("select advertise총 메시지 수"+messageTotalCount);
			if(messageTotalCount>0) {
				System.out.println("메시지 존재함2");
				firstRow = (currentPageNumber - 1)*MESSAGE_COUNT_PER_PAGE+1;
				endRow = firstRow + MESSAGE_COUNT_PER_PAGE -1;
				System.out.println("firstRow+endRow2: "+firstRow+endRow);
				allArticles= service.getAdvertiseSearch(firstRow, endRow, bCategory, searchText);
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