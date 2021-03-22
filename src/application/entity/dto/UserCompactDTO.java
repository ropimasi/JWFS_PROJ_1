package application.entity.dto;


import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.Part;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;



public class UserCompactDTO {
	
	/* Private Class' attributes: */
	private long id = 0;
	private String fullName = "";
	private String loginName = "";
	private String level = "";
	private String pictureBase64 = "";
	private String pictureContentType = "";
	
	
	
	/* Public Class' attributes or methods: */
	public long getId() { return id; }
	
	
	
	public void setId(long id) { this.id = id; }
	
	
	
	// Overload.
	public void setId(String strId) {
		
		if ((strId != null) && !strId.equalsIgnoreCase("")) {
			System.out.println();
			
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
			this.id = 0;
		}
		
	}
	
	
	
	public String getFullName() { return fullName; }
	
	
	
	public void setFullName(String fullName) { this.fullName = fullName; }
	
	
	
	public String getLoginName() { return loginName; }
	
	
	
	public void setLoginName(String loginName) { this.loginName = loginName; }
	
	
	
	public String getLevel() { return level; }
	
	
	
	public void setLevel(String level) { this.level = level; }
	
	
	
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
	
}
