package org.study.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.study.Dao.*;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String no = request.getParameter("no");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String id = request.getParameter("id");
		
		//DB연동
		DiaryDAO dao = new DiaryDAO();
		dao.delete(Integer.parseInt(no));
		
		//이동
		
		response.sendRedirect("DiaryServlet?year="+year+"&month="+month+"&id="+id);
	}

	

}