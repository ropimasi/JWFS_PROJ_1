package application.servelet;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import application.dao.JdbcPhoneDao;
import application.entity.PhoneEntity;
import application.entity.PhoneTypeEnum;
import application.entity.dto.UserCompactDTO;
import application.service.ValidationResultSet;
import application.service.ValidationService;




@WebServlet("/PhoneRegistryServlet")
public class PhoneRegistryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	// Constructor.
	public PhoneRegistryServlet() {
		super();
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* Init RequestDispatcher before loop or try blocks, init once, use several. */
		RequestDispatcher reqDisp = null;
		/* Make sure all attributes are reset each new instance of Servlet. */
		request.setAttribute("phonesList", null);
		// null -> app aways must retrive a fres list from UserDao before send to the frontend.
		request.setAttribute("savingEditingPhone", null);
		// null -> app not in edit mode, otherwiase, id user to edit.
		
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
		
		/* Dependencies: Let a temp objects instantiated fot several uses below. */
		JdbcPhoneDao phoneDao = new JdbcPhoneDao();
		
		/* Select and execute the procedures, in fact. */
		switch (request.getParameter("action")) {
			case "save":
				request.setAttribute("lastAction", "save");
				
				PhoneEntity phoneToSave = new PhoneEntity();
				phoneToSave.setId(request.getParameter("id"));
				phoneToSave.setCountryCode(request.getParameter("countryCode"));
				phoneToSave.setAreaCode(request.getParameter("areaCode"));
				phoneToSave.setNumber(request.getParameter("number"));
				phoneToSave.setType(PhoneTypeEnum.valueOf(request.getParameter("type")));
				phoneToSave.setUserId(request.getParameter("userId"));
				
				ValidationResultSet phoneVRS = new ValidationResultSet();
				phoneVRS = ValidationService.validatePhoneFull(phoneToSave);
				request.setAttribute("phoneVRS", phoneVRS);
				
				if (phoneVRS.getVerdict())
					phoneDao.save(phoneToSave);
				else
					request.setAttribute("savingEditingPhone", phoneToSave);
				break; // FIM DE SAVE.
				
			case "infodetail":
				request.setAttribute("lastAction", "infodetail");
				request.setAttribute("infoDetailPhone",
						phoneDao.seekId(Long.parseLong(request.getParameter("infoDetailPhoneId"))));
				break;
			
			case "edit":
				request.setAttribute("lastAction", "edit");
				request.setAttribute("savingEditingPhone",
						phoneDao.seekId(Long.parseLong(request.getParameter("editPhoneId"))));
				break;
			
			case "exclude":
				request.setAttribute("lastAction", "exclude");
				phoneDao.exclude(Long.parseLong(request.getParameter("excludePhoneId")));
				break;
			
			default:
				request.setAttribute("lastAction", "default");
				break;
		}
		
		// After whatever case from 'request.getParameter("action")' done, we must do to List.
		request.setAttribute("phonesList", phoneDao.list());
		reqDisp = request.getRequestDispatcher("/phoneRegistry.jsp?loggedUserIdParam=" + lucdfsa.getId());
		reqDisp.forward(request, response);
	}
}
