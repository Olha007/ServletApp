package ua.edu.znu.servletapp.sessionstudy.urlrewrite;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.edu.znu.servletapp.sessionstudy.AppSession;
import ua.edu.znu.servletapp.sessionstudy.User;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/ULoginServlet")
public class ULoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        User user = new User();
        if (user.getUsername().equals(request.getParameter("username"))
                && user.getPassword().equals(request.getParameter("password"))) {
            String sessionID = UUID.randomUUID().toString();
            System.out.println("ULoginServlet: sessionID: " + sessionID);
            AppSession.addSessionAttribute(sessionID, user);
            System.out.println("ULoginServlet: globalTable content: `"
                    + AppSession.getGlobalTable());

            response.sendRedirect("/ServletApp/UHomeServlet?jsessionid=" + sessionID);
        } else {
            response.sendRedirect("/ServletApp/login.html");
        }
    }
}
