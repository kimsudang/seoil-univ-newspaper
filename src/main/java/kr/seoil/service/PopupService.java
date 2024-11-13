package kr.seoil.service;

import java.util.ArrayList;

import kr.seoil.dao.PopupDAO;
import kr.seoil.vo.PopupVO;

public class PopupService {
	private static PopupService service = new PopupService();
	public PopupDAO dao =  PopupDAO.getInstance();
	
	private PopupService() { }
	public static PopupService getInstance() {
		return service;
	}
	
	public int popInsert(String pop_title, String pop_img_path, String pop_url, String start_date, String end_date,
			int pop_loc_top,int pop_loc_left ) {
		return dao.popInsert(pop_title, pop_img_path, pop_url, start_date, end_date, pop_loc_top, pop_loc_left);
	}
	
	public PopupVO getKeyForPopup(String pop_title,String pop_img_path){
		return dao.getKeyForPopup(pop_title, pop_img_path);
	}
	
	public int popImageInsert(String image_path,int pop_no, String pReg_date, String img_name) {
		return dao.popImageInsert(image_path, pop_no, pReg_date, img_name);
	}
	public ArrayList<PopupVO> getAllPopupList() {
		return dao.getAllPopupList();
	}
	
	public ArrayList<PopupVO> getPeriodPopupList() {
		return dao.getPeriodPopupList();
	}
	
	public ArrayList<String> getImgPreviewList(int pop_no, String pReg_date) {
		return dao.getImgPreviewList(pop_no, pReg_date);
	}
	
	public int setPopupImageDel(int pop_no, String pReg_date) {
		return dao.setPopupImageDel(pop_no, pReg_date);
	}
	
	public int setWriteDelete(int pop_no, String pReg_date) {
		return dao.setWriteDelete(pop_no, pReg_date);
	}
	public PopupVO getPopupUpdateContent(int pop_no, String pReg_date) {
		return dao.getPopupUpdateContent(pop_no, pReg_date);
	}
	
	public int popimgUpdateContent(String image_path,int pop_no, String pReg_date, String img_name) {
		return dao.popimgUpdateContent(image_path, pop_no, pReg_date, img_name);
	}
	
	public int boardUpdateContent(int pop_no, String pReg_date, String pop_title, String pop_img_path, String pop_url,
			String start_date, String end_date,int pop_loc_top,int pop_loc_left) {
		
		return dao.boardUpdateContent(pop_no, pReg_date, pop_title, pop_img_path, pop_url, start_date, end_date, 
				pop_loc_top, pop_loc_left);
	}
}
