package ua.edu.znu.servletapp.sessionstudy;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;


@WebListener
public class ThymeleafConfiguration implements ServletContextListener {
    public static final String TEMPLATE_ENGINE_ATR = "ua.edu.znu.musicalbumweb.TemplateEngineInstance";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(sce.getServletContext());
        ITemplateEngine templateEngine = getTemplateEngine(application);
        sce.getServletContext().setAttribute(TEMPLATE_ENGINE_ATR, templateEngine);
    }

    private ITemplateEngine getTemplateEngine(IWebApplication application) {
        TemplateEngine templateEngine = new TemplateEngine();
        WebApplicationTemplateResolver templateResolver = getTemplateResolver(application);
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    private WebApplicationTemplateResolver getTemplateResolver(IWebApplication application) {
        WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(3600000L);
        templateResolver.setCacheable(true);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }
}

