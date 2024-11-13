package kr.seoil.service;

import java.util.ArrayList;

import kr.seoil.dao.CategoryDAO;
import kr.seoil.vo.CategoryVO;

public class CategoryService {
	private static CategoryService service = new CategoryService();
	public CategoryDAO dao =  CategoryDAO.getInstance();
	
	private CategoryService() { }
	public static CategoryService getInstance() {
		return service;
	}
	
	public ArrayList<CategoryVO> getHeadCategory() {
		return dao.getHeadCategory();
	}
	
	public ArrayList<CategoryVO> getSubCategory(String category) {
		return dao.getSubCategory(category);
	}
}
