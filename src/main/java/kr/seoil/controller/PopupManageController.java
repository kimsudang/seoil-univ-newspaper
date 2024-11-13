package kr.seoil.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.seoil.service.PopupService;
import kr.seoil.util.HttpUtil;
import kr.seoil.vo.PopupVO;

public class PopupManageController implements Controller {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String admin = (String)session.getAttribute("userAdmin");
		if(admin == null) {
			request.setAttribute("result", -1);
			HttpUtil.forward(request, response, "/manage/admin_access_only_page.jsp");
			return;
		}	
		String pReg_date = request.getParameter("pReg_date");
		String btnValue = request.getParameter("btnValue");
		String pop_no = request.getParameter("pop_no");
		
		ArrayList<PopupVO> popupList = new ArrayList<PopupVO>();
		PopupService service = PopupService.getInstance();
		
		int result = -1;

		if(btnValue == null || btnValue.equals("")) {
			popupList = service.getAllPopupList();
			request.setAttribute("articles", popupList);
			HttpUtil.forward(request, response,"/manage/admin_popup_manage.jsp");
			return;
		}
		else if(btnValue.equals("delete")) {
			ArrayList<String> popupImageDeleteList = service.getImgPreviewList(Integer.parseInt(pop_no), pReg_date);
			if(popupImageDeleteList.size() > 0) {	
				result = service.setPopupImageDel(Integer.parseInt(pop_no), pReg_date); 
				if(result <= 0) {
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/popup_delete_ok.jsp");
					return;
				} else {
					for (String path : popupImageDeleteList) {
					 	File file = new File(request.getSession().getServletContext().getRealPath("/")+path);
						file.delete();
					}
					result = service.setWriteDelete(Integer.parseInt(pop_no), pReg_date);
	
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/popup_delete_ok.jsp");
					return;
				} 
			} else { 
				result = service.setWriteDelete(Integer.parseInt(pop_no), pReg_date);
				request.setAttribute("result", result);
				HttpUtil.forward(request, response,"/manage/popup_delete_ok.jsp");
				return;
			}
		}
	}

}
