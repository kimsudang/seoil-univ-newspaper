package kr.seoil.vo;

public class CategoryVO {
	private int cId;
	private String cName;
	private int cState;
	private int cMainID;
	
	public CategoryVO() {}
	
	public CategoryVO(int cId, String cName, int cState, int cMainID) {
		this.cId = cId;
		this.cName = cName;
		this.cMainID = cMainID;
		this.cState = cState;
		
	}
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public int getcState() {
		return cState;
	}
	public void setcState(int cState) {
		this.cState = cState;
	}
	public int getcMainID() {
		return cMainID;
	}
	public void setcMainID(int cMainID) {
		this.cMainID = cMainID;
	}
}