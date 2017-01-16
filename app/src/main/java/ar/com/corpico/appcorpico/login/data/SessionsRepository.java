package ar.com.corpico.appcorpico.login.data;

import ar.com.corpico.appcorpico.login.domain.entity.Session;

/**
 * Creado por Hermosa Programación.
 */

public class SessionsRepository implements ISessionsRepository {

    private SessionsStore mPrefsStore;
    private SessionsStore mRestStore;

    public SessionsRepository(SessionsStore prefsStore, SessionsStore restStore) {
        this.mPrefsStore = prefsStore;
        this.mRestStore = restStore;
    }

    @Override
    public void login(String userCode, String password, final SessionsRepositoryCallback callback) {
        mRestStore.getSessionByUserCredentials(userCode, password, new SessionsStore.GetCallback() {
            @Override
            public void onSucess(Session session) {
                // Conversión de datos
                callback.onSucess(session);
            }

            @Override
            public void onError(String error) {
                callback.onDataNotAvailable(error);
            }
        });
    }

    @Override
    public void saveSession(Session session) {
        mPrefsStore.save(session);
    }
}
