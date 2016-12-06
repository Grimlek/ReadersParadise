package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import actions.Action;
import actions.ActionFacade;
import actions.ActionFactory;
import actions.CategoryAction;
import actions.CheckoutAction;
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
        urlPatterns = { "/index" }
)
public class ControllerServlet extends HttpServlet {

    
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        ActionFactory.actions.put("POST/updateCart/add", new AddAction());
        ActionFactory.actions.put("POST/updateCart/increment", new IncrementAction());
        ActionFactory.actions.put("POST/updateCart/decrement", new DecrementAction());
        ActionFactory.actions.put("POST/updateCart/remove", new RemoveAction());
        ActionFactory.actions.put("POST/purchase", new PurchaseAction());
        ActionFactory.actions.put("GET/category", new CategoryAction());
        ActionFactory.actions.put("GET/viewCart", new ViewAction());
        ActionFactory.actions.put("GET/checkout", new CheckoutAction());
        ActionFactory.actions.put("GET/index", new HomeAction());
        ActionFactory.actions.put("GET/null", new HomeAction());
    }
    

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try {
            ActionFacade facade = new ActionFacade(request, response);
            Action action = ActionFactory.getAction(request);
            String view = action.execute(facade);

            if (view.equals(request.getServletPath().substring(1))) {
                request.getRequestDispatcher("/WEB-INF/views/" + view + ".jsp").forward(request, response);
            } else {
                response.sendRedirect(view); 
            }
        } catch (Exception e) {
            throw new ServletException("Executing action failed.", e);
        }
    }
}
