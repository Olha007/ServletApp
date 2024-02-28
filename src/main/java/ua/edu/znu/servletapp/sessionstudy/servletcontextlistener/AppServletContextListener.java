package ua.edu.znu.servletapp.sessionstudy.servletcontextlistener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import ua.edu.znu.servletapp.sessionstudy.SCAppSession;

import java.util.HashMap;
import java.util.Map;

public class AppServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Map<String, SCAppSession> sessions = new HashMap<>();
        ServletContext app = sce.getServletContext();
        app.setAttribute("sessions", sessions);
    }
}


