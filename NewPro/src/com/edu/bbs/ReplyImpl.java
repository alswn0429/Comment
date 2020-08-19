package com.edu.bbs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReplyImpl implements BBSService {

	@Override
	public String bbsService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		
		BbsDTO article = new BbsDTO();
		
		article.setId(req.getSession().getAttribute("id").toString());
		article.setTitle(req.getParameter("title"));
		article.setContent(req.getParameter("content"));
		article.setGroupId(Integer.parseInt(req.getParameter("groupId")));
		article.setDepth(Integer.parseInt(req.getParameter("depth")));
		article.setPos(Integer.parseInt(req.getParameter("pos")));
		article.setFileName(req.getParameter("fileName"));
		
		try {
			BBSOracleDAO.getInstance().replyArticle(article);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		//이중 submit을 막기 위해 redirect
		//여기서 pageNum값 못받아오는거야 왜?
		resp.sendRedirect("/new/list.bbs?pageNum="+req.getParameter("pageNum"));
		//resp.sendRedirect("/new/list.bbs?pageNum=1");
		return null;
	}

}
