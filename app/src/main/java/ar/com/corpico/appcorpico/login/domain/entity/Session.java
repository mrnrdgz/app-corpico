package ar.com.corpico.appcorpico.login.domain.entity;

import android.support.annotation.NonNull;

/**
 * Representa las sesiones de usuario en el sistema
 */

public class Session {

    @NonNull
    private String userCode;
    @NonNull
    private String userName;
    @NonNull
    private String token;

    public Session(@NonNull String userCode, @NonNull String userName, @NonNull String token) {
        this.userCode = userCode;
        this.userName = userName;
        this.token = token;
    }

    @NonNull
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(@NonNull String userCode) {
        this.userCode = userCode;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    @NonNull
    public String getToken() {
        return token;
    }

    public void setToken(@NonNull String token) {
        this.token = token;
    }
}
