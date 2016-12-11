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
public class ProductAction implements Action {

    @Override
    public String execute(ActionFacade facade) throws Exception {
        // implement product page per query string id....
        return "product";
    }
    
}
