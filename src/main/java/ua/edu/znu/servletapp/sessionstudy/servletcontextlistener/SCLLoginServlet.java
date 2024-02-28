package ua.edu.znu.servletapp.sessionstudy.servletcontextlistener;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.edu.znu.servletapp.sessionstudy.SCAppSession;
import ua.edu.znu.servletapp.sessionstudy.User;

import java.io.IOException;
import java.util.Map;

@WebServlet("/SCLLoginServlet")
public class SCLLoginServlet extends HttpServlet {

    private Map<String, SCAppSession> sessions;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sessions = (Map<String, SCAppSession>) getServletContext().getAttribute("sessions");
    }

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
        User user = new User();
        if (user.getUsername().equals(request.getParameter("username"))
                && user.getPassword().equals(request.getParameter("password"))) {
            SCAppSession session = new SCAppSession();
            String sessionID = session.getSessionId();
            System.out.println("SCLLoginServlet: sessionID: " + sessionID);
            session.addSessionAttribute("user", user);
            System.out.println("SCLLoginServlet: attributes content: `"
                    + session.getAttributes());
            sessions.put(sessionID, session);
            Cookie sessionCookie = new Cookie("JSESSIONID", sessionID);
            System.out.println("SCLLoginServlet: cookie parameter "
                    + sessionCookie.getName() + " = " + sessionCookie.getValue());
            response.addCookie(sessionCookie);
            response.sendRedirect("/ServletApp/SCLHomeServlet");
        } else {
            response.sendRedirect("/ServletApp/login.html");
        }
    }
}
