package kr.seoil.vo;

import java.sql.Timestamp;

public class UserVO {
	private String userPW;
	private String userName;
	private String userEmail;
	private String userStdID;
	private String userDept;
	private String userAdmin;
	private Timestamp userDate;
	
	public UserVO() {}
	
	// 전체생성자
	public UserVO(String userStdID, String userPW, String userName,String userEmail,
	String userDept, String userAdmin, Timestamp userDate) { 
		super();
		this.userStdID = userStdID;
		this.userPW = userPW;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userDept =  userDept;
		this.userAdmin = userAdmin;
		this.userDate = userDate;
	}
	public String getUserAdmin() {
		return userAdmin;
	}
	public void setUserAdmin(String userAdmin) {
		this.userAdmin = userAdmin;
	}
	public String getUserPW() {
		return userPW;
	}
	public void setUserPW(String userPW) {
		this.userPW = userPW;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserStdID() {
		return userStdID;
	}
	public void setUserStdID(String userStdID) {
		this.userStdID = userStdID;
	}
	public String getUserDept() {
		return userDept;
	}
	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}
	public Timestamp getUserDate() {
		return userDate;
	}
	public void setUserDate(Timestamp userDate) {
		this.userDate = userDate;
	}	
	
}
