/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.cart;

import actions.Action;
import actions.ActionFacade;

/**
 *
 * @author csexton
 */
public class IncrementAction implements Action {
    
    @Override
    public String execute(ActionFacade facade) throws Exception {
        facade.cartIncrementBook();
        if (facade.isAjaxRequest()) {
            return "NO_REDIRECT";
        }
        return "cart";
    }

}
