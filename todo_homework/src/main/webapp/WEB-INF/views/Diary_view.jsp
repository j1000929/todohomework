<%@page import="java.sql.*" %>
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<%@page import="java.io.*" %>
<%@page import="org.study.Dao.*" %>
<%@page import="javax.servlet.http.HttpServlet" %>
<%@page import="javax.servlet.http.HttpServletRequest" %>
<%@page import="javax.servlet.http.HttpServletResponse" %>
<%@page import="javax.servlet.http.HttpSession" %>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page session="false" language="java" import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#diary{
		/*background-image:url(img/back.jpg);*/
	}
	td{
		font-family:휴면매직체;
	}
	
</style>
<script type="text/javascript">
	function change(){
		
		document.frm.submit();
	}
</script>
</head>
<body>
	<%
		HttpSession session = request.getSession(true);
		String id = (String)session.getAttribute("id");
		String name=(String)session.getAttribute("name");
		
		
		SimpleDateFormat simpleDate=new SimpleDateFormat("yyyy-MM-dd");
		String today = simpleDate.format(new java.util.Date());
		
		Calendar cal = Calendar.getInstance();
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		//int day = cal.get(Calendar.DATE);
		
	
		 String[] strWeek={
			"일","월","화","수","목","금","토"
		};
		 
		 int[] lastDay= {
			31,28,31,30,31,30,
			31,31,30,31,30,31
		};
		
		 
		 String strYear = request.getParameter("year");
		 String strMonth=request.getParameter("month");
			
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M");
		StringTokenizer st = new StringTokenizer(sdf.format(new java.util.Date()),"-");
		
		//strYear//와 strMonth가 없을때(null) 현재 달로 넣아줌.
		if(strYear == null)
		{
			strYear=st.nextToken();
		}
		if(strMonth == null)
		{
			strMonth=st.nextToken();
		}
		
		year = Integer.parseInt(strYear);
		month = Integer.parseInt(strMonth);
	%>
<center>	
	<h1><%=id%>님의 <%=year %>년 <%=month %>월 일정</h1>
	
	<table border="1" width="560">
		<tr>
		 <td align="left">
			<form name="frm" action="/DiaryServlet">

			
			<table border="0" width="560">
				<tr>
					<td>
						<select name="year" onchange="change();"> 
						<%
							for(int k=2010; k<=2030; k++){
								if(k==year){
						%>
							<option selected><%=k %></option>
						<%
							}else{
						%>
							<option><%=k %></option>
						<%
								}
							}
						 %>	
						</select>년도&nbsp;&nbsp;&nbsp;
						<select name="month" onchange="change()">
						<%
							for(int i=1;i<=12;i++){
								
								if(i==month){
						%>	
							<option selected><%=i %></option>
						<%
								}
								else{
								
						 %>
						 	<option><%=i %></option>
						 <%
								}
							}
						 %>
						</select>월
									
			   		</td>
				</tr>
			</table>
		 </form>
		 </td>
		</tr>
		
					<!--요일출력 start  --> 
						<tr bgcolor="#ccccff">
							<td>
								<table border="1" width="560" bordercolor="#ccccff">
									<tr bgcolor="#ccccff" >
										<%
											String color="black";
										
											for(int i=0; i<7; i++){
												
												if(i==0)
													color="red";
												else if(i==6)
													color="blue";
												else
													color="black";				
												
											
										%>
										<td align="center" width="80" height="40">
											<font color="<%=color %>"><b><%=strWeek[i] %></b></font>
										</td>
										<%
											}
										%>
										
									</table>
								</td>
						 </tr>	
					<!--  요일출력 end -->
								
					<!--  달력출력 start-->   
				     <tr> 
					     <td>  
								<%
									int total=(year-1)*365 + (year-1)/4 - (year-1)/100 + (year-1)/400; 
							        // --------- year=2018 전년도 2017.12.31일까지 총합을 구함 
							         
							        if((year%4==0 && year%100!=0) || (year%400==0)) 
							        { 
							            lastDay[1]=29; 
							        } 
							        else 
							        { 
							            lastDay[1]=28; 
							        } 
							         
							        for(int i=0; i<month-1; i++) 
							        { 
							            total+=lastDay[i]; 
							        } 
							        //  ------- 전달까지 2018. 5. 31일까지 총합을 구함 
							        total++; 
							        // ------- 2018. 6. 1일까지 총합 
							         
							        int week = total % 7; // 9.1일자의 요일을 구할수 있음 
							     %> 
					        <table id="diary" border="1" width="560" bordercolor="#ccccff"> 
								     <%    
								        for(int i=1; i<=lastDay[month-1]; i++) 
								        { 
								            if(week==0) 
								                color="red"; 
								            else if(week==6) 
								                color="blue"; 
								            else 
								                color="black"; 
								            if(i==1) 
								            { 
								         %>
					            <tr> 
							         <%
							                for(int j=0; j<week; j++) 
							                { 
							            %>
					              
					               <td width="80" height="60" align="left" valign="top">
					                   &nbsp;
					               </td>
							                   
							              <% 
							                } 
							            } 
							         %>  
							      <td width="80" height="60" align="left" valign="top">
							         	<font color="<%=color %>"><b>
							         	
							         	<!-- 일출력 -->
							         	<!-- get방식으로 year, month, day, id 전송하고 &은 변수구분--> 
			
							         	<a href="/InsertServlet?year=<%=year %>&month=<%=month %>&day=<%=i %>&id=<%=id%>" title="일정추가하기" style="text-decoration:none;"><%=i %></a></b></font>
							         	<!-- 그림출력위치 -->
								         	<%
								         		DiaryDAO dao = new DiaryDAO();
								         		boolean bCheck = dao.isDate(id, year, month, i);
								         		System.out.println(bCheck);
								         		int count = dao.isCount(id, year, month, i);
								         		String str="일정:"+count+"개";
								         		
								         		if(bCheck==true)
								         		{
								         			
								         			/*
								         				다른 페잊로 이동(페이지의 흐름)
								         				
								         				HTML
								         				<form action=..>: GET방식과 POST방식 둘다 지원
								         				
								         				GET방식만 지원
								         				<a href="">
								         				JavaScript
								         				location.href="파일"
								         				
								         				Java
								         				response.sendRedirect("사이트");
								         				RequestDispatcher
								         				=>forward("사이트")
								         				
								      
								         			
								         			*/
								         		
								         	%>
								         	
									         	<a href="ListServlet?id=<%=id %>&year=<%=year %>&month=<%=month %>&day=<%=i %>" style="font-size:12px;font-weight:bold;text-decoration:none;" title="일정보기"><br>
									         		<img src="/img/pin.jpg" border="0" alt="<%=str %>"><%=str %>
									         	</a>
									        
								         	<%
								         		}
					         				%>
					        	</td>
						         <%
						         	week++;
						         
						         	if(week>6){
						         		week=0;
						         %>
						         </tr>
						         <tr>
						   <%
					         	}
					        }
					        %>
					         </tr>	
							</tr>
					</table>
				</td>
		</tr> 
		<!-- 달력출력 end -->
	</td>
  </tr>	
		
</table>

</center>	




</body>
</html>