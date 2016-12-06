/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import cart.ShoppingCart;
import java.time.YearMonth;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.OrderManager;
import validate.Validator;

/**
 *
 * @author csexton
 */
public class PurchaseAction implements Action {
    
    @Inject
    private OrderManager orderManager;
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        
        if (cart == null) {
            request.setAttribute("cartError", true);
            return "checkout";
        }

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone-num");
        String ccNum = request.getParameter("cc-num");
        YearMonth expirationDate = YearMonth.of(
                Integer.valueOf(request.getParameter("year")),
                Integer.valueOf(request.getParameter("month")));

        boolean validationError = Validator.validateForm(
                name, email, phone, address, expirationDate, ccNum, request);

        if (validationError) {
            request.setAttribute("validationErrorFlag", true);
            return "checkout";
        }

        int orderId = orderManager.placeOrder(name, email, phone, address,
                expirationDate, ccNum, cart);

        if (orderId == 0) {
            request.setAttribute("validationErrorFlag", true);
            return "checkout";
        }

        cart = null;
        session.invalidate();
        Map orderMap = orderManager.getOrderDetails(orderId);
        request.setAttribute("customer", orderMap.get("customer"));
        request.setAttribute("books", orderMap.get("books"));
        request.setAttribute("orderRecord", orderMap.get("orderRecord"));
        request.setAttribute("orderedBooks", orderMap.get("orderedBooks"));
        return "confirmation";
    }

}
