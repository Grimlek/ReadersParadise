/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

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
public class HomeAction implements Action {

    private CategoryFacade categoryFacade;

    public HomeAction() {
        try {
            Context ctx = new InitialContext();
            this.categoryFacade = (CategoryFacade) ctx.lookup(
                    "java:global/ReadersParadise/CategoryFacade");
        } catch (NamingException ex) {
            System.err.println(ex);
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute("featuredCategories", categoryFacade.findRandom(5));
        System.out.println("return index page");
        return "index";
    }

}
