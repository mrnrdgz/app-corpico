package ar.com.corpico.appcorpico.login.domain.usecase;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.login.data.ISessionsRepository;
import ar.com.corpico.appcorpico.login.domain.entity.Session;

/**
 * Caso de uso para el login de usuarios
 */

public class LoginUser extends UseCase<LoginUser.RequestValues, LoginUser.ResponseValue> {

    private ISessionsRepository mSessionsRepository;

    public LoginUser(ISessionsRepository sessionsRepository) {
        this.mSessionsRepository = sessionsRepository;
    }

    @Override
    public void execute(RequestValues requestValues, final UseCaseCallback callback) {

        mSessionsRepository.login(requestValues.mCodeUser, requestValues.mPassword,
                new ISessionsRepository.SessionsRepositoryCallback() {
                    @Override
                    public void onSucess(Session session) {
                        ResponseValue responseValue = new ResponseValue(session);
                        callback.onSuccess(responseValue);
                    }

                    @Override
                    public void onDataNotAvailable(String error) {
                        callback.onError(error);
                    }
                });

    }

    public void keepUserSession(Session session) {
        mSessionsRepository.saveSession(session);
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private String mCodeUser;
        private String mPassword;

        public RequestValues(String codeUser, String password) {
            if (codeUser != null) {
                this.mCodeUser = codeUser;
            }

            if (password != null) {
                this.mPassword = password;
            }
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private Session mUserSession;

        public ResponseValue(Session mUserSession) {
            this.mUserSession = mUserSession;
        }

        public Session getUserSession() {
            return mUserSession;
        }
    }
}
