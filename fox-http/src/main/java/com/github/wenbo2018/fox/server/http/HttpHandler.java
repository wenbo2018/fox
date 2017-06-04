package com.github.wenbo2018.fox.server.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wenbo.shen on 2017/5/13.
 */
public interface HttpHandler {

    void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
