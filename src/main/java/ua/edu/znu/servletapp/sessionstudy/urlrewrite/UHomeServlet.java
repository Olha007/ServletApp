package ua.edu.znu.servletapp.sessionstudy.urlrewrite;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import ua.edu.znu.servletapp.sessionstudy.AppSession;
import ua.edu.znu.servletapp.sessionstudy.ThymeleafConfiguration;
import ua.edu.znu.servletapp.sessionstudy.User;

import java.io.IOException;
import java.util.Map;

@WebServlet("/UHomeServlet")
public class UHomeServlet extends HttpServlet {
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
        String sessionID = request.getParameter("jsessionid");
        System.out.println("UHomeServlet: sessionID = " + sessionID);
        User user = findUserFromGlobalTable(sessionID);
        System.out.println("UHomeServlet: user = " + user);
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

    private User findUserFromGlobalTable(String sessionID) {
        Map<String, Object> globalTable = AppSession.getGlobalTable();
        for (Map.Entry<String, Object> entry : globalTable.entrySet()) {
            if (sessionID.equals(entry.getKey())) {
                return (User) entry.getValue();
            }
        }
        return null;
    }

    private WebContext getWebContext(HttpServletRequest request, HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }
}
