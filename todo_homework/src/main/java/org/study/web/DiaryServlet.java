package org.study.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; 

@WebServlet("/DiaryServlet")
public class DiaryServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException{
		
		HttpSession session = request.getSession();
			
//		String id = (String)session.getAttribute("id");
//		String name=(String)session.getAttribute("name");
		String id = request.getParameter("id");
		String name=request.getParameter("name");
		
		System.out.println(id +"-" + name);
		//String year = request.getParameter("year");
		//String month=request.getParameter("month");
		
		
		request.getRequestDispatcher("/WEB-INF/views/Diary_view.jsp").forward(request, response);
		
	}
	
	
}