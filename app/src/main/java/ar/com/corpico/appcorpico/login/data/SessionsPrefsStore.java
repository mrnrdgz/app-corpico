package ar.com.corpico.appcorpico.login.data;

import java.util.HashMap;

import ar.com.corpico.appcorpico.login.domain.entity.Session;

/**
 * Creado por Hermosa Programación.
 */

public class SessionsPrefsStore implements SessionsStore {
    private static final HashMap<String, Object> PREFERENCES = new HashMap<>();

    private boolean isLogged = false;

    private static SessionsPrefsStore INSTANCE = null;

    private SessionsPrefsStore() {
    }

    public static SessionsPrefsStore get() {
        if (INSTANCE == null) {
            INSTANCE = new SessionsPrefsStore();
        }
        return INSTANCE;
    }

    @Override
    public void getSessionByUserCredentials(String userCode, String password, GetCallback callback) {
        // No-op
    }

    @Override
    public void save(Session session) {
        PREFERENCES.put("codeUser", session.getUserCode());
        PREFERENCES.put("name", session.getUserName());
        PREFERENCES.put("token", session.getToken());

        isLogged = true;
    }

    public boolean isLogged() {
        return isLogged;
    }
}
