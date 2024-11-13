package kr.seoil.service;

import java.util.ArrayList;

import kr.seoil.dao.ReportDAO;
import kr.seoil.vo.ReportVO;

public class ReportService {
	private static ReportService service = new ReportService();
	public ReportDAO dao = ReportDAO.getInstance();
	
	private ReportService() { }
	public static ReportService getInstance() {
		return service;
	}

	public int insertReport(ReportVO report) {
		return dao.insertReport(report);
	}
	
	public ArrayList<ReportVO> getAllReport(int firstRow, int endRow) {
		return dao.getAllReport(firstRow, endRow);
	}
	
	public int selectAllReportCount() {
		return dao.selectAllReportCount();
	}
	
	public int reportDelete(String rId) {
		return dao.reportDelete(rId);
	}
	
	public ReportVO getReportDetailContent(int rId) {
		return dao.getReportDetailContent(rId);
	}
}
