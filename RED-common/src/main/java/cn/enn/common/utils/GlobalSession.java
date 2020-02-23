package cn.enn.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 */
public class GlobalSession {
    public static void setSessionAttribute(String arg, Object o) {
        HttpSession session = getSession();
        session.setAttribute(arg, o);
    }

    public static Object getSessionAttribute(String arg) {
        HttpSession session = getSession();
        return session.getAttribute(arg);
    }

    public static void removeSessionAttribute(String arg) {
        HttpSession session = getSession();
        if (getSessionAttribute(arg) != null) {
            session.removeAttribute(arg);
        }
    }

    public static void setAttribute(String arg, Object o) {
        HttpSession session = getHttpSession();
        session.setAttribute(arg, o);
    }


    public static Object getAttribute(String arg) {
        HttpSession session = getHttpSession();
        return session.getAttribute(arg);
    }


    public static void removeAttribute(String arg) {
        if (getAttribute(arg) != null) {
            getHttpSession().removeAttribute(arg);
        }
    }


    public static HttpSession getHttpSession() {
        HttpServletRequest session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return session.getSession();
    }


    public static HttpSession getSession() {
        HttpServletRequest session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return session.getSession();
    }
}
