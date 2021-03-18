package application.servelet;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.codec.binary.Base64;
import application.dao.JdbcUserDao;
import application.entity.dto.UserCompactDTO;
import application.service.ObjectTypeConversion;




@WebServlet("/UserFileDownloadServlet")
public class UserFileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	public UserFileDownloadServlet() {
		super();
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* Init RequestDispatcher before loop or try blocks, init once, use several times. */
		RequestDispatcher reqDisp = null;
		
		/* Make sure all attributes are reset each new instance of Servlet. */
		request.setAttribute(" xxx ", null);
		// null -> app always must retrieve a free list from UserDao before send to the frontend.
		

		
		/* ### INÍCIO - TÉCNICA DE ASR-URI ### */
		HttpSession loggedSession = request.getSession();
		
		/* loggedUserCompactDTOFromSessionAttrib */
		UserCompactDTO lucdfsa = (UserCompactDTO) loggedSession.getAttribute("lucdsa");
		
		/* loggedUsesrIdFromParam */
		long luifp = ((request.getParameter("loggedUserIdParam") != null)
				&& (!request.getParameter("loggedUserIdParam").isEmpty()))
						? Long.parseLong(request.getParameter("loggedUserIdParam")) : -1L;
		
		if ((lucdfsa == null) || (luifp != lucdfsa.getId())) {
			request.setAttribute("lastAction", null);
			request.setAttribute("usersList", null);
			reqDisp = request.getRequestDispatcher("resources/error-pages/asr-uri-error.jsp");
			reqDisp.forward(request, response);
		}
		/* ### FIM - TÉCNICA DE ASR-URI ### */
		
		
		
		switch (request.getParameter("action")) {
			case "downlouserpicture":
				
				request.setAttribute("lastAction", "downlouserpicture");
				
				long dowloadUserIdFromParam = ((request.getParameter("downloadUserId") != null)
						&& (!request.getParameter("downloadUserId").isEmpty()))
								? Long.parseLong(request.getParameter("downloadUserId")) : -1L;
				
				/* TODO: Further implementation: validating some kind of sub or super hierarquical permission to
				 * download the selected user. */
				
				UserCompactDTO downloadUserDTO = ObjectTypeConversion
						.userCompactDTOFactoryFrom(new JdbcUserDao().seekId(dowloadUserIdFromParam));
				
				/* Didático: Set the header of the response, properly. */
				response.setHeader("Content-Disposition", "attachment;filename=a-document-"
						+ luifp + "-" + dowloadUserIdFromParam + "."
						+ downloadUserDTO.getPictureContentType().split("\\/")[1]);
				
				/* Didático: To convert the base64 from the database to byte[]array. */
				byte[] userPictureBase64 = Base64.decodeBase64(downloadUserDTO.getPictureBase64String());
				
				/* Didático: To place the bytes from the above array in a InputSream object to be processed. */
				InputStream bais = new ByteArrayInputStream(userPictureBase64);
				
				/* Didático: Get the OutputStream instance from de 'response'. */
				OutputStream baos = response.getOutputStream();
				
				/* Didático: Do reading from the InputStream above byte-by-byte and write it in OutputStream. */
				int readByte = 0;
				byte[] bytes = new byte[1024];
				
				while ((readByte = bais.read(bytes)) != -1) { baos.write(bytes, 0, readByte); }
				
				baos.flush();
				baos.close();
				
				break; // FIM DE "download-a-document".
				
			default: // By default whatever else value from 'request.getParameter("action")' we must do nothing.
				request.setAttribute("lastAction", "default");
				break;
		}
		
		// After whatever case from 'request.getParameter("action")' done, we must show/close the page.
	}
}
