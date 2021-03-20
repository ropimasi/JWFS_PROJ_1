<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="resources/styles/cores.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/formatacoesCadastro.css">
	<title>Configurações da Aplicação - JAVA WEB COMPLETO MÓDULO 20</title>
</head>
<body>
	<!-- JSP notations to page functionality. -->
	<%@page info="Página de cadastrar novos usuários e listar os já existentes."%>
	<%@page errorPage="resources/error-pages/default-error.jsp"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<!-- JAVA importações para pequenos processamentos na apresentação do frontend. -->
	<%@page import="application.entity.UserEntity"%>
	<%@page import="application.service.ValidationResult"%>
	<%@page import="application.service.ValidationResultSet"%>
	<%@page import="application.service.SymmCrypSamp"%>
	
	
	
	<!-- Script para validação de campos <input> do usuário na camada mais externa (navegador). -->
	<script type="text/javascript">
		/* Based on Business and Technicals Rules: */
		const FULLNAME_MIN_LEN = ${application.entity.UserEntity.FULLNAME_MIN_LEN} ;									
		const FULLNAME_MAX_LEN = ${application.entity.UserEntity.FULLNAME_MAX_LEN} ;
		const USERNAME_MIN_LEN = ${application.entity.UserEntity.USERNAME_MIN_LEN} ;
		const USERNAME_MAX_LEN = ${application.entity.UserEntity.USERNAME_MAX_LEN} ;
		const PASSWORD_MIN_LEN = ${application.entity.UserEntity.PASSWORD_MIN_LEN} ;
		const PASSWORD_MAX_LEN = ${application.entity.UserEntity.PASSWORD_MAX_LEN} ;
		const EMAIL_MIN_LEN = ${application.entity.UserEntity.EMAIL_MIN_LEN} ;
		const EMAIL_MAX_LEN = ${application.entity.UserEntity.EMAIL_MAX_LEN} ;

		function validarCampos() {
			if ((document.getElementById('fullName').value.length < FULLNAME_MIN_LEN)
					|| (document.getElementById('fullName').value.length > FULLNAME_MAX_LEN)) {
				alert('Campo \'Full Name\' com comprimento inválido! (mín:'+ FULLNAME_MIN_LEN + ' e máx:' + FULLNAME_MAX_LEN + ')');
				return false;
			} else if ((document.getElementById('userName').value.length < USERNAME_MIN_LEN)
					|| (document.getElementById('userName').value.length > USERNAME_MAX_LEN)) {
				alert('Campo \'User Name\' com comprimento inválido! (mín:'+ USERNAME_MIN_LEN + ' e máx:' + USERNAME_MAX_LEN + ')');
				return false;
			} else if ((document.getElementById('password').value.length < PASSWORD_MIN_LEN)
					|| (document.getElementById('password').value.length > PASSWORD_MAX_LEN)) {
				alert('Campo \'Password\' com comprimento inválido! (mín:'+ PASSWORD_MIN_LEN + ' e máx:' + PASSWORD_MAX_LEN + ')');
				return false;
			} else if ((document.getElementById('email').value.length < EMAIL_MIN_LEN)
					|| (document.getElementById('email').value.length > EMAIL_MAX_LEN)) {
				alert('Campo \'Email\' com comprimento inválido! (mín:'+ EMAIL_MIN_LEN + ' e máx:' + EMAIL_MAX_LEN + ')');
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
		<h3>Configurações da Aplicação</h3>
		<p>&nbsp;</p>
		
		<!-- Painel de mensagens de retorno. -->
		<c:if test="${userVRS != null}">
			<p>&nbsp;</p>
			<c:forEach var="VRSItem" items="${userVRS.getVRList()}">
				<c:choose>
					<c:when test='${VRSItem.getType().equalsIgnoreCase("Information")}'>
						<div id="VRSMsgInfo">
							<img class="plcpanel" src="resources/imgs/placa-information-256-256.png" width="56" height="56" title="Information" alt="Information">
					</c:when>
					<c:when test='${VRSItem.getType().equalsIgnoreCase("Question")}'>
						<div id="VRSMsgQuest">
							<img class="plcpanel" src="resources/imgs/placa-question-256-256.png" width="64" height="64" title="Question" alt="Question">
					</c:when>
					<c:when test='${VRSItem.getType().equalsIgnoreCase("Alert")}'>
						<div id="VRSMsgAlert">
							<img class="plcpanel" src="resources/imgs/placa-alert-256-256.png" width="72" height="72" title="Alert" alt="Alert">
					</c:when>
					<c:when test='${VRSItem.getType().equalsIgnoreCase("Error")}'>
						<div id="VRSMsgError">
							<img class="plcpanel" src="resources/imgs/placa-error-256-256.png" width="80" height="80" title="Error" alt="Error">
					</c:when>
					<c:otherwise>
						<div id="VRSMsgQuest">
							<img class="plcpanel" src="resources/imgs/placa-crash-256-256.png" width="88" height="88" title="Crash" alt="Crash">
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
		Formulário.<br/>
		<!-- 
		<form id="registry" name="registry" action="UserRegistryServlet?action=save" method="post" onSubmit="javascript:return validarCampos();">
			<input type="text" id="id" name="id" placeholder="Id..." value="${soughtUserEdit.id}" readonly>
			<input type="text" id="fullName" name="fullName" placeholder="User's Full Name..." value="${soughtUserEdit.fullName}" tabindex="1" required autofocus>
			<input type="text" id="userName" name="userName" placeholder="User's Name..." value="${soughtUserEdit.userName}" tabindex="2" required>
			<input type="password" id="password" name="password" placeholder="Password..." value="${soughtUserEdit_password_decrypted}" tabindex="3" required>
			<input type="text" id="email" name="email" placeholder="e@mail..." value="${soughtUserEdit.email}" tabindex="4" required>
			<input type="text" id="userLevel" name="userLevel" placeholder="Visitor | Operator | Maintenance | Admin" value="${soughtUserEdit.userLevel}" tabindex="5" required>
		
			<input type="text" id="addrPostalCode" name="addrPostalCode" placeholder="User's Postal Code..." value="${soughtUserEdit.addrPostalCode}"
					tabindex="6" required onBlur="javascript:seekPostalCodeWs();">
			<input type="text" id="addrFu" name="addrFu" placeholder="User's F.U..." value="${soughtUserEdit.addrFu}" tabindex="7" required>
			<input type="text" id="addrCity" name="addrCity" placeholder="User's City..." value="${soughtUserEdit.addrCity}" tabindex="8" required>
			<input type="text" id="addrNeighborhood" name="addrNeighborhood" placeholder="User's Neighborhood..." value="${soughtUserEdit.addrNeighborhood}" tabindex="9" required>
			<input type="text" id="addrVia" name="addrVia" placeholder="User's Via..." value="${soughtUserEdit.addrVia}" tabindex="10" required>
			<input type="text" id="addrNumber" name="addrNumber" placeholder="User's Addr Number..." value="${soughtUserEdit.addrNumber}" tabindex="11" required>
			<input type="text" id="addrComplement" name="addrComplement" placeholder="User's Addr Complement..." value="${soughtUserEdit.addrComplement}" tabindex="12">
		
			<div class="s-r-center-container">
				<button type="submit" id="submit" name="submit" tabindex="13" data-submit="...Sending">Save</button>
				<button type="reset" id="reset" name="reset" tabindex="14">Reset</button>
				<button type="button" id="cancel" name="cancel" tabindex="15" onClick="javascript:window.location.href='UserRegistryServlet?action=list';">Cancel</button>				
			</div>
		</form>
		 -->
		<!-- /FIM Foumulário do cadastro propriamente dito. -->
		
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		
		<!-- Tabela de demonstração dos registros. -->
		<table class="tbListRegistry">
			<tr><!-- PRIMEIRA LINHA, CABEÇALHO NÃO REPETE. -->
				<th class="thListRegistry">Propriedade</th>
				<th class="thListRegistry">Alternativas</th>
				<th class="thListRegistry">Valor atribuído</th>
				<th class="thListRegistry">Descrição</th>
			</tr>
			<c:forEach var="forUser" items="${usersList}">
				<tr>
					<td class="tdListRegistry"><c:out value="${forUser.fullName}" />prop...</td>
					<td class="tdListRegistry" style="font-size: 9pt;">
						<c:out value="${forUser.addrPostalCode}" />alter... <br/>
						<c:out value="${forUser.addrFu}" />alternati... <br/>
						<c:out value="${forUser.addrCity}" />alternativa... <br/>
					</td>
					<td class="tdListRegistry"><c:out value="${forUser.password}" />valor setado</td>
					<td class="tdListRegistry" style="font-size: 9pt;"><c:out value="${forUser.email}" /> uma descrição explicação para o usuário.</td>
					<td class="tdListRegistry">
						<a href="UserRegistryServlet?action=infodetail&id=${forUser.id}"><img class="btntable" src="resources/imgs/button-infodetail-64-64.png" title="Info Detail" alt="Info Detail" width="24px"/></a>
						<a href="UserRegistryServlet?action=edit&id=${forUser.id}"><img class="btntable" src="resources/imgs/button-edit-64-64.png" title="Edit" alt="Edit" width="24px"/></a>
						<a href="UserRegistryServlet?action=exclude&id=${forUser.id}"><img class="btntable" src="resources/imgs/button-exclude-64-64.png" title="Exclude" alt="Exclude" width="24px"/></a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<!-- FIM Tabela de demonstração dos registros. -->
		
	</div>
	<!-- /FIM Container do conteudo principal. -->
	<!-- /FIM Início do layout web no navegador propriamente dito. -->
</body>
</html>
















