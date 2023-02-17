package com.example.demo.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private class User {
        String userID;
        String password;

        public User(String userID, String password) {
            this.userID = userID;
            this.password = password;
        }

        public String getUserID() {
            return userID;
        }

        public String getPassword() {
            return password;
        }
    }

    private static final long serialVersionUID = 1L;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        ArrayList<User> usersList = new ArrayList<>();
        usersList.add(new User("admin", "admin"));
        usersList.add(new User("user", "user"));

        // Это название 2-х параметров, которые мы передаем
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");

        boolean isLoginOK = false;
        for (User user_check : usersList) {
            if (user_check.getUserID().equals(user) && user_check.getPassword().equals(pwd)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user_check.getUserID());
                session.setMaxInactiveInterval(30 * 60);
                Cookie userName = new Cookie("user", user);
                userName.setMaxAge(30 * 60);
                response.addCookie(userName);

                out.println("Hellow, " + user + " :)");
                isLoginOK = true;
                break;
            }
        }
        if (!isLoginOK)
            out.println("Please input correct user Name and Password :)");
    }
}
