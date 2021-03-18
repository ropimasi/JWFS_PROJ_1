package application.servelet;


import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import application.dao.JdbcProductDao;
import application.entity.ProductEntity;
import application.entity.UserEntity;
import application.service.ValidationService;
import application.service.ValidationResultSet;



@WebServlet("/ProductRegistryServlet")
public class ProductRegistryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	// Constructor.
	public ProductRegistryServlet() {
		super();
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Logging [ProductRegistryServlet:doPost]");
		
		/* Init RequestDispatcher before loop or try blocks, init once, use several. */
		RequestDispatcher reqDisp = null;
		/* Make sure all attributes are reset each new instance of Servlet. */
		request.setAttribute("productList", null);
		// null -> app always must retrieve a free list from productDao before send to the frontend.
		request.setAttribute("soughtProductEdit", null);
		// null -> app not in edit mode, otherwise, contents a product (entity) to edit.
		
		/* ### INÍCIO - TÉCNICA DE ASR-URI ### */
		HttpSession loggedSession = request.getSession();
		UserEntity loggedUser = (UserEntity) loggedSession.getAttribute("loggedUser");
		long loggedUsesrIdFromParam =
				(request.getParameter("userId") != null) ? Long.parseLong(request.getParameter("userId")) : 0L;
		
		if ((loggedUser == null) || (loggedUsesrIdFromParam != loggedUser.getId())) {
			request.setAttribute("lastAction", null);
			request.setAttribute("usersList", null);
			reqDisp = request.getRequestDispatcher("resources/error-pages/asr-uri-error.jsp");
			reqDisp.forward(request, response);
		}
		/* ### FIM - TÉCNICA DE ASR-URI ### */
		
		/* Dependencies: Let a temp objects instantiated fot several uses below. */
		JdbcProductDao productDAO = new JdbcProductDao();
		
		/* Select and execute the procedures, in fact. */
		switch (request.getParameter("action")) {
			case "save":
				request.setAttribute("lastAction", "save");
				System.out.println(
						"Logging [ProductRegistryServlet:doPost:Action=" + request.getParameter("action") + "]");
				
				ProductEntity productToSave = new ProductEntity();
				productToSave.setId(request.getParameter("id"));
				productToSave.setName(request.getParameter("name"));
				productToSave.setPurchasePrice(new BigDecimal(request.getParameter("purchasePrice")));
				productToSave.setSalePrice(new BigDecimal(request.getParameter("salePrice")));
				productToSave.setStockQtty(Float.parseFloat(request.getParameter("stockQtty")));
				
				/* Of course these 2 statements could be cast in 1 statement,
				 * but I need userVRS later. */
				ValidationResultSet productVRS = ValidationService.validateProductFull(productToSave);
				request.setAttribute("productVRS", productVRS);
				
				if (productVRS.getVerdict()) productDAO.save(productToSave);
				else request.setAttribute("soughtProductEdit", productToSave);
				break; // FIM DE SAVE.
				
			case "infodetail":
				request.setAttribute("lastAction", "infodetail");
				System.out.println(
						"Logging [ProductRegistryServlet:doPost:Action=" + request.getParameter("action") + "]");
				break;
			
			case "edit":
				request.setAttribute("lastAction", "edit");
				System.out.println(
						"Logging [ProductRegistryServlet:doPost:Action" + request.getParameter("action") + "]");
				request.setAttribute("soughtProductEdit",
						productDAO.seekId(Long.parseLong(request.getParameter("id"))));
				break;
			
			case "exclude":
				request.setAttribute("lastAction", "exclude");
				System.out.println(
						"Logging [ProductRegistryServlet:doPost:Action=" + request.getParameter("action") + "]");
				productDAO.exclude(Long.parseLong(request.getParameter("id")));
				break;
			
			default: // By default whatever else value from 'request.getParameter("action")' we must do nothing.
				request.setAttribute("lastAction", "default");
				System.out.println(
						"Logging [ProductRegistryServlet:doPost:Action=" + request.getParameter("action") + "]");
				break;
		}
		
		// After whatever case from 'request.getParameter("action")' done we must do to List.
		request.setAttribute("productList", productDAO.list());
		reqDisp = request.getRequestDispatcher("/productRegistry.jsp");
		reqDisp.forward(request, response);
	}
}
