package ar.com.corpico.appcorpico.login.data;

import ar.com.corpico.appcorpico.login.domain.entity.Session;

/**
 * Abstracción del repositorio de sesiones
 */

public interface ISessionsRepository {

    void login(String userCode, String password, SessionsRepositoryCallback callback);

    void saveSession(Session session);

    interface SessionsRepositoryCallback{
        void onSucess(Session session);

        void onDataNotAvailable(String error);
    }
}
