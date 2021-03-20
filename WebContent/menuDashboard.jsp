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
	<!-- <link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-grids-lib.css"> -->
	<!-- <link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-buttons-lib.css"> -->
	<!-- <link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-forms-lib.css"> -->
	<title>Menu &amp; Dashboard - <jsp:include page="resources/pages-parts/title.jsp" /></title>
</head>
<body>
	<!-- JSP directives/notations to page functionality. -->
	<%@page info="Menu and dashboard page to the Java Web Full Stack didatic application, by ROPIMASI."%>
	<%@page errorPage="resources/error-pages/default-error.jsp"%>
	<%-- <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>	
	
	
	<!-- JAVA directives/imports to minor processing in the presentation of the frontend. -->
	<%-- <%@page import="application.service.ValidationResult"%> --%>
	<%-- <%@page import="application.service.ValidationResultSet"%> --%>
	<%-- <%@page import="application.service.SymmCrypSamp"%> --%>
	<%-- <%@page import="application.entity.UserEntity"%> --%>
	<%@page import="application.entity.dto.UserCompactDTO"%>
	
	
	<%
	/* BEGIN ASR-URI TECHNIQUE. */
		HttpSession loggedSession = request.getSession();
		
		/* loggedUserCompactDTOFromSessionAttrib */
		UserCompactDTO lucdfsa = (UserCompactDTO) loggedSession.getAttribute("lucdsa");
		
		/* loggedUsesrIdFromParam */
		long luifp = ((request.getParameter("loggedUserIdParam") != null) && (!request.getParameter("loggedUserIdParam").isEmpty()))
				? Long.parseLong(request.getParameter("loggedUserIdParam")) : -1L ;
		
		if ((lucdfsa == null) || (luifp != lucdfsa.getId())) {
			%>
			<jsp:forward page="resources/error-pages/asr-uri-error.jsp">
				<jsp:param name="" value="" />
			</jsp:forward>
			<%
		}
	/* END ASR-URI TECHNIQUE. */
	%>


	<!-- Set focus to the first/most-used element: better UI/UX. -->
	<script type="text/javascript">
	var loader = setInterval(
		function () {
			if(document.readyState !== "complete") return;

			clearInterval(loader);
			document.getElementById("firstFocus").focus();
		}, 250
	);
	</script>


	<!-- Início do layout web no navegador propriamente dito. -->
	<!-- Barra superior de comandos. -->
	<jsp:include page="resources/pages-parts/topNavBar.jsp" />

	<!-- Container do conteúdo principal. -->
	<div class="container-main">
		<div id="menudashboard">
			<h1 align="center">Menu &amp; Painel de Controle</h1>
			<h3 align="center">${lucdsa.getFullName()}, seja bem-vindo!</h3>
			<p>&nbsp;</p>
			<div style="display: inline-table; width: 55%;">
				<a id="firstFocus" class="a4" href="UserRegistryServlet?action=list&loggedUserIdParam=${lucdsa.getId()}">
					<img class="plcpanel" src="resources/imgs/folder-user-registry-128-128.png" width="48px" height="48px"
					title="User Registry" alt="User Registry" /> Cadastro de usuários ↪
				</a>
				<p>&nbsp;</p>
				<a class="a4" href="UserLevelRegistryServlet?action=list&loggedUserIdParam=${lucdsa.getId()}"> <img class="plcpanel"
					src="resources/imgs/folder-level-registry-128-128.png" width="48px" height="48px" title="User's Level Registry"
					alt="User's Level Registry" /> Cadastro de níveis de usuários ↪
				</a>
				<p>&nbsp;</p>
				<a class="a4" href='PhoneRegistryServlet?action=list&loggedUserIdParam=${lucdsa.getId()}'> <img class="plcpanel"
					src="resources/imgs/folder-phone-registry-128-128.png" width="48px" height="48px" title="User's Phone Registry"
					alt="User's Phone Registry" /> Cadastro de telefones ↪
				</a>
				<p>&nbsp;</p>
				<a class="a4" href='EmailRegistryServlet?action=list&loggedUserIdParam=${lucdsa.getId()}'> <img class="plcpanel"
					src="resources/imgs/folder-email-registry-128-128.png" width="48px" height="48px" title="User's Email Registry"
					alt="User's Email Registry" /> Cadastro de emails ↪
				</a>
				<p>&nbsp;</p>
				<a class="a4" href="ProductRegistryServlet?action=list&loggedUserIdParam=${lucdsa.getId()}"> <img class="plcpanel"
					src="resources/imgs/folder-product-registry-128-128.png" width="48px" height="48px" title="Product Registry"
					alt="Product Registry" /> Cadastro de produtos ↪
				</a>
			</div>
			<div style="display: inline-table; width: 44%;">
				<a class="a4" href="ConfigServlet?action=list&loggedUserIdParam=${lucdsa.getId()}"> <img class="plcpanel"
					src="resources/imgs/flat-settings-64-64.png" width="48px" height="48px" title="Configurações" alt="Configurações" />
					Configurações ↪
				</a> <br />
				<p>&nbsp;</p>
			</div>
		</div>
	</div><!-- /FIM Container do conteudo principal. -->

	<!-- Barra inferior de comandos. -->
	<jsp:include page="resources/pages-parts/bottomNavBar.jsp" />
	<p>&nbsp;</p>

	<!-- /FIM Início do layout web no navegador propriamente dito. -->
</body>
</html>