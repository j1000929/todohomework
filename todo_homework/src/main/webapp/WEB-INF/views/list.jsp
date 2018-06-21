<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.study.Dao.*" %>
<%@page session="false" import="java.util.*" %>
<%@page import="java.io.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>todo_list</title>
<script type="text/javascript">
	var i =0;
	function contentOpen(id){
		var p = document.getElementById(id);
		
		if(i==0){
			p.style.display='';
			i=1;
		}else{
			p.style.display='none';
			i=0;
		}
	}
</script>
<style type="text/css">
	
	td{
		font-family: 휴면매직체;
    font-size: 15px;
    font-weight: bold;
	}
	
</style>
</head>
<body>
<%
	response.setContentType("text/html;charset=utf-8"); //한글처리
	request.setCharacterEncoding("utf-8");
	
	//이전 파일에서 보내준 데이터
	String id = request.getParameter("id");
	String year = request.getParameter("year");
	String month = request.getParameter("month");
	String day = request.getParameter("day");
	
	//DB연동 ==> DAO를 불러온다.
	DiaryDAO dao = new DiaryDAO();
	ArrayList<DiaryVo> list = dao.getDiaryData(id, Integer.parseInt(year),
													Integer.parseInt(month),
													Integer.parseInt(day));
	
	HttpSession session = request.getSession(true);
	String name=(String)session.getAttribute("name");
%>
<center>
<h3><%=id %>님의 <%=year%>년 <%=month %>월<%=day %>일의 일정</h3>

	
	
		<table border="0" width="450">
			<tr bgcolor="#ccccff" >
				<th width="20%" align="center">번호</th>
				<th width="50%">일정</th>
				<th width="30%">비고</th>
			</tr>
			
			<!-- 데이터 들어가는 부분
				
				/* 
             *  list  
             *    폼           처리 
             *  insert => insert_ok(파일) ==> list 
             *  content 
             *  update => update_ok 
             *  delete => delete_ok 
             *  reply => reply_ok 
             *   
             *  폼에서는 폼만잡고 코딩처리는 절대 하면 안된다 
             *   
             */ 
			
			 -->
			<%
				for(DiaryVo vo:list){
			 %>
			<tr>
				<td width="20%" align="center"><%=vo.getNo() %></td>
				<td width="50%">
					<a href="javascript:contentOpen('m+<%=vo.getNo() %>')"><%=vo.getSubject() %></a>
				</td>
			
				<td width="30%" align="center">
				<!-- get방식으로 수정할 일정의 번호와 year,month 전송 -->
				<a href="EditServlet?no=<%=vo.getNo()%>&year=<%=year%>&month=<%=month%>&day=<%=day%>&id=<%=id%>">수정</a>
				<!-- get방식으로 삭제할 일정의 번호와 year,month 전송 -->
				<a href="DeleteServlet?no=<%=vo.getNo()%>&year=<%=year%>&month=<%=month%>&day=<%=day%>&id=<%=id%>">삭제</a>
				</td>			
			</tr>
			<!-- tr을 안보이게 설정하고 클릭하면 보이게 함 -->
			<tr style="display:none" id="m+<%=vo.getNo()%>">
				<td colspan="3" align="left" valign="top">
					<hr>
						<pre><%=vo.getContent() %><br></pre>
						일정:<%=year %>-<%=month %>-<%=day %>
					<hr>
				</td>
			</tr>
			<% 
				}//end for
			%>
			
		</table>
		<hr width="450">
		<table border="0" width="450">
			<tr>
				<td><a href="/DiaryServlet?year=<%=year %>&month=<%=month %>&day=<%=day %>&id=<%=id %>">목록</a></td>
			</tr>
		</table>

</center>
<%
	for(int i=0; i<list.size();i++)
	{
		DiaryVo vo = new DiaryVo();
	}
%>
</body>
</html>