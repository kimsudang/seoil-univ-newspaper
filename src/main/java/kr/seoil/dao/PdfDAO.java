package kr.seoil.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import kr.seoil.util.JdbcUtil;
import kr.seoil.vo.PdfVO;

public class PdfDAO {
	private  static PdfDAO dao = new PdfDAO();
	private PdfDAO() {}
	
	public static PdfDAO getInstance() {
		return dao;
	}
	
	JdbcUtil jdbc = new JdbcUtil();
	
	//pdf 정보 db저장
	public int pdfInsert(String pdf_title, String pdf_file_path, String publish_date, int publish_no) {
		Date publish_time = Date.valueOf(publish_date);
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement("insert into pdf(pdf_title, pdf_file_path, publish_date, publish_no) values(?,?,?,?)");
			pstmt.setString(1, pdf_title);
			pstmt.setString(2, pdf_file_path);
			pstmt.setDate(3, publish_time);
			pstmt.setInt(4, publish_no);
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			jdbc.close(conn,pstmt);
		}
		return -1;
	}
	
	//pdf파일 저장위해 key가져옴
	public PdfVO getKeyForPdf(String pdf_title,String pdf_file_path){
		PdfVO keyList = new PdfVO();
		String sql = "select pdf_no, fReg_date from pdf where pdf_title=? and pdf_file_path=? order by pdf_no desc limit 1"; 

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pdf_title);
			pstmt.setString(2, pdf_file_path);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				keyList = new PdfVO();
				keyList.setPdf_no(rs.getInt("pdf_no"));		
				keyList.setfReg_date(rs.getTimestamp("fReg_date"));		
			}
			System.out.println("게시글 목록 정보 조회 완료!");
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		
		return keyList;
	}
	
	//pdf 파일정보 파일db에 저장
	public int pdfFileInsert(String pdf_file_path,int pdf_no, String fReg_date, String file_name) {
		String sql = "INSERT INTO pdf_file(file_path, pdf_no, fReg_date, file_name) VALUES(?,?,?,?)";
		
		Timestamp time = Timestamp.valueOf(fReg_date);
		Connection conn = null;
		PreparedStatement  pstmt = null;
		
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pdf_file_path);
			pstmt.setInt(2, pdf_no);
			pstmt.setTimestamp(3, time);
			pstmt.setString(4, file_name);

			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn,pstmt);
		}
		return -1;
	}
	
	//pdf 관리 위해 전체 pdf 리스트 가져오기
	public ArrayList<PdfVO> getAllPdfList(int firstRow, int endRow) {
		
		ArrayList<PdfVO> pdfList = new ArrayList<>();
		String sql = "SELECT * FROM pdf order by publish_no DESC limit ?,? ";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,firstRow -1);
			pstmt.setInt(2, endRow-firstRow+1);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				PdfVO pdfs = new PdfVO();
				pdfs.setPdf_no(rs.getInt("pdf_no"));
				pdfs.setfReg_date(rs.getTimestamp("fReg_date"));	
				pdfs.setPdf_title(rs.getString("pdf_title"));
				pdfs.setPdf_file_path(rs.getString("pdf_file_path"));
				pdfs.setPublish_no(rs.getInt("publish_no"));
				pdfs.setPublish_date(rs.getTimestamp("publish_date"));
				pdfList.add(pdfs);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		return pdfList;
	}
	
	//pdf 관리 위해 전체 pdf 리스트 가져오기
	public int selectAllPdfCount() {
		int num=0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM pdf";
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
			return num;	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return -1;
	}
	
	public ArrayList<String> getFileList(int pdf_no, String fReg_date) {
		ArrayList<String> File = new ArrayList<>();
		String sql = "SELECT pdf_file_path FROM pdf WHERE pdf_no=? AND fReg_date=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		Timestamp time = Timestamp.valueOf(fReg_date);
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pdf_no);
			pstmt.setTimestamp(2, time);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				File.add(rs.getString("pdf_file_path"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		return File;
	}
	
	public String getFilePath(int pdf_no, int publish_no) {
		String sql = "SELECT pdf_file_path FROM pdf WHERE pdf_no=? AND publish_no=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pdf_no);
			pstmt.setInt(2, publish_no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getString("pdf_file_path");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		return "-1";
	}
	
	//pdf파일 영구삭제
	public int setPdfFileDel(int pdf_no, String fReg_date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM pdf_file WHERE pdf_no=? and fReg_date=?";
		Timestamp time = Timestamp.valueOf(fReg_date);
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pdf_no);
			pstmt.setTimestamp(2, time);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}
	
	// 파일 영구 삭제
	public int setWriteDelete(int pdf_no, String fReg_date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM pdf WHERE pdf_no=? AND fReg_date=?";
		Timestamp time = Timestamp.valueOf(fReg_date);

		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pdf_no);
			pstmt.setTimestamp(2, time);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}

}
