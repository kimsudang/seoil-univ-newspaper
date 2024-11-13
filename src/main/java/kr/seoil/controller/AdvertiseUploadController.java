package kr.seoil.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;

import kr.seoil.service.BoardService;
import kr.seoil.util.HttpUtil;
import kr.seoil.vo.BoardVO;

public class AdvertiseUploadController implements Controller {
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
		String bStdID = request.getParameter("stdId");
		String bContent = request.getParameter("editContent");
		String bTitle = request.getParameter("editTitle");
		String bSubTitle = request.getParameter("editSubTitle");
		String bCategory = "18";
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
		
		System.out.println(bContent);
	
		if(bContent == null){
			HttpUtil.forward(request, response,"/admin/advertise_editor_reporter.jsp");
		}else {
			pattern_src = Pattern.compile("(?i)<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>"); //src ����
			Matcher matcher_src = pattern_src.matcher(bContent);
			
			if(!matcher_src.find()){
				result = service.write(bStdID,bTitle,bSubTitle,bContent,bCategory);
		    	if(result > 0) {
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/advertise_upload_ok.jsp");
		    	}else{
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/advertise_upload_ok.jsp");
				}
		    	return;
			}
			matcher_src.reset();
			
			while(matcher_src.find()){ 
				image_tag = matcher_src.group(0);
	            image_path = matcher_src.group(1);
	
				Pattern pattern_width = Pattern.compile("(?i)width[:\\=\"']*(\\d+)*"); //src
				Pattern pattern_height = Pattern.compile("(?i)height[:\\=\"']*(\\d+)*"); //src
				Matcher matcher_width = pattern_width.matcher(image_tag);
				Matcher matcher_height= pattern_height.matcher(image_tag);
	
	            while(matcher_width.find()) {
	            	img_width = Integer.parseInt(matcher_width.group(1));
	            }
	            
	            while(matcher_height.find()) {
	            	img_height = Integer.parseInt(matcher_height.group(1));
	            }
	            realPath = request.getSession().getServletContext().getRealPath("/");
	            tempPath = realPath+ image_path;
	            
	            System.out.println(image_path);
	
	            System.out.println(tempPath);
	            int index = image_path.lastIndexOf("/");
	            img_name = image_path.substring(index +1);
	            
	            String replacePath = image_path.replace(img_name,"");
	            
	            bContent= bContent.replace(replacePath, "upload/Advertise/");
	            
	            if(write == 0) {
	            	write = 1;
	            	result = service.write(bStdID,bTitle,bSubTitle,bContent,bCategory);
	            	if(result<=0){
		    			request.setAttribute("result", result);
		    			HttpUtil.forward(request, response,"/manage/advertise_upload_ok.jsp");
	        		}
	            }
	            	
	            try {
	                File tempFile = FileUtils.getFile(tempPath);
	                File realFile = FileUtils.getFile(realPath+"upload/Advertise/"+ img_name);
	                FileUtils.moveFile(tempFile, realFile);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            
	            image_path = "upload/Advertise/"+ img_name;
	
				BoardVO keyList = new BoardVO();
				
				keyList = service.getKeyForImage(bStdID, bTitle, bContent, bCategory);
				bId = keyList.getbId();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println("keyList�� reg_date"+keyList.getbReg_date());
				bReg_date = sdf.format(keyList.getbReg_date());
				System.out.println("keyList�� reg_date ��"+bReg_date);
				
				result = service.writeImage(image_path,bId, bReg_date, img_name, img_width,img_height);
			}
			
			request.setAttribute("result", result);
			HttpUtil.forward(request, response,"/manage/advertise_upload_ok.jsp");
		}
	}
}
