package kr.seoil.service;

import java.util.ArrayList;

import kr.seoil.dao.UserDAO;
import kr.seoil.vo.UserVO;

public class UserService { 
	private static UserService service = new UserService();
	public UserDAO dao =  UserDAO.getInstance();
	
	private UserService() {}
	
	public static UserService getInstance() {
		return service;
	}
	
	public ArrayList<UserVO> getAllUser(int firstRow, int endRow) {
		return dao.getAllUser(firstRow, endRow);
	}
	public int selectAllUserCount() {
		return dao.selectAllUserCount();
	}
	public int userDelete(String stdId) {
		return dao.userDelete(stdId);
	} 
	
	public int adminEntrust(String stdId) {
		return dao.adminEntrust(stdId);
	}
	
	public int userCheck(String stdId, String pw) {
		return dao.userCheck(stdId, pw);
	}
	
	public UserVO getUserInfo(String stdId) {
		return dao.getUserInfo(stdId);
	}
	
}

