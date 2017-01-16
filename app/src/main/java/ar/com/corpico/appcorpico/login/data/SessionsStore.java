package ar.com.corpico.appcorpico.login.data;

import ar.com.corpico.appcorpico.login.domain.entity.Session;

/**
 * Creado por Hermosa Programación.
 */

public interface SessionsStore {

    public void getSessionByUserCredentials(String userCode, String password, GetCallback callback);

    public void save(Session session);

    interface GetCallback{
        void onSucess(Session session);

        void onError(String error);
    }
}
