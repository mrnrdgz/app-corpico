package ar.com.corpico.appcorpico.login.data;

import ar.com.corpico.appcorpico.login.domain.entity.Session;

/**
 * Abstracción del repositorio de sesiones
 */

public interface ISessionsRepository {

    public void login(String userCode, String password, SessionsRepositoryCallback callback);

    void saveSession(Session session);

    interface SessionsRepositoryCallback{
        public void onSucess(Session session);

        public void onDataNotAvailable(String error);
    }
}
