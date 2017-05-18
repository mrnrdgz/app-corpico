package ar.com.corpico.appcorpico.login.presentation;

/**
 * Abtracción del presentador del login
 */

public interface Presenter {
    void onClickedSignButton(String userCode, String password);

}
