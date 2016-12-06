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
import javax.servlet.http.HttpSession;
import session.CategoryFacade;

/**
 *
 * @author csexton
 */
public class ActionFacade {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    
    private CategoryFacade categoryFacade;
    
    
    public ActionFacade(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        
        try {
            Context ctx = new InitialContext();
            this.categoryFacade = (CategoryFacade) ctx.lookup(
                    "java:global/ReadersParadise/CategoryFacade");
        } catch (NamingException ex) {
            System.err.println(ex);
        }
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

}
