package kr.seoil.controller;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.*;
import javax.servlet.http.*;

import kr.seoil.util.HttpUtil;
import kr.seoil.service.ReportService;
import kr.seoil.vo.ReportVO;

public class NewsReportController implements Controller{
	public void execute(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		String rPosition = request.getParameter("reportSelect");
		String rDepart = request.getParameter("reportDepart");
		String rName = request.getParameter("reportName");
		String rStdID = request.getParameter("reportStdId");
		String rEmail = request.getParameter("reportEmail");
		String rTel = request.getParameter("reportTel");
		String rContent = request.getParameter("reportContent");
		String rTitle = request.getParameter("reportContentTitle");
		
		if(rPosition.isEmpty() || rDepart.isEmpty()|| rName.isEmpty()|| 
				rStdID.isEmpty()|| rEmail.isEmpty() || rTel.isEmpty() || rContent.isEmpty()  || rTitle.isEmpty() ){
			request.setAttribute("error", "등록에 실패하였습니다.");
			HttpUtil.forward(request, response, "/manage/report_ok.jsp");
			
		}else{
			Pattern pattern_id = null;
			pattern_id = Pattern.compile("[0-9]{8,9}$"); 
			Matcher matcher_id = pattern_id.matcher(rStdID);

			Pattern pattern_name = null;
			pattern_name = Pattern.compile("[가-힣]{2,}$"); 
			Matcher matcher_name = pattern_name.matcher(rName);
			
			Pattern pattern_email = null;
			pattern_email = Pattern.compile("[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+"); 
			Matcher matcher_email = pattern_email.matcher(rEmail);
			
			Pattern pattern_tel = null;
			pattern_tel = Pattern.compile("[0-9]{2,3}-[0-9]{3,4}-[0-9]{3,4}$"); 
			Matcher matcher_tel = pattern_tel.matcher(rTel);
			
			matcher_id.reset();
			matcher_tel.reset();
			matcher_name.reset();
			matcher_email.reset();

			if(matcher_id.find() && matcher_tel.find() && matcher_name.find() && matcher_email.find() && !rPosition.isEmpty()
					&& !rDepart.isEmpty() && !rContent.isEmpty() && !rTitle.isEmpty()) {
				ReportVO report = new ReportVO();
				report.setrTitle(rTitle);
				report.setrTel(rTel);
				report.setrStdID(rStdID);
				report.setrPosition(rPosition);
				report.setrName(rName);
				report.setrEmail(rEmail);
				report.setrDepart(rDepart);
				report.setrContent(rContent);
				
				System.out.println("if문");
				ReportService service = ReportService.getInstance();
				int result = service.insertReport(report);
				
				request.setAttribute("result", result);
				HttpUtil.forward(request, response,"/manage/report_ok.jsp");
			} else{
				System.out.println("else문");
				int result = -1;
				request.setAttribute("result", result);

				HttpUtil.forward(request, response, "/manage/report_ok.jsp");
			}
		}
	}
}
