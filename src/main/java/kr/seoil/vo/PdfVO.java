package kr.seoil.vo;

import java.sql.Timestamp;

public class PdfVO {
	private int pdf_no;
	private Timestamp fReg_date;
	private String pdf_title;
	private String pdf_file_path;
	private int publish_no;
	private Timestamp publish_date;
	private String file_name;
	private String file_path;
//	private int rownum;

	public PdfVO() {};
	
	public PdfVO(int pdf_no, Timestamp fReg_date, String pdf_title, String pdf_file_path, 
			int publish_no, Timestamp publish_date, String file_name, String file_path) {
		this.pdf_no = pdf_no;
		this.fReg_date = fReg_date;
		this.pdf_title = pdf_title;
		this.pdf_file_path = pdf_file_path;
		this.publish_no = publish_no;
		this.publish_date = publish_date;
		this.file_name = file_name;
		this.file_path = file_path;
	};
	
	public int getPdf_no() {
		return pdf_no;
	}

	public void setPdf_no(int pdf_no) {
		this.pdf_no = pdf_no;
	}

	public Timestamp getfReg_date() {
		return fReg_date;
	}

	public void setfReg_date(Timestamp fReg_date) {
		this.fReg_date = fReg_date;
	}

	public String getPdf_title() {
		return pdf_title;
	}

	public void setPdf_title(String pdf_title) {
		this.pdf_title = pdf_title;
	}

	public String getPdf_file_path() {
		return pdf_file_path;
	}

	public void setPdf_file_path(String pdf_file_path) {
		this.pdf_file_path = pdf_file_path;
	}

	public int getPublish_no() {
		return publish_no;
	}

	public void setPublish_no(int publish_no) {
		this.publish_no = publish_no;
	}

	public Timestamp getPublish_date() {
		return publish_date;
	}

	public void setPublish_date(Timestamp publish_date) {
		this.publish_date = publish_date;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	
	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

}
