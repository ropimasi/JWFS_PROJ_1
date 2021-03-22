package application.entity;


public class EmailEntity {
	
	/* Constants containing standards values from Business-and-Technical-Rules: */
	public static final int EADDRESS_MIN_LEN = 5;
	public static final int EADDRESS_MAX_LEN = 98;
	
	/* FUTHER: To analyze whether to dismiss 'id' and get eAddress as primary key, unique. */
	private Long id = 0L;
	private String eAddress = "";
	/* FURTHER: To refactor EmailEntity <-> UserEntity to OOP approach. */
	private long userId = 0L; // OOP: it would be a 'object' geting its id attribute as foreign key.
	
	
	
	/* Constructor. */
	public EmailEntity() {}
	
	
	
	public Long getId() { return this.id; }
	
	
	
	public void setId(Long id) { this.id = id; }
	
	
	
	// Overload.
	public void setId(String strId) {
		
		System.out.println();
		
		if ((!strId.equalsIgnoreCase("")) && (!strId.equalsIgnoreCase(" ")) && (strId != null)) {
			
			try {
				this.id = Long.parseLong(strId);
			}
			catch (Exception e1) {
				System.out.println("Error casting 'string' to 'long' !");
				e1.printStackTrace();
			}
			
		}
		else {
			this.id = 0L;
		}
		
	}
	
	
	
	public String getEAddress() { return this.eAddress; }
	
	
	
	public void setEAddress(String eAddress) { this.eAddress = eAddress; }
	
	
	
	public Long getUserId() { return this.userId; }
	
	
	
	public void setUserId(Long userId) { this.userId = userId; }
	
	
	
	// Overload.
	public void setUserId(String strUserId) {
		
		if ((!strUserId.equalsIgnoreCase("")) && (!strUserId.equalsIgnoreCase(" ")) && (strUserId != null)) {
			
			try {
				this.userId = Long.parseLong(strUserId);
			}
			catch (Exception e1) {
				System.out.println("Error casting 'string' to 'long' !");
				e1.printStackTrace();
			}
			
		}
		else {
			this.userId = 0L;
		}
		
	}
	
	
	
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.eAddress == null) ? 0 : this.eAddress.hashCode());
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + (int) (this.userId ^ (this.userId >>> 32));
		return result;
		
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		EmailEntity other = (EmailEntity) obj;
		
		if (this.eAddress == null) {
			if (other.eAddress != null) return false;
		}
		else if (!this.eAddress.equals(other.eAddress)) return false;
		
		if (this.id == null) {
			if (other.id != null) return false;
		}
		else if (!this.id.equals(other.id)) return false;
		
		if (this.userId != other.userId) return false;
		return true;
		
	}
	
	
	
	@Override
	public String toString() {
		
		return "EmailEntity [id=" + this.id + ", eAddress=" + this.eAddress + ", userId=" + this.userId + "]";
		
	}
	
}
