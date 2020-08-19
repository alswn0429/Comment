package com.edu.bbs;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BBSServlet extends HttpServlet {
	 
	  private static final long serialVersionUID = 1L;
	  Properties properties;
	  HashMap<String, BBSService> bbsMap;   // Upcasting : �θ� �ڸ��� �ڽ� �� �� �ִ�.
	  BBSService bbsService;
	   
	  @Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    System.out.println("GET���� ��û");
	    handling(req, resp);
	  }
	 
	  @Override
	  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    System.out.println("POST�� ��û");
	    handling(req, resp);
	  }
	 
	  public void handling(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  System.out.println("11111111111111111");
	    String servletPath = req.getServletPath();    // ������θ� �̾Ƴ�, ������ ���� �� �Ķ���ʹ� �������� �ʴ´�.
	    String view = bbsMap.get(servletPath).bbsService(req, resp);
		  System.out.println("22222222222222222");
	    if(view != null) {    // view�� null�� ���� ���� �ٿ�ε忡 ������ �̵� ���� �Ͱ� ���� ���
			  System.out.println("333333333333");
	      RequestDispatcher reqDispatcher = req.getRequestDispatcher(view);
		  System.out.println("44444444444444");
	      reqDispatcher.forward(req, resp);   // �θ��ڸ��� �ڽ� �� �� �ִ�(ServletRequest > HttpServletRequest)
		  System.out.println("555555555555");
	    }
	  }
	 
	  @Override
	  public void init(ServletConfig config) throws ServletException {
	    System.out.println("Init...");
	    properties = new Properties();
	    bbsMap = new HashMap<>();
	    String configPath = config.getInitParameter("bbsProperties"); // web.xml
	    try {
	      properties.load(new FileReader(configPath));
	      Iterator<Object> iterator = properties.keySet().iterator();
	      while(iterator.hasNext()) {
	        String key = (String)iterator.next();
	        String value = properties.getProperty(key);
	         
//	        Class instanceValue = Class.forName(value);
	        Class<?> instanceValue = Class.forName(value);
	        bbsService = (BBSService)instanceValue.newInstance();
	        bbsMap.put(key, bbsService);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	   
	}

