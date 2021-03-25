package application.service;


import application.entity.UserEntity;
import application.entity.dto.UserCompactDTO;
import application.entity.dto.UserFullDTO;



public final class DTOConversion {
	
	public static UserCompactDTO userCompactDTOFactoryFrom(UserEntity uE) {
		
		// Using pattern Factory.
		UserCompactDTO returnObj = new UserCompactDTO();
		returnObj.setId(uE.getId());
		returnObj.setFullName(uE.getFullName());
		returnObj.setLoginName(uE.getLoginName());
		returnObj.setLevel(uE.getLevel());
		returnObj.setPictureBase64(uE.getPictureBase64String());
		returnObj.setPictureContentType(uE.getPictureContentType());
		return returnObj;
		
	}
	
	
	
	public static UserFullDTO userFullDTOFactoryFrom(UserEntity uE) {
		
		// Using pattern Factory.
		UserFullDTO returnObj = new UserFullDTO();
		returnObj.setId(uE.getId());
		returnObj.setFullName(uE.getFullName());
		returnObj.setLoginName(uE.getLoginName());
		returnObj.setLevel(uE.getLevel());
		returnObj.setPictureBase64(uE.getPictureBase64String());
		returnObj.setPictureContentType(uE.getPictureContentType());
		
		return returnObj;
		
	}
	
}
