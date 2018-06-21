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

import org.study.Dao.DiaryDAO;
import org.study.Dao.MemberBean;

/*
 * 로그인 요청==>DiaryDAO(id,pwd)
 * 아이디 비번 틀리면 로그인 인증==>로그인창으로
 * 인증==>Diary로 이동/id,name을 서버측에 저장/HttpSession(서버측에 개인정보저장)
 * 
 * */

@WebServlet("/JoinMemberOKServlet")
public class JoinMemberOKServlet extends HttpServlet {
	
	private static final long serialVersionUID=1L;
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      // TODO Auto-generated method stub
	      request.getRequestDispatcher("/WEB-INF/views/JoinMember.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
		ServletException, IOException{
		//doGet안에 코딩되 내용이 JSP
		//아이디와 비밀번호 받아옴
		MemberBean mbean = new MemberBean();
		
		 response.setContentType("text/html;charset=utf-8"); 
		 response.setCharacterEncoding("utf-8");
		 
		String username = request.getParameter("username");
		mbean.setUsername(username);
		System.out.println("userName:" + username);
		String userid = request.getParameter("userid");	
		System.out.println("userid:" + userid);
		mbean.setUserid(userid);
	
		String email = request.getParameter("email");
		mbean.setEmail(email);
		String pass = request.getParameter("pass");
		System.out.println("pass:" + pass);
		mbean.setPassword(pass);
		String pass2 = request.getParameter("pass2");
		System.out.println("pass2:" + pass2);
		mbean.setPassword_a(pass2);
		
		PrintWriter out = response.getWriter();
		out.println(pass);
		out.println(pass2);
		
		
		if(username.length()<1) {
			
			out.println("<script>");
			out.println("alert(\"이름을 입력하세요.\");");
			out.println("history.back();");
			out.println("</script>");
			return;
		}
		
		if(userid.length()<1) {
			
			out.println("<script>");
			out.println("alert(\"아이디를 입력하세요.\");");
			out.println("history.back();");
			out.println("</script>");
			return;
			
		}
		
		if(email.length()<1) {
			out.println("<script>");
			out.println("alert(\"이메일을 입력하세요.\");");
			out.println("history.back();");
			out.println("</script>");
			return;
		}
		
		if(pass.length()<1 && pass2.length()<1) {
			out.println("<script>");
			out.println("alert(\"비밀번호를 입력(확인)하세요.\");");
			out.println("history.back();");
			out.println("</script>");
			return;
		}
		
		if(!(pass.equals(pass2))) {
			
			out.println("<script>");
			out.println("alert(\"비밀번호가 일치하지 않습니다.\");");
			out.println("history.back();");
			out.println("</script>");
			return;
			
		}else {
			
			DiaryDAO dao = new DiaryDAO();
			System.out.println(mbean);
			String result = dao.joinMember(mbean);
			out.println(result);
			
			if(result.equals("ALREADYID")) {
				
				out.println("<script>");
				out.println("alert(\"중복된 아이디가 있습니다.\");");
				out.println("history.back();");
				out.println("</script>");
				
			}else {
				
				out.println("<script>");
				out.println("alert(\"회원가입이 완료되었습니다. 로그인페이지로 이동합니다.\");");
				out.println("</script>");
				/*response.sendRedirect("login.jsp");*/
				
				request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
			
				out.print(userid+"-"+username);
			}
		}
		
		
	}//doPost
	public void destory() {}
}
