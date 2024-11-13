package kr.seoil.vo;

import java.sql.Timestamp;

public class TempVO {
	private String tempStdID;
	private String tempPW;
	private String tempName;
	private String tempEmail;
	private String tempDept;
	private Timestamp tempDate;
	
	public TempVO() {}
	
	// 전체생성자
	public TempVO(String tempStdID, String tempPW, String tempName,String tempEmail,
	String tempDept, Timestamp tempDate) { 
		super();
		this.tempStdID = tempStdID;
		this.tempPW = tempPW;
		this.tempName = tempName;
		this.tempEmail = tempEmail;
		this.tempDept =  tempDept;
		this.tempDate = tempDate;
	}
	public String getTempStdID() {
		return tempStdID;
	}
	public void setTempStdID(String tempStdID) {
		this.tempStdID = tempStdID;
	}
	public String getTempPW() {
		return tempPW;
	}
	public void setTempPW(String tempPW) {
		this.tempPW = tempPW;
	}
	public String getTempName() {
		return tempName;
	}
	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	public String getTempEmail() {
		return tempEmail;
	}
	public void setTempEmail(String tempEmail) {
		this.tempEmail = tempEmail;
	}
	public String getTempDept() {
		return tempDept;
	}
	public void setTempDept(String tempDept) {
		this.tempDept = tempDept;
	}
	public Timestamp getTempDate() {
		return tempDate;
	}
	public void setTempDate(Timestamp tempDate) {
		this.tempDate = tempDate;
	}
	
}
