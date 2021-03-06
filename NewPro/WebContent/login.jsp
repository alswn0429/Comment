<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BBS Login</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style>
 	body {
	padding-top: 100px;
	padding-bottom: 30px;
	<%-- 가운데정렬 하고싶어요 --%>
	}
	
	#loginForm{
		width: 20%;
		margin: 0 auto;
	}
	
	#login {
		text-align: center;
	}
</style>
</head>
<body>
	<div id="loginForm">
		<form method="post" action="/new/login.bbs">
			<fieldset id="login">
				<legend>BBS</legend>
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Username" aria-describedby="basic-addon1"
						id="id" name="id" autofocus required minlength="3" maxlength="15">
					<input type="password" class="form-control" placeholder="Password" aria-describedby="basic-addon1"
						id="pw" name="pw" required minlength="3" maxlength="15">
				</div>
				
				<div class="btn-group" role="group" aria-label="...">
					<input type="submit" class="btn btn-default" value="Login">
					<input type="reset" class="btn btn-default" value="Cancel">
					<input type="button" class="btn btn-default" value="Sign up">
				</div>
			</fieldset>
		</form>
	</div>
</body>
</html>