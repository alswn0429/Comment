package com.edu.bbs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateFormImpl implements BBSService {

	@Override
	public String bbsService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pageNum = req.getParameter("pageNum");
		String articleNumber = req.getParameter("articleNumber");
		
		BbsDTO article = null;
		
		try {
			article = BBSOracleDAO.getInstance().selectArticle(articleNumber);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		req.setAttribute("pageNum", pageNum);;
		req.setAttribute("article", article);
		
		return "/updateForm.jsp";
	}

}
