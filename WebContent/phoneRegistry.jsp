<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-colors-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-fonts-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-root-vars-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-basics-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-grids-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-buttons-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-forms-lib.css">
	<title>Phones Registry - <jsp:include page="resources/pages-parts/title.jsp" /></title>
</head>
<body>
	<!-- JSP directives/notations to page functionality. -->
	<%@page info="Page to registry new phones and listing the existing ones, by ROPIMASI."%>
	<%@page errorPage="resources/error-pages/default-error.jsp"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	
	<!-- JAVA directives/imports to minor processing in the presentation of the frontend. -->
	<%@page import="application.service.ValidationResult"%>
	<%@page import="application.service.ValidationResultSet"%>
	<%-- <%@page import="application.service.SymmCrypSamp"%> --%>
	<%-- <%@page import="application.entity.UserEntity"%> --%>
	<%@page import="application.entity.dto.UserCompactDTO"%>	
	<%@page import="application.entity.PhoneEntity"%>
	<%@page import="application.entity.PhoneTypeEnum"%>
	
	
	<%
	/* BEGIN ASR-URI TECHNIQUE. */
		HttpSession loggedSession = request.getSession();
		
		/* Subtitle: lucdfsa = loggedUserCompactDTOFromSessionAttrib. */
		UserCompactDTO lucdfsa = (UserCompactDTO) loggedSession.getAttribute("lucdsa");
		
		/* Subtitle: luifp = loggedUsesrIdFromParam. */
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
	
	
	<!-- Script para validação de campos <input> do usuário na camada mais externa (navegador). -->
	<script type="text/javascript">
		/* Based on Business and Technicals Rules: */
		const COUNTRYCODE_MIN_LEN = ${application.entity.PhoneEntity.COUNTRYCODE_MIN_LEN} ;
		const COUNTRYCODE_MAX_LEN = ${application.entity.PhoneEntity.COUNTRYCODE_MAX_LEN} ;
		const AREACODE_MIN_LEN = ${application.entity.PhoneEntity.AREACODE_MIN_LEN} ;
		const AREACODE_MAX_LEN = ${application.entity.PhoneEntity.AREACODE_MAX_LEN} ;
		const NUMBER_MIN_LEN = ${application.entity.PhoneEntity.NUMBER_MIN_LEN} ;
		const NUMBER_MAX_LEN = ${application.entity.PhoneEntity.NUMBER_MAX_LEN} ;

		function validarCamposPhone() {
			if ((document.getElementById('countryCode').value.length < COUNTRYCODE_MIN_LEN)
					|| (document.getElementById('countryCode').value.length > COUNTRYCODE_MAX_LEN)) {
				alert('Campo \'Country Code\' com comprimento inválido! (mín:'+ COUNTRYCODE_MIN_LEN + ' e máx:' + COUNTRYCODE_MAX_LEN + ')');
				return false;
			} else if ((document.getElementById('areaCode').value.length < AREACODE_MIN_LEN)
					|| (document.getElementById('areaCode').value.length > AREACODE_MAX_LEN)) {
				alert('Campo \'Area Code\' com comprimento inválido! (mín:'+ AREACODE_MIN_LEN + ' e máx:' + AREACODE_MAX_LEN + ')');
				return false;
			} else if ((document.getElementById('number').value.length < NUMBER_MIN_LEN)
					|| (document.getElementById('number').value.length > NUMBER_MAX_LEN)) {
				alert('Campo \'Number\' com comprimento inválido! (mín:'+ NUMBER_MIN_LEN + ' e máx:' + NUMBER_MAX_LEN + ')');
				return false;
			} else
			{
				//alert('Campos válidos!');
				return true;
			}
		}
	</script>
	<!-- /FIM Script para validação de campos <input> do usuário na camada mais externa (navegador). -->
	
	
	<!-- Início do layout web no navegador propriamente dito. -->
	<!-- Barra superior de comandos. -->	
	<jsp:include page="resources/pages-parts/topNavBar.jsp" />


	<!-- Container do conteúdo principal. -->
	<div class="container-main">
		<h2>Cadastro de telefones</h2>
		
		<p>&nbsp;</p>

		<!-- Painel de mensagens de retorno. -->
		<c:if test="${daoVRMessage != null}"> <!--  Se existe uma Mensagem Resposta do DAO, então mostrar. -->
			<c:choose>
				<c:when test='${daoVRObject.getType().equalsIgnoreCase("Information")}'>
					<div id="daoVRMsgInfo">
					<img class="plcpanel" src="resources/imgs/panel-information-256-256.png" width="96" height="96" title="Information" alt="Information">
				</c:when>	
				<c:when test='${daoVRObject.getType().equalsIgnoreCase("Question")}'>
					<div id="daoVRMsgQuest">
					<img class="plcpanel" src="resources/imgs/panel-question-256-256.png" width="112" height="112" title="Question" alt="Question">
				</c:when>	
				<c:when test='${daoVRObject.getType().equalsIgnoreCase("Alert")}'>
					<div id="daoVRMsgAlert">
					<img class="plcpanel" src="resources/imgs/panel-alert-256-256.png" width="120" height="120" title="Alert" alt="Alert">
				</c:when>	
				<c:when test='${daoVRObject.getType().equalsIgnoreCase("Error")}'>
					<div id="daoVRMsgError">
					<img class="plcpanel" src="resources/imgs/panel-error-256-256.png" width="128" height="128" title="Error" alt="Error">
				</c:when>	
				<c:otherwise>
					<div id="daoVRMsgQuest">
					<img class="plcpanel" src="resources/imgs/panel-crash-256-256.png" width="128" height="128" title="Crash!" alt="Crash!">
				</c:otherwise>
			</c:choose>
			<c:out value="${daoVRObject.getWholeMessage()}" />
			</div><!-- Este /div corresponde ao  div  interno às condicionais acima: <div id="VRSMsg..."> -->
			<p>&nbsp;</p>
		</c:if>
		<!-- /FIM Painel de mensagens de retorno. -->
		
		<p>&nbsp;</p>
			
		
		<!-- Foumulário do cadastro propriamente dito. -->
		<form id="registry" name="registry" action="PhoneRegistryServlet?action=save&loggedUserIdParam=${lucdfsa.getId()}"
		method="post" onSubmit="javascript:return validarCamposPhone();">
		<div class="rsp-flx-cnt-cnt-ctnr w100">
			<div class="rsp-flx-strt-strt-row">
				<div class="rsp-flx-strt-strt-itm w20">
					<input type="text" id="id" name="id" placeholder="Id..." value="${savingEditingPhone.id}" tabindex="-1" readonly disabled> 
				</div>
				<div class="rsp-flx-strt-strt-itm w80">
					<input type="text" id="oldNumber" name="oldNumber" placeholder="Old Number..." value="${savingEditingPhone.number}" tabindex="-1" readonly disabled>
				</div>
			</div>
			
			<div class="rsp-flx-strt-strt-row">
				<div class="rsp-flx-strt-strt-itm w20">
					<input type="text" id="countryCode" name="countryCode" placeholder="Country Code (max length 3)" value="${savingEditingPhone.countryCode}" maxlength="3" tabindex="1" required autofocus>
				</div>
				<div class="rsp-flx-strt-strt-itm w30">
					<input type="text" id="areaCode" name="areaCode" placeholder="Area Code (max length 3)" value="${savingEditingPhone.areaCode}" maxlength="3" tabindex="2" required>
				</div>
				<div class="rsp-flx-strt-strt-itm w50">	
					<input type="text" id="number" name="number" placeholder="Main Number (max length 10)" value="${savingEditingPhone.number}" maxlength="10" tabindex="3" required>
				</div>
			</div>
			
			<div class="rsp-flx-strt-strt-row">
				<div class="rsp-flx-strt-cnt-itm w40">
					<select id="type" name="type" tabindex="4" required>
						<option value="" disabled selected>Type of telephone (select one...)</option>
						<!-- FIXME: Aqui ainda há uma melhoria a ser feita. Mesmo após modificar a lista de 'levels'
						no B.D., esta página está carregando a mesma lista antiga/anterior de 'levels'.  -->
						<c:forEach var="forType" items="${PhoneTypeEnum.values()}">
							<option	value="${forType}" <c:if test="${forType == savingEditingPhone.type}">selected</c:if>>
								${forType}
							</option>
						</c:forEach>
					</select>
				</div>
				
				
				<!-- FURTHER TODO: FIXME: USER.ID E USER.FULLNAME PARA SER ASSOCIADO AO TELEFONE PRECISA TER SUA REGRA
				DE NEGOCIO MELHORADA: OU SOMENTE ASSOCIA CHAVE ESTRANGEIRA PARA O PROPRIO USUARIO LOGADO, OU
				QUALQUER UM A ESCOLHA. - SE ASSOCIAR AO LOGADO ENTAO PRECISA FILTRAR A EXIBIÇÃO TB SÓ DOS LOGADOS, OU
				HABILITAR A EDIÇÃO SÓ PARA O USUARIO PERMITIDO(LOGADO), OU USER ESTRUTURA DE HIERARQUIA DE PERMISSOES
				DE MODIFICAÇÕES ? -->
				<div class="rsp-flx-strt-strt-itm w20">
					<input type="text" id="userId" name="userId" placeholder="User Id..." value="${lucdsa.getId()}" tabindex="-1" readonly disabled> 
				</div>
				<div class="rsp-flx-strt-strt-itm w40">
					<input type="text" id="userName" name="userName" placeholder="User Name..." value="${lucdsa.getFullName()}" tabindex="-1" readonly disabled>
				</div>				
			</div>				
			<button type="submit" id="submit" name="submit" class="middle progress" tabindex="6" data-submit="...Sending">Save</button>
			<button type="reset" id="reset" name="reset" class="middle permanence" tabindex="7">Reset</button>
			<button type="button" id="cancel" name="cancel" class="middle setback" tabindex="8" onClick='javascript:window.location.href="PhoneRegistryServlet?action=list&loggedUserIdParam=${lucdsa.getId()}";'>Cancel</button>
		</div>
		</form>
		<!-- /FIM Foumulário do cadastro propriamente dito. -->
		
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		
		<!-- Painel de demonstração dos registros. -->
		<div class="w3-container">
			<c:forEach var="forPhone" items="${phonesList}">
		
			<div class="w3-row">
				<div class="w3-quarter w3-border">
					<div class="labelCellRegistry">Usuário</div>
					<div class="dataCellRegistry">
						<c:out value="${forPhone.userId}" />
					</div>
				</div>
				
				<div class="w3-half w3-border">
					<div class="labelCellRegistry">Tipo de telefone</div>
					<div class="dataCellRegistry">
						<c:out value="${forPhone.type}" />
					</div>
				</div>
				
				<div class="w3-quarter w3-border">
					<div class="labelCellRegistry">Comandos</div>
					<div class="dataCellRegistry">
						<a href="PhoneRegistryServlet?action=infodetail&infoDetailPhoneId=${forPhone.id}&loggedUserIdParam=${lucdsa.getId()}"><img class="btntable" src="resources/imgs/button-info-detail-64-64.png" title="Info Detail" alt="Info Detail" width="24px"/></a>
						<a href="PhoneRegistryServlet?action=edit&editPhoneId=${forPhone.id}&loggedUserIdParam=${lucdsa.getId()}"><img class="btntable" src="resources/imgs/button-edit-64-64.png" title="Edit" alt="Edit" width="24px"/></a>
						<a href="PhoneRegistryServlet?action=exclude&excludePhoneId=${forPhone.id}&loggedUserIdParam=${lucdsa.getId()}"><img class="btntable" src="resources/imgs/button-exclude-64-64.png" title="Exclude" alt="Exclude" width="24px"/></a>
					</div>
				</div>
			</div>
			
			<div class="w3-row">
				<div class="w3-quarter w3-border">
					<div class="labelCellRegistry">País</div>
					<div class="dataCellRegistry">
						<c:out value="${forPhone.countryCode}" />
					</div>
				</div>
				
				<div class="w3-quarter w3-border">
					<div class="labelCellRegistry">Cód. Área</div>
					<div class="dataCellRegistry">
						<c:out value="${forPhone.areaCode}" />
					</div>
				</div>
				
				<div class="w3-half w3-border">
					<div class="labelCellRegistry">Número</div>
					<div class="dataCellRegistry">
						<c:out value="${forPhone.number}" />
					</div>
				</div>		
			</div>
			
			<p>&nbsp;</p>
			</c:forEach>
		</div><!-- FIM Painel de demonstração dos registros. -->
	
	</div><!-- /FIM Container do conteudo principal. -->
	
	<!-- Barra inferior de comandos. -->
	<jsp:include page="resources/pages-parts/bottomNavBar.jsp" />	
	<p> &nbsp; </p>
	
	<!-- /FIM Início do layout web no navegador propriamente dito. -->
</body>
</html>
















