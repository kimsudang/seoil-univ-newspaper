package kr.seoil.dao;

import java.sql.*;
import java.util.ArrayList;
import kr.seoil.util.*;
import kr.seoil.vo.BoardVO;

public class BoardDAO {
	private static BoardDAO dao = new BoardDAO();
	private BoardDAO() {}
	public static BoardDAO getInstance() {
		return dao;
	}
	JdbcUtil jdbc = new JdbcUtil();
	
	//�Խñ� ��ȣ ��������
	public int getNext() {
		String sql = "SELECT bId FROM news_board order by bId desc";

		Connection conn = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) + 1;
			} else {
				return 1; //ù��° �Խù�
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn,pstmt, rs);
		}
		return -1;
	}
	
	//���� ������ ����
	public boolean nextPage(int pageNumber, String bCategory) {
		String sql = "SELECT * FROM (SELECT (@R := @R+1) RNUM, b1.* FROM news_board b1, (SELECT @R := 0) b2 WHERE b1.bCategory = ? and b1.bAvailable=1) r1 where r1.RNUM<?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bCategory);
			pstmt.setInt(2,getRowNum(bCategory) - (pageNumber -1) * 10);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				return true; //��� ����
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		return false;
	}
	
	
	//ī�װ��� rownum
	public int getRowNum(String bCategory) {
		String sql = "SELECT * FROM (SELECT (@R := @R+1) RNUM FROM news_board b1, (SELECT @R := 0) b2 WHERE b1.bCategory =? "
				+ "and b1.bAvailable=1 order by b1.bId) r1 "
				+ "order by r1.RNUM desc";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bCategory);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) + 1;
			} else {
				return 1; //ù��° �Խù�
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn,pstmt, rs);
		}
		return -1;
	}
	
	//광고 관리 페이지 글 개수
   public int selectAllCount(String bCategory) {
      int num=0;
      
      String sql = "SELECT count(*) FROM news_board WHERE bCategory =?";
      
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      try {
         conn = jdbc.connect();
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, bCategory);
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
         num = rs.getInt(1);
         }
               
      } catch (Exception e) {
         e.printStackTrace();
      }finally {
         jdbc.close(conn, pstmt, rs);
      }
      return num;
   }
	
	//전체 페이지수(main에서 보여지는 근데 이제 카테고리를 더한)
	public int selectCount(String bCategory) {
	  int num=0;
	  
	  String sql = "SELECT count(*) from (SELECT r1.*, i.imgId, i.image_path, i.bId as iId, i.bReg_date as iDate, concat(date_format(r1.bReg_date, '%y%m%d%H%i%s'),r1.bId) as idxno "
	        + "FROM (select * from news_board WHERE bAvailable=1) r1 LEFT JOIN (SELECT MIN(imgId) as imgId ,image_path, bId, bReg_date from news_img "
	        + "group by bId, bReg_date) i ON r1.bId = i.bId and r1.bReg_date = i.bReg_date) b1 right join "
	        + "(select cId from news_category products_sorted, (select @pv := ?) initialisation "
	        + "where find_in_set(cMainID, @pv) > 0 and @pv := concat(@pv, ',', cId) "
	        + "union select cId from news_category where cId = ?) c1 on c1.cId = b1.bCategory where b1.bId is not null;";
	  
	  Connection conn = null;
	  PreparedStatement pstmt = null;
	  ResultSet rs = null;
	  
	  try {
	     conn = jdbc.connect();
	     pstmt = conn.prepareStatement(sql);
	     pstmt.setString(1, bCategory);
	     pstmt.setString(2, bCategory);
	     rs = pstmt.executeQuery();
	     
	     if(rs.next()) {
	     num = rs.getInt(1);
	     }
	     System.out.println("num:" + num);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }finally {
	         jdbc.close(conn, pstmt, rs);
	      }
	      return num;
	   }   

	
	//마이페이지 페이지 수 세기(bStdId)_괄고 제외
	public int selectUserCount(String bStdID) {
		int num=0;
		
		String sql = "SELECT count(*) FROM news_board WHERE bStdID = ? AND bCategory != 18";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bStdID);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			num = rs.getInt(1);
			}
			return num;		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt, rs);
		}
		return -1;
	}	

	
	// 카테고리 별
	public ArrayList<BoardVO> getBoardList(int firstRow, int endRow, String bCategory) {
		ArrayList<BoardVO> articleList = new ArrayList<>();
		String sql = "SELECT * from (SELECT r1.*, i.imgId, i.image_path, i.bId as iId, i.bReg_date as iDate, "
				+ "concat(date_format(r1.bReg_date, '%y%m%d%H%i%s'),r1.bId) as idxno, "
				+ "REGEXP_REPLACE(r1.bContent, '<[^>]+>', '') as text_only "
				+ "FROM (select * from news_board WHERE bAvailable=1 order by bAnnounce desc, bAcc_date desc) r1 "
				+ "LEFT JOIN (SELECT MIN(imgId) as imgId ,image_path, bId, bReg_date from news_img "
				+ "group by bId, bReg_date) i ON r1.bId = i.bId and r1.bReg_date = i.bReg_date) b1 right join "
				+ "(select cId from news_category products_sorted, (select @pv := ?) initialisation "
				+ "where (find_in_set(cMainID, @pv) > 0 and @pv := concat(@pv, ',', cId)) and cId not in ('14', '16') "
				+ "union select cId from news_category where cId = ?) c1 on c1.cId = b1.bCategory where b1.bId is not null LIMIT ?,?;";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bCategory);
			pstmt.setString(2, bCategory);
			pstmt.setInt(3,firstRow -1);
			pstmt.setInt(4, endRow-firstRow+1);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO article = new BoardVO();
				article.setbReg_date(rs.getTimestamp("bReg_date"));
				article.setbStdID(rs.getString("bStdID"));
				article.setbTitle(rs.getString("bTitle"));
				article.setbSubTitle(rs.getString("bSubTitle"));
				article.setbContent(rs.getString("bContent"));
				article.setbCategory(rs.getString("bCategory"));
				article.setbMod_date(rs.getTimestamp("bMod_date"));
				article.setbAcc_date(rs.getTimestamp("bAcc_date"));
				article.setbAvailable(rs.getInt("bAvailable"));
				article.setImage_path(rs.getString("image_path")); 
				article.setIdxno(rs.getString("idxno"));
				article.setText_only(rs.getString("text_only"));
				article.setbAnnounce(rs.getInt("bAnnounce"));
				articleList.add(article);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		return articleList;
	}
	
	// img 미리보기(불러오기)
	public ArrayList<String> getImgPreviewList(int bId, String bReg_date) {
		ArrayList<String> boardPreview = new ArrayList<>();
		String sql = "SELECT image_path FROM news_img WHERE bId=? AND bReg_date=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		Timestamp time = Timestamp.valueOf(bReg_date);
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bId);
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
	
	//메인 뉴스 가져오기
	public BoardVO getMainNews() {
		BoardVO article = new BoardVO();
		String sql = "SELECT r1.*, i.imgId, i.image_path, i.bId as iId, i.bReg_date as iDate, "
				+ "concat(date_format(r1.bReg_date, '%y%m%d%H%i%s'),r1.bId) as idxno, "
				+ "REGEXP_REPLACE(r1.bContent, '<[^>]+>', '') as text_only "
				+ "FROM (select * from news_board WHERE bAvailable=1 order by bAcc_date desc) r1 "
				+ "LEFT JOIN (SELECT MIN(imgId) as imgId ,image_path, bId, bReg_date from news_img "
				+ "group by bId, bReg_date) i ON r1.bId = i.bId and r1.bReg_date = i.bReg_date where r1.bMain = 1 and r1.bCategory != 18 and r1.bId is not null limit 1";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				article.setbReg_date(rs.getTimestamp("bReg_date"));
				article.setbStdID(rs.getString("bStdID"));
				article.setbTitle(rs.getString("bTitle"));
				article.setbSubTitle(rs.getString("bSubTitle"));
				article.setbContent(rs.getString("bContent"));
				article.setbCategory(rs.getString("bCategory"));
				article.setbMod_date(rs.getTimestamp("bMod_date"));
				article.setbAcc_date(rs.getTimestamp("bAcc_date"));
				article.setbAvailable(rs.getInt("bAvailable"));
				article.setImage_path(rs.getString("image_path")); 
				article.setIdxno(rs.getString("idxno"));
				article.setText_only(rs.getString("text_only"));
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		return article;
	}

	//모든 광고 불러오기
	public ArrayList<BoardVO> getAllAdvertise(int firstRow, int endRow, String bCategory) {
		ArrayList<BoardVO> allAdvertise = new ArrayList<>();

		String sql = "SELECT *, concat(date_format(r1.bReg_date, '%y%m%d%H%i%s'), r1.bId) as idxno FROM "
				+ "(SELECT * FROM news_board WHERE bCategory = ? ) r1 "
				+ "LEFT JOIN (SELECT MIN(imgId),image_path, bId, bReg_date from news_img group by bId, bReg_date) i "
				+ "ON r1.bId = i.bId and r1.bReg_date = i.bReg_date order by r1.bAcc_date asc, r1.bReg_date DESC LIMIT ?,? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bCategory);
			pstmt.setInt(2,firstRow -1);
			pstmt.setInt(3, endRow-firstRow+1);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO advertise = new BoardVO();
				advertise.setbId(rs.getInt("bId"));
				advertise.setbReg_date(rs.getTimestamp("bReg_date"));
				advertise.setbStdID(rs.getString("bStdID"));
				advertise.setbTitle(rs.getString("bTitle"));
				advertise.setbSubTitle(rs.getString("bSubTitle"));
				advertise.setbCategory(rs.getString("bCategory"));
				advertise.setbContent(rs.getString("bContent"));
				advertise.setbMod_date(rs.getTimestamp("bMod_date"));
				advertise.setbAcc_date(rs.getTimestamp("bAcc_date"));
				advertise.setbAvailable(rs.getInt("bAvailable"));
				advertise.setImage_path(rs.getString("image_path"));
				advertise.setIdxno(rs.getString("idxno"));
				allAdvertise.add(advertise);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		return allAdvertise;
	}
	
	// 마이페이지에서 모든 기사 불러오기 + 개인 상태(기자/관리자)_광고 제외
	public ArrayList<BoardVO> getUserAllBoard(int firstRow, int endRow, String bStdID) {
		ArrayList<BoardVO> userAllBoard = new ArrayList<>();

		String sql = "SELECT (@R := @R+1) RNUM, r1.*,concat(date_format(r1.bReg_date, '%y%m%d%H%i%s'),r1.bId) as idxno, i.* "
				+ "FROM (SELECT  b1.*, c.cpath FROM news_board b1 JOIN v_category c ON b1.bCategory = c.cId  WHERE b1.bStdID = ? AND b1.bCategory != 18 "
				+ "order by b1.bReg_date asc) r1 left JOIN (SELECT MIN(imgId),image_path, bId as iId, bReg_date as iDate from news_img group by bId, bReg_date) i "
				+ "ON r1.bId = i.iId and r1.bReg_date = i.iDate , (SELECT @R := 0) b2 order by RNUM desc  LIMIT ?,?;";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bStdID);
			pstmt.setInt(2,firstRow -1);
			pstmt.setInt(3, endRow-firstRow+1);

			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO userAllArticle = new BoardVO();
				userAllArticle.setRownum(rs.getInt("RNUM"));
				userAllArticle.setbId(rs.getInt("bId"));
				userAllArticle.setbReg_date(rs.getTimestamp("bReg_date"));
				userAllArticle.setbStdID(rs.getString("bStdID"));
				userAllArticle.setbTitle(rs.getString("bTitle"));
				userAllArticle.setbSubTitle(rs.getString("bSubTitle"));
				userAllArticle.setbCategory(rs.getString("bCategory"));
				userAllArticle.setbMod_date(rs.getTimestamp("bMod_date"));
				userAllArticle.setbAcc_date(rs.getTimestamp("bAcc_date"));
				userAllArticle.setbAvailable(rs.getInt("bAvailable"));
				userAllArticle.setIdxno(rs.getString("idxno"));
				userAllArticle.setImage_path(rs.getString("image_path"));
				userAllArticle.setCpath(rs.getString("cpath"));
				userAllBoard.add(userAllArticle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		return userAllBoard;
	}
	
	//메인 검색에서 광고, pdf 파일 빼고 다 불러오기
	public ArrayList<BoardVO> getAllBoardAllListExcept(int firstRow, int endRow) {
		ArrayList<BoardVO> allBoardList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT *,concat(date_format(r1.bReg_date, '%y%m%d%H%i%s'),r1.bId) as idxno, REGEXP_REPLACE(r1.bContent, '<[^>]+>', '') as text_only, u.* "
				+ "FROM (SELECT  * FROM news_board WHERE bAvailable = 1 AND bCategory != 18) r1 "
				+ "LEFT JOIN (SELECT MIN(imgId),image_path, bId, bReg_date from news_img group by bId, bReg_date) i "
				+ "ON r1.bId = i.bId and r1.bReg_date = i.bReg_date "
				+ "LEFT JOIN (select stdId, name from user) u ON u.stdId = r1.bStdID order by r1.bAcc_date desc LIMIT ?,? ";
	
			try {
				conn = jdbc.connect();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,firstRow -1);
				pstmt.setInt(2, endRow-firstRow+1);
				rs=pstmt.executeQuery();
				while(rs.next()) {
				BoardVO allBoard = new BoardVO();
				allBoard.setbId(rs.getInt("bId"));
				allBoard.setbStdID(rs.getString("bStdID"));
				allBoard.setUserName(rs.getString("name"));
				allBoard.setbTitle(rs.getString("bTitle"));
				allBoard.setbSubTitle(rs.getString("bSubTitle"));
				allBoard.setbContent(rs.getString("bContent"));
				allBoard.setbCategory(rs.getString("bCategory"));
				allBoard.setbReg_date(rs.getTimestamp("bReg_date"));
				allBoard.setbMod_date(rs.getTimestamp("bMod_date"));
				allBoard.setbAcc_date(rs.getTimestamp("bAcc_date"));
				allBoard.setbAvailable(rs.getInt("bAvailable"));
				allBoard.setImage_path(rs.getString("image_path"));
				allBoardList.add(allBoard);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				jdbc.close(conn, pstmt, rs);
		}
		return allBoardList;
	}

	// 기사 보류(재작성 요청)
	public int setArticleDefer(int bId, String bReg_date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "Update news_board SET bAvailable = 3 WHERE bId=? AND bReg_date=? AND bMain = 0 AND bAvailable != 3";
		Timestamp time = Timestamp.valueOf(bReg_date);
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bId);
			pstmt.setTimestamp(2, time);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}
	
	// 광고 수정 시 이미 등록된 광고인 경우 상태변경
	public int setStateChangeAdvertise(int bId, String bReg_date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "Update news_board SET bAvailable = 0, bAcc_date = null WHERE bId=? AND bReg_date=?";
		Timestamp time = Timestamp.valueOf(bReg_date);

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bId);
			pstmt.setTimestamp(2, time);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}
	
	// 기사 등록(승인) + 광고 승인
	public int setArticleAccess(int bId, String bReg_date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "Update news_board SET bAvailable = 1, bAcc_date = CURRENT_TIMESTAMP WHERE bId=? AND bReg_date=? AND (bAvailable = 0 OR bAvailable = 3)";
		Timestamp time = Timestamp.valueOf(bReg_date);
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bId);
			pstmt.setTimestamp(2, time);
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}
	
	// 기사 등록 취소(수정을 위해 메인에서 내린 경우)
	public int setArticleRevise(int bId, String bReg_date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "Update news_board SET bAvailable = 3, bAcc_date = null WHERE bId=? AND bReg_date=? AND bMain = 0";
		Timestamp time = Timestamp.valueOf(bReg_date);

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bId);
			pstmt.setTimestamp(2, time);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}

		return -1;
	}
	
	// 기사+광고 영구 삭제
	public int setArticleDelete(int bId, String bReg_date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM news_board WHERE bId = ? AND bReg_date = ? AND bMain = 0";
		Timestamp time = Timestamp.valueOf(bReg_date);
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bId);
			pstmt.setTimestamp(2, time);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}
	
	// 메인화면에서 기사 검색하기
	public ArrayList<BoardVO> getMainSearch(int firstRow, int endRow, String searchText) {
		ArrayList<BoardVO> getMainSearch = new ArrayList<>();
		
		String search = searchText.trim();
		
		String sql = "SELECT *,concat(date_format(r1.bReg_date, '%y%m%d%H%i%s'),r1.bId) as idxno, REGEXP_REPLACE(r1.bContent, '<[^>]+>', '') as text_only "
				+ "FROM (select b1.*, u.* FROM news_board b1 JOIN (select stdId, name from user) u ON b1.bStdID = u.stdId "
				+ "WHERE b1.bCategory != 18 AND b1.bAvailable = 1 AND";
		String whereSql = null;
		if(searchText != null && !searchText.equals("")) {
			whereSql = " (UPPER(name) LIKE UPPER('%"+search+"%') OR UPPER(bTitle) LIKE UPPER('%"+search+"%') OR UPPER(REGEXP_REPLACE(bContent, '<[^>]+>', '')) LIKE UPPER('%"+search+"%')) AND bAvailable = 1) r1 "
					+ "LEFT JOIN (SELECT MIN(imgId),image_path, bId, bReg_date from news_img group by bId, bReg_date) i "
					+ "ON r1.bId = i.bId and r1.bReg_date = i.bReg_date  order by r1.bAcc_date desc LIMIT ?,?";
		}
		sql = sql+whereSql;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,firstRow -1);
			pstmt.setInt(2, endRow-firstRow+1);			
			rs=pstmt.executeQuery();
			while(rs.next()) {
				BoardVO mainSearch = new BoardVO();
				mainSearch.setbId(rs.getInt("bId"));
				mainSearch.setbStdID(rs.getString("bStdID"));
				mainSearch.setUserName(rs.getString("name"));
				mainSearch.setbTitle(rs.getString("bTitle"));
				mainSearch.setbSubTitle(rs.getString("bSubTitle"));
				mainSearch.setbContent(rs.getString("bContent"));
				mainSearch.setbCategory(rs.getString("bCategory"));
				mainSearch.setbMod_date(rs.getTimestamp("bMod_date"));
				mainSearch.setbAcc_date(rs.getTimestamp("bAcc_date"));
				mainSearch.setbAvailable(rs.getInt("bAvailable"));
				mainSearch.setImage_path(rs.getString("Image_path"));
				mainSearch.setIdxno(rs.getString("idxno"));
				mainSearch.setText_only(rs.getString("text_only"));
				getMainSearch.add(mainSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return getMainSearch;
	}
	
	//메인화면에서 기사 검색한 수
	public int selectMainSearchCount(String searchText) {
		int num=0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT count(*) FROM news_board b JOIN (select stdId, name from user) u ON b.bStdID = u.stdId WHERE (UPPER(u.name) LIKE "
				+ "UPPER('%"+searchText.trim()+"%') OR UPPER(b.bTitle) LIKE UPPER('%"+searchText.trim()+"%') "
						+ "OR UPPER(REGEXP_REPLACE(b.bContent, '<[^>]+>', '')) LIKE UPPER('%"+searchText.trim()+"%')) AND b.bAvailable = 1 and b.bCategory != 18 ";
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return num;
	}
		
	
	// 마이페이지에서 기사 검색
	public ArrayList<BoardVO> getMyPageSearch(int firstRow, int endRow, String bStdID, String searchType, String searchText) {
		ArrayList<BoardVO> getMyPageSearch = new ArrayList<>();
		
		String search = searchText.trim();

		String sql = "SELECT (@R := @R+1) RNUM, r1.*,concat(date_format(r1.bReg_date, '%y%m%d%H%i%s'),r1.bId) as idxno, i.* "
				+ "FROM (SELECT  b1.*, c.cpath FROM news_board b1 JOIN v_category c ON b1.bCategory = c.cId  WHERE b1.bStdID = ? AND b1.bCategory != 18 AND ";
				
		String whereSql = "";
		String colType="";
		if(searchText != null && !searchText.equals("")) {
			switch (searchType) {
				case "allType": {
					colType="(UPPER(bTitle) LIKE UPPER('%"+search+"%') OR UPPER(REGEXP_REPLACE(bContent, '<[^>]+>', '')) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "title": {
					colType="(UPPER(bTitle) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "content": {
					colType="(UPPER(REGEXP_REPLACE(bContent, '<[^>]+>', '')) LIKE UPPER('%"+search+"%'))";
					break;
				}
			}
			whereSql = colType;
			whereSql += "order by b1.bReg_date ASC) r1 left JOIN (SELECT MIN(imgId),image_path, bId as iId, bReg_date as iDate "
					+ "from news_img group by bId, bReg_date) i ON r1.bId = i.iId and r1.bReg_date = i.iDate, "
					+ "(SELECT @R := 0) b2 order by RNUM desc LIMIT ?,?;";
		}
		sql = sql+whereSql;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,bStdID);
			pstmt.setInt(2,firstRow -1);
			pstmt.setInt(3, endRow-firstRow+1);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				BoardVO pageSearch = new BoardVO();
				pageSearch.setRownum(rs.getInt("RNUM"));
				pageSearch.setbId(rs.getInt("bId"));
				pageSearch.setbReg_date(rs.getTimestamp("bReg_date"));
				pageSearch.setbStdID(rs.getString("bStdID"));
				pageSearch.setbTitle(rs.getString("bTitle"));
				pageSearch.setbSubTitle(rs.getString("bSubTitle"));
				pageSearch.setbContent(rs.getString("bContent"));
				pageSearch.setbCategory(rs.getString("bCategory"));
				pageSearch.setbMod_date(rs.getTimestamp("bMod_date"));
				pageSearch.setbAcc_date(rs.getTimestamp("bAcc_date"));
				pageSearch.setbAvailable(rs.getInt("bAvailable"));
				pageSearch.setIdxno(rs.getString("idxno"));
				pageSearch.setImage_path(rs.getString("image_path"));
				pageSearch.setCpath(rs.getString("cpath"));
				getMyPageSearch.add(pageSearch);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return getMyPageSearch;
	}
	
	//검색한 내 기사 게시글 수 불러오기
	public int selectMyPageSearchCount(String bStdID, String searchType, String searchText) {
		int num=0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String search = searchText.trim();
		String sql = "SELECT count(*) FROM news_board WHERE bCategory != 18 AND bStdID=? AND ";
		String whereSql = "";
		String colType="";
		if(searchText != null && !searchText.equals("")) {
			switch (searchType) {
				case "allType": {
					colType="(UPPER(bTitle) LIKE UPPER('%"+search+"%') OR UPPER(REGEXP_REPLACE(bContent, '<[^>]+>', '')) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "title": {
					colType="( UPPER(bTitle) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "content": {
					colType="( UPPER(REGEXP_REPLACE(bContent, '<[^>]+>', '')) LIKE UPPER('%"+search+"%'))";
					break;
				}
			}
			whereSql = colType;
		}
		sql = sql+whereSql;

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,bStdID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return num;
	}
	
	// 관리자페이지에서 기사 검색_미승인
	public ArrayList<BoardVO> getAdminSearch(int firstRow, int endRow, String searchType, String searchText) {
		ArrayList<BoardVO> getAdminSearch = new ArrayList<>();
		
		String search = searchText.trim();

		String sql = "SELECT *, concat(date_format(r1.bReg_date, '%y%m%d%H%i%s'), r1.bId) as idxno "
				+ "FROM (SELECT b1.*, u.*, c.cpath FROM news_board b1 JOIN v_category c ON b1.bCategory = c.cId "
				+ "JOIN (select stdId, name from user) u ON b1.bStdID = u.stdId "
				+ "WHERE b1.bAvailable != 1 AND b1.bCategory != 18 AND ";
				
		String whereSql = "";
		String colType="";
		if(searchText != null && !searchText.equals("")) {
			switch (searchType) {
				case "allType": {
					colType="(UPPER(name) LIKE UPPER('%"+search+"%') OR UPPER(bSubTitle) LIKE UPPER('%"+search+"%') OR UPPER(bTitle) LIKE UPPER('%"+search+"%') OR UPPER(REGEXP_REPLACE(bContent, '<[^>]+>', '')) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "name": {
					colType="(UPPER(name) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "title": {
					colType="(UPPER(bTitle) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "subTitle": {
					colType="(UPPER(bSubTitle) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "content": {
					colType="(UPPER(REGEXP_REPLACE(bContent, '<[^>]+>', '')) LIKE UPPER('%"+search+"%'))";
					break;
				}
			}
			whereSql = colType;
			whereSql += ") r1 LEFT JOIN (SELECT MIN(imgId),image_path, bId, bReg_date from news_img group by bId, bReg_date) i "
					+ "ON r1.bId = i.bId and r1.bReg_date = i.bReg_date  order by r1.bMain DESC, r1.bAcc_date DESC, r1.bReg_date DESC LIMIT ?,? ";
		}
		sql = sql+whereSql;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,firstRow -1);
			pstmt.setInt(2, endRow-firstRow+1);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardVO adminSearch = new BoardVO();
				adminSearch.setbId(rs.getInt("bId"));
				adminSearch.setbStdID(rs.getString("bStdID"));
				adminSearch.setbTitle(rs.getString("bTitle"));
				adminSearch.setbSubTitle(rs.getString("bSubTitle"));
				adminSearch.setbContent(rs.getString("bContent"));
				adminSearch.setbCategory(rs.getString("bCategory"));
				adminSearch.setbReg_date(rs.getTimestamp("bReg_date"));
				adminSearch.setbMod_date(rs.getTimestamp("bMod_date"));
				adminSearch.setbAcc_date(rs.getTimestamp("bAcc_date"));
				adminSearch.setbAvailable(rs.getInt("bAvailable"));
				adminSearch.setImage_path(rs.getString("image_path"));
				adminSearch.setIdxno(rs.getString("idxno"));
				adminSearch.setUserName(rs.getString("name"));
				adminSearch.setCpath(rs.getString("cpath"));
				getAdminSearch.add(adminSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return getAdminSearch;
	}
	
	//검색한 기사 게시글 수 불러오기(관리자)_미승인
	public int selectAdminSearchCount(String searchType, String searchText) {
		int num=0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String search = searchText.trim();
		String sql = "SELECT count(*) FROM news_board b1 JOIN  (select stdId, name from user) u ON b1.bStdID = u.stdId WHERE bCategory != 18 AND bAvailable != 1 AND ";
		String whereSql = "";
		String colType="";
		if(searchText != null && !searchText.equals("")) {
			switch (searchType) {
				case "allType": {
					colType=" (UPPER(u.name) LIKE UPPER('%"+search+"%') OR UPPER(b1.bSubTitle) LIKE UPPER('%"+search+"%') OR UPPER(b1.bTitle) LIKE UPPER('%"+search+"%') OR UPPER(REGEXP_REPLACE(b1.bContent, '<[^>]+>', '')) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "name": {
					colType="(UPPER(u.name) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "title": {
					colType="(UPPER(b1.bTitle) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "subTitle": {
					colType="(UPPER(b1.bSubTitle) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "content": {
					colType="(UPPER(REGEXP_REPLACE(b1.bContent, '<[^>]+>', '')) LIKE UPPER('%"+search+"%'))";
					break;
				}
			}
			whereSql = colType;

		}
		sql = sql+whereSql;

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return num;
	}
	
	// 관리자페이지에서 기사 검색_승인
	public ArrayList<BoardVO> getAccessSearch(int firstRow, int endRow, String searchType, String searchText) {
		ArrayList<BoardVO> getAccessSearch = new ArrayList<>();
		
		String search = searchText.trim();
		
		String sql = "SELECT *, concat(date_format(r1.bReg_date, '%y%m%d%H%i%s'), r1.bId) as idxno "
				+ "FROM (SELECT b1.*, u.*, c.cpath FROM news_board b1 JOIN v_category c ON b1.bCategory = c.cId "
				+ "JOIN (select stdId, name from user) u ON b1.bStdID = u.stdId "
				+ "WHERE b1.bAvailable = 1 AND b1.bCategory != 18 AND ";
				
		String whereSql = "";
		String colType="";
		if(searchText != null && !searchText.equals("")) {
			switch (searchType) {
				case "allType": {
					colType="(UPPER(name) LIKE UPPER('%"+search+"%') OR UPPER(bSubTitle) LIKE UPPER('%"+search+"%') OR UPPER(bTitle) LIKE UPPER('%"+search+"%') OR UPPER(REGEXP_REPLACE(bContent, '<[^>]+>', '')) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "name": {
					colType="(UPPER(name) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "title": {
					colType="(UPPER(bTitle) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "subTitle": {
					colType="(UPPER(bSubTitle) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "content": {
					colType="(UPPER(REGEXP_REPLACE(bContent, '<[^>]+>', '')) LIKE UPPER('%"+search+"%'))";
					break;
				}
			}
			whereSql = colType;
			whereSql += ") r1 LEFT JOIN (SELECT MIN(imgId),image_path, bId, bReg_date from news_img group by bId, bReg_date) i "
					+ "ON r1.bId = i.bId and r1.bReg_date = i.bReg_date  order by r1.bMain DESC, r1.bAcc_date DESC, r1.bReg_date DESC LIMIT ?,? ";
		}
		sql = sql+whereSql;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,firstRow -1);
			pstmt.setInt(2, endRow-firstRow+1);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardVO adminSearch = new BoardVO();
				adminSearch.setbId(rs.getInt("bId"));
				adminSearch.setbStdID(rs.getString("bStdID"));
				adminSearch.setbTitle(rs.getString("bTitle"));
				adminSearch.setbSubTitle(rs.getString("bSubTitle"));
				adminSearch.setbContent(rs.getString("bContent"));
				adminSearch.setbCategory(rs.getString("bCategory"));
				adminSearch.setbReg_date(rs.getTimestamp("bReg_date"));
				adminSearch.setbMod_date(rs.getTimestamp("bMod_date"));
				adminSearch.setbAcc_date(rs.getTimestamp("bAcc_date"));
				adminSearch.setbAvailable(rs.getInt("bAvailable"));
				adminSearch.setImage_path(rs.getString("image_path"));
				adminSearch.setIdxno(rs.getString("idxno"));
				adminSearch.setbMain(rs.getInt("bMain"));
				adminSearch.setUserName(rs.getString("name"));
				adminSearch.setCpath(rs.getString("cpath"));
				getAccessSearch.add(adminSearch);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return getAccessSearch;
	}
	
	//검색한 기사 게시글 수 불러오기(관리자)_승인
	public int selectAccessSearchCount(String searchType, String searchText) {
		int num=0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String search = searchText.trim();
		String sql = "SELECT count(*) FROM news_board b1 JOIN (select stdId, name from user) u ON b1.bStdID = u.stdId WHERE bCategory != 18 AND bAvailable = 1 AND ";
		String whereSql = "";
		String colType="";
		if(searchText != null && !searchText.equals("")) {
			switch (searchType) {
				case "allType": {
					colType=" (UPPER(u.name) LIKE UPPER('%"+search+"%') OR UPPER(b1.bSubTitle) LIKE UPPER('%"+search+"%') OR UPPER(b1.bTitle) LIKE UPPER('%"+search+"%') OR UPPER(REGEXP_REPLACE(b1.bContent, '<[^>]+>', '')) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "name": {
					colType="(UPPER(u.name) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "title": {
					colType="(UPPER(b1.bTitle) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "subTitle": {
					colType="(UPPER(b1.bSubTitle) LIKE UPPER('%"+search+"%'))";
					break;
				}
				case "content": {
					colType="(UPPER(REGEXP_REPLACE(b1.bContent, '<[^>]+>', '')) LIKE UPPER('%"+search+"%'))";
					break;
				}
			}
			whereSql = colType;
		}
		sql = sql+whereSql;
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return num;
	}
	
	//관리자페이지에서 광고, pdf 파일 빼고 다 불러오기_미승인
	public ArrayList<BoardVO> getAllBoardListExcept(int firstRow, int endRow) {
		ArrayList<BoardVO> allBoardList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT *, concat(date_format(r1.bReg_date, '%y%m%d%H%i%s'), r1.bId) as idxno "
				+ "FROM (SELECT  b1.*, c.cpath FROM news_board b1 JOIN v_category c ON b1.bCategory = c.cId "
				+ " WHERE bCategory != 18 AND bAvailable != 1 ) r1 "
				+ "LEFT JOIN (SELECT MIN(imgId),image_path, bId, bReg_date from news_img group by bId, bReg_date) i "
				+ "ON r1.bId = i.bId and r1.bReg_date = i.bReg_date "
				+ "LEFT JOIN (SELECT stdId, name from user) u ON r1.bStdID = u.stdId "
				+ "order by r1.bMain DESC, r1.bAcc_date DESC, r1.bReg_date DESC LIMIT ?,?";
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,firstRow -1);
			pstmt.setInt(2, endRow-firstRow+1);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				BoardVO allBoard = new BoardVO();
				allBoard.setbId(rs.getInt("bId"));
				allBoard.setbStdID(rs.getString("bStdID"));
				allBoard.setUserName(rs.getString("name"));
				allBoard.setbTitle(rs.getString("bTitle"));
				allBoard.setbSubTitle(rs.getString("bSubTitle"));
				allBoard.setbContent(rs.getString("bContent"));
				allBoard.setbCategory(rs.getString("bCategory"));
				allBoard.setbReg_date(rs.getTimestamp("bReg_date"));
				allBoard.setbMod_date(rs.getTimestamp("bMod_date"));
				allBoard.setbAcc_date(rs.getTimestamp("bAcc_date"));
				allBoard.setbAvailable(rs.getInt("bAvailable"));
				allBoard.setImage_path(rs.getString("image_path"));
				allBoard.setIdxno(rs.getString("idxno"));
				allBoard.setCpath(rs.getString("cpath"));
				allBoardList.add(allBoard);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return allBoardList;
	}
	
	//모든 기사 목록(상태 상관없이) 수_미승인
	public int selectAllBoardListExceptCount() {
		int num=0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT count(*) FROM news_board WHERE bCategory != 18 AND bAvailable != 1";
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return num;
	}
	
	//관리자페이지에서 광고, pdf 파일 빼고 다 불러오기_승인
	public ArrayList<BoardVO> getAllAccessBoardListExcept(int firstRow, int endRow) {
		ArrayList<BoardVO> allBoardList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT *, concat(date_format(r1.bReg_date, '%y%m%d%H%i%s'), r1.bId) as idxno "
				+ "FROM (SELECT  b1.*, c.cpath FROM news_board b1 JOIN v_category c ON b1.bCategory = c.cId "
				+ " WHERE bCategory != 18 AND bAvailable = 1 ) r1 "
				+ "LEFT JOIN (SELECT MIN(imgId),image_path, bId, bReg_date from news_img group by bId, bReg_date) i "
				+ "ON r1.bId = i.bId and r1.bReg_date = i.bReg_date "
				+ "LEFT JOIN (SELECT stdId, name from user) u ON r1.bStdID = u.stdId "
				+ "order by r1.bMain DESC, r1.bAnnounce DESC, r1.bAcc_date DESC, r1.bReg_date DESC LIMIT ?,?";
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,firstRow -1);
			pstmt.setInt(2, endRow-firstRow+1);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				BoardVO allBoard = new BoardVO();
				allBoard.setbId(rs.getInt("bId"));
				allBoard.setbStdID(rs.getString("bStdID"));
				allBoard.setUserName(rs.getString("name"));
				allBoard.setbTitle(rs.getString("bTitle"));
				allBoard.setbSubTitle(rs.getString("bSubTitle"));
				allBoard.setbContent(rs.getString("bContent"));
				allBoard.setbCategory(rs.getString("bCategory"));
				allBoard.setbReg_date(rs.getTimestamp("bReg_date"));
				allBoard.setbMod_date(rs.getTimestamp("bMod_date"));
				allBoard.setbAcc_date(rs.getTimestamp("bAcc_date"));
				allBoard.setbAvailable(rs.getInt("bAvailable"));
				allBoard.setImage_path(rs.getString("image_path"));
				allBoard.setIdxno(rs.getString("idxno"));
				allBoard.setbMain(rs.getInt("bMain"));
				allBoard.setCpath(rs.getString("cpath"));
				allBoard.setbAnnounce(rs.getInt("bAnnounce"));
				allBoardList.add(allBoard);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return allBoardList;
	}
	
	//전체 메인 페이지 게시글 수
	public int selectAllBoardAllListCount() {
		int num=0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT count(*) FROM news_board WHERE bCategory != 18 and bAvailable = 1";
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return num;
	}
	

	//해당 게시글 내용 불러오기(기사 수정 시)
	public BoardVO getBoardUpdateContent(int bId, String bReg_date) {
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		BoardVO boardContent = new BoardVO();
		Timestamp time = Timestamp.valueOf(bReg_date);
		
		String sql = "SELECT * FROM news_board WHERE bId=? and bReg_date=?";
	
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bId);
			pstmt.setTimestamp(2, time);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				boardContent.setbStdID(rs.getString("bStdID"));
				boardContent.setbTitle(rs.getString("bTitle"));
				boardContent.setbSubTitle(rs.getString("bSubTitle"));
				boardContent.setbContent(rs.getString("bContent"));
				boardContent.setbCategory(rs.getString("bCategory"));
				boardContent.setbAvailable(rs.getInt("bAvailable"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt,rs);
		}
		return boardContent;
	}
	

	//게시판 내용 불러오기(기사 내부 내용)
	public BoardVO getBoardDetailContent(int bId, Timestamp bReg_date, String bCategory) {
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		BoardVO boardContent = new BoardVO();
		System.out.println(bId+" "+bReg_date + " " + bCategory);
		String sql = "select * from (SELECT b.bId, b.bReg_date, b.bStdID, u.name, u.email, u.dept, b.bTitle, b.bSubTitle, b.bContent, b.bCategory, b.bMod_date, b.bAcc_date\r\n"
				+ "FROM news_board as b JOIN user as u ON b.bStdID = u.stdID)r1 left join (select cId,cName from news_category) c \r\n"
				+ "on r1.bCategory=c.cId where r1.bId=? and r1.bReg_date=? and r1.bCategory=? ";

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bId);
			pstmt.setTimestamp(2, bReg_date);
			pstmt.setString(3, bCategory);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				boardContent.setbId(rs.getInt("bId"));
				boardContent.setbReg_date(rs.getTimestamp("bReg_date"));
				boardContent.setbStdID(rs.getString("bStdID"));
				boardContent.setUserName(rs.getString("name"));
				boardContent.setUserEmail(rs.getString("email"));
				boardContent.setUserDept(rs.getString("dept"));
				boardContent.setcName(rs.getString("cName"));
				boardContent.setbTitle(rs.getString("bTitle"));
				boardContent.setbSubTitle(rs.getString("bSubTitle"));
				boardContent.setbCategory(rs.getString("bCategory"));
				boardContent.setbContent(rs.getString("bContent"));
				boardContent.setbMod_date(rs.getTimestamp("bMod_date"));
				boardContent.setbAcc_date(rs.getTimestamp("bAcc_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return boardContent;
	}
	
	//학보사 소식내용 불러오기(학보사 소식 내용)
	public BoardVO getInfoContent(String bCategory) {
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		BoardVO boardContent = new BoardVO();
		String sql = "select * from (SELECT bStdID, bTitle, bSubTitle, bContent, bCategory "
				+ "FROM news_board) r1 left join (select cId,cName from news_category) c "
				+ "on r1.bCategory=c.cId where r1.bCategory=?" ;

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bCategory);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				boardContent.setbStdID(rs.getString("bStdID"));
				boardContent.setcName(rs.getString("cName"));
				boardContent.setbTitle(rs.getString("bTitle"));
				boardContent.setbSubTitle(rs.getString("bSubTitle"));
				boardContent.setbCategory(rs.getString("bCategory"));
				boardContent.setbContent(rs.getString("bContent"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return boardContent;
	}
	
	//미리보기 내용 불러오기(미리보기 기사 내부 내용)
	public BoardVO getBoardDetailPreviewContent(int bId, Timestamp bReg_date, String bStdID) {
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		BoardVO boardContent = new BoardVO();
		
		String sql = "SELECT b.bId, b.bReg_date, b.bStdID, u.name, u.email, u.dept, b.bTitle, b.bSubTitle, b.bContent, b.bCategory, b.bMod_date, b.bAcc_date "
				+ "FROM news_board as b JOIN user as u ON b.bStdID = u.stdID where b.bId=? and b.bReg_date=? and b.bStdID=?";

		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bId);
			pstmt.setTimestamp(2, bReg_date);
			pstmt.setString(3, bStdID);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				boardContent.setbId(rs.getInt("bId"));
				boardContent.setbReg_date(rs.getTimestamp("bReg_date"));
				boardContent.setbStdID(rs.getString("bStdID"));
				boardContent.setUserName(rs.getString("name"));
				boardContent.setUserEmail(rs.getString("email"));
				boardContent.setUserDept(rs.getString("dept"));
				boardContent.setbTitle(rs.getString("bTitle"));
				boardContent.setbSubTitle(rs.getString("bSubTitle"));
				boardContent.setbCategory(rs.getString("bCategory"));
				boardContent.setbContent(rs.getString("bContent"));
				boardContent.setbMod_date(rs.getTimestamp("bMod_date"));
				boardContent.setbAcc_date(rs.getTimestamp("bAcc_date"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return boardContent;
	}
	
	//일반 기자 게시물 작성
	public int write(String bStdID,String bTitle, String bSubTitle, String bContent, String bCategory ) {
		String sql = "INSERT INTO news_board(bStdID, bTitle, bSubTitle, bContent, bCategory, bAvailable) VALUES(?,?,?,?,?,?)";
		
		Connection conn = null;
		PreparedStatement  pstmt = null;
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bStdID);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bSubTitle);
			pstmt.setString(4, bContent);
			pstmt.setString(5, bCategory);
			pstmt.setInt(6, 0); //삭제된 글인지 아닌지
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn,pstmt);
		}
		return -1;
	}
		
	//일반 기자 게시물 작성시 이미지정보db저장
	public int writeImage(String image_path,int bId, String bReg_date, String img_name, int img_width, int img_height ) {
		String sql = "INSERT INTO news_img(image_path, bId, bReg_date, img_name, img_width, img_height) VALUES(?,?,?,?,?,?)";
		
		Connection conn = null;
		PreparedStatement  pstmt = null;
		Timestamp time = Timestamp.valueOf(bReg_date);
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, image_path);
			pstmt.setInt(2, bId);
			pstmt.setTimestamp(3, time);
			pstmt.setString(4, img_name);
			pstmt.setInt(5, img_width);
			pstmt.setInt(6, img_height); //삭제된 글인지 아닌지
			return pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn,pstmt);
		}
		return -1;
	}
	
	//이미지 패스에 저장위해 bId랑 bReg_date꺼냄
	public BoardVO getKeyForImage(String bStdID,String bTitle, String bContent, String bCategory){
		BoardVO keyList = new BoardVO();
		String sql = "select bId, bReg_date from news_board where bStdID=? and bTitle=? and bContent=? and bCategory=? order by bReg_date desc limit 1"; 
		
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bStdID);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setString(4, bCategory);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				keyList = new BoardVO();
				keyList.setbId(rs.getInt("bId"));
				keyList.setbReg_date(rs.getTimestamp("bReg_date"));
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		
		return keyList;
	}
	
	//이미지 영구삭제(기자/관리자)
	public int setArticleImageDel(int bId, String bReg_date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM news_img WHERE bId=? and bReg_date=?";
		Timestamp time = Timestamp.valueOf(bReg_date);
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bId);
			pstmt.setTimestamp(2, time);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}
	
	//게시글 내용 업데이트
	public int boardUpdateContent(int bId, String bReg_date, String bStdID, String bTitle, String bSubTitle, String bContent, String bCategory ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Timestamp time = Timestamp.valueOf(bReg_date);
		String sql = "Update news_board SET bStdID=?, bTitle=?, bSubTitle=?, bContent=?, bCategory=?, bMod_date=CURRENT_TIMESTAMP, bAvailable = 0, bAcc_date = null WHERE bId=? AND bReg_date=?";
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bStdID);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bSubTitle);
			pstmt.setString(4, bContent);
			pstmt.setString(5, bCategory);
			pstmt.setInt(6, bId);
			pstmt.setTimestamp(7, time);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		
		return -1;
	}
	
	
	//광고 검색하기
	public ArrayList<BoardVO> getAdvertiseSearch(int firstRow, int endRow, String bCategory, String searchText) {
		ArrayList<BoardVO> allAdvertise = new ArrayList<>();

		String search = searchText.trim();
		String sql = "SELECT *, concat(date_format(r1.bReg_date, '%y%m%d%H%i%s'), r1.bId) as idxno, "
				+ " REGEXP_REPLACE(r1.bContent, '<[^>]+>', '') as text_only "
				+ "	FROM (SELECT * FROM news_board "
				+ "	WHERE bCategory = ? AND (UPPER(bTitle) LIKE UPPER('%"+search+"%') OR UPPER(REGEXP_REPLACE(bContent, '<[^>]+>', '')) LIKE UPPER('%"+search+"%'))) r1"
				+ "	LEFT JOIN (SELECT MIN(imgId),image_path, bId, bReg_date from news_img group by bId, bReg_date) i "
				+ "	ON r1.bId = i.bId and r1.bReg_date = i.bReg_date order by r1.bAcc_date asc, r1.bReg_date DESC LIMIT ?,? ";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bCategory);
			pstmt.setInt(2,firstRow -1);
			pstmt.setInt(3, endRow-firstRow+1);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO advertise = new BoardVO();
				advertise.setbId(rs.getInt("bId"));
				advertise.setbReg_date(rs.getTimestamp("bReg_date"));
				advertise.setbStdID(rs.getString("bStdID"));
				advertise.setbTitle(rs.getString("bTitle"));
				advertise.setbSubTitle(rs.getString("bSubTitle"));
				advertise.setbCategory(rs.getString("bCategory"));
				advertise.setbContent(rs.getString("bContent"));
				advertise.setbMod_date(rs.getTimestamp("bMod_date"));
				advertise.setbAcc_date(rs.getTimestamp("bAcc_date"));
				advertise.setbAvailable(rs.getInt("bAvailable"));
				advertise.setImage_path(rs.getString("image_path"));
				advertise.setIdxno(rs.getString("idxno"));
				advertise.setText_only(rs.getString("text_only"));
				allAdvertise.add(advertise);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		return allAdvertise;
	}
	
	// 광고 검색 게시글 수 불러오기
	public int selectAdvertiseSearchCount(String bCategory, String searchText) {
		int num=0;
		
		String sql = "SELECT count(*) FROM news_board WHERE bCategory =? AND (UPPER(bTitle) LIKE UPPER('%"+searchText.trim()+"%') OR UPPER(bContent) LIKE UPPER('%"+searchText.trim()+"%'))";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bCategory);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			num = rs.getInt(1);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt, rs);
		}
		return num;
	}
	
	//대표 기사 수
	public int selectMainArticleCount() {
		String sql = "SELECT COUNT(*) FROM news_board WHERE bMain = 1";
		int num = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt, rs);
		}
		return num;
	}
	
	//대표기사를 처음 고르는 경우(대표 기사가 아무것도 없는 경우)
	public int setMainArticleNone(int bId, String bReg_date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Timestamp time = Timestamp.valueOf(bReg_date);
		
		String sql = "Update news_board SET bMain = 1 WHERE bId=? AND bReg_date = ?";
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bId);
			pstmt.setTimestamp(2, time);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}
	
	//메인화면 대표 기사 설정(대표 기사가 1개인 경우)
	@SuppressWarnings("resource")
	public int setMainArticle(int bId, String bReg_date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Timestamp time = Timestamp.valueOf(bReg_date);
		
		int changeSucc = 0;
		int selectSucc = 0;
		int preMainbId  = 0;
		Timestamp preMainbReg_date  = null;

		String findMainSql = "SELECT * FROM news_board WHERE bAvailable = 1 AND bMain = 1";
		String newMainSql = "Update news_board SET bMain = 1 WHERE bId=? AND bReg_date = ?";
		String preMainSql = "Update news_board SET bMain = 0 WHERE bId=? AND bReg_date = ?";
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(findMainSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();
			rs.last();
			preMainbId = rs.getInt("bId");
			preMainbReg_date = Timestamp.valueOf(rs.getString("bReg_date"));
			selectSucc = rs.getRow();
			rs.beforeFirst();
			if(selectSucc == 1) {
				pstmt = conn.prepareStatement(newMainSql);
				pstmt.setInt(1, bId);
				pstmt.setTimestamp(2, time);
				changeSucc = pstmt.executeUpdate();
				
				if(changeSucc == 1) {
					pstmt = conn.prepareStatement(preMainSql);
					pstmt.setInt(1, preMainbId);
					pstmt.setTimestamp(2, preMainbReg_date);
					return pstmt.executeUpdate();			
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return -1;
	}
	
	//대표기사가 두 개 이상 존재하는 경우(다 대표에서 지움)
	public int setMainArticleMult() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String Sql = "UPDATE news_board SET bMain = 0 WHERE bMain = 1";
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(Sql);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}
	

	//공지 기사 수
	public int selectAnnounceArticleCount() {
		String sql = "SELECT COUNT(*) FROM news_board WHERE bAnnounce = 1";
		int num = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt, rs);
		}
		return num;
	}
	
	//공지기사를 처음 고르는 경우(대표 기사가 아무것도 없는 경우)
	public int setAnnounceArticleNone(int bId, String bReg_date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Timestamp time = Timestamp.valueOf(bReg_date);
		
		String sql = "Update news_board SET bAnnounce = 1 WHERE bId=? AND bReg_date = ?";
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bId);
			pstmt.setTimestamp(2, time);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}
	
	//공지 기사 설정(기존 공지 기사가 1개인 경우)
	@SuppressWarnings("resource")
	public int setAnnounceArticle(int bId, String bReg_date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Timestamp time = Timestamp.valueOf(bReg_date);
		
		int changeSucc = 0;
		int selectSucc = 0;
		int preMainbId  = 0;
		Timestamp preMainbReg_date  = null;

		String findMainSql = "SELECT * FROM news_board WHERE bAvailable = 1 AND bAnnounce = 1";
		String newMainSql = "Update news_board SET bAnnounce = 1 WHERE bId=? AND bReg_date = ?";
		String preMainSql = "Update news_board SET bAnnounce = 0 WHERE bId=? AND bReg_date = ?";
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(findMainSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();
			rs.last();
			preMainbId = rs.getInt("bId");
			preMainbReg_date = Timestamp.valueOf(rs.getString("bReg_date"));
			selectSucc = rs.getRow();
			rs.beforeFirst();
			if(selectSucc == 1) {
				pstmt = conn.prepareStatement(newMainSql);
				pstmt.setInt(1, bId);
				pstmt.setTimestamp(2, time);
				changeSucc = pstmt.executeUpdate();
				
				if(changeSucc == 1) {
					pstmt = conn.prepareStatement(preMainSql);
					pstmt.setInt(1, preMainbId);
					pstmt.setTimestamp(2, preMainbReg_date);
					return pstmt.executeUpdate();			
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt, rs);
		}
		return -1;
	}
	
	//공지기사가 두 개 이상 존재하는 경우(다 공지에서 삭제)
	public int setAnnounceArticleMult() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String Sql = "UPDATE news_board SET bAnnounce = 0 WHERE bAnnounce = 1";
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(Sql);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.close(conn, pstmt);
		}
		return -1;
	}
	
	//최신 10개 가져올때 사용위함
	public ArrayList<BoardVO> getRecentBoardList() {
		ArrayList<BoardVO> articleList = new ArrayList<>();
		String sql = "SELECT *, concat(date_format(r1.bReg_date, '%y%m%d%H%i%s'), r1.bId) as idxno "
				+ "FROM (SELECT * FROM news_board WHERE bAvailable=1 and bCategory != '18' and bCategory != '14' and bCategory != '15' and bCategory != '16' and bCategory != '17') r1 "
				+ "LEFT JOIN (SELECT MIN(imgId),image_path, bId, bReg_date from news_img group by bId, bReg_date) i "
				+ "ON r1.bId = i.bId and r1.bReg_date = i.bReg_date  order by r1.bAcc_date desc limit 0,10;";
  
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO article = new BoardVO();
				article.setbReg_date(rs.getTimestamp("bReg_date"));
				article.setbStdID(rs.getString("bStdID"));
				article.setbTitle(rs.getString("bTitle"));
				article.setbSubTitle(rs.getString("bSubTitle"));
				article.setbCategory(rs.getString("bCategory"));
				article.setbContent(rs.getString("bContent"));
				article.setbMod_date(rs.getTimestamp("bMod_date"));
				article.setbAcc_date(rs.getTimestamp("bAcc_date"));
				article.setbAvailable(rs.getInt("bAvailable"));
				article.setImage_path(rs.getString("image_path"));
				article.setIdxno(rs.getString("idxno"));
				articleList.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt,rs);
		}
		return articleList;
	}
	
	//학보사소식->하단 캐러셀 기사
	@SuppressWarnings("resource")
	public ArrayList<BoardVO> getPrevNextBoardList(int bId, Timestamp bReg_date) {
		ArrayList<BoardVO> prevNextList = new ArrayList<>();
		String findSql="select bAcc_date from news_board where bId=? AND bReg_Date=? and bAvailable = 1 and bCategory = '15'";
		String sql = "select *, concat(date_format(c.bReg_date, '%y%m%d%H%i%s'), c.bId) as idxno, i.image_path from (( "
				+ "select * from news_board where bAcc_date=? and bAvailable = 1 and bCategory = '15' "
				+ "union "
				+ "select * from (select * from news_board where bAcc_date>? and bAvailable = 1 and bCategory = '15' "
				+ "order by bAcc_date asc limit 3) a "
				+ "union "
				+ "select * from (select * from news_board where bAcc_date<? and bAvailable = 1 and bCategory = '15' "
				+ "order by bAcc_date desc limit 3) b "
				+ ") c LEFT JOIN (SELECT MIN(imgId) as imgId ,image_path, bId, bReg_date from news_img "
				+ "group by bId, bReg_date) i ON c.bId = i.bId and c.bReg_date = i.bReg_date) order by c.bAcc_date";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		int count=0;
		Timestamp bAcc_date  = null;
		
		try {
			conn = jdbc.connect();
			pstmt = conn.prepareStatement(findSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setInt(1, bId);
			pstmt.setTimestamp(2, bReg_date);
			rs = pstmt.executeQuery();
			rs.last();
			bAcc_date = Timestamp.valueOf(rs.getString("bAcc_date"));
			count = rs.getRow();
			rs.beforeFirst();
			if(count == 1) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setTimestamp(1, bAcc_date);
				pstmt.setTimestamp(2, bAcc_date);
				pstmt.setTimestamp(3, bAcc_date);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					BoardVO article = new BoardVO();
					article.setbReg_date(rs.getTimestamp("bReg_date"));
					article.setbStdID(rs.getString("bStdID"));
					article.setbTitle(rs.getString("bTitle"));
					article.setbSubTitle(rs.getString("bSubTitle"));
					article.setbCategory(rs.getString("bCategory"));
					article.setbContent(rs.getString("bContent"));
					article.setbMod_date(rs.getTimestamp("bMod_date"));
					article.setbAcc_date(rs.getTimestamp("bAcc_date"));
					article.setbAvailable(rs.getInt("bAvailable"));
					article.setImage_path(rs.getString("image_path"));
					article.setIdxno(rs.getString("idxno"));
					prevNextList.add(article);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbc.close(conn, pstmt, rs);
		}
		return prevNextList;
	}
}