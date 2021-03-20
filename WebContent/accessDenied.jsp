<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="resources/styles/formatacoesLogin.css">
	<title>ACESSO NEGADO - JAVA WEB COMPLETO MÓDULO 20</title>
</head>

<body>
	<%@ page info="Página para logar ao cadastro de usuário do JavaWebCompleto-Módulo-20."%>
	<%@ page errorPage="default-error-page.jsp"%>



	<script type="text/javascript">
		function goBack() {
			window.history.back();
		}
	</script>



	<div class="container-main">
			<h3>ACESSO NEGADO!</h3>
			<h5>Usuário e/ou senha incorretos.</h5>
			<div class="s-r-center-container" align=center>
				<h6>[${sessionScope.loggedUser}]</h6>
				<button type="submit" id="submit" name="submit" tabindex="1"
						autofocus="autofocus" onClick="javascript:goBack();">
					Voltar ↩
				</button>
			</div>
		</form>
	</div>

</body>
</html>
