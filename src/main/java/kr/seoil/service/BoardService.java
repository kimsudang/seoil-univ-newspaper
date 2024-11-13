package kr.seoil.service;

import java.sql.Timestamp;
import java.util.ArrayList;

import kr.seoil.dao.BoardDAO;
import kr.seoil.vo.BoardVO;


public class BoardService {
	private static BoardService service = new BoardService();
	public BoardDAO dao = BoardDAO.getInstance();
	
	private BoardService() { }
	public static BoardService getInstance() {
		return service;
	}

	public int getNext(){
		 return dao.getNext();
	}
	
	public int selectAllCount(String bCategory) {
		return dao.selectAllCount(bCategory);
	}
	public ArrayList<BoardVO> getBoardList(int firstRow, int endRow, String bCategory) {
		return dao.getBoardList(firstRow,endRow, bCategory);
	}
	
	public int selectCount(String bCategory) {
		return dao.selectCount(bCategory);
	}
	
	public boolean nextPage (int pageNumber, String bCategory) {
		return dao.nextPage(pageNumber, bCategory);
	}
	
	public BoardVO getMainNews() {
		return dao.getMainNews();
	}
	public int getRowNum(String bCategory) {
		return dao.getRowNum(bCategory);
	}
	// ��� ����
	public int setArticleDefer(int bId, String bReg_date) {
		return dao.setArticleDefer(bId, bReg_date);
	}
	//기사 승인
	public int setArticleAccess(int bId, String bReg_date) {
		return dao.setArticleAccess(bId, bReg_date);
	}
	// 기사 등록 취소(수정을 위해 메인에서 내린 경우)
	public int setArticleRevise(int bId, String bReg_date) {
		return dao.setArticleRevise(bId, bReg_date);
	}
	public int setArticleDelete(int bId, String bReg_date) {
		return dao.setArticleDelete(bId, bReg_date);
	}
	
	//메인에서 검색
	public ArrayList<BoardVO> getMainSearch(int firstRow, int endRow, String searchText){
		return dao.getMainSearch(firstRow, endRow, searchText);
	}
	//메인 검색 수
	public int selectMainSearchCount(String searchText) {
		return dao.selectMainSearchCount(searchText);
	}
	
	public int writeImage(String image_path,int bId, String bReg_date, String img_name, int img_width, int img_height ) {
		return dao.writeImage(image_path, bId, bReg_date, img_name, img_width, img_height );
	}
	
	public int write(String bStdID,String bTitle, String bSubTitle, String bContent, String bCategory){
		 return dao.write(bStdID, bTitle, bSubTitle, bContent, bCategory);
	}

	public BoardVO getKeyForImage(String bStdID,String bTitle, String bContent, String bCategory){
		return dao.getKeyForImage(bStdID, bTitle, bContent, bCategory);
	}
	
	// ������������� ��� �ҷ�����
	public ArrayList<BoardVO> getUserAllBoard(int firstRow, int endRow, String bStdID){
		return dao.getUserAllBoard(firstRow, endRow, bStdID);
	}

	//���� ���ؼ� �Խñ� ���� ���� ��������
	public BoardVO getBoardUpdateContent(int bId, String bReg_date){
		return dao.getBoardUpdateContent(bId, bReg_date);
	}
	
	
	//�Խñ� ������Ʈ
	public int boardUpdateContent(int bId,  String bReg_date, String bStdID, String bTitle, String bSubTitle, String bContent, String bCategory ) {
		return dao.boardUpdateContent(bId, bReg_date, bStdID, bTitle, bSubTitle, bContent, bCategory );
	}
	
	//�� timestamp�� �ް� ���μ���....
	public BoardVO getBoardDetailContent(int bId, Timestamp bReg_date, String bCategory) {
		return dao.getBoardDetailContent(bId, bReg_date, bCategory);
	}
	
	//�̹��� path �ҷ�����
	public ArrayList<String> getImgPreviewList(int bId, String bReg_date){
		return dao.getImgPreviewList(bId, bReg_date);
	}
		
	public int setArticleImageDel(int bId, String bReg_date) {
		return dao.setArticleImageDel(bId, bReg_date);
	}
	
	public ArrayList<BoardVO> getAllAdvertise(int firstRow, int endRow, String bCategory) {
		return dao.getAllAdvertise(firstRow, endRow, bCategory);
	}
	
	public ArrayList<BoardVO> getAllBoardListExcept(int firstRow, int endRow) {
		return dao.getAllBoardListExcept(firstRow, endRow);
	}
	// ������ ���˻�
	public ArrayList<BoardVO> getAdminSearch(int firstRow, int endRow, String searchType, String searchText){
		return dao.getAdminSearch(firstRow, endRow,searchType, searchText);
	}
	
	public int selectAdminSearchCount(String searchType, String searchText) {
		return dao.selectAdminSearchCount(searchType, searchText);
	}
	
	public int selectAllBoardListExceptCount() {
		return dao.selectAllBoardListExceptCount();
	}
	public int selectUserCount(String bStdId) {
		return dao.selectUserCount(bStdId);
	}
	public ArrayList<BoardVO> getMyPageSearch(int firstRow, int endRow, String bStdID, String searchType, String searchText) {
		return dao.getMyPageSearch(firstRow, endRow, bStdID, searchType, searchText);
	}
	public int selectMyPageSearchCount(String bStdID, String searchType, String searchText) {
		return dao.selectMyPageSearchCount(bStdID, searchType, searchText);
	}
	//미리보기 기사 내부
	public BoardVO getBoardDetailPreviewContent(int bId, Timestamp bReg_date, String bStdID) {
		return dao.getBoardDetailPreviewContent(bId, bReg_date, bStdID);
	}
	//광고 수정 버튼 클릭 시 1->0으로 상태변경
	public int setStateChangeAdvertise(int bId, String bReg_date) {
		return dao.setStateChangeAdvertise(bId, bReg_date);
	}
	//광고 검색
	public ArrayList<BoardVO> getAdvertiseSearch(int firstRow, int endRow, String bCategory, String searchText) {
		return dao.getAdvertiseSearch(firstRow, endRow, bCategory, searchText);
	}
	//광고 검색 글 수 
	public int selectAdvertiseSearchCount(String bCategory, String searchText) {
		return dao.selectAdvertiseSearchCount(bCategory, searchText);
	}
	//최신 10개
   public ArrayList<BoardVO> getRecentBoardList() {
      return dao.getRecentBoardList();
   }
   //메인화면 대표기사로 설정
   public int setMainArticle(int bId, String bReg_date) {
	   return dao.setMainArticle(bId, bReg_date);
   }
   //대표기사 수 세기
	public int selectMainArticleCount() {
		return dao.selectMainArticleCount();
	}
	//기존에 대표기사 없던 경우
	public int setMainArticleNone(int bId, String bReg_date) {
		return dao.setMainArticleNone(bId, bReg_date);
	}
	//대표기사가 둘 이상인 경우
	public int setMainArticleMult() {
		return dao.setMainArticleMult();
	}
	public int selectAllBoardAllListCount() {
		return dao.selectAllBoardAllListCount();
	}
	public ArrayList<BoardVO> getAllBoardAllListExcept(int firstRow, int endRow) {
		return dao.getAllBoardAllListExcept(firstRow, endRow);
	}
	public int selectAccessSearchCount(String searchType, String searchText) {
		return dao.selectAccessSearchCount(searchType, searchText);
	}
	public ArrayList<BoardVO> getAccessSearch(int firstRow, int endRow, String searchType, String searchText) {
		return dao.getAccessSearch(firstRow, endRow, searchType, searchText);
	}
	public ArrayList<BoardVO> getAllAccessBoardListExcept(int firstRow, int endRow) {
		return dao.getAllAccessBoardListExcept(firstRow, endRow);
	}

	public BoardVO getInfoContent(String bCategory) {
		return dao.getInfoContent(bCategory);
	}
	
	public int selectAnnounceArticleCount() {
		return dao.selectAnnounceArticleCount();
	}
	public int setAnnounceArticleNone(int bId, String bReg_date) {
	   return dao.setAnnounceArticleNone(bId, bReg_date);
   }
	public int setAnnounceArticle(int bId, String bReg_date) {
		return dao.setAnnounceArticle(bId, bReg_date);
	}
	public int setAnnounceArticleMult() {
		return dao.setAnnounceArticleMult();
	}
	public ArrayList<BoardVO> getPrevNextBoardList(int bId, Timestamp bReg_date) {
		return dao.getPrevNextBoardList(bId, bReg_date);
	}

}
