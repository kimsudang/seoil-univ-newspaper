package kr.seoil.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.seoil.service.CategoryService;
import kr.seoil.vo.CategoryVO;

public class SelectController implements Controller {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String category = request.getParameter("category");
		CategoryService service = CategoryService.getInstance();
		ArrayList<CategoryVO> categoryList = service.getSubCategory(category);

		Gson gson = new Gson();
		response.setCharacterEncoding("utf-8");
		String value = gson.toJson(categoryList);
		PrintWriter out = response.getWriter();
		out.print(value);
	}
}
