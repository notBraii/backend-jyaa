package test.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Magnitude;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import dao.implementations.IngredientDAO;
import dao.implementations.ResourceTagDAO;

/**
 * Servlet implementation class Ingredient
 */
@WebServlet("/ingredients")
public class Ingredient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Ingredient() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		IngredientDAO ingredientDAO = new IngredientDAO();

		String action = request.getParameter("action");
		Long ingredientId = getIngredientId(request);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Ingredientes</title><style>");
		// CSS styles
		out.println("table { border-collapse: collapse; width: 100%; }");
		out.println("th, td { border: 1px solid black; padding: 8px; text-align: left; }");
		out.println("h1 { margin-bottom: 20px; }");
		out.println("ul, table { margin-bottom: 20px; }");
		out.println("a, form { margin-right: 10px; }");
		out.println("</style></head><body>");

		
		if (action == null) {
			showList(out, ingredientDAO.getAll());
		} else if (action.equals("create")) {
			ResourceTagDAO resourceDAO = new ResourceTagDAO();
			showCreateForm(out, resourceDAO.getAll());
		} else if (ingredientId != null) {
			ResourceTagDAO resourceDAO = new ResourceTagDAO();
			showEditForm(out, ingredientId, ingredientDAO, resourceDAO.getAll());
		} else {
			response.sendRedirect(request.getContextPath() + "/ingredients");
		}

		out.println("</body></html>");
	}

	private Long getIngredientId(HttpServletRequest request) {
		try {
			return Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private void showList(PrintWriter out, List<models.Ingredient> ingredients) {
		// TODO Auto-generated method stub
		out.println("<h1>Lista de ingredientes</h1>");
		out.println("<table>");
		// Header
		out.println("<tr><th>Recurso</th><th>Cantidad</th><th>Acciones</th></tr>");
		// Rows
		for (models.Ingredient ingredient : ingredients) {
			out.println("<tr>");
			out.println("<td>" + ingredient.getResource().getName() + "</td>");
			out.println("<td>" + ingredient.getMagnitude().getValue() + " " + ingredient.getMagnitude().getUnit()
					+ "</td>");

			// Action columns
			out.println("<td>");
			out.println("<a href='?action=edit&id=" + ingredient.getId() + "'>Editar</a> | ");
			out.println("<form method='POST' style='display:inline;'>");
			out.println("<input type='hidden' name='action' value='delete'>");
			out.println("<input type='hidden' name='id' value='" + ingredient.getId() + "'>");
			out.println("<input type='submit' value='Eliminar'>");
			out.println("</form>");
			out.println("</td>");

			out.println("</tr>");
		}
		out.println("</table>");
		out.println("<a href='?action=create'>Crear nuevo ingrediente</a>");
		out.println("</body></html>");
	}

	private void showCreateForm(PrintWriter out, List<models.ResourceTag> resources) {
		out.println("<h2>Crear nuevo ingrediente</h2>");
		out.println("<form method='POST'>");
		out.println("<input type='hidden' name='action' value='create'>");
		out.println("<label for='resource'>Recurso:</label>");
		out.println("<select id='resource' name='resourceId'>");
		for (models.ResourceTag resource : resources) {
			out.println("<option value='" + resource.getId() + "'>" + resource.getName() + "</option>");
		}
		out.println("</select><br><br>");
		out.println("<label for='quantity'>Cantidad:</label>");
		out.println("<input type='number' id='quantity' name='quantity'><br><br>");
		out.println("<label for='unit'>Unidad:</label>");
		out.println("<input type='text' id='unit' name='unit'><br><br>");
		out.println("<input type='submit' value='Crear'>");
		out.println("</form>");
	}

	private void showEditForm(PrintWriter out, Long ingredientId, IngredientDAO ingredientDAO,
			List<models.ResourceTag> resources) {
		models.Ingredient ingredient = ingredientDAO.get(ingredientId);
		if (ingredient != null) {
			out.println("<h2>Editar ingrediente</h2>");
			out.println("<form method='POST'>");
			out.println("<input type='hidden' name='action' value='update'>");
			out.println("<input type='hidden' name='id' value='" + ingredientId + "'>");
			out.println("<label for='resource'>Recurso:</label>");
			out.println("<select id='resource' name='resourceId'>");
			for (models.ResourceTag resource : resources) {
				String selected = resource.getId().equals(ingredient.getResource().getId()) ? "selected" : "";
				out.println("<option value='" + resource.getId() + "' " + selected + ">" + resource.getName()
						+ "</option>");
			}
			out.println("</select><br><br>");
			out.println("<label for='quantity'>Cantidad:</label>");
			out.println("<input type='number' id='quantity' name='quantity' value='"
					+ ingredient.getMagnitude().getValue() + "'><br><br>");
			out.println("<label for='unit'>Unidad:</label>");
			out.println("<input type='text' id='unit' name='unit' value='" + ingredient.getMagnitude().getUnit()
					+ "'><br><br>");
			out.println("<input type='submit' value='Actualizar'>");
			out.println("</form>");
		} else {
			out.println("<p>Ingrediente no encontrado</p>");
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
		IngredientDAO ingredientDAO = new IngredientDAO();
		ResourceTagDAO resourceDAO = new ResourceTagDAO();

		if (action.equals("create")) {
			createIngredient(request, ingredientDAO, resourceDAO);
		} else if (action.equals("edit")) {
			updateIngredient(request, ingredientDAO, resourceDAO);
		} else if (action.equals("delete")) {
			deleteIngredient(request, ingredientDAO);
		}

		response.sendRedirect(request.getContextPath() + "/ingredients");
	}

	private void createIngredient(HttpServletRequest request, IngredientDAO ingredientDAO, ResourceTagDAO resourceDAO) {
		Long resourceId = Long.parseLong(request.getParameter("resourceId"));
		models.ResourceTag resource = resourceDAO.get(resourceId);
		double quantity = Double.parseDouble(request.getParameter("quantity"));
		String unit = request.getParameter("unit");
		models.Ingredient ingredient = new models.Ingredient(resource, new Magnitude(quantity, unit));
		ingredientDAO.save(ingredient);
	}

	private void updateIngredient(HttpServletRequest request, IngredientDAO ingredientDAO, ResourceTagDAO resourceDAO) {
		Long ingredientId = getIngredientId(request);
		if (ingredientId != null) {
			models.Ingredient ingredient = ingredientDAO.get(ingredientId);
			if (ingredient != null) {
				Long resourceId = Long.parseLong(request.getParameter("resourceId"));
				models.ResourceTag resource = resourceDAO.get(resourceId);
				double quantity = Double.parseDouble(request.getParameter("quantity"));
				String unit = request.getParameter("unit");
				ingredient.setResource(resource);
				ingredient.setMagnitude(new Magnitude(quantity, unit));
				ingredientDAO.update(ingredient, null);
			}
		}
	}

	private void deleteIngredient(HttpServletRequest request, IngredientDAO ingredientDAO) {
		Long ingredientId = getIngredientId(request);
		if (ingredientId != null) {
			ingredientDAO.delete(ingredientId);
		}
	}

}
