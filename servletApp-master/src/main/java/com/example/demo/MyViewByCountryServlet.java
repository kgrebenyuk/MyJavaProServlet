package com.example.demo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/myViewByCountryServlet")
public class MyViewByCountryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String country = request.getParameter("country");

        List<Employee> list = EmployeeRepository.myGetByCountry(country);

        for (Employee employee : list)
            out.print(employee);

        out.close();
    }
}
