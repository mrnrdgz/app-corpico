package ar.com.corpico.appcorpico.login.presentation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.home.HomeActivity;

import static android.view.View.GONE;

/**
 * Implementación concreta de la vista para el login
 * Referencia: @link {http://www.hermosaprogramacion.com/2014/09/android-aplicaciones-fragmento/}
 */
public class LoginFragment extends Fragment
        implements ar.com.corpico.appcorpico.login.presentation.View {

    // Relación de composición
    private Presenter mLoginPresenter;

    // UI references.
    private EditText mUserCodeView;
    private EditText mPasswordView;
    private TextInputLayout mFloatLabelUserCode;
    private TextInputLayout mFloatLabelPassword;
    private View mProgressView;
    private View mLoginFormView;
    private Button mLogInButton;
    private ImageView mLogoImage;


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Toman parámetros
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        // Obtención de referencias UI
        mUserCodeView = (EditText) root.findViewById(R.id.user_code);
        mPasswordView = (EditText) root.findViewById(R.id.password);
        mFloatLabelUserCode = (TextInputLayout) root.findViewById(R.id.float_label_user_code);
        mFloatLabelPassword = (TextInputLayout) root.findViewById(R.id.float_label_password);
        mProgressView = root.findViewById(R.id.login_progress);
        mLoginFormView = root.findViewById(R.id.login_form);
        mLogInButton = (Button) root.findViewById(R.id.email_sign_in_button);
        mLogoImage = (ImageView) root.findViewById(R.id.imageView);

        // Eventos
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager im = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                mLoginPresenter.onClickedSignButton(
                        mUserCodeView.getText().toString(),
                        mPasswordView.getText().toString());
            }
        });

        mUserCodeView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mFloatLabelUserCode.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mPasswordView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mFloatLabelPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return root;
    }

    @Override
    public void showUserCodeError(String msg) {
        mFloatLabelUserCode.setError(msg);
    }

    @Override
    public void showPasswordError(String msg) {
        mFloatLabelPassword.setError(msg);
    }

    @Override
    public void showHomeScreen() {
        startActivity(new Intent(getActivity(),HomeActivity.class));
        getActivity().finish();
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mLoginPresenter = presenter;
    }

    @Override
    public void showProgressIndicator(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : GONE);
        mLoginFormView.setVisibility(show ? GONE : View.VISIBLE);
        mLogoImage.setVisibility(show ? GONE : View.VISIBLE);
    }

    @Override
    public void showLoginError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG)
                .show();
    }
}
