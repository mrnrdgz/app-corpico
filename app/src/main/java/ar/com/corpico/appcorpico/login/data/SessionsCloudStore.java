package ar.com.corpico.appcorpico.login.data;

import android.os.Handler;
import android.widget.Toast;

import java.util.UUID;

import ar.com.corpico.appcorpico.login.domain.entity.PostLogin;
import ar.com.corpico.appcorpico.login.domain.entity.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

        String loginText = "username=mrodriguez&password=280776&grant_type=password";

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://172.16.14.24:25772/")
                .baseUrl("http://172.16.14.24/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServiceLogin service = retrofit.create(ApiServiceLogin.class);
        final Call<PostLogin> postLogin = service.getToken("mrodriguez","280776","password");
        postLogin.enqueue(new Callback<PostLogin>() {
            @Override
            public void onResponse(Call<PostLogin> call, Response<PostLogin> response) {

                if(response.isSuccessful()) {
                    Session newSession = new Session(response.body().getUserName(), response.body().getUserName(), response.body().getAccessToken());
                    callback.onSucess(newSession);
                }
            }

            @Override
            public void onFailure(Call<PostLogin> call, Throwable t) {
                callback.onError("El código de usuario no está registrado");
            }
        });
        //service.metododelogin(llamar con la callback enqueue(Callback<T> callback))
        //en la callback controlar si la respuesta fue exitosa mapeo el resultado del post en la entidad q defini para recibir

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!DUMMY_USER_CODE.equals(userCode)) {
                    callback.onError("El código de usuario no está registrado");
                    return;
                }

                if (!DUMMY_PASSWORD.equals(password)) {
                    String
                    return;
                }

                // Todo salió bien
                Session newSession = new Session(userCode, "Test Name", UUID.randomUUID().toString());

                callback.onSucess(newSession);
            }
        }, 2000);*/

    }

    @Override
    public void save(Session session) {
        // No-op
    }
}
