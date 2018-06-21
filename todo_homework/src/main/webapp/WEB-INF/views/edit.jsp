<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.study.Dao.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	response.setContentType("text/html;charset=utf-8"); //한글처리
	request.setCharacterEncoding("utf-8");
	
	String no = request.getParameter("no");
	
	DiaryDAO dao = new DiaryDAO();
	DiaryVo vo= dao.getData(Integer.parseInt(no));
	
%>


<center>
<h1>일정수정</h1>

	<form method="post" action="EditServlet">
	
		<table border="1" bordercolor="#ccccff" width="450">
			<tr>
				<td width="20%" align="center">번호</td>
				<td width="80%"><input type="text" name="no" readonly value="<%=no %>" size="3"/>번</td>
			</tr>
			<tr>
				<td width="20%" align="center">일정</td>
				<td width="80%">
					<input type="text" name="year" readonly value="<%=vo.getYear() %>" size="5" readonly>년
					<input type="text" name="month" readonly value="<%=vo.getMonth() %>" size="3" readonly>월
					<input type="text" name="day" readonly value="<%=vo.getDay() %>" size="3" readonly>일 
				</td>
			</tr>
			<tr>
				<td width="20%" align="center">제목</td>
				<td width="80%"><input type="text" name="subject" size="50" value="<%=vo.getSubject()%>"/></td>
			</tr>
			<tr>
				<td width="20%" align="center">내용</td>
				<td width="80%">
					<textarea name="content" id="" cols="50" rows="12"><%=vo.getContent() %></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="수정"/>
					<input type="button" value="취소" onclick="javascript:history.back()"/>
				</td>
			</tr>
		</table>
	</form>
</center>
</body>
</html>