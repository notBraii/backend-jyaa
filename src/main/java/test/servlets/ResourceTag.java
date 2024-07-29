
package test.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import dao.implementations.ResourceTagDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class resourceTags
 */

@WebServlet("/resourceTags")
public class ResourceTag extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResourceTag() {
		super();
		// TODO Auto-generated constructor stub }
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ResourceTagDAO resourceTagDAO = new ResourceTagDAO();
		String action = request.getParameter("action");
		Long resourceTagId = getResourceTagId(request);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Recursos</title><style>");
		// CSS styles
		out.println("table { border-collapse: collapse; width: 100%; }");
		out.println("th, td { border: 1px solid black; padding: 8px; text-align: left; }");
		out.println("h1 { margin-bottom: 20px; }");
		out.println("ul, table { margin-bottom: 20px; }");
		out.println("a, form { margin-right: 10px; }");
		out.println("</style></head><body>");

		if (action == null) {
			showList(out, resourceTagDAO.getAll());
		} else if (action.equals("create")) {
			showCreateForm(out);
		} else if (action.equals("edit") && resourceTagId != null) {
			showEditForm(out, resourceTagId, resourceTagDAO);
		}
		out.println("</body></html>");
	}

	private Long getResourceTagId(HttpServletRequest request) {
		try {
			return Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private void showList(PrintWriter out, List<models.ResourceTag> resourceTags) {
		// TODO Auto-generated method stub
		out.println("<h1>Lista de Recursos</h1>");
		out.println("<table>");
		// Header
		out.println("<tr><th>Nombre</th><th>Acciones</th></tr>");
		// Rows
		for (models.ResourceTag resourceTag : resourceTags) {
			out.println("<tr>");
			out.println("<td>" + resourceTag.getName() + "</td>");

			// Action columns
			out.println("<td>");
			out.println("<a href='?action=edit&id=" + resourceTag.getId() + "'>Editar</a> | ");
			out.println("<form method='POST' style='display:inline;'>");
			out.println("<input type='hidden' name='action' value='delete'>");
			out.println("<input type='hidden' name='id' value='" + resourceTag.getId() + "'>");
			out.println("<input type='submit' value='Eliminar'>");
			out.println("</form>");
			out.println("</td>");

			out.println("</tr>");
		}

		out.println("</table>");
		out.println("<a href='?action=create'>Crear nuevo recurso</a>");
		out.println("</body></html>");

	}

	private void showCreateForm(PrintWriter out) {
		// TODO Auto-generated method stub
		out.println("<h2>Crear nuevo recurso</h2>");
		out.println("<form method='POST'>");
		out.println("<input type='hidden' name='action' value='create'>");
		out.println("<label for='name'>Nombre:</label>");
		out.println("<input type='text' id='name' name='name'><br><br>");
		out.println("<input type='submit' value='Crear'>");
		out.println("</form>");
	}

	private void showEditForm(PrintWriter out, Long resourceTagId, ResourceTagDAO resourceTagDAO) {

		// TODO Auto-generated method stub
		models.ResourceTag resourceTag = resourceTagDAO.get(resourceTagId);
		if (resourceTag != null) {
			out.println("<h2>Editar recurso</h2>");
			out.println("<form method='POST'>");
			out.println("<input type='hidden' name='id' value='" + resourceTagId + "'>");
			out.println("<label for='name'>Nombre:</label>");
			out.println("<input type='text' id='name' name='name' value='" + resourceTag.getName() + "'><br><br>");
			out.println("<input type='submit' value='Actualizar'>");
			out.println("</form>");
		} else {
			out.println("<p>Recurso no encontrado</p>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String action = request.getParameter("action");
		ResourceTagDAO resourceTagDAO = new ResourceTagDAO();
		System.out.println("Llego al post");
		System.out.println("Con la action=" + action);

		if (action.equals("create")) {
			createResourceTag(request, resourceTagDAO);
		} else if (action.equals("edit")) {
			// Update resourceTag
			updateResourceTag(request, resourceTagDAO);
		} else if (action.equals("delete")) {
			// Delete resourceTag
			deleteResourceTag(request, resourceTagDAO);
		}

		response.sendRedirect(request.getContextPath() + "/resourceTags");
	}

	private void createResourceTag(HttpServletRequest request, ResourceTagDAO resourceTagDAO) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		models.ResourceTag resourceTag = new models.ResourceTag(name);
		resourceTagDAO.save(resourceTag);

	}

	private void updateResourceTag(HttpServletRequest request, ResourceTagDAO resourceTagDAO) {
		// TODO Auto-generated method stub
		models.ResourceTag resourceTag = resourceTagDAO.get(getResourceTagId(request));
		System.out.println("El resourceTag a editar es " + resourceTag.getName());

		if (resourceTag != null) {
			resourceTag.setName(request.getParameter("name"));
			resourceTagDAO.update(resourceTag, null);
		}

	}

	private void deleteResourceTag(HttpServletRequest request, ResourceTagDAO resourceTagDAO) {
		// TODO Auto-generated method stub
		Long resourceTagId = Long.parseLong(request.getParameter("id"));
		models.ResourceTag resourceTag = resourceTagDAO.get(resourceTagId);
		System.out.println("El resourceTag a eliminar es" + resourceTag.getName());
		if (resourceTag != null) {
			resourceTagDAO.delete(resourceTag.getId());
		}
	}
}
