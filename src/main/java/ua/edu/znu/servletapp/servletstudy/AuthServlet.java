package ua.edu.znu.servletapp.servletstudy;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AuthServlet",
        urlPatterns = {"/AuthServlet"})
public class AuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println(printHtmlHead("Login"));
            out.println("""
                    <body>
                    <form action="AuthServlet" method="post">
                    <p>Username: <input type="text" name="username"/></p>
                    <p>Password: <input type="password" name="password"/></p>
                    <p><input type="submit" value="Login"/></p>
                    </form>
                    """);
            request.getRequestDispatcher("/FooterServlet").include(request, response);
            out.println("""
                    </body>
                    </html>
                    """);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (PrintWriter out = response.getWriter()) {
            out.println(printHtmlHead("Authentication"));
            out.println("<body>");

            if (isAuthenticated(username, password)) {
                out.println("<h3>Hello, " + username + "!</h3>");
            } else {
                out.println("<h3>Authentication failed!</h3>");
            }

            request.getRequestDispatcher("/FooterServlet").include(request, response);
            out.println("""
                    </body>
                    </html>
                    """);
        }

        response.setContentType("text/html;charset=UTF-8");
    }

    /**
     * Authenticates user.
     *
     * @param username username
     * @param password password
     * @return is user authenticated
     */
    private boolean isAuthenticated(String username, String password) {
        return "tomcat".equalsIgnoreCase(username) && "tomcat".equalsIgnoreCase(password);
    }

    @Override
    public String getServletInfo() {
        return "Servlet that processes user login.";
    }

    private String printHtmlHead(String title) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                <title>"""
                + title
                + """
                </title>
                </head>
                """;
    }
}
