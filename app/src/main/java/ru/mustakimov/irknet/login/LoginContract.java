package ru.mustakimov.irknet.login;

import ru.mustakimov.irknet.BasePresenter;
import ru.mustakimov.irknet.BaseView;

/**
 * Created by misha on 29.10.2016.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showLoginError();
        void showLoginSuccessful();
        void showMainUi();
    }

    interface Presenter extends BasePresenter {
        void result(int requestCode, int resultCode);
        void login(String login, String password);
    }
}
