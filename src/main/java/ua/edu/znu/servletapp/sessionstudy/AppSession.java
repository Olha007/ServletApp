package ua.edu.znu.servletapp.sessionstudy;

import java.util.HashMap;
import java.util.Map;

public class AppSession {
    private static Map<String, Object> globalTable = new HashMap<>();

    public static void addSessionAttribute(String name, Object value) {
        globalTable.put(name, value);
    }

    public static Map<String, Object> getGlobalTable() {
        return globalTable;
    }
}
