package kr.seoil.vo;
import java.sql.Timestamp;

public class BoardVO {
	private int bId;
	private Timestamp bReg_date;
	private int rownum;
	private String bStdID; 
	private String bTitle;
	private String bSubTitle;
	private String bContent;
	private String bCategory;
	private Timestamp bMod_date;
	private Timestamp bAcc_date;
	private int bAvailable;
	private String userName;
	private String userEmail;
	private String userDept;
	private int userAdmin;
	private int bMain;
	private int bAnnounce;
	
	private String cName;
	private String cpath;
	
	private String image_path;
	private String idxno;
	private String text_only;

	public BoardVO() {}
	
	// 전체생성자 -보드
	public BoardVO(int rownum, int bId, Timestamp bReg_date, String bStdID, String bTitle, String bSubTitle,String bContent, String bCategory, 
			Timestamp bMod_date, Timestamp bAcc_date, int bAvailable, String image_path, String idxno, String text_only, 
			String userName, String userEmail, String userDept, int bMain, String cName, String cpath,  int bAnnounce) { 
		super();
		this.rownum = rownum;
		this.bId = bId;
		this.bReg_date = bReg_date;
		this.bStdID = bStdID;
		this.bTitle = bTitle;
		this.bSubTitle = bSubTitle;
		this.bContent = bContent;
		this.bCategory = bCategory;
		this.bMod_date = bMod_date;
		this.bAcc_date = bAcc_date;
		this.bAvailable = bAvailable;
		this.image_path = image_path;
		this.idxno = idxno;
		this.text_only = text_only;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userDept = userDept;
		this.bMain = bMain;
		this.cName = cName;
		this.cpath = cpath;
		this.bAnnounce = bAnnounce;
	}

	public int getbAnnounce() {
		return bAnnounce;
	}

	public void setbAnnounce(int bAnnounce) {
		this.bAnnounce = bAnnounce;
	}

	public String getCpath() {
		return cpath;
	}

	public void setCpath(String cpath) {
		this.cpath = cpath;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public int getbMain() {
		return bMain;
	}

	public void setbMain(int bMain) {
		this.bMain = bMain;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	
	public int getbId() {
		return bId;
	}

	public void setbId(int bId) {
		this.bId = bId;
	}

	public Timestamp getbReg_date() {
		return bReg_date;
	}

	public void setbReg_date(Timestamp bReg_date) {
		this.bReg_date = bReg_date;
	}

	public String getbStdID() {
		return bStdID;
	}

	public void setbStdID(String bStdID) {
		this.bStdID = bStdID;
	}

	public String getbTitle() {
		return bTitle;
	}

	public void setbTitle(String bTitle) {
		this.bTitle = bTitle;
	}

	public String getbContent() {
		return bContent;
	}

	public void setbContent(String bContent) {
		this.bContent = bContent;
	}

	public String getbCategory() {
		return bCategory;
	}

	public void setbCategory(String bCategory) {
		this.bCategory = bCategory;
	}

	public Timestamp getbMod_date() {
		return bMod_date;
	}

	public void setbMod_date(Timestamp bMod_date) {
		this.bMod_date = bMod_date;
	}

	public int getbAvailable() {
		return bAvailable;
	}

	public void setbAvailable(int bAvailable) {
		this.bAvailable = bAvailable;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserDept() {
		return userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	public Timestamp getbAcc_date() {
		return bAcc_date;
	}

	public void setbAcc_date(Timestamp bAcc_date) {
		this.bAcc_date = bAcc_date;
	}

	public String getbSubTitle() {
		return bSubTitle;
	}

	public void setbSubTitle(String bSubTitle) {
		this.bSubTitle = bSubTitle;
	}

	public String getIdxno() {
		return idxno;
	}

	public void setIdxno(String idxno) {
		this.idxno = idxno;
	}

	public String getText_only() {
		return text_only;
	}

	public void setText_only(String text_only) {
		this.text_only = text_only;
	}
	public int getUserAdmin() {
		return userAdmin;
	}

	public void setUserAdmin(int userAdmin) {
		this.userAdmin = userAdmin;
	}

	
}
