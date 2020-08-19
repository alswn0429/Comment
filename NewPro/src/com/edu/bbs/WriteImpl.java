package com.edu.bbs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class WriteImpl implements BBSService {
	 
	  @Override
	  public String bbsService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    req.setCharacterEncoding("utf-8");
	    BbsDTO article = new BbsDTO();
	    String saveDir = req.getServletContext().getInitParameter("saveDirectory");
	    
	    article.setId(req.getSession().getAttribute("id").toString());
	    article.setTitle(req.getParameter("title"));
	    article.setContent(req.getParameter("content"));
	     
	    if (req.getPart("file").getSize() != 0) {
			Part filePart = req.getPart("file");
			String originFileName = getFileName(filePart);
			article.setFileName(originFileName);
			
			File file = new File(saveDir + originFileName);
			InputStream is = filePart.getInputStream();
			FileOutputStream fos = null;
			
			fos = new FileOutputStream(file);
			
			int temp = -1;
			while((temp = is.read()) != -1) {
				fos.write(temp);
			}
			
			is.close();
			fos.close();
			}
	    
	    try {
	      if(BBSOracleDAO.getInstance().insertArticle(article) == 1) {
	        System.out.println("게시글이 삽입되었습니다.");
	      } else {
	        System.out.println("게시글 삽입이 실패하였습니다.");
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	 
//	    return "/list.bbs";
//	    return "/writeForm.bbs";
	     
	    // 이중 submit 방지
	    //resp.sendRedirect("/new/writeForm.bbs");
	    //resp.sendRedirect("/new/list.bbs");
	    resp.sendRedirect("/new/list.bbs?pageNum=1");
	    
	    return null;
	  }
	  public String getFileName(Part filePart) {
		  for(String filePartData : filePart.getHeader("Content-Dispostion").split(";")) {
			  System.out.println(filePartData);
			  if(filePartData.trim().startsWith("fileName")) {
				  return filePartData.substring(filePartData.indexOf("=")+ 1).trim().replace("\"", "");
			  }
		  }
		  
		  return null;
	  }

	}
