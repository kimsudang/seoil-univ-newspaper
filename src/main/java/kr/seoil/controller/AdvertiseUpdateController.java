package kr.seoil.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


public class AdvertiseUpdateController implements Controller {
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
		String btnValue = request.getParameter("btnValue");
		//만약,,,오류 나면,,,
		String bReg_date = request.getParameter("bReg_date");
		
		
		System.out.println("bReg_date 후 :"+bReg_date);
		//이렇게 넣어보아요
		String bStdID = request.getParameter("stdId");
		String bContent = request.getParameter("editContent");
		String bTitle = request.getParameter("editTitle");
		String bSubTitle = request.getParameter("editSubTitle");
		String bCategory = "18";
		
		Pattern pattern_src = null;
		System.out.println(bStdID + "학번");
		
		String image_path = null;
		String realPath = null;
		String tempPath = null;
		String img_name = null;
		int img_width = 0;
		int img_height = 0;
		String image_tag = null;
		
		int result = -1;
		int write = 0;
		
		BoardService service = BoardService.getInstance();
		ArrayList<String> boardImagePath = service.getImgPreviewList(Integer.parseInt(bId), bReg_date);
		

		if(btnValue != null && btnValue.equals("revise")) {
			String bTemp_date = bReg_date.substring(0,bReg_date.indexOf("."));
			
			BoardVO boardContent = service.getBoardUpdateContent(Integer.parseInt(bId), bReg_date);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); //날짜형식으로 폴더만든다.
			Date nowDate = new Date();
			String tempDate = sdf.format(nowDate)+"/"; // 2022/06/29/형식
			tempPath= "upload/Temp/"+tempDate;
			
			bContent = boardContent.getbContent().replace("upload/Advertise/", tempPath);
	
			for (String path : boardImagePath) { //임시적으로 파일 옮기기
				File file = new File(request.getSession().getServletContext().getRealPath("/")+path);   
				File newFile = new File(request.getSession().getServletContext().getRealPath("/")+tempPath+file.getName());         
				FileUtils.copyFile(file, newFile);
			}
			
			bTitle = boardContent.getbTitle();
			bSubTitle = boardContent.getbSubTitle();
			bCategory = boardContent.getbCategory();
			
			request.setAttribute("bId", bId);
			request.setAttribute("bReg_date", bTemp_date);
			request.setAttribute("bTitle", bTitle);
			request.setAttribute("bSubTitle", bSubTitle);
			request.setAttribute("bCategory", bCategory);
			request.setAttribute("bContent", bContent);
			HttpUtil.forward(request, response,"/manage/advertise_editor_reporter.jsp");
		}
		else if(btnValue != null && btnValue.equals("update")) {
			//원본삭제
			for (String path : boardImagePath) {
			 	File file = new File(request.getSession().getServletContext().getRealPath("/")+path);   
				file.delete();
			}
			
			result = service.setArticleImageDel(Integer.parseInt(bId), bReg_date); //img db에서 지움
			if(result == -1) {
				request.setAttribute("result", result);
				HttpUtil.forward(request, response,"/manage/advertise_update_ok.jsp");
				return;
			}
			
			pattern_src = Pattern.compile("(?i)<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>"); //src
			Matcher matcher_src = pattern_src.matcher(bContent);
	
			
			int i = 1;
			
			if(!matcher_src.find()){
				result = service.boardUpdateContent(Integer.parseInt(bId), bReg_date, bStdID, bTitle, bSubTitle, bContent, bCategory);
				System.out.println("이미지 없는 광고 수정");
		    	if(result > 0) {
					System.out.println("이미지 없는 광고 수정 완료");
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/advertise_update_ok.jsp");
		    	}else{
		    		System.out.println("이미지 없는 광고 수정 오류");
		    		if(boardImagePath.size()>0){
			    		try {
		        			for (String path : boardImagePath) {
		        				 File tempErrorFile = FileUtils.getFile(realPath+path);
		        				 path =path.replace("upload/Advertise/", "upload/Temp/"); //이제 path는 Temp
		        				 File realErrorFile = FileUtils.getFile(realPath+path);
		                         FileUtils.copyFile(realErrorFile, tempErrorFile);  //Temp -> ImgPath로 파일 카피
		                         path =path.replace("upload/Temp/","upload/Advertise/");
		                         int idx = path.lastIndexOf("/");
		                         String img_error_name = path.substring(idx +1);
		                         int width = 100;
		                         int height = 100;
		                         service.writeImage(path, Integer.parseInt(bId), bReg_date, img_error_name, width,height);
		        			}     
		                } catch (IOException e) {
		                    e.printStackTrace();
		                }
			    	}
					request.setAttribute("result", result);
					HttpUtil.forward(request, response,"/manage/advertise_update_ok.jsp");
				}
		    	return;
			}
			matcher_src.reset();
			
			while(matcher_src.find()){ //이미지 있음
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
	            	System.out.println("dao 들어가기전 bStdID"+bStdID);
	            	result = service.boardUpdateContent(Integer.parseInt(bId), bReg_date, bStdID, bTitle, bSubTitle, bContent, bCategory);
	            	if(result<=0){
	            		System.out.println("이미지 있는 광고 수정 오류");
	            		if(boardImagePath.size()>0){
	    		    		try {
	    	        			for (String path : boardImagePath) {
	    	        				 File tempErrorFile = FileUtils.getFile(realPath+path);
	    	        				 path =path.replace("upload/Advertise/", "upload/Temp/"); //이제 path는 Temp
	    	        				 File realErrorFile = FileUtils.getFile(realPath+path);
	    	                         FileUtils.copyFile(realErrorFile, tempErrorFile);  //Temp -> ImgPath로 파일 카피
	    	                         path =path.replace("upload/Temp/","upload/Advertise/");
	    	                         int idx = path.lastIndexOf("/");
	    	                         String img_error_name = path.substring(idx +1);
	    	                         int width = 100;
	    	                         int height = 100;
	    	                         service.writeImage(path, Integer.parseInt(bId), bReg_date, img_error_name, width,height);
	    	        			}     
	    	                } catch (IOException e) {
	    	                    e.printStackTrace();
	    	                }
	    		    	}
	    				request.setAttribute("result", result);
	    				HttpUtil.forward(request, response,"/manage/advertise_update_ok.jsp");
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
	            System.out.println(i+"번째"+image_path);
				
				System.out.println(bStdID);
				System.out.println(bTitle);
				System.out.println(bContent);
				System.out.println(bCategory);
				
				result = service.writeImage(image_path, Integer.parseInt(bId), bReg_date, img_name, img_width, img_height);
			}
			
			System.out.println("이미지 있는 광고 수정 완료");
			request.setAttribute("result", result);
			HttpUtil.forward(request, response,"/manage/advertise_update_ok.jsp");
		}
	}
}
