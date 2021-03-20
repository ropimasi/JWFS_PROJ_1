<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link rel="stylesheet" type="text/css" href="resources/styles/jwc-color-lib.css">
<title>JUST TESTING COLORS</title>
</head>
<body>
	<!-- JSP notations to page functionality. -->
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<%-- 		Só faz progressiva: ${_n}, _n, <c:out value="_n" />, <c:out value="${_n}" /> --%>

	<br>
	<c:forEach var="j" begin="0" end="255" step="2">
		<div>
		<c:forEach var="k" begin="0" end="255" step="2">
			<span onMouseDown="alert('(255,${j},${k})');" style="background-color:rgb(255,${j},${k})"> &nbsp; </span>
		</c:forEach>
		</div>
	</c:forEach>
	
	<br>
	<c:forEach var="j" begin="0" end="255" step="2">
		<div>
		<c:forEach var="k" begin="0" end="255" step="2">
			<span onMouseDown="alert('(${j},255,${k})');" style="background-color:rgb(${j},255,${k})"> &nbsp; </span>
		</c:forEach>
		</div>
	</c:forEach>

	<br>
	<c:forEach var="j" begin="0" end="255" step="2">
		<div>
		<c:forEach var="k" begin="0" end="255" step="2">
			<span onMouseDown="alert('(${j},${k},255)');" style="background-color:rgb(${j},${k},255)"> &nbsp; </span>
		</c:forEach>
		</div>
	</c:forEach>

</body>
</html>
