/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author csexton
 */
public class ActionFactory {
    
    public static Map<String, Action> actions = new HashMap<String, Action>();
    
    public static Action getAction(HttpServletRequest request) {
        if (request.getPathInfo() != null) {
            System.out.println(request.getMethod() + request.getServletPath() + request.getPathInfo());
            return actions.get(request.getMethod() + request.getServletPath() + request.getPathInfo());
        }
        System.out.println(request.getMethod() + request.getServletPath() + request.getPathInfo());
        return actions.get(request.getMethod() + request.getServletPath());
    }
}
