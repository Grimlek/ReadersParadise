/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.cart;

import actions.Action;
import cart.ShoppingCart;
import entity.Book;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.BookFacade;

/**
 *
 * @author csexton
 */
public class DecrementAction implements Action {

    @Inject
    private BookFacade bookFacade;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        String bookId = request.getParameter("bookId");
        Book book = bookFacade.find(Integer.parseInt(bookId));
        cart.decrement(book);
        return "cart";
    }

}
