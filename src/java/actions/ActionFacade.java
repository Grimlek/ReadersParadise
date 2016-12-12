/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import entity.Book;
import entity.Category;
import java.util.Collection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.CategoryFacade;

/**
 *
 * @author csexton
 */
public class ActionFacade extends GenericActionFacade implements AutoCloseable {

    private static ThreadLocal<ActionFacade> instance = new ThreadLocal<>();

    private HttpServletRequest request;
    private CategoryFacade categoryFacade;

    public ActionFacade(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.request = request;

        try {
            Context ctx = new InitialContext();
            this.categoryFacade = (CategoryFacade) ctx.lookup(
                    "java:global/ReadersParadise/CategoryFacade!session.CategoryFacade");
        } catch (NamingException ex) {
            System.err.println(ex);
        }
    }

    public static ActionFacade create(HttpServletRequest request, HttpServletResponse response) {
        ActionFacade facade = new ActionFacade(request, response);
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
        super.getSession().setAttribute("featuredCategories", categoryFacade.findRandom(5));
    }

    public void setSelectedCategory() {
        String categoryName = request.getParameter("category");

        Category selectedCategory = categoryFacade.findByName(categoryName);

        if (selectedCategory == null) {
            selectedCategory = categoryFacade.find(1);
        }
        super.getSession().setAttribute("selectedCategory", selectedCategory);

        Collection<Book> categoryBooks = selectedCategory.getBookCollection();
        super.getSession().setAttribute("categoryBooks", categoryBooks);
    }

    public void setAllCategories() {
        request.setAttribute("categories", categoryFacade.findAll());
    }
}
