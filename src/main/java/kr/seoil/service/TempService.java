package kr.seoil.service;

import java.util.ArrayList;

import kr.seoil.dao.TempDAO;
import kr.seoil.vo.TempVO;

public class TempService {
	private static TempService service = new TempService();
	public TempDAO dao = TempDAO.getInstance();
	
	private TempService() { }
	public static TempService getInstance() {
		return service;
	}

	public int tempInsert(TempVO temp) {
		return dao.insertTemp(temp);
	}
	
	public ArrayList<TempVO> getAllTemp(int firstRow, int endRow) {
		return dao.getAllTemp(firstRow, endRow);
	}
	
	public int selectAllTempCount() {
		return dao.selectAllTempCount();
	}
	public boolean confirmId(String stdId) {
		return dao.confirmId(stdId);
	}
	public int tempDelete(String stdId) {
		return dao.tempDelete(stdId);
	}
	public int tempAccess(String stdId) {
		return dao.tempAccess(stdId);
		
	}
}
