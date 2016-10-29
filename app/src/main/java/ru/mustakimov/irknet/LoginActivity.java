package ru.mustakimov.irknet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mustakimov.irknet.api.IrknetApi;
import ru.mustakimov.irknet.utils.HashingUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    Retrofit retrofit;
    IrknetApi irknetApi;

    @BindView(R.id.login)
    EditText login;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.loginButton)
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);
        ButterKnife.bind(this);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://openapi.irknet.ru/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        irknetApi = retrofit.create(IrknetApi.class);
    }

    @OnClick(R.id.loginButton)
    public void onLoginButtonClicked() {

        irknetApi.loginObservable(login.getText().toString(), HashingUtils.md5(password.getText().toString()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    try {
                        Toast.makeText(LoginActivity.this, "Answer: " + responseBody.body().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    Log.e("Error", throwable.getMessage());
                    Toast.makeText(this, "Ошибочка", Toast.LENGTH_SHORT).show();
                });
    }

}
