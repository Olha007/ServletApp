package ua.edu.znu.servletapp.thymleaf;

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
    public static final String TEMPLATE_ENGINE_ATR = "ua.edu.znu.servletapp.TemplateEngineInstance";
    private ITemplateEngine templateEngine;
    private IWebApplication application;

    /**
     * Method called when the application is started.
     * @param sce ServletContextEvent - the event to deal with the application
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.application = JakartaServletWebApplication.buildApplication(sce.getServletContext());
        /* We will see later how the TemplateEngine object is built and configured*/
        this.templateEngine = getTemplateEngine(this.application);
        /*Save the template engine as a ServletContext attribute*/
        sce.getServletContext().setAttribute(TEMPLATE_ENGINE_ATR, templateEngine);
    }

    private ITemplateEngine getTemplateEngine(IWebApplication application) {
        TemplateEngine templateEngine = new TemplateEngine();
        /* The template resolver is the object that will be in charge of finding the template files*/
        WebApplicationTemplateResolver templateResolver = getTemplateResolver(application);
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    private WebApplicationTemplateResolver getTemplateResolver(IWebApplication application) {
        WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
        /*Configure the template resolver*/
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
        templateResolver.setCacheable(true);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }
}




