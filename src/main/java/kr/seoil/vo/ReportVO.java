package kr.seoil.vo;

import java.sql.Timestamp;

public class ReportVO {
	private int rId;
	private Timestamp rReg_date;
	private String rPosition;	
	private String rDepart;
	private String rName ;
	private String rStdID;
	private String rEmail;
	private String rTel;
	private String rContent;
	private String rTitle;
	
	public ReportVO() {}
	
	// 전체생성자
	public ReportVO(int rId, Timestamp rReg_date,String rPosition, String rDepart, String rName,String rStdID,
	String rEmail, String rTel, String rContent, String rTitle) { 
		super();
		this.rId = rId;
		this.rReg_date = rReg_date;
		this.rPosition = rPosition;
		this.rDepart = rDepart;
		this.rName = rName;
		this.rStdID = rStdID;
		this.rEmail = rEmail;
		this.rTel = rTel;
		this.rContent = rContent;
		this.rTitle = rTitle;
	}
	public String getrPosition() {
		return rPosition;
	}

	public void setrPosition(String rPosition) {
		this.rPosition = rPosition;
	}

	public String getrDepart() {
		return rDepart;
	}

	public void setrDepart(String rDepart) {
		this.rDepart = rDepart;
	}

	public String getrName() {
		return rName;
	}

	public void setrName(String rName) {
		this.rName = rName;
	}

	public String getrStdID() {
		return rStdID;
	}

	public void setrStdID(String rStdID) {
		this.rStdID = rStdID;
	}

	public String getrEmail() {
		return rEmail;
	}

	public void setrEmail(String rEmail) {
		this.rEmail = rEmail;
	}

	public String getrTel() {
		return rTel;
	}

	public void setrTel(String rTel) {
		this.rTel = rTel;
	}

	public String getrContent() {
		return rContent;
	}

	public void setrContent(String rContent) {
		this.rContent = rContent;
	}

	public String getrTitle() {
		return rTitle;
	}

	public void setrTitle(String rTitle) {
		this.rTitle = rTitle;
	}

	public int getrId() {
		return rId;
	}

	public void setrId(int rId) {
		this.rId = rId;
	}

	public Timestamp getrReg_date() {
		return rReg_date;
	}

	public void setrReg_date(Timestamp rReg_date) {
		this.rReg_date = rReg_date;
	}

	
}
