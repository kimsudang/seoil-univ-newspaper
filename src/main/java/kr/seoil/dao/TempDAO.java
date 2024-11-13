package kr.seoil.dao;

import java.sql.*;
import java.util.ArrayList;
import kr.seoil.util.*;
import kr.seoil.vo.TempVO;


public class TempDAO {
	private  static TempDAO tdao = new TempDAO();
	private TempDAO() {}
	
	public static TempDAO getInstance() {
		return tdao;
	}
	
	JdbcUtil jdbc = new JdbcUtil();
	
	//가입시 임시유저에
	public int insertTemp(TempVO temp) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement("insert into tempuser(stdId, password, name, email, dept) values(?,?,?,?,?)");
			pstmt.setString(1, temp.getTempStdID());
			pstmt.setString(2, temp.getTempPW());
			pstmt.setString(3, temp.getTempName());
			pstmt.setString(4, temp.getTempEmail());
			pstmt.setString(5, temp.getTempDept());
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn,pstmt);
		}
		return -1;
	}

	//ID가 같은게 있나 확인
	public boolean confirmId(String stdId) {
		boolean flag = false;
		String sql = "SELECT stdId FROM tempuser WHERE stdId=?";

		Connection conn = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, stdId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn,pstmt, rs);
		}
		return flag;
	}
	
	// tempDB�� ��� ���� ��� �˻�
	public ArrayList<TempVO> getAllTemp(int firstRow, int endRow) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TempVO temp = null;
		String sql = "SELECT stdId, name, email, dept, join_date FROM tempuser order by join_date, name limit ?,?";
		ArrayList<TempVO> templist = new ArrayList<TempVO>();
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,firstRow -1);
			pstmt.setInt(2, endRow-firstRow+1);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				temp = new TempVO();
				temp.setTempStdID(rs.getString("stdId"));
				temp.setTempName(rs.getString("name"));
				temp.setTempEmail(rs.getString("email"));
				temp.setTempDept(rs.getString("dept"));
				temp.setTempDate(rs.getTimestamp("join_date"));
				templist.add(temp);
			}
		} catch (Exception e3) {
			e3.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return templist;
	}
	
	//모든 임시유저 수
	public int selectAllTempCount() {
		int num=0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM tempuser";
		
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
		
	//임시 유저 지우기
	public int tempDelete(String stdId) {
		//int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM tempuser WHERE stdId=?";
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, stdId);
			return pstmt.executeUpdate();
		} catch (Exception e3) {
			e3.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}
	
	//임시 가입 -> 정식으로 승인
	@SuppressWarnings("resource")
	public int tempAccess(String stdId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int saveSuccess = 0;
		String accSql = "INSERT INTO user (stdId, password, name, email, dept)"
				+ " SELECT stdId, password, name, email, dept FROM tempuser WHERE stdId=?";
		String delSql = "DELETE FROM tempuser WHERE stdId=?";

		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(accSql);
			pstmt.setString(1, stdId);
			saveSuccess = pstmt.executeUpdate();
			
			if(saveSuccess == 1) {
				pstmt = conn.prepareStatement(delSql);
				pstmt.setString(1, stdId);
				return pstmt.executeUpdate();		
			}
		} catch (Exception e4) {
			e4.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}
		
}
