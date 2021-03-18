package application.entity;

public class PhoneEntity {

	/* Constants containing standards values from Business-and-Technical-Rules: */
	public static final int COUNTRYCODE_MIN_LEN = 1;
	public static final int COUNTRYCODE_MAX_LEN = 3;
	public static final int AREACODE_MIN_LEN = 1;
	public static final int AREACODE_MAX_LEN = 4;
	public static final int NUMBER_MIN_LEN = 3;
	public static final int NUMBER_MAX_LEN = 10;
	//public static final int TYPE_MIN_LEN = 3;
	//public static final int TYPE_MAX_LEN = 8;

	/* Private Class' attributes: */
	private long id = 0; // Primary key on DB;
	private String countryCode = "";
	private String areaCode = "";
	private String number = "";
	private PhoneTypeEnum type;
	private long userId = 0; // Foreign Key on DB.

	/* Public Class' attributes: */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setId(String strId) {
		if ((!strId.equalsIgnoreCase("")) && (!strId.equalsIgnoreCase(" ")) && (strId != null)) {
			try {
				this.id = Long.parseLong(strId);
			} catch (Exception e1) {
				System.out.println("Error casting 'string' to 'long' !");
				e1.printStackTrace();
			}
		} else {
			this.id = 0;
		}
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public PhoneTypeEnum getType() {
		return type;
	}

	public void setType(PhoneTypeEnum pte) {
		this.type = pte;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setUserId(String strUserId) {
		if ((!strUserId.equalsIgnoreCase("")) && (!strUserId.equalsIgnoreCase(" ")) && (strUserId != null)) {

			try {
				this.userId = Long.parseLong(strUserId);
			} catch (Exception e1) {
				System.out.println("Error casting 'string' to 'long' !");
				e1.printStackTrace();
			}
		} else {
			this.userId = 0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areaCode == null) ? 0 : areaCode.hashCode());
		result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhoneEntity other = (PhoneEntity) obj;
		if (areaCode == null) {
			if (other.areaCode != null)
				return false;
		} else if (!areaCode.equals(other.areaCode))
			return false;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (id != other.id)
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PhoneEntity [id=" + id + ", countryCode=" + countryCode + ", areaCode=" + areaCode + ", number="
				+ number + ", type=" + type + ", userId=" + userId + "]";
	}

}
