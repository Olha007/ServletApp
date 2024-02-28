package ua.edu.znu.servletapp.sessionstudy;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SCAppSession {
    private String sessionId;
    private Map<String, Object> attributes;

    public SCAppSession() {
        this.sessionId = UUID.randomUUID().toString();
        this.attributes = new HashMap<>();
    }

    public String getSessionId() {
        return sessionId;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void addSessionAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getSessionAttribute(String name) {
        return attributes.get(name);
    }

    public Object removeSessionAttribute(String name) {
        return attributes.remove(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SCAppSession that = (SCAppSession) o;

        return sessionId.equals(that.sessionId);
    }

    @Override
    public int hashCode() {
        return sessionId.hashCode();
    }
}
