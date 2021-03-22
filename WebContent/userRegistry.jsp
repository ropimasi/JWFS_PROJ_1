<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-colors-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-fonts-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-root-vars-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-basics-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-grids-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-buttons-lib.css">
	<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-forms-lib.css">
	<%! String THIS_PAGE = "Users Registry"; %>
	<title><%= THIS_PAGE %> - <jsp:include page="resources/pages-parts/title.jsp" /></title>
	

	<!-- Add JQuery Lib -->
	<script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
</head>
<body>
	<!--
	FURTHER:
	Todos usuários visualizam:
	Usuário "Visitor" somente visualiza;
	Usuário "Operator" edita "levels" de seu próprio "Id";
	Usuário "Maintenence" edita "levels" de seu próprio "Id" e "Id" com "levels" = "Operator" e "Visitor";
	Usuário "Admin" edita todos.	
	-->
	
	

	<!-- JSP directives/notations to page functionality. -->
	<%@page info="Page to registry new users and listing the existing ones, by ROPIMASI."%>
	<%@page errorPage="resources/error-pages/default-error.jsp"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	
	
	<!-- JAVA directives/imports to minor processing in the presentation of the frontend. -->
	<%@page import="application.service.ValidationResult"%>
	<%@page import="application.service.ValidationResultSet"%>
	<%@page import="application.service.SymmCrypSamp"%>
	<%@page import="application.entity.UserEntity"%>
	<%@page import="application.entity.UserLevelEntity"%>
	<%@page import="application.entity.dto.UserCompactDTO"%>
	<%@page import="application.entity.PhoneEntity"%>
	
	
	
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
			<!-- FURTHER: link to back; -->
			<jsp:forward page="resources/error-pages/asr-uri-error.jsp">
				<jsp:param name="" value="" />
			</jsp:forward>
			<%
		}
	/* END ASR-URI TECHNIQUE. */
	%>
	
	
	
	<%
		/* PRE PROCESSAMENTO DE "$3NH@" PARA OS CAMPOS EM EDIÇÃO: */
		/* ESTE BLOCO É TEMPORÁRIO, ATÉ FUTURA VERSÃO TER SISTEMA DE SENHA ASYMMETRIC. */
		if (request.getAttribute("savingEditingUser") != null) {
			if (request.getAttribute("lastAction") == "edit") {
		UserEntity preProcessedUser = (UserEntity) request.getAttribute("savingEditingUser");
		request.setAttribute("savingEditingUser_password_decrypted", SymmCrypSamp.undoIt(preProcessedUser.getLoginPassword()));
			} else {
		UserEntity preProcessedUser = (UserEntity) request.getAttribute("savingEditingUser");
		request.setAttribute("savingEditingUser_password_decrypted", preProcessedUser.getLoginPassword());
			}
		}
	%>
	
	
	
	<!-- Script para validação de campos <input> do usuário na camada mais externa (navegador). -->
	<script type="text/javascript">
		/* Based on Business and Technicals Rules: */
		const FULLNAME_MIN_LEN = ${application.entity.UserEntity.FULLNAME_MIN_LEN} ;
		const FULLNAME_MAX_LEN = ${application.entity.UserEntity.FULLNAME_MAX_LEN} ;
		const LOGINNAME_MIN_LEN = ${application.entity.UserEntity.LOGINNAME_MIN_LEN} ;
		const LOGINNAME_MAX_LEN = ${application.entity.UserEntity.LOGINNAME_MAX_LEN} ;
		const LOGINPASSWORD_MIN_LEN = ${application.entity.UserEntity.LOGINPASSWORD_MIN_LEN} ;
		const LOGINPASSWORD_MAX_LEN = ${application.entity.UserEntity.LOGINPASSWORD_MAX_LEN} ;

		function validarCampos() {
			if ((document.getElementById('fullName').value.length < FULLNAME_MIN_LEN)
					|| (document.getElementById('fullName').value.length > FULLNAME_MAX_LEN)) {
				alert('Campo \'Full Name\' com comprimento inválido! (mín:'
						+ FULLNAME_MIN_LEN + ' e máx:' + FULLNAME_MAX_LEN + ')');
				return false;
			} else if ((document.getElementById('userName').value.length < LOGINNAME_MIN_LEN)
					|| (document.getElementById('userName').value.length > LOGINNAME_MAX_LEN)) {
				alert('Campo \'User Name\' com comprimento inválido! (mín:'
						+ LOGINNAME_MIN_LEN + ' e máx:' + LOGINNAME_MAX_LEN + ')');
				return false;
			} else if ((document.getElementById('password').value.length < LOGINPASSWORD_MIN_LEN)
					|| (document.getElementById('password').value.length > LOGINPASSWORD_MAX_LEN)) {
				alert('Campo \'Password\' com comprimento inválido! (mín:'
						+ LOGINPASSWORD_MIN_LEN + ' e máx:' + LOGINPASSWORD_MAX_LEN + ')');
				return false;
			} else {
				//alert('Campos válidos!');
				return true;
			}
		}
	</script>
	<!-- /FIM Script para validação de campos <input> do usuário na camada mais externa (navegador). -->



	<!-- Script para cepWebService. -->
	<script type="text/javascript">
		function seekPostalCodeWs() {
			var postalCode = $("#addrPostalCode").val();

			/* My request to webservice. */
			$.getJSON("https://viacep.com.br/ws/" + postalCode
					+ "/json/?callback=?", function(dados) {

				if (!("erro" in dados)) {
					$("#addrVia").val(dados.logradouro);
					$("#addrNeighborhood").val(dados.bairro);
					$("#addrCity").val(dados.localidade);
					$("#addrFu").val(dados.uf);
				} else { // Sought PostalCode not foundon ws.
					alert("CEP não encontrado.");
					$("#addrVia").val("");
					$("#addrNeighborhood").val("");
					$("#addrCity").val("");
					$("#addrFu").val("");
				}
			});
		}
	</script>
	<!-- /FIM Script para cepWebService. -->
	
	
	
	<!-- Script para mostrar userPicture. -->
	<script type="text/javascript">
		function pictureInjection(elmt, picFromInputFile) {
			// Ajustar o style do elmt para foto ficar bom visual:
			document.getElementById(elmt).setAttribute('class', 'userPictureShowerOff userPictureShowerOn');
		
			// Pegar o hold de referencia de arquivo.
			var theFile = picFromInputFile; // O próprio algumento passado é o elemento_input.target.files[0].
			
			// Interromper a função se o arquivo não for imagem.
			/* FIXME: implementar uma verificação de string ou de file.type.
			if (theFile.type != 'image/*') return; */
			
			// Ler o hold da referência de arquivo pego a cima.
			var fReader = new FileReader();
			//fReader.readAsText(theFile,'UTF-8'); // Opção de ler em text.
			fReader.readAsDataURL(theFile); // Opção de ler em bytes.
			
			// Aguardar a leitura do arquivo caregado:
			fReader.onloadend = rEvent => {
				var contentFile = rEvent.target.result; // Conteúdo do arquivo em si.
				
				// Carregar o conteúdo do arquivo (foto) para o elemento escolhido no DOM, neste caso o argumento 'elmt':
				document.getElementById(elmt).style.backgroundImage = 'url('+ contentFile +')';
			}
		}
	</script>
	<!-- /FIM Script mostrar userPicture. -->
	
	
	
	<!-- Início do layout web no navegador propriamente dito. -->
	<!-- Barra superior de comandos. -->
	<jsp:include page="resources/pages-parts/topNavBar.jsp" />



	<!-- Container do conteúdo principal. -->
	<div class="container-main">
		<h2><%= THIS_PAGE %></h2>
		
		<p>&nbsp;</p>
		
		<!-- Painel de mensagens de retorno. -->
		<c:if test="${userVRS != null}">
			<c:forEach var="VRSItem" items="${userVRS.getVRList()}">
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
				</div><!-- Este /div corresponde ao  div  interno às condicionais acima: <div id="VRSMsg..."> -->
			</c:forEach>
			<p>&nbsp;</p>
		</c:if>
		<!-- /FIM Painel de mensagens de retorno. -->

		<p>&nbsp;</p>
	
		<!-- Foumulário do cadastro propriamente dito. -->
		<form id="registry" name="registry" action="UserRegistryServlet?action=save&loggedUserIdParam=${lucdsa.getId()}" method="post"
			enctype="multipart/form-data" onSubmit="javascript:return validarCampos();">
			
			<div class="grid-container-user-info">
				<div class="ga-user-id"><input type="text" id="id" name="id" placeholder="Id..." value="${savingEditingUser.id}" tabindex="-1" readonly></div>
				<div class="ga-user-pic">
					
					<div id="userPictureShower" class="userPictureShowerOff"
						<c:if test='${savingEditingUser != null}'>
							style=" background-image: url('<c:out value="data:${savingEditingUser.pictureContentType};base64,${savingEditingUser.getPictureBase64String()}" />'); "
						</c:if>
					></div>
					
					<input type="file" id="userPictureInput" name="userPictureInput" tabindex="5"
					style="min-width:50px; width:80%; max-width:300px;" value=
					onchange="javascript:pictureInjection('userPictureShower', this.files[0]);" />
					
				</div>
				<div class="ga-user-full-name">
					<input type="text" id="fullName" name="fullName" placeholder="User's Full Name..." value="${savingEditingUser.fullName}" tabindex="1" required autofocus>
				</div>
				<div class="ga-user-name">
					<input type="text" id="userName" name="userName" placeholder="User's Name..." value="${savingEditingUser.loginName}" tabindex="2" required>
				</div>
				<div class="ga-user-pass">
					<input type="password" id="password" name="password" placeholder="Password..." value="${savingEditingUser_password_decrypted}" tabindex="3" required>
				</div>
				<div class="ga-user-level">
					<select id="userLevel" name="userLevel" tabindex="4" required>
					<option value="" disabled selected>Level of user, select one...</option>
					<!-- FIXME: Aqui ainda há uma melhoria a ser feita. Mesmo após modificar a lista de 'levels'
					no B.D., esta página está carregando a mesma lista antiga/anterior de 'levels'.  -->
					<c:forEach var="forLevel" items="${UserLevelEntity.getNamesList()}">
						<option value="${forLevel}" <c:if test="${forLevel == savingEditingUser.level}">selected</c:if>>
							${forLevel}
						</option>
					</c:forEach>
					</select>
				</div>
				<div class="ga-user-phones"><input type="text" id="phone" name="phone" placeholder="Telephones..." value="${savingEditingUser.getAllPhonesList()}" tabindex="-1" readonly></div>
				<div class="ga-user-emails"><input type="text" id="email" name="email" placeholder="Emails..." value="${savingEditingUser.getAllEmailsList()}" tabindex="-1" readonly></div>
			</div>
			
			<div class="grid-container-u-address-info">
				<div class="ga-u-address-country">
					<input type="text" id="addrContry" name="addrContry" placeholder="Temporariamente desativado..." value="" tabindex="-1" readonly>
				</div>
				<div class="ga-u-address-postal-code">
					<input type="text" id="addrPostalCode" name="addrPostalCode" placeholder="User's Postal Code..." value="${savingEditingUser.addrPostalCode}" tabindex="6" required onBlur="javascript:seekPostalCodeWs();">
				</div>
				<div class="ga-u-address-fu">
					<input type="text" id="addrFu" name="addrFu" placeholder="User's F.U..." value="${savingEditingUser.addrFu}" tabindex="7" required>
				</div>  
				<div class="ga-u-address-city">
					<input type="text" id="addrCity" name="addrCity" placeholder="User's City..." value="${savingEditingUser.addrCity}" tabindex="8" required>
				</div>
				<div class="ga-u-address-neighborhood">
					<input type="text" id="addrNeighborhood" name="addrNeighborhood" placeholder="User's Neighborhood..." value="${savingEditingUser.addrNeighborhood}" tabindex="9" required>
				</div>
				<div class="ga-u-address-via">
					<input type="text" id="addrVia" name="addrVia" placeholder="User's Via..." value="${savingEditingUser.addrVia}" tabindex="10" required>
				</div>
				<div class="ga-u-address-number">
					<input type="text" id="addrNumber" name="addrNumber" placeholder="User's Addr Number..." value="${savingEditingUser.addrNumber}" tabindex="11" required>
				</div>
				<div class="ga-u-address-complement">
					<input type="text" id="addrComplement" name="addrComplement" placeholder="User's Addr Complement..." value="${savingEditingUser.addrComplement}" tabindex="12">
				</div>
			</div>
			
			<p>&nbsp;</p>
			
			<div class="s-r-center-container">
				<button type="submit" id="submit" name="submit" class="middle progress" tabindex="13">Save</button>
				<button type="reset" id="reset" name="reset" class="middle permanence" tabindex="14">Reset</button>
				<button type="button" id="cancel" name="cancel" class="middle setback" tabindex="15" onClick='javascript:window.location.href="UserRegistryServlet?action=list&loggedUserIdParam=${lucdsa.getId()}";'>Cancel</button>
			</div>
		</form>
		<!-- /FIM Foumulário do cadastro propriamente dito. -->
	
		<p>&nbsp;</p>
		<p>&nbsp;</p>
	
		<!-- Painel de demonstração dos registros. -->
		<div class="w3-container">
			<c:forEach var="forUser" items="${usersList}">
			
			
			<div class="w3-row containerCellsRegistry">
				<div class="w3-row">
					<div class="w3-col w3-left" style="width:20%">
						<div id="userPictureShowerReg${forUser.id}" class="userPictureShowerOff userPictureShowerOn"
								style="background-image: url('<c:out value="data:${forUser.pictureContentType};base64,${forUser.getPictureBase64String()}" />');">
						</div>
					</div>
				
					<div class="w3-col w3-right" style="width:80%">
						<div class="w3-row">
							<div class="w3-twothird containerCellRegistry">
								<div class="labelCellRegistry">Nome Completo</div>
								<div class="dataCellRegistry">
									${forUser.fullName}
								</div>
							</div>
							
							<div class="w3-third containerCellRegistry">
								<div class="labelCellRegistry">Nível de Usuário</div>
								<div class="dataCellRegistry">
									${forUser.level}
								</div>
							</div>
						</div>
					
						<div class="w3-row">	
							<div class="w3-half containerCellRegistry">
								<div class="labelCellRegistry">Nome de Usuário</div>
								<div class="dataCellRegistry">
									${forUser.loginName}
								</div>
							</div>
							<div class="w3-half containerCellRegistry">
								<div class="labelCellRegistry">Senha</div>
								<div class="dataCellRegistry">
									${forUser.loginPassword}
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="w3-row">
					<div class="w3-half containerCellRegistry">
						<div class="labelCellRegistry">Telefones</div>
						<div class="dataCellRegistry">
							<c:forEach var="forUAPList" items="${forUser.getAllPhonesList()}">
								${forUAPList.type} &nbsp; ${forUAPList.countryCode} ${forUAPList.areaCode} ${forUAPList.number}
								<br />
							</c:forEach>
						</div>
					</div>
					<div class="w3-half containerCellRegistry">
						<div class="labelCellRegistry">Emails</div>
						<div class="dataCellRegistry">
							<c:forEach var="forUAEList" items="${forUser.getAllEmailsList()}">
								${forUAEList.getEAddress()}
								<br />
							</c:forEach>
						</div>
					</div>
				</div>
				
				<div class="w3-row">
					<div class="w3-twothird  containerCellRegistry">
						<div class="labelCellRegistry">Endereço</div>
						<div class="dataCellRegistry">
							${forUser.addrPostalCode}, ${forUser.addrFu}
							<br />
							${forUser.addrCity}
							<br />
							${forUser.addrNeighborhood}
							<br />
							${forUser.addrVia}, ${forUser.addrNumber}
							<br />
							${forUser.addrComplement}
						</div>
					</div>
					<div class="w3-third containerCellRegistry">
						<div class="labelCellRegistry">Comandos</div>
						<div class="dataCellRegistry">
							<a href="UserRegistryServlet?action=infodetail&infoDetailUserId=${forUser.getId()}&loggedUserIdParam=${lucdsa.getId()}">
								<img class="btntable" src="resources/imgs/button-info-detail-64-64.png" title="Info Detail" alt="Info Detail" width="32px" />
							</a>
							<a href="PhoneRegistryServlet?action=list&loggedUserIdParam=${lucdsa.getId()}">
								<img class="btntable" src="resources/imgs/button-phone-registry-64-64.png" title="Phone Registry" alt="Phone Registry" width="32px" />
							</a>
							<a href="UserRegistryServlet?action=edit&editUserId=${forUser.getId()}&loggedUserIdParam=${lucdsa.getId()}">
								<img class="btntable" src="resources/imgs/button-edit-64-64.png" title="Edit" alt="Edit" width="32px" />
							</a>
							<a href="UserRegistryServlet?action=exclude&excludeUserId=${forUser.getId()}&loggedUserIdParam=${lucdsa.getId()}">
								<img class="btntable" src="resources/imgs/button-exclude-64-64.png" title="Exclude" alt="Exclude" width="32px" />
							</a>
							<a target="blank" href="
								<c:choose>
									<c:when test='${ forUser.getPictureContentType().trim().equalsIgnoreCase("")
											|| forUser.getPictureContentType().trim().isEmpty()
											|| (forUser.getPictureContentType() == null) }'>
										javascript:alert('There is no file to download!');
									</c:when>
									<c:otherwise>
										UserFileDownloadServlet?action=downlouserpicture&downloadUserId=${forUser.id}&loggedUserIdParam=${lucdsa.getId()}
									</c:otherwise>
								</c:choose>
							"> <img class="btntable" src="resources/imgs/button-file-download-64-64.png" title="Download a Document" alt="Download a Document" width="32px" /> </a>
						</div>
					</div>
				</div>
			</div>
			
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			
		</c:forEach>
		</div><!-- FIM Painel de demonstração dos registros. -->
	
	</div><!-- /FIM Container do conteúdo principal. -->
	
	<!-- Barra inferior de comandos. -->
	<jsp:include page="resources/pages-parts/bottomNavBar.jsp" />
	<p>&nbsp;</p>
	
	<!-- /FIM Início do layout web no navegador propriamente dito. -->
</body>
</html>






<%--
URL.createObjectURL($("#userPictureInput").file[0]).contenttype() , URL.createObjectURL($("#userPictureInput").file[0])

"data:${forUser.pictureContentType};base64,${forUser.getPictureBase64String()}"
${forUser.pictureContentType}
${forUser.getPictureBase64String()}
--%>


<!-- 		function pictureInjection(target, picContentType, picBase64Str) {
			// Ajustar o style para foto ficar bom visual:
			document.getElementById(target).setAttribute('class', 'userPictureShowerOff userPictureShowerOn');
		
			// Carregar a foto em BASE64 para o elemento do DOM:
			document.getElementById(target).setAttribute('style', 'background-image: url("data:'+ picContentType +';base64,'+ picBase64Str +'");');
			} -->





