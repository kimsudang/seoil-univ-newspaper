package kr.seoil.dao;

import java.sql.*;
import java.util.ArrayList;
import kr.seoil.util.*;
import kr.seoil.vo.UserVO;

public class UserDAO {
	private static UserDAO dao = new UserDAO();
	private UserDAO() {}
	public static UserDAO getInstance() {
		return dao;
	}
	
	JdbcUtil jdbc = new JdbcUtil();
	
	//유저 로그인 체크
	public int userCheck(String stdId, String pw) {
		int resultNum = 0;
		String sql = "SELECT password FROM user WHERE stdId=?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, stdId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String dbPw = rs.getString("password");
				if (dbPw.equals(pw)) { 
					resultNum = 1;
				} else {
					resultNum = -1;
				}
			} else { 
				resultNum = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return resultNum;
	}
	
	//로그인시 유저 정보 가져오기
	public UserVO getUserInfo(String stdId) {
		UserVO user = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String query = "SELECT stdId, name, admin FROM user WHERE stdId=?";
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, stdId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new UserVO();
				user.setUserStdID(rs.getString("stdId"));
				user.setUserName(rs.getString("name"));
				user.setUserAdmin(rs.getString("admin"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return user;
	}
	
	//모든 유저 정보 가져오기
	public ArrayList<UserVO> getAllUser(int firstRow, int endRow) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserVO user = null;
		String sql = "SELECT stdId, name, email, dept, admin, join_date FROM user order by admin desc, name LIMIT ?,?";
		ArrayList<UserVO> list = new ArrayList<UserVO>();
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,firstRow -1);
			pstmt.setInt(2, endRow-firstRow+1);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				user = new UserVO();
				user.setUserStdID(rs.getString("stdId"));
				user.setUserName(rs.getString("name"));
				user.setUserEmail(rs.getString("email"));
				user.setUserDept(rs.getString("dept"));
				user.setUserAdmin(rs.getString("admin"));
				user.setUserDate(rs.getTimestamp("join_date"));
				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return list;
	}
	
	//모든 유저 수
	public int selectAllUserCount() {
		int num=0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM user";
		
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
	
	//유저 삭제
	public int userDelete(String stdId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM user WHERE stdId=? and admin=0";
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, stdId);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		
		return -1;
	}
	
	//admin으로 권한위임
	@SuppressWarnings("resource")
	public int adminEntrust(String stdId) {
		int entruseSucc = 0;
		int selectSucc = 0;
		String preAdmin  = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String findAdminSql = "SELECT stdId FROM user WHERE admin = 1";
		String newAdminSql = "Update user SET admin = 1 WHERE stdId=?";
		String preAdminSql = "Update user SET admin = 0 WHERE stdId=?";
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(findAdminSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();
			rs.last();
			preAdmin = rs.getString("stdId");
			selectSucc = rs.getRow();
			rs.beforeFirst();
			if(selectSucc == 1) {
				pstmt = conn.prepareStatement(newAdminSql);
				pstmt.setString(1, stdId);
				pstmt.executeUpdate();
				entruseSucc = pstmt.executeUpdate();
				
				if(entruseSucc == 1) {
					pstmt = conn.prepareStatement(preAdminSql);
					pstmt.setString(1, preAdmin);
					return pstmt.executeUpdate();			
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}
}













