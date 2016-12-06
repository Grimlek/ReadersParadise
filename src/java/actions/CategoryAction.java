/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

public class CategoryAction implements Action {
    
    @Override
    public String execute(ActionFacade facade) throws Exception {
            facade.setSelectedCategory();
            facade.setAllCategories();
            return "category";
    }
    
}
