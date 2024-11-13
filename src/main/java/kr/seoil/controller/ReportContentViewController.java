package kr.seoil.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.seoil.service.ReportService;
import kr.seoil.util.HttpUtil;
import kr.seoil.vo.ReportVO;

public class ReportContentViewController implements Controller {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		ReportService service = ReportService.getInstance();
		int rId = Integer.parseInt(request.getParameter("rId"));

		ReportVO reportContent = service.getReportDetailContent(rId);
		request.setAttribute("boardContent", reportContent);
		HttpUtil.forward(request, response,"/board/report_content.jsp");
	}
}
