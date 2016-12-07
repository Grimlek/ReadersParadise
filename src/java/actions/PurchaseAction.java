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
public class PurchaseAction implements Action {
    
    @Override
    public String execute(ActionFacade facade) throws Exception {

        if (!facade.isCheckoutValid()) {
            return "checkout";
        }

        int orderId = facade.placeOrder();
        
        if (orderId == 0) {
            return "checkout";
        }

        facade.setOrderDetails(orderId);
        return "confirmation";
    }

}
