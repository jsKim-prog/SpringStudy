<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>exUpload: 파일업로드 처리</title>
</head>
<body>
	<h4>파일업로드 테스트</h4>
	<form action="/sample/exUplodPost" method="post" enctype="multipart/form-data">
		<div>
			<input type="file" name="files">
		</div>
		<div>
			<input type="file" name="files">
		</div>
		<div>
			<input type="file" name="files">
		</div>
		<div>
			<input type="file" name="files">
		</div>
		<div>
			<input type="file" name="files">
		</div>
		<div>
			<input type="submit">
		</div>
	</form>
</body>
</html>