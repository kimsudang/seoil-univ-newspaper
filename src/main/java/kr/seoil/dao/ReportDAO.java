package kr.seoil.dao;

import java.sql.*;
import java.util.ArrayList;
import kr.seoil.util.*;
import kr.seoil.vo.ReportVO;


public class ReportDAO {
	private  static ReportDAO dao = new ReportDAO();
	private ReportDAO() {}
	
	public static ReportDAO getInstance() {
		return dao;
	}
	
	JdbcUtil jdbc = new JdbcUtil();
	
	//가입시 임시유저에
	public int insertReport(ReportVO report) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement("insert into news_report_board(rPosition, rDepart, rStdID, rName, rEmail, rTel, rContent, rTitle) values(?,?,?,?,?,?,?,?)");
			pstmt.setString(1, report.getrPosition());
			pstmt.setString(2, report.getrDepart());
			pstmt.setString(3, report.getrStdID());
			pstmt.setString(4, report.getrName());
			pstmt.setString(5, report.getrEmail());
			pstmt.setString(6, report.getrTel());
			pstmt.setString(7, report.getrContent());
			pstmt.setString(8, report.getrTitle());
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn,pstmt);
		}
		return -1;
	}

	
	
	// tempDB�� ��� ���� ��� �˻�
	public ArrayList<ReportVO> getAllReport(int firstRow, int endRow) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReportVO report = null;
		String sql = "SELECT * FROM news_report_board order by rReg_date desc limit ?,?";
		ArrayList<ReportVO> reportlist = new ArrayList<ReportVO>();
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,firstRow -1);
			pstmt.setInt(2, endRow-firstRow+1);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				report = new ReportVO();
				report.setrId(rs.getInt("rId"));
				report.setrReg_date(rs.getTimestamp("rReg_date"));
				report.setrStdID(rs.getString("rStdID"));
				report.setrName(rs.getString("rName"));
				report.setrPosition(rs.getString("rPosition"));
				report.setrDepart(rs.getString("rDepart"));
				report.setrEmail(rs.getString("rEmail"));
				report.setrTel(rs.getString("rTel"));
				report.setrTitle(rs.getString("rTitle"));
				report.setrContent(rs.getString("rContent"));
				reportlist.add(report);
			}
		} catch (Exception e3) {
			e3.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return reportlist;
	}
	
	//모든 임시유저 수
	public int selectAllReportCount() {
		int num=0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM news_report_board";
		
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
	
	//기사 제보함 지우기
	public int reportDelete(String rId) {
		//int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM news_report_board WHERE rId=?";
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rId);
			return pstmt.executeUpdate();
		} catch (Exception e3) {
			e3.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}
	
	//내용 불러오기(기사 제보 내용)
	public ReportVO getReportDetailContent(int rId) {
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		ReportVO report = new ReportVO();
		
		String sql = "SELECT * FROM news_report_board where rId=?";

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rId);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				report = new ReportVO();
				report.setrId(rs.getInt("rId"));
				report.setrReg_date(rs.getTimestamp("rReg_date"));
				report.setrStdID(rs.getString("rStdID"));
				report.setrName(rs.getString("rName"));
				report.setrPosition(rs.getString("rPosition"));
				report.setrDepart(rs.getString("rDepart"));
				report.setrEmail(rs.getString("rEmail"));
				report.setrTel(rs.getString("rTel"));
				report.setrTitle(rs.getString("rTitle"));
				report.setrContent(rs.getString("rContent"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return report;
	}
		
}
