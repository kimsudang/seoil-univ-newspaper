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

import kr.seoil.util.HttpUtil;


public class ImgUploadController implements Controller {
	private static final String CHARSET = "UTF-8";
	private static final int LIMIT_SIZE_BYTES = 1024 * 1024;
 
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding(CHARSET);
		HttpSession session = request.getSession();
		String admin = (String)session.getAttribute("userAdmin");
		if(admin == null) {
			request.setAttribute("result", -1);
			HttpUtil.forward(request, response, "/manage/admin_access_only_page.jsp");
			return;
		}	
		PrintWriter out = response.getWriter();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if(isMultipart){ //multipart�곗�댄�곕��
			String addPath =  "upload/Temp/";
			String tempPath = request.getSession().getServletContext().getRealPath("/")+ addPath;
			System.out.println(request.getSession().getServletContext().getRealPath("/"));
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); //��吏������쇰� �대��留�����.
			Date nowDate = new Date();
			String tempDate = sdf.format(nowDate);
			tempPath = tempPath + tempDate; 
			
			File tempDir = new File(tempPath);
			DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
			fileItemFactory.setRepository(tempDir);
	        fileItemFactory.setSizeThreshold(LIMIT_SIZE_BYTES); //�������� ��怨� �ш린
	        
			ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
			
			try {
				List<FileItem> items = fileUpload.parseRequest(request);
				for(FileItem item : items) {
					if(item.isFormField()) { //false硫� 洹몃�� �쇳�� text媛��� �곗�댄��
						System.out.printf("[���쇳������ ���� ���쇰�명��] ���쇰�명�곕�: %s, ���� 紐�: %s, ���쇳�ш린: %s bytes \n", item.getFieldName(), item.getName(), item.getSize());
					} else { //true硫� ���쇰�곗�댄��
						System.out.printf("[���쇳���� ���쇰�명��] ���쇰�명�곕�: %s, ���� 紐�: %s, ���� �ш린: %s bytes \n", item.getFieldName(), item.getName(), item.getSize());
						if(item.getSize() > 0 && item.getSize() <= LIMIT_SIZE_BYTES) {
							UUID uuid = UUID.randomUUID();
							
							String separator = File.separator; //���� 援щ���. �댁��泥댁��蹂�濡� �ㅻ�
							String originFilePath = item.getName();
							int index = originFilePath.lastIndexOf(separator);
							String fileName = originFilePath.substring(index + 1); //���� �대� 媛��몄��
							String ext = originFilePath.substring(originFilePath.lastIndexOf("."));
							System.out.println(ext);
							
							if(ext.equals(".jpg") || ext.equals(".png")) {			
								File uploadFile = new File(tempPath + separator);
								
								if(!uploadFile.exists()) {
									uploadFile.mkdirs();
								}
								
								String newFilePath = uuid + "_"+fileName;
								
								item.write(new File(uploadFile, newFilePath));
								String fileUrl = addPath+tempDate+"/" +newFilePath;
								
								System.out.println("FileURL: "+fileUrl);
								
								String callback = request.getParameter("CKEditorFuncNum");
								out.write("<script>window.parent.CKEDITOR.tools.callFunction(" + callback + ", '" + fileUrl + "');</script>");
										
							}else {
								out.println("<script>alert('���� ������ �щ�瑜댁� ���듬����. jpg�� png濡� ��濡����댁＜�몄��.');</script>");
								item.delete();
								return;
							}
						}else if(item.getSize() > LIMIT_SIZE_BYTES){
							out.println("<script>alert('���� �⑸���� 1MB �댁��������. �⑸���� 以��� ��濡����댁＜�몄��.');</script>");
							item.delete();
							return;
						}else if(item.getSize() < 0){
							out.println("<script>alert('���쇱�� �щ�瑜닿� ��濡��� ��吏� �����듬����. �ㅼ�� ��濡����댁＜�몄��.');</script>");
							item.delete();
							return;
						}
					}				
				}		

			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("���쇱��濡��� 以� �ㅻ�媛� 諛��������듬����.");
			} finally {
	    		try {
	        		if(out != null) { out.close(); }
	        	} catch(Exception e) { e.printStackTrace(); }
	        	}
	
		}else{
			System.out.println("Multipart媛� ����");
		}
		return;
	}

}