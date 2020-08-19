package com.edu.bbs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutImpl implements BBSService {

	@Override
	public String bbsService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//技记狼 葛电 加己阑 昏力茄促.
		req.getSession().invalidate();
		
		resp.sendRedirect("/new/login.bbs");
		
		return null;
	}

}
