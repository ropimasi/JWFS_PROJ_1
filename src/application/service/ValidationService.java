package application.service;


import java.math.BigDecimal;
import application.dao.JdbcUserDao;
import application.entity.EmailEntity;
import application.entity.PhoneEntity;
import application.entity.ProductEntity;
import application.entity.UserEntity;
import application.entity.UserLevelEntity;



public final class ValidationService {
	
	/* BELOW: PARTIAL VALIDATION METHODS FOR USER'S PROPERTIES. */
	/* userName validation. */
	public static ValidationResult validateUserName(String userName, Long requesterId) {
		
		UserEntity tmpSoughtUser = new UserEntity();
		JdbcUserDao tmpUserDAO = new JdbcUserDao();
		ValidationResult returnVR = new ValidationResult();
		
		if (userName == null) {
			/* According technical rules: null user is not allowed: INVALID. */
			returnVR.setCode(-1);
			returnVR.setType("Error");
			returnVR.setDescription("The 'userName' property is null, impratical: INVALID !");
		}
		else if (userName.isEmpty()) {
			/* According technical rules: EMPTY user is not allowed: INVALID. */
			returnVR.setCode(-2);
			returnVR.setType("Error");
			returnVR.setDescription("The 'userName' property is EMPTY, impratical: INVALID !");
		}
		else if (userName.length() < UserEntity.USERNAME_MIN_LEN) {
			/* According technical rules: ... */
			/* ALWAYS SEE Entities Classes FOR KNOW THEIRS STANDARD CONSTANT VALUES. */
			returnVR.setCode(-3);
			returnVR.setType("Error");
			returnVR.setDescription("The 'userName' property is too short, against rules: INVALID !");
		}
		else if (userName.length() > UserEntity.USERNAME_MAX_LEN) {
			/* According technical rules: ... */
			/* ALWAYS SEE Entities Classes FOR KNOW THEIRS STANDARD CONSTANT VALUES. */
			returnVR.setCode(-4);
			returnVR.setType("Error");
			returnVR.setDescription("The 'userName' property is too long, against rules: INVALID !");
		}
		else {
			/* IMPORTANTE VERIFICAR PRE EXISTÊNCIA DESTE NOME NA BASE DE DADOS. */
			tmpSoughtUser = tmpUserDAO.seekUserName(userName);
			
			if (tmpSoughtUser == null) {
				/* According business rules, there was NO FOUND a user with
				 * that userName registered: VALID. */
				returnVR.setCode(0);
				returnVR.setType("Information");
				returnVR.setDescription("The 'userName' property [" + userName
						+ "] being registred is not registred currently, so: Valid.");
			}
			else if (tmpSoughtUser.getId() == requesterId) {
				/* According business rules, there was FOUND ONE user with
				 * that userName registered, and it is the requester itself: VALID. */
				returnVR.setCode(1);
				returnVR.setType("Alert");
				returnVR.setDescription("The 'userName' property being registred [" + userName
						+ "] is already registred under 'id' [" + tmpSoughtUser.getId()
						+ "] which is owned by the requester itself is trying to register it, therefore: Valid!");
			}
			else {
				returnVR.setCode(-5);
				returnVR.setType("Error");
				returnVR.setDescription("The 'userName' property being registred [" + userName
						+ "] is already registred, under 'id' [" + tmpSoughtUser.getId()
						+ "] which is NOT owned by the requester is trying to register it, both are NOT the"
						+ " same, consequently: INVALID !");
			}
			
		}
		
		return returnVR;
		
	}
	
	
	
	/* password validation. */
	public static ValidationResult validatePassword(String password) {
		
		/* Business Rules password must has 3 character at least, and has 21 character at most.
		 * ALTHOUGH THOSE VALUES CAN BE CHANGED. ALWAYS SEE Entities Classes FOR KNOW THEIRS STANDARD CONSTANT
		 * VALUES. */
		
		ValidationResult returnVR = new ValidationResult();
		
		if (password == null) {
			returnVR.setCode(-1);
			returnVR.setType("Error");
			returnVR.setDescription("The 'password' property is null, impratical: INVALID !");
		}
		else if (password.isEmpty()) {
			returnVR.setCode(-2);
			returnVR.setType("Error");
			returnVR.setDescription("The 'password' property is empty, impratical: INVALID !");
		}
		else if (password.length() < UserEntity.PASSWORD_MIN_LEN) {
			returnVR.setCode(-3);
			returnVR.setType("Error");
			returnVR.setDescription("The 'password' property is too short, against rules: INVALID !");
		}
		else if (password.length() > UserEntity.PASSWORD_MAX_LEN) {
			returnVR.setCode(-4);
			returnVR.setType("Error");
			returnVR.setDescription("The 'password' property is too long, against rules: INVALID !");
		}
		else {
			returnVR.setCode(0);
			returnVR.setType("Information");
			returnVR.setDescription("The 'password' property has a right size: VALID !");
		}
		
		return returnVR;
		
	}
	
	/* fullName */
	// public static ValidationResult validateFullName(String fullName) {}
	
	
	
	/* INTEGRAL VALIDATION METHOD FOR USER'S PROPERTIES. GHATHERING THE ABOVE ONES. */
	public static ValidationResultSet validateUserFull(UserEntity user) {
		
		ValidationResult tmpVR = new ValidationResult();
		ValidationResultSet returnVRS = new ValidationResultSet();
		
		// First, test validating 'userName'.
		/* Further TODO: refatorar otimizando para 1 parãmetro conforme regra de negocio
		 * do produto (o que eu espero de uma validacao de user?) */
		tmpVR = validateUserName(user.getUserName(), user.getId());
		// Add to the List one ValidationResult about 'userName'.
		returnVRS.add(tmpVR);
		
		// Now, second, test validating about 'password'.
		tmpVR = validatePassword(user.getPassword()); // SymmCrypSamp.undoIt().
		// Add to the List one ValidationResult about 'password'.
		returnVRS.add(tmpVR);
		
		// Now, third, fourth, test validating about 'fullName'.
		// tmpVR = validat....
		// returnVRS.add(tmpVR);
		
		// So we have the 'UserEntity' fully verified/validated.
		
		// Send the LIST containing 2 or more VR as return.
		return returnVRS;
		
	}
	
	
	
	/* BELOW: PARTIAL VALIDATION METHODS FOR EMAIL'S PROPERTIES. */
	/* email validation. */
	public static ValidationResult validateEmail(String email) {
		
		ValidationResult returnVR = new ValidationResult();
		
		/* ### TODO Em construcao falta codificar demais condições que validam o email @. */
		
		if (email == null) {
			returnVR.setCode(-1);
			returnVR.setType("Error");
			returnVR.setDescription("The 'email' property is null, impratical: INVALID !");
		}
		else if (email.isEmpty()) {
			returnVR.setCode(-2);
			returnVR.setType("Error");
			returnVR.setDescription("The 'email' property is empty, impratical: INVALID !");
		}
		else if (email.length() < EmailEntity.EADDRESS_MIN_LEN) {
			returnVR.setCode(-3);
			returnVR.setType("Error");
			returnVR.setDescription("The 'email' property is too short, against rules: INVALID !");
		}
		else if (email.length() > EmailEntity.EADDRESS_MAX_LEN) {
			returnVR.setCode(-4);
			returnVR.setType("Error");
			returnVR.setDescription("The 'email' property is too long, against rules: INVALID !");
		}
		else {
			returnVR.setCode(0);
			returnVR.setType("Information");
			returnVR.setDescription("The 'email' property has a right size: VALID !");
		}
		
		return returnVR;
		
	}
	
	// TODO parei aki 20210126 mult-email0registry back end ok, agora iniciar ajuste no front end.
	
	
	
	/* INTEGRAL VALIDATION METHOD FOR EMAIL'S PROPERTIES. GHATHERING THE ABOVE ONES. */
	public static ValidationResultSet validateEmailFull(EmailEntity email) {
		
		ValidationResult tmpVR = new ValidationResult();
		ValidationResultSet returnVRS = new ValidationResultSet();
		
		// First, test validating 'countryCode'.
		tmpVR = validateEmail(email.getEAddress());
		// Add to the List one ValidationResult about 'countryCode'.
		returnVRS.add(tmpVR);
		
		// Now, second, test validating about 'email'.
		// tmpVR = validat....
		// returnVRS.add(tmpVR);
		
		// So we have the 'EmailEntity' fully verified/validated.
		
		// Send the LIST containing 1 or more VR as return.
		return returnVRS;
		
	}
	
	
	
	/* BELOW: PARTIAL VALIDATION METHODS FOR PHONE'S PROPERTIES. */
	/* countryCode validation */
	public static ValidationResult validateCountryCode(String countryCode) {
		
		/* Business Rules countryCode must has 2 character at least, and has 3 character at most.
		 * ALTHOUGH THOSE VALUES CAN BE CHANGED. ALWAYS SEE Entities Classes FOR KNOW THEIRS STANDARD CONSTANT
		 * VALUES. */
		
		ValidationResult returnVR = new ValidationResult();
		
		if (countryCode == null) {
			returnVR.setCode(-1);
			returnVR.setType("Error");
			returnVR.setDescription("The 'countryCode' property is null, impratical: INVALID !");
		}
		else if (countryCode.isEmpty()) {
			returnVR.setCode(-2);
			returnVR.setType("Error");
			returnVR.setDescription("The 'countryCode' property is empty, impratical: INVALID !");
		}
		else if (countryCode.length() < PhoneEntity.COUNTRYCODE_MIN_LEN) {
			returnVR.setCode(-3);
			returnVR.setType("Error");
			returnVR.setDescription("The 'countryCode' property is too short, against rules: INVALID !");
		}
		else if (countryCode.length() > PhoneEntity.COUNTRYCODE_MAX_LEN) {
			returnVR.setCode(-4);
			returnVR.setType("Error");
			returnVR.setDescription("The 'countryCode' property is too long, against rules: INVALID !");
		}
		else {
			returnVR.setCode(0);
			returnVR.setType("Information");
			returnVR.setDescription("The 'countryCode' property has a right size: VALID !");
		}
		
		return returnVR;
		
	}
	
	
	
	/* areaCode validation. */
	public static ValidationResult validateAreaCode(String areaCode) {
		
		/* Business Rules areaCode must has 2 character at least, and has 3 character at most.
		 * ALTHOUGH THOSE VALUES CAN BE CHANGED. ALWAYS SEE Entities Classes FOR KNOW THEIRS STANDARD CONSTANT
		 * VALUES. */
		
		ValidationResult returnVR = new ValidationResult();
		
		if (areaCode == null) {
			returnVR.setCode(-1);
			returnVR.setType("Error");
			returnVR.setDescription("The 'areaCode' property is null, impratical: INVALID !");
		}
		else if (areaCode.isEmpty()) {
			returnVR.setCode(-2);
			returnVR.setType("Error");
			returnVR.setDescription("The 'areaCode' property is empty, impratical: INVALID !");
		}
		else if (areaCode.length() < PhoneEntity.AREACODE_MIN_LEN) {
			returnVR.setCode(-3);
			returnVR.setType("Error");
			returnVR.setDescription("The 'areaCode' property is too short, against rules: INVALID !");
		}
		else if (areaCode.length() > PhoneEntity.AREACODE_MAX_LEN) {
			returnVR.setCode(-4);
			returnVR.setType("Error");
			returnVR.setDescription("The 'areaCode' property is too long, against rules: INVALID !");
		}
		else {
			returnVR.setCode(0);
			returnVR.setType("Information");
			returnVR.setDescription("The 'areaCode' property has a right size: VALID !");
		}
		
		return returnVR;
		
	}
	
	
	
	/* phoneNumber validation. */
	public static ValidationResult validatePhoneNumber(String phoneNumber) {
		
		/* Business Rules areaCode must has 3 character at least, and has 10 character at most.
		 * ALTHOUGH THOSE VALUES CAN BE CHANGED. ALWAYS SEE Entities Classes FOR KNOW THEIRS STANDARD CONSTANT
		 * VALUES. */
		
		ValidationResult returnVR = new ValidationResult();
		
		if (phoneNumber == null) {
			returnVR.setCode(-1);
			returnVR.setType("Error");
			returnVR.setDescription("The 'phoneNumber' property is null, impratical: INVALID !");
		}
		else if (phoneNumber.isEmpty()) {
			returnVR.setCode(-2);
			returnVR.setType("Error");
			returnVR.setDescription("The 'phoneNumber' property is empty, impratical: INVALID !");
		}
		else if (phoneNumber.length() < PhoneEntity.NUMBER_MIN_LEN) {
			returnVR.setCode(-3);
			returnVR.setType("Error");
			returnVR.setDescription("The 'phoneNumber' property is too short, against rules: INVALID !");
		}
		else if (phoneNumber.length() > PhoneEntity.NUMBER_MAX_LEN) {
			returnVR.setCode(-4);
			returnVR.setType("Error");
			returnVR.setDescription("The 'phoneNumber' property is too long, against rules: INVALID !");
		}
		else {
			returnVR.setCode(0);
			returnVR.setType("Information");
			returnVR.setDescription("The 'phoneNumber' property has a right size: VALID !");
		}
		
		return returnVR;
		
	}
	
	/* type */
	/* SOON FUTURE THIS ATTRIBUTE WILL BE A FOREIGN KEY USING LOOKUP-SELECT-BOX.
	 * THEREFORE THE VALIDATION WILL BE BUILT IN DIFFERENT WAY FROM OTHERS
	 * ATTRIBUTES SUCH validatePhoneNumber. */
	// public static ValidationResult validateType(String type) {}
	
	/* userId */
	// public static ValidationResult validateUser(String user) {}
	/* SOON FUTURE THIS ATTRIBUTE WILL BE A FOREIGN KEY USING LOOKUP-SELECT-BOX.
	 * THEREFORE THE VALIDATION WILL BE BUILT IN DIFFERENT WAY FROM OTHERS
	 * ATTRIBUTES SUCH validatePhoneNumber. */
	
	
	
	/* INTEGRAL VALIDATION METHOD FOR PHONE'S PROPERTIES. GHATHERING THE ABOVE ONES. */
	public static ValidationResultSet validatePhoneFull(PhoneEntity phone) {
		
		ValidationResult tmpVR = new ValidationResult();
		ValidationResultSet returnVRS = new ValidationResultSet();
		
		// First, test validating 'countryCode'.
		tmpVR = validateCountryCode(phone.getCountryCode());
		// Add to the List one ValidationResult about 'countryCode'.
		returnVRS.add(tmpVR);
		
		// Now, second, test validating about 'getAreaCode'.
		tmpVR = validateAreaCode(phone.getAreaCode());
		// Add to the List one ValidationResult about 'getAreaCode'.
		returnVRS.add(tmpVR);
		
		// Now, third, test validating about 'number'.
		tmpVR = validatePhoneNumber(phone.getNumber());
		// Add to the List one ValidationResult about 'number'.
		returnVRS.add(tmpVR);
		
		// Now, fourth, test validating about 'type'.
		// tmpVR = validat....
		// returnVRS.add(tmpVR);
		
		// Now, fiveth, test validating about 'user'.
		// tmpVR = validat....
		// returnVRS.add(tmpVR);
		
		// So we have the 'PhoneEntity' fully verified/validated.
		
		// Send the LIST containing 2 or more VR as return.
		return returnVRS;
		
	}
	
	
	
	/* BELOW: PARTIAL VALIDATION METHODS FOR PRODUCT'S PROPERTIES. */
	/* productName validation. */
	public static ValidationResult validateProductName(String productName) {
		
		/* Business Rules areaCode must has 3 character at least, and has 128 character at most.
		 * ALTHOUGH THOSE VALUES CAN BE CHANGED. ALWAYS SEE Entities Classes FOR KNOW THEIRS STANDARD CONSTANT
		 * VALUES. */
		
		ValidationResult returnVR = new ValidationResult();
		
		if (productName == null) {
			returnVR.setCode(-1);
			returnVR.setType("Error");
			returnVR.setDescription("The 'productName' property is null, impratical: INVALID !");
		}
		else if (productName.isEmpty()) {
			returnVR.setCode(-2);
			returnVR.setType("Error");
			returnVR.setDescription("The 'productName' property is empty, impratical: INVALID !");
		}
		else if (productName.length() < ProductEntity.NAME_MIN_LEN) {
			returnVR.setCode(-3);
			returnVR.setType("Error");
			returnVR.setDescription("The 'productName' property is too short, against rules: INVALID !");
		}
		else if (productName.length() > ProductEntity.NAME_MAX_LEN) {
			returnVR.setCode(-4);
			returnVR.setType("Error");
			returnVR.setDescription("The 'productName' property is too long, against rules: INVALID !");
		}
		else {
			returnVR.setCode(0);
			returnVR.setType("Information");
			returnVR.setDescription("The 'productName' property has a right size: VALID !");
		}
		
		return returnVR;
		
	}
	
	
	
	/* purchasePrice OR salePrice validation, whatever field in 1 method, one each time. */
	public static ValidationResult validateProductPrices(BigDecimal aPrice) {
		
		/* Business Rules: both prices must have 0.00 value at least, and have 9999999999.99 value at most.
		 * ALTHOUGH THOSE VALUES CAN BE CHANGED. ALWAYS SEE Entities Classes FOR KNOW THEIRS STANDARD CONSTANT
		 * VALUES. */
		
		ValidationResult returnVR = new ValidationResult();
		
		if (aPrice == null) {
			returnVR.setCode(-1);
			returnVR.setType("Error");
			returnVR.setDescription("The given 'price' to be validated is null, impratical: INVALID !");
		}
		else if ((aPrice.compareTo(ProductEntity.PURCHASEPRICE_MIN_VAL) == -1)
				|| (aPrice.compareTo(ProductEntity.SALEPRICE_MIN_VAL) == -1)) {
					returnVR.setCode(-2);
					returnVR.setType("Error");
					returnVR.setDescription(
							"The given 'price' to be validated is less than " + ProductEntity.PURCHASEPRICE_MIN_VAL
									+ " or " + ProductEntity.SALEPRICE_MIN_VAL + ", impratical: INVALID !");
				}
		else if ((aPrice.compareTo(ProductEntity.PURCHASEPRICE_MAX_VAL) == 1)
				|| (aPrice.compareTo(ProductEntity.SALEPRICE_MAX_VAL) == 1)) {
					returnVR.setCode(-3);
					returnVR.setType("Error");
					returnVR.setDescription(
							"The given 'price' to be validated is grater than " + ProductEntity.PURCHASEPRICE_MAX_VAL
									+ " or " + ProductEntity.SALEPRICE_MAX_VAL + ", against rules: INVALID !");
				}
		else {
			returnVR.setCode(0);
			returnVR.setType("Information");
			returnVR.setDescription(
					"The given 'price' to be validated in inside the range of acceptable values: VALID !");
		}
		
		return returnVR;
		
	}
	
	
	
	/* Overload. */
	/* purchasePrice AND salePrice validation, both fields in the same time. ### THIS METHOD IS BETTER ### */
	public static ValidationResult validateProductPrices(BigDecimal purchasePrice, BigDecimal salePrice) {
		
		/* Business Rules: both prices must have 0.00 value at least, and have 9999999999.99 value at most.
		 * ALTHOUGH THOSE VALUES CAN BE CHANGED. ALWAYS SEE Entities Classes FOR KNOW THEIRS STANDARD CONSTANT
		 * VALUES. */
		
		ValidationResult returnVR = new ValidationResult();
		
		if (purchasePrice == null) {
			returnVR.setCode(-1);
			returnVR.setType("Error");
			returnVR.setDescription("The 'purchasePrice' property is null, impratical: INVALID !");
		}
		else if (salePrice == null) {
			returnVR.setCode(-1);
			returnVR.setType("Error");
			returnVR.setDescription("The 'salePrice' property is null, impratical: INVALID !");
		}
		else if (purchasePrice.compareTo(ProductEntity.PURCHASEPRICE_MIN_VAL) == -1) {
			returnVR.setCode(-2);
			returnVR.setType("Error");
			returnVR.setDescription("The 'purchasePrice' property is less than " + ProductEntity.PURCHASEPRICE_MIN_VAL
					+ ", impratical: INVALID !");
		}
		else if (salePrice.compareTo(ProductEntity.SALEPRICE_MIN_VAL) == -1) {
			returnVR.setCode(-2);
			returnVR.setType("Error");
			returnVR.setDescription("The 'salePrice' property is less than " + ProductEntity.SALEPRICE_MIN_VAL
					+ ", impratical: INVALID !");
		}
		else if (purchasePrice.compareTo(ProductEntity.PURCHASEPRICE_MAX_VAL) == 1) {
			returnVR.setCode(-3);
			returnVR.setType("Error");
			returnVR.setDescription("The 'purchasePrice' property is grater than " + ProductEntity.PURCHASEPRICE_MAX_VAL
					+ ", against rules: INVALID !");
		}
		else if (salePrice.compareTo(ProductEntity.SALEPRICE_MAX_VAL) == 1) {
			returnVR.setCode(-3);
			returnVR.setType("Error");
			returnVR.setDescription("The 'salePrice' property is grater than " + ProductEntity.SALEPRICE_MAX_VAL
					+ ", against rules: INVALID !");
		}
		else {
			returnVR.setCode(0);
			returnVR.setType("Information");
			returnVR.setDescription(
					"The 'purchasePrice' and 'salePrice' properties are inside the range ov acceptable values: VALID !");
		}
		
		return returnVR;
		
	}
	
	
	
	/* stockQtty validation. */
	public static ValidationResult validateProductStockQtty(float stockQtty) {
		
		/* Business Rules: stockQtty must have 0.00 value at least, and have 9999999999.99 value at most.
		 * ALTHOUGH THOSE VALUES CAN BE CHANGED. ALWAYS SEE Entities Classes FOR KNOW THEIRS STANDARD CONSTANT
		 * VALUES. */
		
		ValidationResult returnVR = new ValidationResult();
		
		/* BUG NOTE: NOT DONE YET: foat == null == NaN isNull isNaN ? */
		
		/* if (isNaN(stockQtty)) {
		 * returnVR.setCode(-1);
		 * returnVR.setType("Error");
		 * returnVR.setDescription("The 'stockQtty' property is NOT a number, impratical: INVALID !");
		 * } else */ if (stockQtty < ProductEntity.STOCKQTTY_MIN_VAL) {
			returnVR.setCode(-2);
			returnVR.setType("Error");
			returnVR.setDescription("The 'stockQtty' property is less than " + ProductEntity.STOCKQTTY_MIN_VAL
					+ ", impratical: INVALID !");
		}
		else if (stockQtty > ProductEntity.STOCKQTTY_MAX_VAL) {
			returnVR.setCode(-3);
			returnVR.setType("Error");
			returnVR.setDescription("The 'stockQtty' property is grater than " + ProductEntity.STOCKQTTY_MAX_VAL
					+ ", against rules: INVALID !");
		}
		else {
			returnVR.setCode(0);
			returnVR.setType("Information");
			returnVR.setDescription("The 'stockQtty' properties have right size: VALID !");
		}
		
		return returnVR;
		
	}
	
	
	
	/* INTEGRAL VALIDATION METHOD FOR PRODUCT'S PROPERTIES. GHATHERING THE ABOVE ONES. */
	public static ValidationResultSet validateProductFull(ProductEntity product) {
		
		ValidationResult tmpVR = new ValidationResult();
		ValidationResultSet returnVRS = new ValidationResultSet();
		
		// First, test validating 'name'.
		tmpVR = validateProductName(product.getName());
		// Add to the List one ValidationResult about 'name'.
		returnVRS.add(tmpVR);
		
		// Now, second, test validating about 'purchasePrice'.
		tmpVR = validateProductPrices(product.getPurchasePrice(), product.getSalePrice());
		// Add to the List one ValidationResult about 'purchasePrice'.
		returnVRS.add(tmpVR);
		
		// Now, third, test validating about 'number'.
		tmpVR = validateProductStockQtty(product.getStockQtty());
		// Add to the List one ValidationResult about 'number'.
		returnVRS.add(tmpVR);
		
		// So we have the 'ProductEntity' fully verified/validated.
		
		// Send the LIST containing 2 or more VR as return.
		return returnVRS;
		
	}
	
	
	
	/* BELOW: PARTIAL VALIDATION METHODS FOR USERLEVEL'S PROPERTIES. */
	/* userLevelName validation. */
	public static ValidationResult validateUserLevelName(String userLevelName) {
		
		/* Business Rules areaCode must has 3 character at least, and has 128 character at most.
		 * ALTHOUGH THOSE VALUES CAN BE CHANGED. ALWAYS SEE Entities Classes FOR KNOW THEIRS STANDARD CONSTANT
		 * VALUES. */
		
		ValidationResult returnVR = new ValidationResult();
		
		if (userLevelName == null) {
			returnVR.setCode(-1);
			returnVR.setType("Error");
			returnVR.setDescription("The 'userLevelName' property is null, impratical: INVALID !");
		}
		else if (userLevelName.isEmpty()) {
			returnVR.setCode(-2);
			returnVR.setType("Error");
			returnVR.setDescription("The 'userLevelName' property is empty, impratical: INVALID !");
		}
		else if (userLevelName.length() < UserLevelEntity.NAME_MIN_LEN) {
			returnVR.setCode(-3);
			returnVR.setType("Error");
			returnVR.setDescription("The 'userLevelName' property is too short, against rules: INVALID !");
		}
		else if (userLevelName.length() > UserLevelEntity.NAME_MAX_LEN) {
			returnVR.setCode(-4);
			returnVR.setType("Error");
			returnVR.setDescription("The 'userLevelName' property is too long, against rules: INVALID !");
		}
		else {
			returnVR.setCode(0);
			returnVR.setType("Information");
			returnVR.setDescription("The 'productName' property has a right size: VALID !");
		}
		
		return returnVR;
		
	}
	
	
	
	/* INTEGRAL VALIDATION METHOD FOR USERLEVEL'S PROPERTIES. GHATHERING THE ABOVE ONES. */
	public static ValidationResultSet validateUserLevelFull(String userLevel) {
		
		ValidationResult tmpVR = new ValidationResult();
		ValidationResultSet returnVRS = new ValidationResultSet();
		
		// First, test validating 'name'.
		tmpVR = validateUserLevelName(userLevel);
		// Add to the List one ValidationResult about 'name'.
		returnVRS.add(tmpVR);
		
		// So we have the 'ProductEntity' fully verified/validated.
		
		// Send the LIST containing 2 or more VR as return.
		return returnVRS;
		
	}
	
}
