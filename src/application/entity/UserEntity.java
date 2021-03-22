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
	public static final int LOGINNAME_MIN_LEN = 3;
	public static final int LOGINNAME_MAX_LEN = 21;
	public static final int LOGINPASSWORD_MIN_LEN = 3;
	public static final int LOGINPASSWORD_MAX_LEN = 128;
	
	public static final int EMAIL_MIN_LEN = 5;
	public static final int EMAIL_MAX_LEN = 128;
	/* FURTHER: Entity needs architecture review for 'addresses'. */
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
	public static final int ADDR_VIA_MAX_LEN = 128; // Via = Street, Avenue, Highway, Road, etc.
	public static final int ADDR_NUMBER_MIN_LEN = 1;
	public static final int ADDR_NUMBER_MAX_LEN = 9;
	public static final int ADDR_COMPLEMENT_MIN_LEN = 0; // Business rules: 'addrComplement' may be empty or null.
	public static final int ADDR_COMPLEMENT_MAX_LEN = 128;
	
	/* Private Class' attributes: */
	private long id = 0;
	private String fullName = "";
	private String loginName = "";
	private String loginPassword = "";
	/* FURTHER: To refactor UserEntity <-> UserLevelEntity to OOP approach. */
	private String level = ""; // OOP: it would be a 'object' geting its id attribute as foreign key.
	/* FURTHER: To refactor UserEntity <-> AddressEntity to OOP approach. */
	private String addrPostalCode = "";
	private String addrFu = "";
	private String addrCity = "";
	private String addrNeighborhood = "";
	private String addrVia = "";
	private String addrNumber = "";
	private String addrComplement = "";
	private String pictureBase64; // Type TEXT on database.
	private String pictureContentType; // Type Character Varing on database.
	
	
	
	/* Public Class' attributes or methods: */
	public long getId() { return id; }
	
	
	
	public void setId(long id) { this.id = id; }
	
	
	
	// Overload.
	public void setId(String strId) {
		
		if ((strId != null) && !strId.equalsIgnoreCase("")) {
			
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
	
	
	
	public String getLoginName() { return loginName; }
	
	
	
	public void setLoginName(String loginName) { this.loginName = loginName; }
	
	
	
	public String getLoginPassword() { return loginPassword; }
	
	
	
	public void setLoginPassword(String loginPassword) { this.loginPassword = loginPassword; }
	
	
	
	public String getLevel() { return level; }
	
	
	
	public void setLevel(String level) { this.level = level; }
	
	
	
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
		result = prime * result + ((this.addrCity == null) ? 0 : this.addrCity.hashCode());
		result = prime * result + ((this.addrComplement == null) ? 0 : this.addrComplement.hashCode());
		result = prime * result + ((this.addrFu == null) ? 0 : this.addrFu.hashCode());
		result = prime * result + ((this.addrNeighborhood == null) ? 0 : this.addrNeighborhood.hashCode());
		result = prime * result + ((this.addrNumber == null) ? 0 : this.addrNumber.hashCode());
		result = prime * result + ((this.addrPostalCode == null) ? 0 : this.addrPostalCode.hashCode());
		result = prime * result + ((this.addrVia == null) ? 0 : this.addrVia.hashCode());
		result = prime * result + ((this.fullName == null) ? 0 : this.fullName.hashCode());
		result = prime * result + (int) (this.id ^ (this.id >>> 32));
		result = prime * result + ((this.level == null) ? 0 : this.level.hashCode());
		result = prime * result + ((this.loginName == null) ? 0 : this.loginName.hashCode());
		result = prime * result + ((this.loginPassword == null) ? 0 : this.loginPassword.hashCode());
		result = prime * result + ((this.pictureBase64 == null) ? 0 : this.pictureBase64.hashCode());
		result = prime * result + ((this.pictureContentType == null) ? 0 : this.pictureContentType.hashCode());
		return result;
		
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		UserEntity other = (UserEntity) obj;
		
		if (this.addrCity == null) {
			if (other.addrCity != null) return false;
		}
		else if (!this.addrCity.equals(other.addrCity)) return false;
		
		if (this.addrComplement == null) {
			if (other.addrComplement != null) return false;
		}
		else if (!this.addrComplement.equals(other.addrComplement)) return false;
		
		if (this.addrFu == null) {
			if (other.addrFu != null) return false;
		}
		else if (!this.addrFu.equals(other.addrFu)) return false;
		
		if (this.addrNeighborhood == null) {
			if (other.addrNeighborhood != null) return false;
		}
		else if (!this.addrNeighborhood.equals(other.addrNeighborhood)) return false;
		
		if (this.addrNumber == null) {
			if (other.addrNumber != null) return false;
		}
		else if (!this.addrNumber.equals(other.addrNumber)) return false;
		
		if (this.addrPostalCode == null) {
			if (other.addrPostalCode != null) return false;
		}
		else if (!this.addrPostalCode.equals(other.addrPostalCode)) return false;
		
		if (this.addrVia == null) {
			if (other.addrVia != null) return false;
		}
		else if (!this.addrVia.equals(other.addrVia)) return false;
		
		if (this.fullName == null) {
			if (other.fullName != null) return false;
		}
		else if (!this.fullName.equals(other.fullName)) return false;
		
		if (this.id != other.id) return false;
		
		if (this.level == null) {
			if (other.level != null) return false;
		}
		else if (!this.level.equals(other.level)) return false;
		
		if (this.loginName == null) {
			if (other.loginName != null) return false;
		}
		else if (!this.loginName.equals(other.loginName)) return false;
		
		if (this.loginPassword == null) {
			if (other.loginPassword != null) return false;
		}
		else if (!this.loginPassword.equals(other.loginPassword)) return false;
		
		if (this.pictureBase64 == null) {
			if (other.pictureBase64 != null) return false;
		}
		else if (!this.pictureBase64.equals(other.pictureBase64)) return false;
		
		if (this.pictureContentType == null) {
			if (other.pictureContentType != null) return false;
		}
		else if (!this.pictureContentType.equals(other.pictureContentType)) return false;
		
		return true;
		
	}
	
	
	
	@Override
	public String toString() {
		
		return "UserEntity [id=" + this.id + ", fullName=" + this.fullName + ", loginName=" + this.loginName
				+ ", loginPassword=" + this.loginPassword + ", level=" + this.level + ", addrPostalCode="
				+ this.addrPostalCode + ", addrFu=" + this.addrFu + ", addrCity=" + this.addrCity
				+ ", addrNeighborhood=" + this.addrNeighborhood + ", addrVia=" + this.addrVia + ", addrNumber="
				+ this.addrNumber + ", addrComplement=" + this.addrComplement + ", pictureBase64=" + this.pictureBase64
				+ ", pictureContentType=" + this.pictureContentType + "]";
		
	}
	
}
