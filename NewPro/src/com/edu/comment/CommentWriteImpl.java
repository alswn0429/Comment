package com.edu.comment;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.edu.bbs.BBSOracleDAO;
import com.edu.bbs.BBSService;

public class CommentWriteImpl implements BBSService {

	@Override
	public String bbsService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		String id = req.getSession().getAttribute("id").toString();
		String commentContent = req. getParameter("commentContent");
		String articleNumber = req.getParameter("articleNumber");
		HashMap<String, Object> result = null;
			
		try {
			result = BBSOracleDAO.getInstance().insertComment(id, commentContent, articleNumber);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		JSONObject jsonObj = new JSONObject(result);
		PrintWriter pw = resp.getWriter();
		pw.println(jsonObj);
		
		return null;
	}

}
