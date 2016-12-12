/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import java.util.Map;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author csexton
 */
public abstract class GenericActionFacade {
    
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, ? extends Object> flashScope;
    
    public GenericActionFacade(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpServletRequest getServletRequest() {
        return request;
    }

    public HttpSession getSession() {
        return request.getSession();
    }

    public boolean containsRequestCookies() {
        return request.getCookies() != null;
    }

    public String getReffererUri() {
        String pathTrace = request.getHeader("referer");
        return pathTrace.split("ReadersParadise/")[1];
    }

    public boolean isAjaxRequest() {
        return request.getHeader("x-requested-with") != null;
    }

    public void setFlashScope(Map<String, ? extends Object> flashScope) {
        this.flashScope = flashScope;
    }

    public void createValidationFlashScope() {
        String flashScopeId = UUID.randomUUID().toString();
        request.getSession().setAttribute(flashScopeId, flashScope);
        Cookie cookie = new Cookie("flash", flashScopeId);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
    }

    public void setValidationErrors() {
        if (containsRequestCookies()) {
            for (Cookie cookie : request.getCookies()) {
                if ("flash".equals(cookie.getName())) {
                    flashScope = (Map<String, Object>) request.getSession().getAttribute(cookie.getValue());

                    if (flashScope != null) {
                        request.getSession().removeAttribute(cookie.getValue());

                        flashScope.entrySet().forEach((entry) -> {
                            request.setAttribute(entry.getKey(), entry.getValue());
                        });
                    }

                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);
                }
            }
        }
    }

}
