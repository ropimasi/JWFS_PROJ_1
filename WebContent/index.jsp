<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"> -->
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-colors-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-fonts-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-root-vars-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-basics-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-grids-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-buttons-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-forms-lib.css">
	<%-- <title>Login - <jsp:include page="resources/pages-parts/title.jsp" /></title> --%>
</head>
<body>
	<!-- JSP directives/notations to page functionality. -->
	<%@page info="Login page to the Java Web Full Stack didatic application, by ROPIMASI."%>
	<%@page errorPage="resources/error-pages/default-error.jsp"%>
	<%-- <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>	
	
	
	<!-- JAVA directives/imports to minor processing in the presentation of the frontend. -->
	<%-- <%@page import="application.service.ValidationResult"%> --%>
	<%-- <%@page import="application.service.ValidationResultSet"%> --%>
	<%-- <%@page import="application.service.SymmCrypSamp"%> --%>
	<%@page import="application.entity.UserEntity"%>
	<%@page import="application.entity.dto.UserCompactDTO"%>
	
	
	<%
	/* BEGIN *PRE-REQUIREMENTS* ASR-URI TECHNIQUE. */
		HttpSession loggedSession = request.getSession();
		
		/* Subtitle: lucdfsa = loggedUserCompactDTOFromSessionAttrib. */
		UserCompactDTO lucdfsa = (UserCompactDTO) loggedSession.getAttribute("lucdsa");
		
		/* Subtitle: luifp = loggedUsesrIdFromParam. */
		long luifp = ((request.getParameter("loggedUserIdParam") != null) && (!request.getParameter("loggedUserIdParam").isEmpty()))
				? Long.parseLong(request.getParameter("loggedUserIdParam")) : -1L ;
	/* END ASR-URI TECHNIQUE. */
	%>


	<div class="container-login">
		<h3>JAVA WEB FULL STACK PORTFOLIO</h3>
		<h4>ROJECT #1: DIDATIC APPLICATION</h4>
		<br/>
		<h5>Login to project.</h5>
		<br/>
		<form id="login" name="login" action="LoginServlet" method="post" target="">
		<div class="rsp-flx-cnt-cnt-ctnr">
			<input type="text" id="usuario" name="usuario" placeholder="User Name..." tabindex="1" required autofocus>
			<input type="password" id="senha" name="senha" placeholder="Password..." tabindex="2" required>
			<button type="submit" id="submit" name="submit" class="middle progress" tabindex="3" data-submit="...Sending">Login</button>
			<button type="reset" id="reset" name="reset" class="middle permanence" tabindex="4">Reset</button>
		</div>
		</form>
	</div>
</body>
</html>

