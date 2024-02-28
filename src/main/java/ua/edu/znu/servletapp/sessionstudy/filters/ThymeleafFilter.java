package ua.edu.znu.servletapp.sessionstudy.filters;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import ua.edu.znu.servletapp.sessionstudy.ThymeleafConfiguration;

import java.io.IOException;
import java.util.Enumeration;

@WebFilter(urlPatterns = "/SF2HomeServlet",
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class ThymeleafFilter implements Filter {

    private TemplateEngine templateEngine;
    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        this.context = filterConfig.getServletContext();
        this.templateEngine = (TemplateEngine) context
                .getAttribute(ThymeleafConfiguration.TEMPLATE_ENGINE_ATR);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        WebContext context = getWebContext(req, resp);
        Enumeration<String> params = req.getAttributeNames();
        params.asIterator().forEachRemaining(param -> {
            if (!"nextUrl".equals(param))
                context.setVariable(param, req.getAttribute(param));
        });
        templateEngine.process((String) req.getAttribute("nextUrl"), context, response.getWriter());
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private WebContext getWebContext(HttpServletRequest request, HttpServletResponse response) {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(context)
                .buildExchange(request, response);
        return new WebContext(webExchange);
    }
}
