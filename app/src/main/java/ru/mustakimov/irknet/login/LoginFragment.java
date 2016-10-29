package ru.mustakimov.irknet.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.mustakimov.irknet.R;

/**
 * Created by misha on 29.10.2016.
 */

public class LoginFragment extends Fragment implements LoginContract.View {

    private LoginContract.Presenter presenter;

    @BindView(R.id.login)
    EditText login;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.loadingProgress)
    ProgressBar loadingProgress;

    public LoginFragment() {

    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @OnClick(R.id.loginButton)
    public void onLoginButtonClicked() {
        presenter.login(login.getText().toString(), password.getText().toString());
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active)
            loadingProgress.setVisibility(View.VISIBLE);
        else
            loadingProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoginError() {
        Toast.makeText(getContext(), "Произошла ошибка!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginSuccessful() {
        Toast.makeText(getContext(), "Успешно!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMainUi() {

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
