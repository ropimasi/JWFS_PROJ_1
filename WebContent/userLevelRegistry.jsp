<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-lib.css">
	<title>Cadastro de níveis de usuários - JAVA WEB COMPLETO MÓDULO 20</title>
</head>

<body>

	<!-- /* TODO: Somente usuário com level "Admin" pode alterar. Demais só visualizar. */ -->

	<!-- JSP notations to page functionality. -->
	<%@page info="Página de cadastrar no níveis possíveis para usuários e listar os já existentes."%>
	<%@page errorPage="resources/error-pages/default-error.jsp"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<!-- JAVA importações para pequenos processamentos na apresentação do frontend. -->
	<%@page import="application.service.ValidationResult"%>
	<%@page import="application.service.ValidationResultSet"%>
	<!--  %@page import="application.service.SymmCrypSamp"% -->
	<%@page import="application.entity.UserLevelEntity"%>
	<%@page import="application.entity.UserEntity"%>
	<%
		HttpSession loggedSession = request.getSession();
		UserEntity loggedUser = (UserEntity) loggedSession.getAttribute("loggedUser");
		long loggedUsesrIdFromParam;
		
		loggedUsesrIdFromParam = (request.getParameter("userId") != null) ?
				Long.parseLong(request.getParameter("userId")) : 0L ;
		
		if (loggedUsesrIdFromParam != loggedUser.getId()) {
			%>
			<jsp:forward page="resources/error-pages/asr-uri-error.jsp">
				<jsp:param name="" value="" />
			</jsp:forward>
			<%
		}
	%>
	
	
	<!-- Script para validação de campos <input> do usuário na camada mais externa (navegador). -->
	<script type="text/javascript">
		/* Based on Business and Technicals Rules: */
		const NAME_MIN_LEN = ${application.entity.UserLevelEntity.NAME_MIN_LEN} ;
		const NAME_MAX_LEN = ${application.entity.UserLevelEntity.NAME_MAX_LEN} ;

		function validarCamposLevel() {
			if ((document.getElementById('name').value.length < NAME_MIN_LEN)
					|| (document.getElementById('name').value.length > NAME_MAX_LEN)) {
				alert('Campo \'Name\' com comprimento inválido! (mín:'+ NAME_MIN_LEN + ' e máx:' + NAME_MAX_LEN + ')');
				return false;
			} else {
				//alert('Campos válidos!');
				return true;
			}
		}
	</script>
	<!-- /FIM Script para validação de campos <input> do usuário na camada mais externa (navegador). -->
	
	
	<!-- Início do layout web no navegador propriamente dito. -->
	<!-- Barra de comandos superior. -->
	<jsp:include page="resources/pages-parts/topNavBar.jsp" />
	
	
	
	<!-- Container do conteudo principal. -->
	<div class="container-main">
		<h2>Cadastro de níveis de usuários</h2>
		
		<p>&nbsp;</p>

		<!-- Painel de mensagens retornos. -->
		<c:if test="${userVRS != null}">
			<c:forEach var="VRSItem" items="${userVRS.getVRList()}">
				<c:choose>
					<c:when test='${VRSItem.getType().equalsIgnoreCase("Information")}'>
						<div id="VRSMsgInfo">
							<img class="plcpanel" src="resources/imgs/panel-information-256-256.png" width="56" height="56" title="Information" alt="Information">
					</c:when>
					<c:when test='${VRSItem.getType().equalsIgnoreCase("Question")}'>
						<div id="VRSMsgQuest">
							<img class="plcpanel" src="resources/imgs/panel-question-256-256.png" width="64" height="64" title="Question" alt="Question">
					</c:when>
					<c:when test='${VRSItem.getType().equalsIgnoreCase("Alert")}'>
						<div id="VRSMsgAlert">
							<img class="plcpanel" src="resources/imgs/panel-alert-256-256.png" width="72" height="72" title="Alert" alt="Alert">
					</c:when>
					<c:when test='${VRSItem.getType().equalsIgnoreCase("Error")}'>
						<div id="VRSMsgError">
							<img class="plcpanel" src="resources/imgs/panel-error-256-256.png" width="80" height="80" title="Error" alt="Error">
					</c:when>
					<c:otherwise>
						<div id="VRSMsgQuest">
							<img class="plcpanel" src="resources/imgs/panel-crash-256-256.png" width="88" height="88" title="Crash!" alt="Crash!">
					</c:otherwise>
				</c:choose>
				<c:out value="${VRSItem.getWholeMsg()}" />
				</div>
			</c:forEach>
			<p>&nbsp;</p>
		</c:if>
		<!-- /FIM Painel de mensagens de retorno. -->
		
		<p>&nbsp;</p>
		
		<!-- Foumulário do cadastro propriamente dito. -->
		<c:if test='${lastAction == "edit"}'>
		<form id="registry" name="registry" action="UserLevelRegistryServlet?action=update&userId=${loggedUser.getId()}" method="post" onSubmit="javascript:return validarCamposLevel();">
		</c:if>
		<c:if test='${lastAction != "edit"}'>
		<form id="registry" name="registry" action="UserLevelRegistryServlet?action=insert&userId=${loggedUser.getId()}" method="post" onSubmit="javascript:return validarCamposLevel();">
		</c:if>
			<input type="text" id="oldName" name="oldName" placeholder="Old name..." value="${soughtUserLevelEdit}" tabindex="-1" readonly>
			<input type="text" id="name" name="name" placeholder="Name..." value="${soughtUserLevelEdit}" tabindex="1" required autofocus>
			<div class="s-r-center-container">
				<c:if test='${lastAction == "edit"}'>
					<button type="submit" id="submit" name="submit" tabindex="2" data-submit="...Sending">Update</button>
				</c:if>
				<c:if test='${lastAction != "edit"}'>
					<button type="submit" id="submit" name="submit" tabindex="2" data-submit="...Sending">Insert</button>
				</c:if>		
				<button type="reset" id="reset" name="reset" tabindex="3">Reset</button>
				<button type="button" id="cancel" name="cancel" tabindex="4" onClick="javascript:window.location.href='UserLevelRegistryServlet?action=list&userId=${loggedUser.getId()}';">Cancel</button>				
			</div>
		</form>
		
		<p>&nbsp;</p>
		<p>&nbsp;</p>
	
		<!-- Painel de demonstração dos registros. -->
		<div class="w3-container">
			<c:forEach var="forUserLevel" items="${userLevelsList}">
		
			<div class="w3-row">
				<div class="w3-twothird w3-border">
					<p class="labelCellRegistry">Nome do nível</p>
					<div class="dataCellRegistry">
						<c:out value="${forUserLevel}" />
					</div>
				</div>
				<div class="w3-third w3-border">
					<p class="labelCellRegistry">Comandos</p>
					<div class="dataCellRegistry">
						<a href="UserLevelRegistryServlet?action=infodetail&name=${forUserLevel}&userId=${loggedUser.getId()}"><img class="btntable" src="resources/imgs/button-info-detail-64-64.png" title="Info Detail" alt="Info Detail" width="24px"/></a>
						<a href="UserLevelRegistryServlet?action=edit&name=${forUserLevel}&userId=${loggedUser.getId()}"><img class="btntable" src="resources/imgs/button-edit-64-64.png" title="Edit" alt="Edit" width="24px"/></a>
						<a href="UserLevelRegistryServlet?action=exclude&name=${forUserLevel}&userId=${loggedUser.getId()}"><img class="btntable" src="resources/imgs/button-exclude-64-64.png" title="Exclude" alt="Exclude" width="24px"/></a>
					</div>
				</div>		
			</div>
			<p>&nbsp;</p>
			</c:forEach>
		</div> <!-- FIM Painel de demonstração dos registros. -->		
		
	</div><!-- /FIM Container do conteúdo principal. -->

	<jsp:include page="resources/pages-parts/bottomNavBar.jsp" />
	
</body> <!-- /FIM Início do layout web no navegador propriamente dito. -->
</html>
















