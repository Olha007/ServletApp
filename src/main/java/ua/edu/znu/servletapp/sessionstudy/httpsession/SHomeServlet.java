package ua.edu.znu.servletapp.sessionstudy.httpsession;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import ua.edu.znu.servletapp.sessionstudy.ThymeleafConfiguration;
import ua.edu.znu.servletapp.sessionstudy.User;

import java.io.IOException;

@WebServlet("/SHomeServlet")
public class SHomeServlet extends HttpServlet {
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
        String nextUrl;
        String message;
        HttpSession session = request.getSession(false);
        if (session == null) {
            message = "Please login first";
            nextUrl = "login";
        } else {
            String sessionID = session.getId();
            System.out.println("SHomeServlet: sessionID = " + sessionID);
            User user = (User) session.getAttribute("user");
            if (user == null) {
                message = "Please login first";
                nextUrl = "login";
                session.invalidate();
            } else {
                message = "Dear " + user.getUsername() + ", Your session Id: " + sessionID;
                nextUrl = "loginresult";
            }
            System.out.println("SHomeServlet: user = " + user);
        }
        context.setVariable("message", message);
        templateEngine.process(nextUrl, context, response.getWriter());
        response.setContentType("text/html;charset=UTF-8");
    }

    private WebContext getWebContext(HttpServletRequest request, HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }
}
