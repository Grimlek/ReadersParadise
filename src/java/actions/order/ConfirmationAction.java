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
public class ConfirmationAction implements Action<OrderActionFacade> {

    @Override
    public String execute(OrderActionFacade facade) throws Exception {
        String orderId = facade.getServletRequest().getParameter("id");
        if (orderId.matches("^\\d+$")) {
            facade.setOrderDetails(Integer.parseInt(orderId));                    
        }
        return "confirmation";
    }
    
}
