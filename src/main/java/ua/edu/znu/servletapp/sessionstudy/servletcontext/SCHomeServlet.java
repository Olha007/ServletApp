package ua.edu.znu.servletapp.sessionstudy.servletcontext;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import ua.edu.znu.servletapp.sessionstudy.SCAppSession;
import ua.edu.znu.servletapp.sessionstudy.ThymeleafConfiguration;
import ua.edu.znu.servletapp.sessionstudy.User;

import java.io.IOException;
import java.util.Map;


@WebServlet("/SCHomeServlet")
public class SCHomeServlet extends HttpServlet {

    private Map<String, SCAppSession> sessions;
    private TemplateEngine templateEngine;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.templateEngine = (TemplateEngine) getServletContext()
                .getAttribute(ThymeleafConfiguration.TEMPLATE_ENGINE_ATR);
        sessions = (Map<String, SCAppSession>) getServletContext().getAttribute("sessions");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        WebContext context = getWebContext(request, response);
        String sessionID = getSessionIdFromCookies(request);
        System.out.println("SCHomeServlet: sessionID = " + sessionID);
        SCAppSession session = sessions.get(sessionID);
        User user = (User) session.getSessionAttribute("user");
        System.out.println("SCHomeServlet: user = " + user);
        String message;
        if (user != null) {
            message = "Dear " + user.getUsername() + ", Your session Id: " + sessionID;
        } else {
            message = "Please login first";
        }

        context.setVariable("message", message);

        templateEngine.process("loginresult", context, response.getWriter());
        response.setContentType("text/html;charset=UTF-8");
    }

    private String getSessionIdFromCookies(HttpServletRequest request) {
        String sessionID = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if ("JSESSIONID".equals(c.getName())) {
                sessionID = c.getValue();
            }
        }
        return sessionID;
    }

    private WebContext getWebContext(HttpServletRequest request, HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }
}
