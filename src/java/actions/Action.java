/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

public interface Action<T extends GenericActionFacade> {
    public String execute(T facade) throws Exception;
}
