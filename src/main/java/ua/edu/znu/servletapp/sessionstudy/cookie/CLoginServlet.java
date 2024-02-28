package ua.edu.znu.servletapp.sessionstudy.cookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.edu.znu.servletapp.sessionstudy.AppSession;
import ua.edu.znu.servletapp.sessionstudy.User;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/CLoginServlet")
public class CLoginServlet extends HttpServlet {
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
//        request.setCharacterEncoding("UTF-8");
        User user = new User();
        if (user.getUsername().equals(request.getParameter("username"))
                && user.getPassword().equals(request.getParameter("password"))) {
            String sessionID = UUID.randomUUID().toString();
            System.out.println("CLoginServlet: sessionID: " + sessionID);
            AppSession.addSessionAttribute(sessionID, user);
            System.out.println("CLoginServlet: globalTable content: `"
                    + AppSession.getGlobalTable());
            Cookie sessionCookie = new Cookie("JSESSIONID", sessionID);
            System.out.println("CLoginServlet: cookie parameter "
                    + sessionCookie.getName() + " = " + sessionCookie.getValue());
            response.addCookie(sessionCookie);

            response.sendRedirect("/ServletApp/CHomeServlet");
        } else {
            response.sendRedirect("/ServletApp/login.html");
        }
    }
}
