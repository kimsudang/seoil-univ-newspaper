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

import kr.seoil.service.PdfService;
import kr.seoil.util.HttpUtil;
import kr.seoil.vo.PdfVO;

public class PdfUploadController implements Controller {
	private static final String CHARSET = "UTF-8";
	
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
		PdfService service = PdfService.getInstance();

		request.setCharacterEncoding(CHARSET);
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		String fReg_date = null;
		String pdf_title = null;
		String publish_date = null;
		int publish_no = 0;
		String pdf_file_path = null;
		File uploadFile = null;
		String newFilePath = null;
		System.out.println("들어와요.");
		int result = -1;
		PdfVO keyList = new PdfVO();
		
		if(!isMultipart) {
			System.out.println("들어와요1.");
			HttpUtil.forward(request, response,"/admin/admin_pdf.jsp");
			return;
		}else{
			String addPath =  "upload/Pdf/";
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd"); //날짜형식으로 폴더만든다.
			Date nowDate = new Date();
			String tempDate = dateformat.format(nowDate)+"/"; // 2022/06/29/형식
			String pPath = addPath +tempDate;
			
			String pdfPath = request.getSession().getServletContext().getRealPath("/")+ pPath;
			
			File pdfDir = new File(pdfPath);
			DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
			fileItemFactory.setRepository(pdfDir);
			
			ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
		
			try {
				List<FileItem> items = fileUpload.parseRequest(request);
				for(FileItem item : items) {
					if(item.isFormField()) { 
						System.out.println(item.getFieldName());
						if("pdfTitle".equals(item.getFieldName())) {
							pdf_title = new String(item.getString().getBytes("8859_1"), "UTF-8");
							System.out.println("pdf_title:"+pdf_title);
						}else if("pdfDate".equals(item.getFieldName())){
							String sdate = item.getString();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
							Date d = sdf.parse(sdate);
							publish_date = output.format(d);
							System.out.println("publish_date:"+publish_date);
						}else if("pdfNumber".equals(item.getFieldName())){
							publish_no = Integer.parseInt(item.getString());
							System.out.println("publish_no:"+publish_no);
						}

					}else { 
						System.out.printf("[파일형식 파라미터] 파라미터명: %s, 파일 명: %s, 파일 크기: %s bytes \n", item.getFieldName(), item.getName(), item.getSize());
						if(item.getSize() > 0) {
							UUID uuid = UUID.randomUUID();
		
							String separator = File.separator; //파일 구분자. 운영체제별로 다름
							String originFilePath = item.getName();
							int index = originFilePath.lastIndexOf(separator);
							String fileName = originFilePath.substring(index + 1); //파일 이름 가져옴
							String ext = originFilePath.substring(originFilePath.lastIndexOf("."));
							
							if(ext.equals(".pdf")) {			
								uploadFile = new File(pdfPath + separator);
								System.out.println("uploadFile = "+uploadFile);
								
								if(!uploadFile.exists()) {
									uploadFile.mkdirs();
								}
								newFilePath = uuid + "_" + fileName;
								System.out.println("uploadFile : "+uploadFile);
								item.write(new File(uploadFile, newFilePath));
						
							}else {
								out.println("<script>alert('파일 형식이 올바르지 않습니다. pdf로 업로드해주세요.');</script>");
								item.delete();
								return;
							}
		
						}else if(item.getSize() < 0){
							out.println("<script>alert('파일이 올바르게 업로드 되지 않았습니다. 다시 업로드해주세요.');</script>");
							item.delete();
							return;
						}
					}
				}
				pdf_file_path = pPath + newFilePath;
				result = service.pdfInsert(pdf_title, pdf_file_path, publish_date, publish_no);
				System.out.println("pdf_title11: "+pdf_title);
				System.out.println("pdf_file_path11: "+pdf_file_path);
				System.out.println("publish_date11: "+publish_date);
				System.out.println("publish_no: "+publish_no);
				
				if(result <= 0) {
					File fileremove = new File(request.getSession().getServletContext().getRealPath("/")+pdf_file_path);
					fileremove.delete();
					request.setAttribute("result",result);	
					HttpUtil.forward(request, response,"/manage/admin_pdf_ok.jsp");
					return;
				}
				keyList = service.getKeyForPdf(pdf_title, pdf_file_path);
				if(keyList == null) {
					File fileremove = new File(request.getSession().getServletContext().getRealPath("/")+pdf_file_path);
					fileremove.delete();
					result = -1;
					request.setAttribute("result",result);	
					HttpUtil.forward(request, response,"/manage/admin_pdf_ok.jsp");
					return;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				fReg_date = sdf.format(keyList.getfReg_date());
				System.out.println("fReg_date:: "+fReg_date+"|"+keyList.getPdf_no());
				result = service.pdfFileInsert(pdf_file_path, keyList.getPdf_no(), fReg_date, newFilePath);
				if(result <= 0) {
					File fileremove = new File(request.getSession().getServletContext().getRealPath("/")+pdf_file_path);
					fileremove.delete();
					request.setAttribute("result",result);	
					HttpUtil.forward(request, response,"/manage/admin_pdf_ok.jsp");
					return;
				}
				request.setAttribute("result",result);	
				HttpUtil.forward(request, response,"/manage/admin_pdf_ok.jsp");
				return;
							
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("파일업로드 중 오류가 발생하였습니다.");
			} finally {
	    		try {
	        		if(out != null) { out.close(); }
	        	} catch(Exception e) { e.printStackTrace();}
	        }
		}
	}
}
