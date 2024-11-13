package kr.seoil.service;

import java.util.ArrayList;
import kr.seoil.vo.PdfVO;
import kr.seoil.dao.PdfDAO;

public class PdfService {
	private static PdfService service = new PdfService();
	public PdfDAO dao =  PdfDAO.getInstance();
	
	private PdfService() { }
	public static PdfService getInstance() {
		return service;
	}
	public ArrayList<PdfVO> getAllPdfList(int firstRow, int endRow) {
		return dao.getAllPdfList(firstRow, endRow);
	}
	
	public int selectAllPdfCount() {
		return dao.selectAllPdfCount();		
	}
	
	public int setPdfFileDel(int pdf_no, String fReg_date) {
		return dao.setPdfFileDel(pdf_no, fReg_date);
	}
	public int setWriteDelete(int pdf_no, String fReg_date) {
		return dao.setWriteDelete(pdf_no, fReg_date);
	}
	public ArrayList<String> getFileList(int pdf_no, String fReg_date) {
		return dao.getFileList(pdf_no, fReg_date);
	}
	public int pdfInsert(String pdf_title, String pdf_file_path, String publish_date, int publish_no) {
		return dao.pdfInsert(pdf_title, pdf_file_path, publish_date, publish_no);
	}
	public PdfVO getKeyForPdf(String pdf_title, String pdf_file_path) {
		return dao.getKeyForPdf(pdf_title, pdf_file_path);
	}
	public int pdfFileInsert(String pdf_file_path, int pdf_no, String fReg_date, String file_name) {
		return dao.pdfFileInsert(pdf_file_path, pdf_no, fReg_date, file_name);
	}
	public String getFilePath(int pdf_no, int publish_no) {
		return dao.getFilePath(pdf_no, publish_no);
	}
}
