/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

/**
 *
 * @author csexton
 */
public class LocaleAction implements Action<ActionFacade>{

    @Override
    public String execute(ActionFacade facade) throws Exception {
        String language = facade.getServletRequest().getParameter("language");
        facade.getSession().setAttribute("language", language);
        return facade.getReffererUri();
    }
}
