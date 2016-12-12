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
public class HomeAction implements Action<ActionFacade> {

    @Override
    public String execute(ActionFacade facade) throws Exception {
        facade.setFeaturedCategories();
        return "index";
    }

}
