package ua.edu.znu.servletapp.sessionstudy.cookie;

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
import ua.edu.znu.servletapp.sessionstudy.AppSession;
import ua.edu.znu.servletapp.thymleaf.ThymeleafConfiguration;
import ua.edu.znu.servletapp.sessionstudy.User;

import java.io.IOException;
import java.util.Map;

@WebServlet("/CHomeServlet")
public class CHomeServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.templateEngine = (TemplateEngine) getServletContext()
                .getAttribute(ThymeleafConfiguration.TEMPLATE_ENGINE_ATR);
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
        System.out.println("CHomeServlet: sessionID = " + sessionID);
        User user = findUserFromGlobalTable(sessionID);
        System.out.println("CHomeServlet: user = " + user);
        String message;
        if (user != null) {
            message = "Dear " + user.getUsername() + ", Your session Id: " + sessionID;
        } else {
            message = "Please login first";
        }

        context.setVariable("message", message);

        templateEngine.process("loginresult", context, response.getWriter());
    }

    private User findUserFromGlobalTable(String sessionID) {
        Map<String, Object> globalTable = AppSession.getGlobalTable();
        for (Map.Entry<String, Object> entry : globalTable.entrySet()) {
            if (sessionID.equals(entry.getKey())) {
                return (User) entry.getValue();
            }
        }
        return null;
    }

    private String getSessionIdFromCookies(HttpServletRequest request) {
        String sessionID = null;
        //Retrieving the session ID from a cookie
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

