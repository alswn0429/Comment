package com.edu.bbs;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WriteFormImpl implements BBSService{
	
	@Override
	public String bbsService(HttpServletRequest req, HttpServletResponse res) throws ServletException {
	//�α��� ������ Ȯ��
	HttpSession session = req.getSession();
	session.setAttribute("id", "kim");	//���Ƿ� ���ǿ� �� �ο�
	
	/*
	 * if(session.getAttribute("id") == null){
	 * return "/login.jsp";
	 */
	
	req.setAttribute("pageNum", req.getParameter("pageNum"));
	req.setAttribute("dept", req.getParameter("depth"));
	req.setAttribute("pos", req.getParameter("pos"));
	req.setAttribute("groupId", req.getParameter("pageNum"));
	
	return "/writeForm.jsp";
	}
}
