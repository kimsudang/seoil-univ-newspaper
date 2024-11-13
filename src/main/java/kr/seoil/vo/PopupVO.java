package kr.seoil.vo;

import java.sql.Timestamp;

public class PopupVO {
	private int pop_no;
	private Timestamp pReg_date;
	private String pop_title;
	private String pop_img_path;
	private String pop_url;
	private Timestamp start_date;
	private Timestamp end_date; 
	private int pop_loc_top;
	private int pop_loc_left;	
	private int imgId;
	private String img_name;
	
	public PopupVO() {};
	public PopupVO(int pop_no, Timestamp pReg_date, String pop_title, String pop_img_path,String pop_url,Timestamp start_date, 
			Timestamp end_date, int pop_loc_top,int pop_loc_left, int imgId, String img_name) {
		this.pop_no = pop_no;
		this.pReg_date = pReg_date;
		this.pop_title = pop_title;
		this.pop_img_path = pop_img_path;
		this.pop_url = pop_url;
		this.start_date = start_date;
		this.end_date = end_date;
		this.pop_loc_top = pop_loc_top;
		this.pop_loc_left = pop_loc_left;
		this.imgId = imgId;
		this.img_name = img_name;
	}
	public int getPop_no() {
		return pop_no;
	}
	public void setPop_no(int pop_no) {
		this.pop_no = pop_no;
	}
	public String getPop_title() {
		return pop_title;
	}
	public void setPop_title(String pop_title) {
		this.pop_title = pop_title;
	}
	public String getPop_img_path() {
		return pop_img_path;
	}
	public void setPop_img_path(String pop_img_path) {
		this.pop_img_path = pop_img_path;
	}
	public String getPop_url() {
		return pop_url;
	}
	public void setPop_url(String pop_url) {
		this.pop_url = pop_url;
	}
	public Timestamp getStart_date() {
		return start_date;
	}
	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}
	public Timestamp getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Timestamp end_date) {
		this.end_date = end_date;
	}
	public int getPop_loc_top() {
		return pop_loc_top;
	}
	public void setPop_loc_top(int pop_loc_top) {
		this.pop_loc_top = pop_loc_top;
	}
	public int getPop_loc_left() {
		return pop_loc_left;
	}
	public void setPop_loc_left(int pop_loc_left) {
		this.pop_loc_left = pop_loc_left;
	}
	public int getImgId() {
		return imgId;
	}
	public void setImgId(int imgId) {
		this.imgId = imgId;
	}
	public String getImg_name() {
		return img_name;
	}
	public void setImg_name(String img_name) {
		this.img_name = img_name;
	}
	public Timestamp getpReg_date() {
		return pReg_date;
	}
	public void setpReg_date(Timestamp pReg_date) {
		this.pReg_date = pReg_date;
	}
}
