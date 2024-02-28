package ua.edu.znu.servletapp.passdata;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        /*FORWARD*/
        request.setAttribute("msg", "Hello");
        request.getRequestDispatcher("AnotherServlet").forward(request,response);
        /*REDIRECT*/
//        response.sendRedirect("AnotherServlet?msg=Hello");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        /*FORWARD*/
        request.setAttribute("username", username);
        request.getRequestDispatcher("AnotherServlet").forward(request,response);
        /*REDIRECT*/
//        response.sendRedirect(request.getContextPath() + "/AnotherServlet?msg=" + username);
    }
}
