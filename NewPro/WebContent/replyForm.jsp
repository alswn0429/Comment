<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BBS Reply</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style>
	body {
	padding-top: 70px;
	padding-bottom: 30px;
	}
	
	 #contentForm {
      width: 40%;
      margin: 0 auto;
      padding-top: 10px;
    }
 
    .table > thead > tr > th, .table > tbody > tr > th {
      background-color: #e6ecff;
      text-align: center;
    }
</style>
</head>
<body>
	<form action="/new/reply.bbs" method="post">
		<div class="content" align="center">
  			<h2>Reply</h2>
  		</div>
		<div id="contentForm">
			<input type="hidden" name="pageNum" value="${pageNum }">
			<input type="hidden" name="groupId" value="${groupId }">
			<input type="hidden" name="depth" value="${depth }">
			<input type="hidden" name="pos" value="${pos }">
			
			<div class="input-group input-group-sm" role="group" aria-label="...">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th width="30%">글쓴이</th>
							<td width="70%">${id }</td>
						</tr>
						<tr>
							<th style="padding-bottom: 15px;">제목</th>
							<td><input type="text" name="title" value="[Re]"
									class="form-control" aria-describedby="basic-addon1"></td>		
						</tr>
						</thead>
						<tbody>
						<tr>
							<td colspan="2">
								<textarea class="form-control" rows="10" name="content" placeholder="글을 적어주세요."></textarea>
							</td>
						</tr>
						<tr>
							<th style="padding-top: 15px;">첨부파일</th>
							<td><input type="file" class="btn btn-default" name="fileName"></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="btn-group btn-group-sm" role="group" aria-label="...">
				<input type="submit" class="btn btn-default" value="글쓰기">
				<input type="reset" class="btn btn-default" value="초기화">
				<input type="button" class="btn btn-default" value="취소" onclick="window.history.back()">
			</div> 
		</div>
	</form>
</body>
</html>