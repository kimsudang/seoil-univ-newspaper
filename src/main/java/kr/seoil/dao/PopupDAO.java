package kr.seoil.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import kr.seoil.util.JdbcUtil;
import kr.seoil.vo.PopupVO;

public class PopupDAO {
	private  static PopupDAO dao = new PopupDAO();
	private PopupDAO() {}
	
	public static PopupDAO getInstance() {
		return dao;
	}
	
	JdbcUtil jdbc = new JdbcUtil();
	
	//팝업 정보 db저장
	public int popInsert(String pop_title, String pop_img_path, String pop_url, String start_date, String end_date,
			 int pop_loc_top,int pop_loc_left ) {
		Timestamp start_time = Timestamp.valueOf(start_date);
		Timestamp end_time = Timestamp.valueOf(end_date);
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement("insert into popup(pop_title, pop_img_path, pop_url, start_date, end_date, pop_loc_top, pop_loc_left) values(?,?,?,?,?,?,?)");
			pstmt.setString(1, pop_title);
			pstmt.setString(2, pop_img_path);
			pstmt.setString(3, pop_url);
			pstmt.setTimestamp(4, start_time);
			pstmt.setTimestamp(5, end_time);
			pstmt.setInt(6, pop_loc_top);
			pstmt.setInt(7, pop_loc_left);
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			jdbc.close(conn,pstmt);
		}
		return -1;
	}
	
	//popup이미지 저장위해 key가져옴
	public PopupVO getKeyForPopup(String pop_title,String pop_img_path){
		PopupVO keyList = new PopupVO();
		String sql = "select pop_no, pReg_date from popup where pop_title=? and pop_img_path=? order by pop_no desc limit 1"; 

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pop_title);
			pstmt.setString(2, pop_img_path);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				keyList = new PopupVO();
				keyList.setPop_no(rs.getInt("pop_no"));		
				keyList.setpReg_date(rs.getTimestamp("pReg_date"));		
			}
			System.out.println("게시글 목록 정보 조회 완료!");
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		
		return keyList;
	}
	
	//팝업 이미지정보db저장
	public int popImageInsert(String image_path,int pop_no, String pReg_date, String img_name) {
		String sql = "INSERT INTO popup_img(image_path, pop_no, pReg_date, img_name) VALUES(?,?,?,?)";
		
		Timestamp time = Timestamp.valueOf(pReg_date);
		Connection conn = null;
		PreparedStatement  pstmt = null;
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, image_path);
			pstmt.setInt(2, pop_no);
			pstmt.setTimestamp(3, time);
			pstmt.setString(4, img_name);

			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn,pstmt);
		}
		return -1;
	}
	
	//팝업 관리 위해 전체 팝업리스트 가져오기
	public ArrayList<PopupVO> getAllPopupList() {
		
		ArrayList<PopupVO> popupList = new ArrayList<>();
		String sql = "SELECT * FROM popup order by pop_no desc";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				PopupVO pops = new PopupVO();
				pops.setPop_no(rs.getInt("pop_no"));
				pops.setpReg_date(rs.getTimestamp("pReg_date"));	
				pops.setPop_title(rs.getString("pop_title"));
				pops.setPop_img_path(rs.getString("pop_img_path"));
				pops.setPop_url(rs.getString("pop_url"));
				pops.setStart_date(rs.getTimestamp("start_date"));
				pops.setEnd_date(rs.getTimestamp("end_date"));
				pops.setPop_loc_top(rs.getInt("pop_loc_top"));
				pops.setPop_loc_left(rs.getInt("pop_loc_left"));
				popupList.add(pops);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		return popupList;
	}
	
	//현재 팝업이 진행되어야 하는 리스트 가져옴
	public ArrayList<PopupVO> getPeriodPopupList() {
		
		ArrayList<PopupVO> popupList = new ArrayList<>();
		String sql = "select * from popup where timestampdiff(minute, start_date, now())>=0 and timestampdiff(minute, end_date, now())<=0";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			System.out.println("���Ⱑ�� dao");
			
			while(rs.next()) {
				PopupVO pops = new PopupVO();
				pops.setPop_no(rs.getInt("pop_no"));
				pops.setpReg_date(rs.getTimestamp("pReg_date"));
				pops.setPop_title(rs.getString("pop_title"));
				pops.setPop_img_path(rs.getString("pop_img_path"));
				pops.setPop_url(rs.getString("pop_url"));
				pops.setStart_date(rs.getTimestamp("start_date"));
				pops.setEnd_date(rs.getTimestamp("end_date"));
				pops.setPop_loc_top(rs.getInt("pop_loc_top"));
				pops.setPop_loc_left(rs.getInt("pop_loc_left"));
				popupList.add(pops);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		return popupList;
	}

	//팝업이미지 영구삭제
	public int setPopupImageDel(int pop_no, String pReg_date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM popup_img WHERE pop_no=? and pReg_date=?";
		Timestamp time = Timestamp.valueOf(pReg_date);
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pop_no);
			pstmt.setTimestamp(2, time);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}
	
	//이미지 수정위해 원본 path가져옴
	public ArrayList<String> getImgPreviewList(int pop_no, String pReg_date) {
		ArrayList<String> boardPreview = new ArrayList<>();
		String sql = "SELECT image_path FROM popup_img WHERE pop_no=? AND pReg_date=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		Timestamp time = Timestamp.valueOf(pReg_date);
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pop_no);
			pstmt.setTimestamp(2, time);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				boardPreview.add(rs.getString("image_path"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		return boardPreview;
	}
	
	// 팝업 영구 삭제
	public int setWriteDelete(int pop_no, String pReg_date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM popup WHERE (pop_no=? AND pReg_date=?)";
		Timestamp time = Timestamp.valueOf(pReg_date);	
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pop_no);
			pstmt.setTimestamp(2, time);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}
	
	//해당 게시글 내용 불러오기(팝업 수정 시)
	public PopupVO getPopupUpdateContent(int pop_no, String pReg_date) {
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		PopupVO pops = new PopupVO();
		Timestamp time = Timestamp.valueOf(pReg_date);
		
		String sql = "SELECT * FROM popup WHERE pop_no=? and pReg_date=?";
	
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pop_no);
			pstmt.setTimestamp(2, time);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				pops.setPop_no(rs.getInt("pop_no"));
				pops.setpReg_date(rs.getTimestamp("pReg_date"));
				pops.setPop_title(rs.getString("pop_title"));
				pops.setPop_img_path(rs.getString("pop_img_path"));
				pops.setPop_url(rs.getString("pop_url"));
				pops.setStart_date(rs.getTimestamp("start_date"));
				pops.setEnd_date(rs.getTimestamp("end_date"));
				pops.setPop_loc_top(rs.getInt("pop_loc_top"));
				pops.setPop_loc_left(rs.getInt("pop_loc_left"));
			}
			
			System.out.println("얍");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt,rs);
		}
		return pops;
	}
	
	//게시글 내용 업데이트
	public int boardUpdateContent(int pop_no, String pReg_date, String pop_title, String pop_img_path, String pop_url,
			String start_date, String end_date,int pop_loc_top,int pop_loc_left) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println(pReg_date);
		Timestamp time1 = Timestamp.valueOf(pReg_date);
		System.out.println(time1);
		Timestamp time2 = Timestamp.valueOf(start_date);
		Timestamp time3 = Timestamp.valueOf(end_date);
		String sql = "Update popup SET pop_title=?, pop_img_path=?, pop_url=?, start_date=?, end_date=?,"
				+ "pop_loc_top=?, pop_loc_left=?"
				+ " WHERE pop_no=? AND pReg_date=?";
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pop_title);
			pstmt.setString(2, pop_img_path);
			pstmt.setString(3, pop_url);
			pstmt.setTimestamp(4, time2);
			pstmt.setTimestamp(5, time3);
			pstmt.setInt(6, pop_loc_top);
			pstmt.setInt(7, pop_loc_left);
			pstmt.setInt(8, pop_no);
			pstmt.setTimestamp(9, time1);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		
		return -1;
	}
	
	
	//게시글 내용 업데이트
	public int popimgUpdateContent(String image_path,int pop_no, String pReg_date, String img_name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Timestamp time1 = Timestamp.valueOf(pReg_date);
		String sql = "Update popup_img SET image_path=?, img_name=?"
				+ " WHERE pop_no=? AND pReg_date=?";
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, image_path);
			pstmt.setString(2, img_name);
			pstmt.setInt(3, pop_no);
			pstmt.setTimestamp(4, time1);
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("tkrrrp");
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		
		return -1;
	}
}
