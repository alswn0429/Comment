package com.edu.bbs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginImpl implements BBSService, LoginStatus{

	@Override
	public String bbsService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		int result = 0;
		
		try {
			result = BBSOracleDAO.getInstance().loginCheck(id, pw);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(result == LOGIN_SUCCESS) {
			resp.sendRedirect("/new/list.bbs?pageNum=1");
			
			//session�� id�� �ɴ´�.
			req.getSession().setAttribute("id", id);
			return null;
		} else {
			return "login.jsp";
		}
	}

}
