package kr.seoil.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import kr.seoil.service.PopupService;
import kr.seoil.util.HttpUtil;
import kr.seoil.vo.PopupVO;


public class PopupUpdateController implements Controller {
	private static final String CHARSET = "UTF-8";
	private static final int LIMIT_SIZE_BYTES = 1024 * 1024;
 
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String admin = (String)session.getAttribute("userAdmin");
		if(admin == null) {
			request.setAttribute("result", -1);
			HttpUtil.forward(request, response, "/manage/admin_access_only_page.jsp");
			return;
		}
		
		PrintWriter out = response.getWriter();
		PopupService service = PopupService.getInstance();
		
		String btnValue = request.getParameter("btnValue");
		String no = request.getParameter("pop_no");
		String pReg = request.getParameter("pReg_date");
		String pReg_date = null;
		int pop_no =  0;
		
		String originPath = null;
		String pop_title = null;
		String start_date = null; 
		String end_date = null;
		String pop_img_path = null;
		String pop_url =  null;
		String newFilePath = null;
		File uploadFile = null;
		Date end_d = null;
		int pop_loc_top =  0;
		int pop_loc_left =  0;
		System.out.println("들어옹ㅂ니다.");
		int result = -1;
		PopupVO keyList = new PopupVO();
		
		if(btnValue != null && btnValue.equals("revise") ) { //popup manage.jsp에서 온거임
			PopupVO popList = service.getPopupUpdateContent(Integer.parseInt(no), pReg);
			request.setAttribute("articles", popList);
			request.setAttribute("state", 1);
			HttpUtil.forward(request, response,"/admin/admin_popup.jsp");
			return;
		}
		else if(btnValue == null) {
		request.setCharacterEncoding(CHARSET);
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
	
	
		if(!isMultipart) {
			result = -1;
			request.setAttribute("result",result);	
			HttpUtil.forward(request, response,"/manage/popup_revise_ok.jsp");
			return;
		}else{
			
			String addPath =  "upload/Popup/";
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd"); //날짜형식으로 폴더만든다.
			Date nowDate = new Date();
			String tempDate = dateformat.format(nowDate)+"/"; // 2022/06/29/형식
			String popPath = addPath +tempDate;
			
			String popupPath = request.getSession().getServletContext().getRealPath("/")+ popPath;

			File popDir = new File(popupPath);
			DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
			fileItemFactory.setRepository(popDir);
	        fileItemFactory.setSizeThreshold(LIMIT_SIZE_BYTES); //임시파일 한계 크기
	        
			ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
			
			try {
				List<FileItem> items = fileUpload.parseRequest(request);
				for(FileItem item : items) {
					if(item.isFormField()) { 
						System.out.println();
						System.out.println(item.getFieldName());
						if("popupTitle".equals(item.getFieldName())) {
							pop_title = new String(item.getString().getBytes("8859_1"), "UTF-8");
							System.out.println(pop_title);
						}else if("popupStartDate".equals(item.getFieldName())){
							String sdate = item.getString();
							System.out.println("sdate"+sdate);
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
							SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date d = sdf.parse(sdate);
							start_date = output.format(d);
							System.out.println("start_date"+start_date);
						}else if("popupEndDate".equals(item.getFieldName())){
							String edate = item.getString();
							System.out.println("edate"+edate);
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
							SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							end_d = sdf.parse(edate);
							end_date = output.format(end_d);
							System.out.println("end_date"+end_date);
						}else if("popupUrl".equals(item.getFieldName())){
							pop_url = new String(item.getString().getBytes("8859_1"), "UTF-8");
						
						}else if("popupTop".equals(item.getFieldName())){
							pop_loc_top = Integer.parseInt(item.getString());
						}else if("popupLeft".equals(item.getFieldName())){
							pop_loc_left = Integer.parseInt(item.getString());
						}else if("pop_no".equals(item.getFieldName())){
							pop_no = Integer.parseInt(item.getString());
						}
						else if("pReg_date".equals(item.getFieldName())){
							String pReg_d = item.getString();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date d = sdf.parse(pReg_d);
							pReg_date = sdf.format(d);
							System.out.println("pReg_date"+pReg_date);
						}else if("originPath".equals(item.getFieldName())){
							originPath = item.getString();
							
						}

					} else { 
						System.out.printf("[파일형식 파라미터] 파라미터명: %s, 파일 명: %s, 파일 크기: %s bytes \n", item.getFieldName(), item.getName(), item.getSize());
						if(item.getSize() > 0 && item.getSize() <= LIMIT_SIZE_BYTES) {
							UUID uuid = UUID.randomUUID();
							
							String separator = File.separator; //파일 구분자. 운영체제별로 다름
							String originFilePath = item.getName();
							int index = originFilePath.lastIndexOf(separator);
							String fileName = originFilePath.substring(index + 1); //파일 이름 가져옴
							String ext = originFilePath.substring(originFilePath.lastIndexOf("."));
							
							if(ext.equals(".jpg") || ext.equals(".png")) {			
								uploadFile = new File(popupPath + separator);
								
								if(!uploadFile.exists()) {
									uploadFile.mkdirs();
								}
								
								newFilePath = uuid + "_"+fileName;
								
								item.write(new File(uploadFile, newFilePath));
									
								
							}else {
								out.println("<script>alert('파일 형식이 올바르지 않습니다. jpg나 png로 업로드해주세요.');</script>");
								item.delete();
								return;
							}
							
						}else if(item.getSize() > LIMIT_SIZE_BYTES){
							out.println("<script>alert('파일 용량이 1MB 이상입니다. 용량을 줄여 업로드해주세요.');</script>");
							item.delete();
							return;
						}else if(item.getSize() < 0){
							out.println("<script>alert('파일이 올바르게 업로드 되지 않았습니다. 다시 업로드해주세요.');</script>");
							item.delete();
							return;
						}
					}
						    
	
				}
				File original = new File(request.getSession().getServletContext().getRealPath("/")+originPath);
				System.out.println(original);
				original.delete();
				
				dateformat = new SimpleDateFormat("yyyy/MM/dd"); 
				String end_path = dateformat.format(end_d)+"/";
				String savePath = addPath +end_path; //end_date기준의 파일
				
				File file = new File(popupPath+newFilePath);   
				File newFile = new File(request.getSession().getServletContext().getRealPath("/")+savePath+newFilePath);         
				FileUtils.moveFile(file, newFile);
				
				pop_img_path = savePath + newFilePath;	

				result = service.boardUpdateContent(pop_no, pReg_date,pop_title, pop_img_path, pop_url, start_date, end_date, pop_loc_top, pop_loc_left);

				if(result <= 0) {
					File fileremove = new File(request.getSession().getServletContext().getRealPath("/")+pop_img_path);
					fileremove.delete();
					request.setAttribute("result",result);	
					HttpUtil.forward(request, response,"/manage/admin_popup_ok.jsp");
					return;
				}
				keyList = service.getKeyForPopup(pop_title, pop_img_path);
				if(keyList == null) {
					File fileremove = new File(request.getSession().getServletContext().getRealPath("/")+pop_img_path);
					fileremove.delete();
					result = -1;
					request.setAttribute("result",result);	
					HttpUtil.forward(request, response,"/manage/popup_revise_ok.jsp");
					return;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				pReg_date = sdf.format(keyList.getpReg_date());
				System.out.println("newFilePath"+newFilePath);
				result = service.popimgUpdateContent(pop_img_path,keyList.getPop_no(),pReg_date, newFilePath);
				
				if(result <= 0) {
					File fileremove = new File(request.getSession().getServletContext().getRealPath("/")+pop_img_path);
					fileremove.delete();
					request.setAttribute("result",result);	
					HttpUtil.forward(request, response,"/manage/popup_revise_ok.jsp");
					return;
				}
			   
				request.setAttribute("result",result);	
				HttpUtil.forward(request, response,"/manage/popup_revise_ok.jsp");
				return;
				
			
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("파일업로드 중 오류가 발생하였습니다.");
			} finally {
	    		try {
	        		if(out != null) { out.close(); }
	        	} catch(Exception e) { e.printStackTrace(); }
	        }
		
				
		}
	}
	}
}