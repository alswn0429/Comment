package com.edu.bbs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateImpl implements BBSService {

	@Override
	public String bbsService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		
		BbsDTO article = new BbsDTO();
		int result = 0;
		
		article.setArticleNumber(Integer.parseInt(req.getParameter("articleNumber")));
		article.setTitle(req.getParameter("title"));
		article.setContent(req.getParameter("content"));
		article.setFileName(req.getParameter("fileName"));
		
		try {
			result = BBSOracleDAO.getInstance().updateArticle(article);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		resp.sendRedirect("/new/list.bbs?pageNum="+req.getParameter("pageNum"));
		
		return null;
	}

}
