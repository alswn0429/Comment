package com.edu.bbs;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class ListImpl implements BBSService {
 
  @Override
  public String bbsService(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
	String pageNum = req.getParameter("pageNum");
	
	//web.xml�� ������ context-param���� ���� �о�´�.
	int pageSize = Integer.parseInt(req.getServletContext().getInitParameter("pageSize"));
    int pageBlock = Integer.parseInt(req.getServletContext().getInitParameter("pageBlock"));
	
	int totalCount = 0;
    ArrayList<BbsDTO> articles = null;
    Page page = Page.getInstance();
    
    try {
      totalCount = BBSOracleDAO.getInstance().getArticleTotalCount();
      //articles = BBSOracleDAO.getInstance().selectArticles(1, 10);
      page.paging(Integer.parseInt(pageNum), totalCount, pageSize, pageBlock);
      articles = BBSOracleDAO.getInstance().selectArticles(page.getStartRow(), page.getEndRow());
    } catch (Exception e) {
      e.printStackTrace();
    }
 
    req.setAttribute("totalCount", totalCount);
    req.setAttribute("articles", articles);
    
    req.setAttribute("pageNum", pageNum);
    req.setAttribute("pageCode", page.getSb().toString());
    
    return "/list.jsp";
  }
 
}


