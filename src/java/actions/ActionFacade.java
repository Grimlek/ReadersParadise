/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import cart.ShoppingCart;
import entity.Book;
import entity.Category;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import session.BookFacade;
import session.CategoryFacade;
import session.OrderManager;
import validate.Validator;

/**
 *
 * @author csexton
 */
public class ActionFacade implements AutoCloseable {

    private static ThreadLocal<ActionFacade> instance = new ThreadLocal<>();

    private HttpServletRequest request;
    private HttpSession session;
    
    private CategoryFacade categoryFacade;
    private BookFacade bookFacade;
    private OrderManager orderManager;

    private ShoppingCart cart;

    
    public ActionFacade(HttpServletRequest request) {
        this.request = request;
        this.session = request.getSession();
        
        try {
            Context ctx = new InitialContext();
            this.categoryFacade = (CategoryFacade) ctx.lookup(
                    "java:global/ReadersParadise/CategoryFacade!session.CategoryFacade");
            this.bookFacade = (BookFacade) ctx.lookup(
                    "java:global/ReadersParadise/BookFacade!session.BookFacade");
            this.orderManager = (OrderManager) ctx.lookup(
                    "java:global/ReadersParadise/OrderManager!session.OrderManager");
        } catch (NamingException ex) {
            System.err.println(ex);
        }
    }

    
    public static ActionFacade create(HttpServletRequest request) {
        ActionFacade facade = new ActionFacade(request);
        instance.set(facade);
        return facade;
    }

    
    public static ActionFacade getCurrentInstance() {
        return instance.get();
    }

    
    @Override
    public void close() {
        instance.remove();
    }

    
    public void setFeaturedCategories() {
        session.setAttribute("featuredCategories", categoryFacade.findRandom(5));
    }

    
    public void setSelectedCategory() {
        String categoryName = request.getParameter("category");

        Category selectedCategory = categoryFacade.findByName(categoryName);

        if (selectedCategory == null) {
            selectedCategory = categoryFacade.find(1);
        }
        session.setAttribute("selectedCategory", selectedCategory);

        Collection<Book> categoryBooks = selectedCategory.getBookCollection();
        session.setAttribute("categoryBooks", categoryBooks);
    }

    
    public void setAllCategories() {
        request.setAttribute("categories", categoryFacade.findAll());
    }
    
    
    public String getReffererUri() {
        String pathTrace = request.getHeader("referer");
        return pathTrace.split("ReadersParadise/")[1];
    }

    
    public void addBookToCart() {
        String bookId = request.getParameter("bookId");
        if (!bookId.isEmpty()) {
            Book book = bookFacade.find(Integer.valueOf(bookId));
            getShoppingCart().addItem(book);
        }
    }

    
    public void calculateTotal() {
        cart.calculateTotal(session.getServletContext().getInitParameter("deliverySurcharge"));
    }

    
    public ShoppingCart getShoppingCart() {
        cart = (ShoppingCart) session.getAttribute("cart");

        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    
    public void setYearMonths() {
        session.setAttribute("months",
                Stream.of(Month.values())
                        .map(month -> month.getValue())
                        .toArray());
        session.setAttribute("currentMonth", YearMonth.now().getMonthValue());
        session.setAttribute("currentYear", Year.now().getValue());
    }

    
    public boolean isCheckoutValid() {
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
        return orderId;
    }

    
    public void setOrderDetails(int orderId) {
        cart = null;
        session.invalidate();
        Map orderMap = orderManager.getOrderDetails(orderId);
        request.setAttribute("customer", orderMap.get("customer"));
        request.setAttribute("books", orderMap.get("books"));
        request.setAttribute("orderRecord", orderMap.get("orderRecord"));
        request.setAttribute("orderedBooks", orderMap.get("orderedBooks"));
    }

    
    public void cartDecrementBook() {
        String bookId = request.getParameter("bookId");
        Book book = bookFacade.find(Integer.parseInt(bookId));
        getShoppingCart().decrement(book);
    }

    
    public void cartIncrementBook() {
        String bookId = request.getParameter("bookId");
        Book book = bookFacade.find(Integer.parseInt(bookId));
        getShoppingCart().increment(book);
    }

    
    public void cartRemoveBook() {
        String bookId = request.getParameter("bookId");
        Book book = bookFacade.find(Integer.parseInt(bookId));
        getShoppingCart().update(book, "0");
    }
}
