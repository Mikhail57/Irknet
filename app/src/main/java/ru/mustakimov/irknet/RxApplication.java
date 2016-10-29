package ru.mustakimov.irknet;

import android.app.Application;

/**
 * Created by misha on 29.10.2016.
 */

public class RxApplication extends Application {

    private static NetworkService networkService;

    @Override
    public void onCreate() {
        super.onCreate();

        networkService = new NetworkService();
    }

    public static NetworkService getNetworkService() {
        return networkService;
    }
}
