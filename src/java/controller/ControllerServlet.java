package controller;

import actions.Action;
import actions.ActionFacade;
import actions.ActionFactory;
import actions.CategoryAction;
import actions.CheckoutAction;
import actions.ConfirmationAction;
import actions.HomeAction;
import actions.PurchaseAction;
import actions.cart.AddAction;
import actions.cart.DecrementAction;
import actions.cart.IncrementAction;
import actions.cart.RemoveAction;
import actions.cart.ViewAction;
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
            "/cart/*",
            "/purchase",
            "/checkout",
            "/confirmation"
        }
)
public class ControllerServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        ActionFactory.actions.put("POST/cart/add", new AddAction());
        ActionFactory.actions.put("POST/cart/increment", new IncrementAction());
        ActionFactory.actions.put("POST/cart/decrement", new DecrementAction());
        ActionFactory.actions.put("POST/cart/remove", new RemoveAction());
        ActionFactory.actions.put("GET/cart/view", new ViewAction());
        ActionFactory.actions.put("POST/purchase", new PurchaseAction());
        ActionFactory.actions.put("GET/confirmation", new ConfirmationAction());
        ActionFactory.actions.put("GET/category", new CategoryAction());
        ActionFactory.actions.put("GET/checkout", new CheckoutAction());
        ActionFactory.actions.put("GET/index", new HomeAction());
        ActionFactory.actions.put("GET/", new HomeAction());
        ActionFactory.actions.put("GET", new HomeAction());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try (ActionFacade facade = ActionFacade.create(request)) {

            Action action = ActionFactory.getAction(request);
            String view = action.execute(facade);
            
            if (request.getServletPath().equals("") || view.equals(request.getServletPath().substring(1))) {
                request.getRequestDispatcher("/WEB-INF/views/" + view + ".jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/" + view);
            }

        } catch (NullPointerException e) {
            // No action was found, return 404
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            throw new ServletException("Executing action failed.", e);
        }
    }
}
