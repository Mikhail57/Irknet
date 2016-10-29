package ru.mustakimov.irknet.login;

import android.support.annotation.NonNull;

import nucleus.presenter.RxPresenter;
import ru.mustakimov.irknet.RxApplication;
import ru.mustakimov.irknet.utils.HashingUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by misha on 29.10.2016.
 */

public class LoginPresenter extends RxPresenter<LoginFragment> implements LoginContract.Presenter {

    @NonNull
    private LoginContract.View loginView;

    @NonNull
    private CompositeSubscription subscriptions;

    public LoginPresenter(@NonNull LoginContract.View loginView) {
        this.loginView = loginView;

        subscriptions = new CompositeSubscription();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void login(String login, String password) {
        loginView.setLoadingIndicator(true);
        subscriptions.clear();

        String passwordHash = HashingUtils.md5(password);
        subscriptions.add(
                RxApplication.getNetworkService().getApi().loginObservable(login, passwordHash)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(responseBody -> {
                            loginView.setLoadingIndicator(false);
                            loginView.showLoginSuccessful();
                        }, throwable -> {
                            loginView.setLoadingIndicator(false);
                            loginView.showLoginError();
                        })
        );
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();
    }
}
