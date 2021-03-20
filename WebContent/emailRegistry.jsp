<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-lib.css">
	<title>Cadastro de emails - JAVA WEB COMPLETO MÓDULO 20</title>
</head>

<body>
	<!-- JSP notations to page functionality. -->
	<%@page info="Página de cadastrar novos emails e listar os já existentes."%>
	<!--  %@page errorPage="resources/error-pages/default-error.jsp"% -->
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<!-- JAVA importações para pequenos processamentos na apresentação do frontend. -->
	<%@page import="application.service.ValidationResult"%>
	<%@page import="application.service.ValidationResultSet"%>
	<!--  %@page import="application.service.SymmCrypSamp"% -->
	<%@page import="application.entity.UserEntity"%>
	<%@page import="application.entity.EmailEntity"%>
	<%
	HttpSession loggedSession = request.getSession();
	UserEntity loggedUser = (UserEntity) loggedSession.getAttribute("loggedUser");
	long loggedUsesrIdFromParam;

	loggedUsesrIdFromParam = (request.getParameter("userId") != null) ? Long.parseLong(request.getParameter("userId")) : 0L;

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

		/* FIXME: Erro não encontrada a causa: application.entity.EmailEntity.EADDRESS_MIN_LEN  não existe ?! */ 
		const EADDRESS_MIN_LEN = ${application.entity.EmailEntity.EADDRESS_MIN_LEN} ;
		// < %= application.entity.EmailEntity.EADDRESS_MIN_LEN % > ;
		const EADDRESS_MAX_LEN = ${application.entity.EmailEntity.EADDRESS_MAX_LEN} ;
		// < %= application.entity.EmailEntity.EADDRESS_MAX_LEN % > ; 

		function validarCamposEmail() {
			if ((document.getElementById('eAddress').value.length < EADDRESS_MIN_LEN)
					|| (document.getElementById('eAddress').value.length > EADDRESS_MAX_LEN)) {
				alert('Campo \'email-Address\' com comprimento inválido! (mín:'
						+ EADDRESS_MIN_LEN + ' e máx:' + EADDRESS_MAX_LEN + ')');
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


	<!-- Container do conteúdo principal. -->
	<div class="container-main">
		<h2>Cadastro de emails</h2>
		
		<p>&nbsp;</p>

		<!-- Painel de mensagens de retorno. -->
		<c:if test="${emailVRS != null}">
			<p>&nbsp;</p>
			<c:forEach var="VRSItem" items="${emailVRS.getVRList()}">
				<c:choose>
					<c:when test='${VRSItem.getType().equalsIgnoreCase("Information")}'>
						<div id="VRSMsgInfo">
							<img class="plcpanel" src="resources/imgs/panel-information-256-256.png" width="56" height="56"
								title="Information" alt="Information">
					</c:when>
					<c:when test='${VRSItem.getType().equalsIgnoreCase("Question")}'>
						<div id="VRSMsgQuest">
							<img class="plcpanel" src="resources/imgs/panel-question-256-256.png" width="64" height="64" title="Question"
								alt="Question">
					</c:when>
					<c:when test='${VRSItem.getType().equalsIgnoreCase("Alert")}'>
						<div id="VRSMsgAlert">
							<img class="plcpanel" src="resources/imgs/panel-alert-256-256.png" width="72" height="72" title="Alert"
								alt="Alert">
					</c:when>
					<c:when test='${VRSItem.getType().equalsIgnoreCase("Error")}'>
						<div id="VRSMsgError">
							<img class="plcpanel" src="resources/imgs/panel-error-256-256.png" width="80" height="80" title="Error"
								alt="Error">
					</c:when>
					<c:otherwise>
						<div id="VRSMsgQuest">
							<img class="plcpanel" src="resources/imgs/panel-crash-256-256.png" width="88" height="88" title="Crash!"
								alt="Crash!">
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
	<form id="registry" name="registry" action="EmailRegistryServlet?action=save&userId=${loggedUser.getId()}"
		method="post" onSubmit="javascript:return validarCamposEmail();">
		<div class=" s-r-center-container">
			<input type="text" class="towCols" id="id" name="id" placeholder="Id..." value="${soughtEmailEdit.getId()}"
				tabindex="-1" readonly> &nbsp; <input type="text" class="towCols" id="oldEAddress" name="oldEAddress"
				placeholder="email-Address..." value="${soughtEmailEdit.getEAddress()}" tabindex="-1" readonly>
		</div>
		<input type="text" id="eAddress" name="eAddress" placeholder="email-Adress..."
			value="${soughtEmailEdit.getEAddress()}" minlength="${application.entity.EmailEntity.EADDRESS_MIN_LEN}"
			maxlength="${application.entity.EmailEntity.EADDRESS_MAX_LEN}" tabindex="1" required autofocus>
		<div class="s-r-center-container">
			<input type="text" class="towCols" id="userId" name="userId" placeholder="User Id..." value="${loggedUser.getId()}"
				tabindex="-1" readonly> &nbsp; <input type="text" class="towCols" id="userName" name="userName"
				placeholder="User Name..." value="${loggedUser.getFullName()}" tabindex="-1" readonly>
		</div>
		<div class="s-r-center-container">
			<button type="submit" id="submit" name="submit" tabindex="2" data-submit="...Sending">Save</button>
			<button type="reset" id="reset" name="reset" tabindex="3">Reset</button>
			<button type="button" id="cancel" name="cancel" tabindex="4"
				onClick="javascript:window.location.href='EmailRegistryServlet?action=list&userId=${loggedUser.getId()}';">Cancel</button>
		</div>
	</form>
	<!-- /FIM Foumulário do cadastro propriamente dito. -->

	<p>&nbsp;</p>
	<p>&nbsp;</p>

	<!-- Painel de demonstração dos registros. -->
		<div class="w3-container">
			<c:forEach var="forEmail" items="${emailsList}">
		
			<div class="w3-row">
				<div class="w3-half w3-border">
					<div class="labelCellRegistry">Endereço Email</div>
					<div class="dataCellRegistry">
						<c:out value="${forEmail.getEAddress()}" />
					</div>
				</div>
				<div class="w3-quarter w3-border">
					<div class="labelCellRegistry">Cód usuário</div>
					<div class="dataCellRegistry">
						<c:out value="${forEmail.getUserId()}" />
					</div>
				</div>
				<div class="w3-quarter w3-border">
					<div class="labelCellRegistry">Comandos</div>
					<div class="dataCellRegistry">
						<a href="EmailRegistryServlet?action=infodetail&id=${forEmail.getId()}&userId=${loggedUser.getId()}"> <img class="btntable" src="resources/imgs/button-info-detail-64-64.png" title="Info Detail" alt="Info Detail" width="24px" /></a>
						<a href="EmailRegistryServlet?action=edit&id=${forEmail.id}&userId=${loggedUser.getId()}"> <img class="btntable" src="resources/imgs/button-edit-64-64.png" title="Edit" alt="Edit" width="24px" /></a>
						<a href="EmailRegistryServlet?action=exclude&id=${forEmail.id}&userId=${loggedUser.getId()}&userId=${loggedUser.getId()}"> <img class="btntable" src="resources/imgs/button-exclude-64-64.png" title="Exclude" alt="Exclude" width="24px" /></a>
					</div>
				</div>
			</div>
			
			<p>&nbsp;</p>
			</c:forEach>
		</div> <!-- FIM Painel de demonstração dos registros. -->
	
	</div><!-- /FIM Container do conteudo principal. -->
	
	<jsp:include page="resources/pages-parts/bottomNavBar.jsp" />
	
</body><!-- /FIM Início do layout web no navegador propriamente dito. -->
</html>
















