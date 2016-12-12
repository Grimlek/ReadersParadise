/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions.order;

import actions.Action;

/**
 *
 * @author csexton
 */
public class CheckoutAction implements Action<OrderActionFacade> {

    @Override
    public String execute(OrderActionFacade facade) throws Exception {
        facade.calculateTotal();
        facade.setYearMonths(); 
        
        if (facade.containsRequestCookies()) {
            facade.setValidationErrors();
        }
        
        return "checkout";
    }

}
