package ua.edu.znu.servletapp.sessionstudy.servletfilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.edu.znu.servletapp.sessionstudy.User;

import java.io.IOException;

@WebServlet("/SFLoginServlet")
public class SFLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String nextUrl;
        User user = new User();
        if (user.getUsername().equals(request.getParameter("username"))
                && user.getPassword().equals(request.getParameter("password"))) {
            HttpSession session = request.getSession();
            String sessionID = session.getId();
            System.out.println("SFLoginServlet: sessionID: " + sessionID);
            session.setAttribute("user", user);
            nextUrl = response.encodeRedirectURL("/ServletApp/SF2HomeServlet");
            System.out.println("encodedURL: " + nextUrl);
        } else {
            nextUrl = "/ServletApp/login.html";
        }
        request.getRequestDispatcher("SF2HomeServlet").forward(request, response);
         response.sendRedirect(nextUrl);
    }
}
