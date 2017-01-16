package ar.com.corpico.appcorpico.login.presentation;

/**
 * Representación abstracta de la vista del login
 */

public interface View {

    void showUserCodeError(String msg);

    void showPasswordError(String msg);

    void showHomeScreen();

    void setPresenter(Presenter presenter);

    void showProgressIndicator(boolean show);

    void showLoginError(String error);
}
