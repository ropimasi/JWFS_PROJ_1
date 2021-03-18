package application.entity;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.Part;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import application.dao.JdbcEmailDao;
import application.dao.JdbcPhoneDao;




public class UserEntity {
	
	/* Constants containing standards values from Business-and-Technical-Rules: */
	public static final int FULLNAME_MIN_LEN = 3;
	public static final int FULLNAME_MAX_LEN = 64;
	public static final int USERNAME_MIN_LEN = 3;
	public static final int USERNAME_MAX_LEN = 21;
	public static final int PASSWORD_MIN_LEN = 3;
	public static final int PASSWORD_MAX_LEN = 128;
	public static final int EMAIL_MIN_LEN = 5;
	public static final int EMAIL_MAX_LEN = 128;
	/* Further improvement: Entity needs architecture review for 'addresses'. */
	public static final int ADDR_POSTAL_CODE_MIN_LEN = 8; // Business rules: only to Brasil, then 8 characters.
	public static final int ADDR_POSTAL_CODE_MAX_LEN = 8; // Business rules: only to Brasil, then 8 characters.
	public static final int ADDR_FU_CODEL_MIN_LEN = 2; // Business tules: FU = Federal Unit. Only to Brasil, then 2
														// characters.
	public static final int ADDR_FU_MAX_LEN = 2; // Business tules: FU = Federal Unit. Only to Brasil, then 2
													// characters.
	public static final int ADDR_CITY_MIN_LEN = 2;
	public static final int ADDR_CITY_MAX_LEN = 128;
	public static final int ADDR_NEIGHBORHOOD_MIN_LEN = 2;
	public static final int ADDR_NEIGHBORHOOD_MAX_LEN = 96;
	public static final int ADDR_VIA_MIN_LEN = 2; // Via = Street, Avenue, Highway, Road, etc.
	public static final int ADDR_VIA_MAX_LEN = 96; // Via = Street, Avenue, Highway, Road, etc.
	public static final int ADDR_NUMBER_MIN_LEN = 1;
	public static final int ADDR_NUMBER_MAX_LEN = 9;
	public static final int ADDR_COMPLEMENT_MIN_LEN = 0; // Business rules: 'addrComplement' may be empty or null.
	public static final int ADDR_COMPLEMENT_MAX_LEN = 128;
	
	/* Private Class' attributes: */
	private long id = 0;
	private String fullName = "";
	private String userName = "";
	private String password = "";
	private String userLevel = "";
	private String addrPostalCode = "";
	private String addrFu = "";
	private String addrCity = "";
	private String addrNeighborhood = "";
	private String addrVia = "";
	private String addrNumber = "";
	private String addrComplement = "";
	private String pictureBase64;
	private String pictureContentType;
	
	
	
	/* Public Class' attributes or methods: */
	public long getId() { return id; }
	
	
	
	public void setId(long id) { this.id = id; }
	
	
	
	// Overload.
	public void setId(String strId) {
		
		if ( (strId != null) && !strId.equalsIgnoreCase("") ) {
			
			try {
				this.id = Long.parseLong(strId);
			}
			catch (Exception e1) {
				System.out.println("Erro transformando string para long-int.");
				e1.printStackTrace();
			}
			
		}
		else {
			/* If Id was not defined (Empty or Null) the 0 value is adopted as default by
			 * the 'view layer' meaning this object was not stored in project's persistence,
			 * yet, that is, this object is a 'add new' object. */
			this.id = -1;
		}
		
	}
	
	
	
	public String getFullName() { return fullName; }
	
	
	
	public void setFullName(String fullName) { this.fullName = fullName; }
	
	
	
	public String getUserName() { return userName; }
	
	
	
	public void setUserName(String userName) { this.userName = userName; }
	
	
	
	public String getPassword() { return password; }
	
	
	
	public void setPassword(String password) { this.password = password; }
	
	
	
	public String getUserLevel() { return userLevel; }
	
	
	
	public void setUserLevel(String userLevel) { this.userLevel = userLevel; }
	
	
	
	public String getAddrPostalCode() { return addrPostalCode; }
	
	
	
	public void setAddrPostalCode(String addrPostalCode) { this.addrPostalCode = addrPostalCode; }
	
	
	
	public String getAddrFu() { return addrFu; }
	
	
	
	public void setAddrFu(String addrFu) { this.addrFu = addrFu; }
	
	
	
	public String getAddrCity() { return addrCity; }
	
	
	
	public void setAddrCity(String addrCity) { this.addrCity = addrCity; }
	
	
	
	public String getAddrNeighborhood() { return addrNeighborhood; }
	
	
	
	public void setAddrNeighborhood(String addrNeighborhood) { this.addrNeighborhood = addrNeighborhood; }
	
	
	
	public String getAddrVia() { return addrVia; }
	
	
	
	public void setAddrVia(String addrVia) { this.addrVia = addrVia; }
	
	
	
	public String getAddrNumber() { return addrNumber; }
	
	
	
	public void setAddrNumber(String addrNumber) { this.addrNumber = addrNumber; }
	
	
	
	public String getAddrComplement() { return addrComplement; }
	
	
	
	public void setAddrComplement(String addrComplement) { this.addrComplement = addrComplement; }
	
	
	
	public String getPictureBase64String() { return this.pictureBase64; }
	
	
	
	public void setPictureBase64(Part userPicturePart) {
		
		if (userPicturePart != null) {
			InputStream inputStream;
			
			try {
				inputStream = userPicturePart.getInputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				int readByte;
				
				while ((readByte = inputStream.read()) != -1) { baos.write(readByte); }
				
				this.pictureBase64 = Base64.encodeBase64String(baos.toByteArray());
				baos.close();
				
				setPictureContentType(userPicturePart.getContentType());
			}
			catch (IOException excep) {
				System.out.println("Error:"
						+ "\n\tinputStream = userPicturePart.getInputStream();"
						+ "\n\tor"
						+ "\n\twhile ((readByte = inputStream.read()) != -1) { baos.write(readByte); }");
				excep.printStackTrace();
			}
			
		}
		
	}
	
	
	
	// Overload.
	public void setPictureBase64(String userPictureBase64String) {
		this.pictureBase64 = userPictureBase64String;
		
		/* String tmpStr = userPictureStringBase64.split(";base64,")[0];
		 * tmpStr = tmpStr.split("data:")[0];
		 * 
		 * setPictureContentType(tmpStr); */
	}
	
	
	
	public String getPictureContentType() { return this.pictureContentType; }
	
	
	
	public void setPictureContentType(String pictureContentType) { this.pictureContentType = pictureContentType; }
	
	
	
	public List<PhoneEntity> getAllPhonesList() { return new JdbcPhoneDao().listByUser(this); }
	
	
	
	public List<EmailEntity> getAllEmailsList() { return new JdbcEmailDao().listByUser(this); }
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addrCity == null) ? 0 : addrCity.hashCode());
		result = prime * result + ((addrComplement == null) ? 0 : addrComplement.hashCode());
		result = prime * result + ((addrFu == null) ? 0 : addrFu.hashCode());
		result = prime * result + ((addrNeighborhood == null) ? 0 : addrNeighborhood.hashCode());
		result = prime * result + ((addrNumber == null) ? 0 : addrNumber.hashCode());
		result = prime * result + ((addrPostalCode == null) ? 0 : addrPostalCode.hashCode());
		result = prime * result + ((addrVia == null) ? 0 : addrVia.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userLevel == null) ? 0 : userLevel.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
}
