/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import actions.Action;
import actions.ActionFactory;
import actions.order.CheckoutAction;
import actions.order.ConfirmationAction;
import actions.order.OrderActionFacade;
import actions.order.PurchaseAction;
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
        name = "OrderControllerServlet",
        urlPatterns = {
            "/purchase",
            "/checkout",
            "/confirmation"
        }
)
public class OrderControllerServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        // POST Requests
        ActionFactory.actions.put("POST/purchase", new PurchaseAction());

        // GET Requests
        ActionFactory.actions.put("GET/confirmation", new ConfirmationAction());
        ActionFactory.actions.put("GET/checkout", new CheckoutAction());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try (OrderActionFacade facade = OrderActionFacade.create(request, response)) {

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
