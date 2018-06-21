package org.study.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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

@WebServlet("/LoginOk")
public class LoginOkServlet extends HttpServlet {
	
	private static final long serialVersionUID=1L;
	
	
	
	@Override
	   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      // TODO Auto-generated method stub
	    
		System.out.println("AAAAAAAAAAAAAAAA");
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	   }
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
		ServletException, IOException{
		MemberBean mbean = new MemberBean();
		//doGet안에 코딩되 내용이 JSP
		response.setContentType("text/html;charset=euc-kr");
		request.setCharacterEncoding("EUC-KR");
		
		PrintWriter out = response.getWriter();
		//아이디와 비밀번호 받아옴
		String id = request.getParameter("id");
		System.out.println(id);
		mbean.setUserid(id);
		String pwd = request.getParameter("Password");
		mbean.setPassword(pwd);
		
		if(id.length()<1) {
			
			out.println("<script>");
			out.println("alert(\"ID를 입력하세요.\");");
			out.println("history.back();");
			out.println("</script>");
			return;
		}
		
		if(pwd.length()<1) {
			
			out.println("<script>");
			out.println("alert(\"비밀번호를 입력하세요.\");");
			out.println("history.back();");
			out.println("</script>");
			return;
		}
		
		
		//DB연동
		DiaryDAO dao = new DiaryDAO();
		String result = dao.isLogin(mbean);
		
		if(result.equals("NOID")) {
			
			/*메세지를 뿌리고 LoginServlet으로 다시 이동시킴
			request.setAttribute("error", "당신의 아이디/비밀번호가 맞지 않습니다");
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);*/
			
			out.println("<script>");
			out.println("alert(\"ID가 존재하지 않습니다.\");");
			out.println("history.back();");
			out.println("</script>");
			
		}else if(result.equals("NOPWD")) {//인증
			
			
			out.println("<script>");
			out.println("alert('비밀번호가 틀립니다.');");
			out.println("history.back();");
			out.println("</script>");
			//out.println("location.href='해당페이지주소';");
		}else {
			
			//DiaryServlet으로 이동
			//핵심부분 sendRedirect서버에 다른 페이지로 이동시킨다.
			//session세션
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			session.setAttribute("name", result);
			
			//response.sendRedirect("/DiaryServlet?id="+id+"&name="+result);
			request.getRequestDispatcher("/WEB-INF/views/Diary_view.jsp").forward(request, response);
				
		}
		
		
	}
}