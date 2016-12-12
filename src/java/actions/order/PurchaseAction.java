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
public class PurchaseAction implements Action<OrderActionFacade> {
    
    @Override
    public String execute(OrderActionFacade facade) throws Exception {
        
        if (!facade.isCheckoutValid()) {
            facade.createValidationFlashScope();
            return "checkout";
        }

        int orderId = facade.placeOrder();

        if (orderId == 0) {
            return "checkout";
        }
        return "confirmation?id=" + orderId;
    }

}
