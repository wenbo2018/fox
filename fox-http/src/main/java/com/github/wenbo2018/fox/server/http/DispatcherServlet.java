package com.github.wenbo2018.fox.server.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wenbo.shen on 2017/5/13.
 */
public class DispatcherServlet extends HttpServlet implements Serializable {

    private static final long serialVersionUID = 5202057971036946698L;
    private static DispatcherServlet INSTANCE;

    private static final Map<Integer, HttpHandler> handlers = new ConcurrentHashMap<Integer, HttpHandler>();

    public static void addHttpHandler(int port, HttpHandler processor) {
        handlers.put(port, processor);
    }

    public static void removeHttpHandler(int port) {
        handlers.remove(port);
    }

    public static DispatcherServlet getInstance() {
        return INSTANCE;
    }

    public DispatcherServlet() {
        DispatcherServlet.INSTANCE = this;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpHandler handler = handlers.get(request.getLocalPort());
        if (handler == null) {// service not found.
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Service not found.");
        } else {
            handler.handle(request, response);
        }
    }
}
