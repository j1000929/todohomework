package org.study.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.study.Dao.*;


/*
 * 로그인 요청==>DiaryDAO(id,pwd)
 * 아이디 비번 틀리면 로그인 인증==>로그인창으로
 * 인증==>Diary로 이동/id,name을 서버측에 저장/HttpSession(서버측에 개인정보저장)
 * 
 * */

@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {
	
	public void init() {}
	private static final long serialVersionUID=1L;
	
	@Override
	   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      // TODO Auto-generated method stub
	      request.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(request, response);
	   }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
		ServletException, IOException{
	
		response.setCharacterEncoding("utf-8");//한글변화
		response.setContentType("text/html;charset=utf-8");
	
		String no = request.getParameter("no");
		String year= request.getParameter("year");
		String month = request.getParameter("month");			
		String day = request.getParameter("day");
		String target_date =year+"-"+month+"-"+day;
		String subject = request.getParameter("subject");		
		String content = request.getParameter("content");
	
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		
		if(subject.length()<1) {
			
			out.println("<script>");
			out.println("alert(\"제목을 입력하세요.\");");
			out.println("history.back();");
			out.println("</script>");
			return;
		}
		
		if(content.length()<1) {
			
			out.println("<script>");
			out.println("alert(\"일정 입력하세요.\");");
			out.println("history.back();");
			out.println("</script>");
			return;
			
		}
		
		DiaryVo vo = new DiaryVo();
		vo.setNo(Integer.parseInt(no));
		vo.setId(id);
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setYear(Integer.parseInt(year));
		vo.setMonth(Integer.parseInt(month));
		vo.setDay(Integer.parseInt(day));
		
		//DB연동
		DiaryDAO dao = new DiaryDAO();
		dao.edit(vo);
		
		
		//response.sendRedirect("/DiaryServlet?year="+year+"&month="+month+"&day="+day+"&id="+id);
		request.getRequestDispatcher("/WEB-INF/views/Diary_view.jsp").forward(request, response);

		
		
	}//doPost
	public void destory() {}
}