<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BBS Write</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style>
	body {
	padding-top: 70px;
	padding-bottom: 30px;
	}
	
    #contentForm {
      width: 40%;
      margin: 0 auto;
      padding-top:10px;
    }
 
    .table > thead > tr > th, .table > tbody > tr > th {
      background-color: #e6ecff;
      text-align: center;
    }
</style>
</head>
<body>
  <!-- enctype="application/x-www-form-urlencoded" 속성이 default로 잡혀있음 -->
  <form action="/new/write.bbs" method="post" enctype="multipart/form-data">
  	<div class="content" align="center">
  			<h2>Write</h2>
  	</div>
  	
  	<div id="contentForm">	
      <input type="hidden" name="pageNum" value="${pageNum}">
      <input type="hidden" name="depth" value="${article.depth}">
      <input type="hidden" name="pos" value="${article.pos}">
      <input type="hidden" name="groupId" value="${article.groupId}">
       
      <div class="input-group input-group-sm" role="group" aria-label="...">
      <!-- HTML5에서는 태그 속성을 바로 명시하지 않고, CSS를 작성하여 붙여준다. -->
      	<table class="table table-striped table-bordered">
        	<thead>
	        	<tr>
	          		<td width="30%">글쓴이 : </td>
	          		<td width="70%">${sessionScope.id}</td>
	        	</tr>
	       	 	<tr>
	          		<td style="padding-top: 15px">제목</td>
	          		<td><input type="text" name="title" class="form-control"></td>
	        	</tr>
	        </thead>
	        <tbody>
	        	<tr>
	          		<td colspan="2">
	            	<textarea cols="50" rows="20" name="content" class="form-control"></textarea>
	          		</td>
	        	</tr>
	        	<tr>
	          		<td style="padding-top: 15px">첨부파일</td>
	          		<td><input type="file" name="file" class="btn btn-default"></td>
	          		
	        	</tr>
	        </tbody>
      	  </table>
	        	<div class="btn-group btn-broup-sm" role="group" aria-label="...">
					<input type="submit" class="btn btn-default" value="글쓰기">
					<input type="reset" class="btn btn-default" value="다시 쓰기" >
					<input type="button" class="btn btn-default" value="취소" onclick="document.location.href='/new/content.bbs?articleNumber=${articleNumber }&pageNum=${pageNum }'">
				</div>
       </div>
   	</div>
  </form>
</body>
</html>
