package ar.com.corpico.appcorpico.login.data;

import android.os.Handler;

import java.util.UUID;

import ar.com.corpico.appcorpico.login.domain.entity.Session;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Creado por Hermosa Programación.
 */

public class SessionsCloudStore implements SessionsStore {

    private static final String DUMMY_USER_CODE = "0000000000";
    private static final String DUMMY_PASSWORD = "11111111";

    @Override
    public void getSessionByUserCredentials(final String userCode,
                                            final String password,
                                            final GetCallback callback) {

        /*String loginText = "name=ajaj&asswor";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        service.metododelogin(llamar con la callback enqueue(Callback<T> callback))*/
        //en la callback controlar si la respuesta fue exitosa mapeo el resultado del post en la entidad q defini para recibir

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!DUMMY_USER_CODE.equals(userCode)) {
                    callback.onError("El código de usuario no está registrado");
                    return;
                }

                if (!DUMMY_PASSWORD.equals(password)) {
                    callback.onError("Password incorrecto");
                    return;
                }

                // Todo salió bien
                Session newSession = new Session(userCode, "Test Name", UUID.randomUUID().toString());

                callback.onSucess(newSession);
            }
        }, 2000);

    }

    @Override
    public void save(Session session) {
        // No-op
    }
}
