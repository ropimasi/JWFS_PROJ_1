package application.service;


import application.entity.UserEntity;
import application.entity.dto.UserCompactDTO;



public abstract class DTOConversion {
	
	public static UserCompactDTO userCompactDTOFactoryFrom(UserEntity uE) {
		
		// No reflexion. Using pattern Factory.
		UserCompactDTO returnObj = new UserCompactDTO();
		returnObj.setId(uE.getId());
		returnObj.setFullName(uE.getFullName());
		returnObj.setLoginName(uE.getLoginName());
		returnObj.setLevel(uE.getLevel());
		returnObj.setPictureBase64(uE.getPictureBase64String());
		returnObj.setPictureContentType(uE.getPictureContentType());
		return returnObj;
		
	}
	
	
	
	public static void convert(Object oFrom, Object oTo) {
		
		// Using reflexion.
		// Further implementation.
	}
	
}
