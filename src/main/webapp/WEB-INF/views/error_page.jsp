<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>error_page.jsp : Controller의 예외처리1</title>
</head>
<body>
<h4>Controller의 예외처리: Exception</h4>
	<h4>
		<c:out value="${exception.getMessage()}" ></c:out>
	</h4>
	<ul>
		<c:forEach items="${exception.getStackTrace()}" var="stack">
			<li><c:out value="${stack}"></c:out></li>
		</c:forEach>
	</ul>
</body>
</html>