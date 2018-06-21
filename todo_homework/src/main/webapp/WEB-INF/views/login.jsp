<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/latest/js/bootstrap.min.js"></script>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/latest/css/bootstrap.min.css" rel="stylesheet">
<script language="javaScript">
	function login(){
		document.frm.submit();
	}
	function cancel(){
		document.frm.reset();
	}
	function backToList(){
		location.href="/MVC/list.jsp?pageNumber=${pageNumber}";
	}
</script>
<style>
    body {
        background: #f8f8f8;
        padding: 60px 0;
       
    }
    
    #login-form > div {
        margin: 15px 0;
    }

</style>
</head>
<body>
${error}
<div class="container">
    <div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
        <div class="panel panel-success">
            <div class="panel-heading">
                <div class="panel-title">로그인</div>
            </div>
            <div class="panel-body">
                <form action="/LoginOk" id="login-form" method="post" name="frm">
                    <div>
                       <input type="text" class="form-control" name="id" placeholder="ID" autofocus>
                    </div>
                    <div>
                      <input type="password" class="form-control" name="Password" placeholder="Password">
                    </div>
                  
                    <div>
                        <button type="submit" class="form-control btn btn-primary btn-xs">로그인</button>
                    </div>
                    <div>
                        <button type="button" class="form-control btn btn-primary btn-xs" onclick="location='JoinMemberOKServlet'">회원가입</button>                       									
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>