/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.order;

import actions.GenericActionFacade;
import cart.ShoppingCart;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Map;
import java.util.stream.Stream;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.OrderManager;
import validate.Validator;

/**
 *
 * @author csexton
 */
public class OrderActionFacade extends GenericActionFacade implements AutoCloseable {
    
    private static ThreadLocal<OrderActionFacade> instance = new ThreadLocal<>();
    private ShoppingCart cart;
    private HttpServletRequest request;
    private OrderManager orderManager;

    public OrderActionFacade(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.request = request;
        this.cart = (ShoppingCart) super.getSession().getAttribute("cart");

        try {
            Context ctx = new InitialContext();
            this.orderManager = (OrderManager) ctx.lookup(
                    "java:global/ReadersParadise/OrderManager!session.OrderManager");
        } catch (NamingException ex) {
            System.err.println(ex);
        }
    }

    public static OrderActionFacade create(HttpServletRequest request, HttpServletResponse response) {
        OrderActionFacade facade = new OrderActionFacade(request, response);
        instance.set(facade);
        return facade;
    }

    public static OrderActionFacade getCurrentInstance() {
        return instance.get();
    }

    @Override
    public void close() {
        instance.remove();
    }

    public void calculateTotal() {
        cart.calculateTotal(
                super.getSession()
                        .getServletContext()
                        .getInitParameter("deliverySurcharge"));
    }

    public boolean isCheckoutValid() {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone-num");
        String ccNum = request.getParameter("cc-num");
        String year = request.getParameter("year");
        String month = request.getParameter("month");

        Map<String, Boolean> validationErrors = Validator.validateForm(
                name, email, phone, address, year, month, ccNum);

        if (validationErrors.get("errorFlag")) {
            super.setFlashScope(validationErrors);
            return false;
        }
        return true;
    }

    public int placeOrder() {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone-num");
        String ccNum = request.getParameter("cc-num");
        YearMonth expirationDate = YearMonth.of(
                Integer.valueOf(request.getParameter("year")),
                Integer.valueOf(request.getParameter("month")));

        int orderId = orderManager.placeOrder(name, email, phone, address,
                expirationDate, ccNum, cart);

        if (orderId == 0) {
            request.setAttribute("validationErrorFlag", true);
        }

        cart = null;
        super.getSession().invalidate();

        return orderId;
    }

    public void setOrderDetails(int orderId) {
        Map orderMap = orderManager.getOrderDetails(orderId);
        request.setAttribute("customer", orderMap.get("customer"));
        request.setAttribute("books", orderMap.get("books"));
        request.setAttribute("orderRecord", orderMap.get("orderRecord"));
        request.setAttribute("orderedBooks", orderMap.get("orderedBooks"));
    }

    public void setYearMonths() {
        getSession().setAttribute("months",
                Stream.of(Month.values())
                        .map(month -> month.getValue())
                        .toArray());
        getSession().setAttribute("currentMonth", YearMonth.now().getMonthValue());
        getSession().setAttribute("currentYear", Year.now().getValue());
    }
}
