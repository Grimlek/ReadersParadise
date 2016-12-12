/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import actions.Action;
import actions.ActionFactory;
import actions.cart.AddAction;
import actions.cart.CartActionFacade;
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
        name = "CartControllerServlet",
        urlPatterns = {
            "/cart/*"
        }
)
public class CartControllerServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        // POST Requests
        ActionFactory.actions.put("POST/cart/add", new AddAction());
        ActionFactory.actions.put("POST/cart/increment", new IncrementAction());
        ActionFactory.actions.put("POST/cart/decrement", new DecrementAction());
        ActionFactory.actions.put("POST/cart/remove", new RemoveAction());

        // GET Requests
        ActionFactory.actions.put("GET/cart/view", new ViewAction());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try (CartActionFacade facade = CartActionFacade.create(request, response)) {
            Action action = ActionFactory.getAction(request);
            String view = action.execute(facade);
            
            if (view.equals(request.getServletPath().substring(1))) {
                request.getRequestDispatcher("/WEB-INF/views/" + view + ".jsp").forward(request, response);
            } else if (!view.equals("NO_REDIRECT")) {
                response.sendRedirect(request.getContextPath() + "/" + view);
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (NullPointerException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            throw new ServletException("Executing action failed.", e);
        }
    }

}
