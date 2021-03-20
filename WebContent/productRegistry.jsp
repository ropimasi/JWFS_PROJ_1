<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-lib.css">
	<title>Cadastro de produtos - JAVA WEB COMPLETO MÓDULO 20</title>
</head>

<body>
	<!-- JSP notations to page functionality. -->
	<%@page info="Página de cadastrar novos produtos e listar os já existentes."%>
	<%@page errorPage="resources/error-pages/default-error.jsp"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	<!-- JAVA importações para pequenos processamentos na apresentação do frontend. -->
	<%@page import="application.service.ValidationResult"%>
	<%@page import="application.service.ValidationResultSet"%>
	<!--  %@page import="application.service.SymmCrypSamp"% -->
	<%@page import="application.entity.ProductEntity"%>
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
		const NAME_MIN_LEN = ${application.entity.ProductEntity.NAME_MIN_LEN} ;
		const NAME_MAX_LEN = ${application.entity.ProductEntity.NAME_MAX_LEN} ;
		const PURCHASEPRICE_MIN_VAL = ${application.entity.ProductEntity.PURCHASEPRICE_MIN_VAL} ;
		const PURCHASEPRICE_MAX_VAL = ${application.entity.ProductEntity.PURCHASEPRICE_MAX_VAL} ;
		const SALEPRICE_MIN_VAL = ${application.entity.ProductEntity.SALEPRICE_MIN_VAL} ;
		const SALEPRICE_MAX_VAL = ${application.entity.ProductEntity.SALEPRICE_MAX_VAL} ;
		const STOCKQTTY_MIN_VAL = ${application.entity.ProductEntity.STOCKQTTY_MIN_VAL} ;
		const STOCKQTTY_MAX_VAL = ${application.entity.ProductEntity.STOCKQTTY_MAX_VAL} ;

		function validarCamposProduct() {
			if ((document.getElementById('name').value.length < NAME_MIN_LEN)
					|| (document.getElementById('name').value.length > NAME_MAX_LEN)) {
				alert('Campo \'Name\' com comprimento inválido! (mín:'+ NAME_MIN_LEN + ' e máx:' + NAME_MAX_LEN + ')');
				return false;
			} else if ((document.getElementById('purchasePrice').value < PURCHASEPRICE_MIN_VAL)
					|| (document.getElementById('purchasePrice').value > PURCHASEPRICE_MAX_VAL)) {
				alert('Campo \'Purchase Price\' com VALOR inválido! (mín:'+ PURCHASEPRICE_MIN_VAL + ' e máx:' + PURCHASEPRICE_MAX_VAL + ')');
				return false;
			} else if ((document.getElementById('salePrice').value < SALEPRICE_MIN_VAL)
					|| (document.getElementById('salePrice').value > SALEPRICE_MAX_VAL)) {
				alert('Campo \'Sale Price\' com VALOR inválido! (mín:'+ SALEPRICE_MIN_VAL + ' e máx:' + SALEPRICE_MAX_VAL + ')');
				return false;
			} else if ((document.getElementById('stockQtty').value < STOCKQTTY_MIN_VAL)
					|| (document.getElementById('stockQtty').value > STOCKQTTY_MAX_VAL)) {
				alert('Campo \'Stock Quantity\' com QUANTIDADE inválida! (mín:'+ STOCKQTTY_MIN_VAL + ' e máx:' + STOCKQTTY_MAX_VAL + ')');
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
		<h2>Cadastro de produtos</h2>
		
		<p>&nbsp;</p>

		<!-- Painel de mensagens de retorno. -->
		<c:if test="${productVRS != null}">
		<c:forEach var="VRSItem" items="${productVRS.getVRList()}">
			<c:choose>
				<c:when test='${VRSItem.getType().equalsIgnoreCase("Information")}'>
					<div id="daoVRMsgInfo">
					<img class="plcpanel" src="resources/imgs/panel-information-256-256.png" width="96" height="96" title="Information" alt="Information">
				</c:when>	
				<c:when test='${VRSItem.getType().equalsIgnoreCase("Question")}'>
					<div id="daoVRMsgQuest">
					<img class="plcpanel" src="resources/imgs/panel-question-256-256.png" width="112" height="112" title="Question" alt="Question">
				</c:when>	
				<c:when test='${VRSItem.getType().equalsIgnoreCase("Alert")}'>
					<div id="daoVRMsgAlert">
					<img class="plcpanel" src="resources/imgs/panel-alert-256-256.png" width="120" height="120" title="Alert" alt="Alert">
				</c:when>	
				<c:when test='${VRSItem.getType().equalsIgnoreCase("Error")}'>
					<div id="daoVRMsgError">
					<img class="plcpanel" src="resources/imgs/panel-error-256-256.png" width="128" height="128" title="Error" alt="Error">
				</c:when>	
				<c:otherwise>
					<div id="daoVRMsgQuest">
					<img class="plcpanel" src="resources/imgs/panel-crash-256-256.png" width="128" height="128" title="Crash!" alt="Crash!">
				</c:otherwise>
			</c:choose>
			<c:out value="${VRSItem.getWholeMsg()}" /></div>
		</c:forEach>
		<p>&nbsp;</p>
		</c:if>
		<!-- /FIM Painel de mensagens de retorno. -->

		<p>&nbsp;</p>
		
		<!-- Foumulário do cadastro propriamente dito. -->
		<form id="registry" name="registry" action="ProductRegistryServlet?action=save&userId=${loggedUser.getId()}" method="post" onSubmit="javascript:return validarCamposProduct();">
			<input type="text" id="id" name="id" placeholder="Id..." value="${soughtProductEdit.id}" readonly>
			<input type="text" id="name" name="name" placeholder="Product name..." value="${soughtProductEdit.name}" tabindex="1" required autofocus>
			<input type="text" id="purchasePrice" name="purchasePrice" placeholder="Purchase price..." value="${soughtProductEdit.purchasePrice}" tabindex="2" required>
			<input type="text" id="salePrice" name="salePrice" placeholder="Sale price..." value="${soughtProductEdit.salePrice}" tabindex="3" required>
			<input type="text" id="stockQtty" name="stockQtty" placeholder="Stock quantity..." value="${soughtProductEdit.stockQtty}" tabindex="4" required>
			<div class="s-r-center-container">
				<button type="submit" id="submit" name="submit" tabindex="6" data-submit="...Sending">Save</button>
				<button type="reset" id="reset" name="reset" tabindex="7">Reset</button>
				<button type="button" id="cancel" name="cancel" tabindex="8" onClick="javascript:window.location.href='ProductRegistryServlet?action=list&userId=${loggedUser.getId()}';">Cancel</button>				
			</div>
		</form>
		<!-- /FIM Foumulário do cadastro propriamente dito. -->
				
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		
		<!-- Painel de demonstração dos registros. -->
		<div class="w3-container">
			<c:forEach var="productItem" items="${productList}">
		
			<div class="w3-row">
				<div class="w3-rest w3-border">
					<div class="labelCellRegistry">Nome do Produto</div>
					<div class="dataCellRegistry">
						<c:out value="${productItem.name}" />
					</div>
				</div>
			</div>
				
			<div class="w3-row">
				<div class="w3-quarter w3-border">
					<div class="labelCellRegistry">Preço Compra</div>
					<div class="dataCellRegistry">
						<c:out value="${productItem.purchasePrice}" />
					</div>
				</div>
				<div class="w3-quarter w3-border">
					<div class="labelCellRegistry">Preço Venda</div>
					<div class="dataCellRegistry">
						<c:out value="${productItem.salePrice}" />
					</div>
				</div>
				<div class="w3-quarter w3-border">
					<div class="labelCellRegistry">Qtde. Estoque</div>
					<div class="dataCellRegistry">
						<c:out value="${productItem.stockQtty}" />
					</div>
				</div>
				<div class="w3-quarter w3-border">
					<div class="labelCellRegistry">Comandos</div>
					<div class="dataCellRegistry">
						<a href="ProductRegistryServlet?action=infodetail&id=${productItem.getId()}&userId=${loggedUser.getId()}"><img class="btntable" src="resources/imgs/button-info-detail-64-64.png" title="Info Detail" alt="Info Detail" width="24px"/></a>
						<a href="ProductRegistryServlet?action=edit&id=${productItem.getId()}&userId=${loggedUser.getId()}"><img class="btntable" src="resources/imgs/button-edit-64-64.png" title="Edit" alt="Edit" width="24px"/></a>
						<a href="ProductRegistryServlet?action=exclude&id=${productItem.getId()}&userId=${loggedUser.getId()}"><img class="btntable" src="resources/imgs/button-exclude-64-64.png" title="Exclude" alt="Exclude" width="24px"/></a>
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
















