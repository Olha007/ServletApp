package ua.edu.znu.servletapp.passdata;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/AnotherServlet")
public class AnotherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        String message = request.getParameter("msg");
        try (PrintWriter writer = response.getWriter()) {
            writer.println(printHtmlHead("Another Servlet"));
            writer.println("<body>");
            writer.println("<h2>Another Servlet</h2>");
            writer.println("<br/>");
            writer.println("From Another servlet: " + message);
            writer.println("</body>");
            writer.println("</html>");
        }
//        System.out.println("From Another servlet: " + message);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        try (PrintWriter writer = response.getWriter()) {
            writer.println(printHtmlHead("Another Servlet"));
            writer.println("<body>");
            writer.println("<h2>Another Servlet</h2>");
            writer.println("<br/>");
            writer.println("Hello " + username);
            writer.println("</body>");
            writer.println("</html>");
        }
//        System.out.println("Hello " + username);
    }

    private String printHtmlHead(String title) {
        return """
                <!DOCTYPE html >
                <html >
                <head >
                <title > """
                + title
                + """
                    </title >
                </head >
                    """;
    }
}
