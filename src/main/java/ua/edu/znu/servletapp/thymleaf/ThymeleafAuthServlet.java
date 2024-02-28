package ua.edu.znu.servletapp.thymleaf;

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

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/ThymeleafAuthServlet")
public class ThymeleafAuthServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.templateEngine = (TemplateEngine) getServletContext()
                .getAttribute(ThymeleafConfiguration.TEMPLATE_ENGINE_ATR);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
        WebContext context = getWebContext(request, response);
        context.setVariable("currentYear", LocalDate.now().getYear());
        templateEngine.process("advlogin", context, response.getWriter());
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        WebContext context = getWebContext(request, response);
        if (isAuthenticated(username, password)) {
            context.setVariable("message", "Hello, " + username + "!");
        } else {
            context.setVariable("message", "Authentication failed!");
        }
        context.setVariable("currentYear", LocalDate.now().getYear());
        templateEngine.process("loginresult", context, response.getWriter());

        response.setContentType("text/html;charset=UTF-8");
    }

    private WebContext getWebContext(HttpServletRequest request, HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }

    /**
     * Authenticates user.
     *
     * @param username username
     * @param password password
     * @return is user authenticated
     */
    private boolean isAuthenticated(String username, String password) {
        return "123".equalsIgnoreCase(username) && "123".equalsIgnoreCase(password);
    }

    @Override
    public String getServletInfo() {
        return "Servlet that processes user login.";
    }
}