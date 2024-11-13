package kr.seoil.controller;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.*;
import javax.servlet.http.*;

import kr.seoil.util.HttpUtil;
import kr.seoil.service.TempService;
import kr.seoil.vo.TempVO;

public class UserJoinController implements Controller{
	public void execute(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		String stdId = request.getParameter("tempStdID");
		String passwd = request.getParameter("tempPW");
		String passwd_chk = request.getParameter("tempPWCheck");
		String name = request.getParameter("tempName");
		String email = request.getParameter("tempEmail");
		String dept = request.getParameter("tempDept");
		
		if((stdId == "" || stdId == null) && (passwd == "" || passwd == null) &&
				(name == "" || name == null) &&
				(email == "" || email == null)  &&
				(dept == "" || dept == null)) {
			
			HttpUtil.forward(request, response, "/admin/join.jsp");
		}else if(stdId.isEmpty() || passwd.isEmpty()|| name.isEmpty()|| 
				email.isEmpty()|| dept.isEmpty()){
			request.setAttribute("error", "회원가입에 실패하였습니다.");
			HttpUtil.forward(request, response, "/admin/join_ok.jsp");
			
		}else{
			Pattern pattern_id = null;
			pattern_id = Pattern.compile("[0-9]{9}$"); 
			Matcher matcher_id = pattern_id.matcher(stdId);
			
			Pattern pattern_passwd = null;
			pattern_passwd = Pattern.compile("(?!((?:[A-Za-z]+)|(?:[~!@#$%^&*()_+=]+)|(?=[0-9]+))$)[A-Za-z\\d~!@#$%^&*()_+=]{8,}$"); 
			Matcher matcher_passwd = pattern_passwd.matcher(passwd);
			
			Pattern pattern_name = null;
			pattern_name = Pattern.compile("[가-힣]{2,}$"); 
			Matcher matcher_name = pattern_name.matcher(name);
			
			Pattern pattern_email = null;
			pattern_email = Pattern.compile("[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+"); 
			Matcher matcher_email = pattern_email.matcher(email);
			
			matcher_id.reset();
			matcher_passwd.reset();
			matcher_name.reset();
			matcher_email.reset();
			if(matcher_id.find() && matcher_passwd.find() && matcher_name.find() && matcher_email.find() && passwd.equals(passwd_chk)) {
				TempVO temp = new TempVO();
				temp.setTempStdID(stdId);
				temp.setTempPW(passwd);
				temp.setTempName(name);
				temp.setTempEmail(email);
				temp.setTempDept(dept);

				TempService service = TempService.getInstance();
				int result = service.tempInsert(temp);
				
				request.setAttribute("result", result);
				request.setAttribute("tempStdID",stdId);
				
				HttpUtil.forward(request, response,"/admin/join_ok.jsp");
			} else{
				int result = -1;
				request.setAttribute("result", result);

				HttpUtil.forward(request, response, "/admin/join_ok.jsp");
			}
		}
	}
}
