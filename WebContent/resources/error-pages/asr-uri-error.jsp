<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<%! String THIS_PAGE = "ASR-URI ERROR PAGE"; %>
		<title><%= THIS_PAGE %> - <jsp:include page="../pages-parts/title.jsp" /></title>
	</head>
<body bgcolor="#DFB">
	<%@ page info="" %>
	<%@ page isErrorPage="true" %>
		
	<h2>JWFS PROJ 1: Default error page for <%= THIS_PAGE %>.</h2>
	<h4>Exception = <%= exception %>.</h4>
	
	<p><br/></p>
	
	<!-- FURTHER: link to back; -->
	<%-- <h3> <a href="${voltarPara}">Voltar</a> </h3> --%>
	
</body>
</html>