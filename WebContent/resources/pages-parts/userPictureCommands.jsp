<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="resources/styles/jwc-style-buttons-lib.css">

<!-- FIXME:  POR ESTE FORM ESPECÍFICO DA PICTURE EM UM  JQUARY ! -->

	<div class="s-r-center-container">
	
		<!-- input type="text" id="userPictureName" name="userPictureName" placeholder="User Picture Path..."
		style="margin:5px auto; height:12px; width:auto;" readonly -->
		
		<!-- button type="button" class="small solution" id="submitPicture" name="submitPicture" tabindex="20"
		onClick="onButtonClicked();">Select a pic</button -->
		
		<button type="button" class="small progress" id="removePicture" name="removePicture" tabindex="21"
		onClick="javascript:window.location.href='#';">Upload the pic</button>
		
		<br>

		<button type="button" class="small disapproval" id="removePicture" name="removePicture" tabindex="23"
		onClick="javascript:window.location.href='#';">Clear selected pic</button>
		
		<button type="button" class="small setback" id="removePicture" name="removePicture" tabindex="24"
		onClick="javascript:window.location.href='#';">Remove both pic</button>
	</div>
	
