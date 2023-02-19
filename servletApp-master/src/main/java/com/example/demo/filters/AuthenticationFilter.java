package com.example.demo.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        context = fConfig.getServletContext();
        context.log(">>> AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;


        PrintWriter out = res.getWriter();
        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);
        //       HttpSession session = req.getSession();

        context.log("Requested Resource::http://localhost:8080" + uri);


        if (session != null)
            out.println("session == " + session.toString() + ", user = " + session.getAttribute("user"));
        else
            out.println("session == null");


        if (session == null && !uri.endsWith("demo/loginServlet")) {
            out.println("Please Login first!!! ( demo/loginServlet)");
        } else if (session != null && uri.endsWith("demo/loginServlet")) {
            out.println("You are already logined as " + session.getAttribute("user") + ", logout first!");
        } else if (session != null && session.getAttribute("user").equals("user") &&
                !(uri.endsWith("demo/logoutServlet") || uri.endsWith("demo/viewServlet") || uri.endsWith("demo/myViewByIDServlet") ||
                        uri.endsWith("demo/myViewByCountryServlet") || uri.endsWith("demo/viewByIDServlet"))) {

            context.log("<<< Not enough rights!");
            out.println("Not enough rights!");
        } else
            chain.doFilter(request, response);

    }

    public void destroy() {
        //close any resources here
    }
}
