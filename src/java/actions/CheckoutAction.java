/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import cart.ShoppingCart;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author csexton
 */
public class CheckoutAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        cart.calculateTotal(session.getServletContext().getInitParameter("deliverySurcharge"));

        session.setAttribute("months",
                Stream.of(Month.values())
                        .map(month -> month.getValue())
                        .toArray());
        session.setAttribute("currentMonth", YearMonth.now().getMonthValue());
        session.setAttribute("currentYear", Year.now().getValue());

        return "checkout";
    }

}
