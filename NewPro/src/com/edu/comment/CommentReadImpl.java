package com.edu.comment;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.edu.bbs.BBSOracleDAO;
import com.edu.bbs.BBSService;

public class CommentReadImpl implements BBSService {

	@Override
	public String bbsService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 resp.setCharacterEncoding("utf-8");        // JSON 한글 깨짐 해결
	        
	        int commPageNum = Integer.parseInt(req.getParameter("commPageNum"));
	        String articleNumber = req.getParameter("articleNumber");
	        ArrayList<CommentDTO> comments = null;
	        
	        try {
	            comments = BBSOracleDAO.getInstance().selectComments(articleNumber, commPageNum);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        JSONArray jsonArr = new JSONArray(comments);        // 스프링에선 애노테이션(?)
	        PrintWriter pw = resp.getWriter();
	        pw.println(jsonArr);
	        
	        return null;
	    }
	 
	}


