package controller;

import actions.Action;
import actions.ActionFacade;
import actions.ActionFactory;
import actions.CategoryAction;
import actions.HomeAction;
import actions.LocaleAction;
import actions.ProductAction;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author csexton
 */
@WebServlet(
        name = "ControllerServlet",
        urlPatterns = {
            "",
            "/index",
            "/category",
            "/product",
            "/locale"
        }
)
public class ControllerServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        
        // GET Requests
        ActionFactory.actions.put("GET/locale", new LocaleAction());
        ActionFactory.actions.put("GET/product", new ProductAction());
        ActionFactory.actions.put("GET/category", new CategoryAction());
        ActionFactory.actions.put("GET/index", new HomeAction());
        ActionFactory.actions.put("GET/", new HomeAction());
        ActionFactory.actions.put("GET", new HomeAction());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try (ActionFacade facade = ActionFacade.create(request, response)) {
            Action action = ActionFactory.getAction(request);
            String view = action.execute(facade);
            
            if (request.getServletPath().equals("") || view.equals(request.getServletPath().substring(1))) {
                request.getRequestDispatcher("/WEB-INF/views/" + view + ".jsp").forward(request, response);
            } 
            else if (!view.equals("NO_REDIRECT")) {
                response.sendRedirect(request.getContextPath() + "/" + view);
            }
            else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (NullPointerException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            throw new ServletException("Executing action failed.", e);
        }
    }
}
