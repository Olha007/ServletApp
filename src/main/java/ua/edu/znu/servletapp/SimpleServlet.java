package ua.edu.znu.servletapp;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/SimpleServlet")
public class SimpleServlet extends HttpServlet {
    private String message;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        message = "Hello from SimpleServlet!";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.printf(
                    """
                            <!DOCTYPE html>
                            <html>
                            <head>
                                <title>SimpleServlet at %s</title>
                            </head>
                            <body>
                                <h1>%s</h1>
                            </body>
                            </html>
                            """, req.getContextPath(), message
            );
        }
    }
}

