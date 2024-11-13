package kr.seoil.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;

import kr.seoil.service.BoardService;
import kr.seoil.service.CategoryService;
import kr.seoil.util.HttpUtil;
import kr.seoil.vo.BoardVO;
import kr.seoil.vo.CategoryVO;

public class BoardUploadController implements Controller {
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
		String bStdID = request.getParameter("stdId");
		String bContent = request.getParameter("editContent");
		String bTitle = request.getParameter("editTitle");
		String bSubTitle = request.getParameter("editSubTitle");
		String bCategory1 = request.getParameter("editCategory1");
		String bCategory2 = request.getParameter("editCategory2");
		String bCategory3 = request.getParameter("editCategory3");
		String bCategory = null;
		System.out.println(bCategory1 + " "+ bCategory2 + " "+bCategory3 );
		if(bCategory3 == null || bCategory3 == "" || bCategory3 == "none") {
			if(bCategory2 == null || bCategory2 == ""  || bCategory2 == "none") {
				bCategory = bCategory1;
			}else {
				bCategory = bCategory2;
			}
		}else {
			bCategory = bCategory3;
		}
		
		System.out.println(bCategory);
		Pattern pattern_src = null;
		
		int bId = -1;
		String bReg_date = null;
		String image_path = null;
		String realPath = null;
		String tempPath = null;
		String img_name = null;
		String image_tag = null;
		int img_width = 0;
		int img_height = 0;
		
		int result = -1;
		int write = 0;
		
		BoardService service = BoardService.getInstance();
		CategoryService categoryService = CategoryService.getInstance();
		System.out.println(bContent);
		
		if(bContent == null){
			ArrayList<CategoryVO> headList = categoryService.getHeadCategory();
			request.setAttribute("headList", headList);
			HttpUtil.forward(request, response,"/admin/article_editor_reporter.jsp");
		}else {
			pattern_src = Pattern.compile("(?i)<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>"); //src 추출
			Matcher matcher_src = pattern_src.matcher(bContent);
			
			int i = 1;
			
			if(!matcher_src.find()){ //이미지 없는 경우
				result = service.write(bStdID,bTitle,bSubTitle,bContent,bCategory);
				System.out.println("맨글쓰기입니다.");
		    	if(result > 0) {
					System.out.println("이미지 없는 글 작성 완료");
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/board_upload_ok.jsp");
		    	}else{
		    		System.out.println("글 등록 제대로 안됨");
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/board_upload_ok.jsp");
				}
		    	return;
			}
			matcher_src.reset();
			
			while(matcher_src.find()){ //이미지 있을 경우
				image_tag = matcher_src.group(0);
	            image_path = matcher_src.group(1);
	            
				Pattern pattern_width = Pattern.compile("(?i)width[:\\=\"']*(\\d+)*"); //src 추출
				Pattern pattern_height = Pattern.compile("(?i)height[:\\=\"']*(\\d+)*"); //src 추출
				Matcher matcher_width = pattern_width.matcher(image_tag);
				Matcher matcher_height= pattern_height.matcher(image_tag);
	
	            while(matcher_width.find()) {
	            	img_width = Integer.parseInt(matcher_width.group(1));
	            }
	            
	            while(matcher_height.find()) {
	            	img_height = Integer.parseInt(matcher_height.group(1));
	            }
	            System.out.println("이미지 글쓰기.");
	            realPath = request.getSession().getServletContext().getRealPath("/");
	            tempPath = realPath+ image_path;
	            
	            System.out.println(image_path);
	
	            System.out.println(tempPath);
	            int index = image_path.lastIndexOf("/");
	            img_name = image_path.substring(index +1);
	            
	            String replacePath = image_path.replace(img_name,"");
	        	
	            bContent= bContent.replace(replacePath, "upload/ImgPath/");
	            
	            if(write == 0) {
	            	write = 1; //한번만 업데이트하게하기 위함
	            	result = service.write(bStdID,bTitle,bSubTitle,bContent,bCategory);
	            	if(result<=0){
	            		System.out.println("글 등록 제대로 안됨");
		    			request.setAttribute("result", result);
		    			HttpUtil.forward(request, response,"/manage/board_upload_ok.jsp");
	        		}
	            }
	
	            try {
	                File tempFile = FileUtils.getFile(tempPath);
	                File realFile = FileUtils.getFile(realPath+"upload/ImgPath/"+ img_name);
	                FileUtils.moveFile(tempFile, realFile);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            
	            image_path = "upload/ImgPath/"+ img_name;
	            System.out.println(i+"번째 바뀐 경로"+image_path);
	
				BoardVO keyList = new BoardVO();
				
				keyList = service.getKeyForImage(bStdID, bTitle,bContent, bCategory);
				bId = keyList.getbId();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println("keyList의 reg_date"+keyList.getbReg_date());
				bReg_date = sdf.format(keyList.getbReg_date());
				System.out.println("keyList의 reg_date 후"+bReg_date);
				
				result = service.writeImage(image_path,bId, bReg_date, img_name, img_width,img_height);
			}
			
			System.out.println("이미지 있는글 제대로 작성 완료");
			request.setAttribute("result", result);
			HttpUtil.forward(request, response,"/manage/board_upload_ok.jsp");
		}
	}
}
