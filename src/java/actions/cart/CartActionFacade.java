/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.cart;

import actions.GenericActionFacade;
import cart.ShoppingCart;
import entity.Book;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.BookFacade;

/**
 *
 * @author csexton
 */
public class CartActionFacade extends GenericActionFacade implements AutoCloseable {

    private static ThreadLocal<CartActionFacade> instance = new ThreadLocal<>();
    private ShoppingCart cart;
    private HttpServletRequest request;
    private BookFacade bookFacade;

    public CartActionFacade(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.request = request;

        this.cart = (ShoppingCart) super.getSession().getAttribute("cart");

        try {
            Context ctx = new InitialContext();
            this.bookFacade = (BookFacade) ctx.lookup(
                    "java:global/ReadersParadise/BookFacade!session.BookFacade");
        } catch (NamingException ex) {
            System.err.println(ex);
        }
    }

    public static CartActionFacade create(HttpServletRequest request, HttpServletResponse response) {
        CartActionFacade facade = new CartActionFacade(request, response);
        instance.set(facade);
        return facade;
    }

    public static CartActionFacade getCurrentInstance() {
        return instance.get();
    }

    @Override
    public void close() {
        instance.remove();
    }

    public ShoppingCart getShoppingCart() {

        if (cart == null) {
            cart = new ShoppingCart();
            super.getSession().setAttribute("cart", cart);
        }
        return cart;
    }

    public void decrementCartBook() {
        String bookId = request.getParameter("bookId");
        Book book = bookFacade.find(Integer.parseInt(bookId));
        getShoppingCart().decrement(book);
    }

    public void incrementCartBook() {
        String bookId = request.getParameter("bookId");
        Book book = bookFacade.find(Integer.parseInt(bookId));
        getShoppingCart().increment(book);
    }

    public void removeCartBook() {
        String bookId = request.getParameter("bookId");
        Book book = bookFacade.find(Integer.parseInt(bookId));
        getShoppingCart().update(book, "0");
    }

    public void addBookToCart() {
        String bookId = request.getParameter("bookId");
        if (!bookId.isEmpty()) {
            Book book = bookFacade.find(Integer.valueOf(bookId));
            getShoppingCart().addItem(book);
        }
    }

}
