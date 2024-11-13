package kr.seoil.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.seoil.util.JdbcUtil;
import kr.seoil.vo.CategoryVO;

public class CategoryDAO {
	private  static CategoryDAO dao = new CategoryDAO();
	private CategoryDAO() {}
	
	public static CategoryDAO getInstance() {
		return dao;
	}
	
	JdbcUtil jdbc = new JdbcUtil();
	
	//카테고리 정보 가져옴(대분류)
	public ArrayList<CategoryVO> getHeadCategory() {
		ArrayList<CategoryVO> headList= new ArrayList<>();
		String sql ="select cId, cName from news_category where cState =0 and cId != 22 and cId != 18;";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				CategoryVO heads = new CategoryVO();
				heads.setcId(rs.getInt("cId"));		
				heads.setcName(rs.getString("cName"));	
				headList.add(heads);
			}
			System.out.println("카테고리 대분류 정보 조회 완료!");
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		
		return headList;
	}
	
	//카테고리 정보 가져옴(대분류)
	public ArrayList<CategoryVO> getSubCategory(String category) {
		ArrayList<CategoryVO> subList= new ArrayList<>();
		String sql ="select  cId, cName"
				+ "	from news_category products_sorted,"
				+ "	(select @pv := ?) initialisation"
				+ "	where find_in_set(cMainID, @pv) >0;";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				CategoryVO subs = new CategoryVO();
				subs.setcId(rs.getInt("cId"));		
				subs.setcName(rs.getString("cName"));	
				subList.add(subs);
			}
			System.out.println("카테고리 중분류 정보 조회 완료!");
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		
		return subList;
	}
}
