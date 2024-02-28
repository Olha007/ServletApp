package ua.edu.znu.servletapp.sessionstudy;

public class User {
    private final String username = "tomcat";
    private final String password = "tomcat";

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + '}';
    }
}
