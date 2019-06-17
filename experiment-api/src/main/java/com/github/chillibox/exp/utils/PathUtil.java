package com.github.chillibox.exp.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Created on 2017/6/27.</p>
 *
 * @author Gonster
 */
public class PathUtil {

    private PathUtil() {
    }

    public static String addUrlPathPrefix(HttpServletRequest request, String prefix, String origin) {
        return getPathBase(request) + prefix + origin;
    }

    public static String getPathBase(HttpServletRequest request) {
        String protocol = request.getScheme();
        String hostname = request.getServerName();
        int port = request.getServerPort();
        String contextPath = request.getContextPath();

        String target = protocol + "://" + hostname;
        if (isPortNeeded(protocol, port)) {
            target = target + ":" + String.valueOf(port);
        }
        return target + contextPath;
    }

    private static boolean isPortNeeded(String protocol, int port) {
        return !(("https".equalsIgnoreCase(protocol) && port == 443)
                || ("http".equalsIgnoreCase(protocol) && port == 80)
                || port < 0);
    }
}
