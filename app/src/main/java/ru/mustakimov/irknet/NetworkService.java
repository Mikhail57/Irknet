package ru.mustakimov.irknet;

import android.util.LruCache;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mustakimov.irknet.api.IrknetApi;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by misha on 29.10.2016.
 */

public class NetworkService {

    private static String baseUrl = "https://openapi.irknet.ru";
    private IrknetApi irknetApi;
    private LruCache<Class<?>, Observable<?>> apiObservables;

    public NetworkService() {
        this(baseUrl);
    }

    public NetworkService(String baseUrl) {
        NetworkService.baseUrl = baseUrl;
        apiObservables = new LruCache<>(10);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        irknetApi = retrofit.create(IrknetApi.class);
    }

    /**
     * Method to return the API interface
     *
     * @return API interface
     */
    public IrknetApi getApi() {
        return irknetApi;
    }

    /**
     * Method to clear the entire cache of observables
     */
    public void clearCache() {
        apiObservables.evictAll();
    }

    /**
     * Method to either return a cached observable or prepare a new one.
     *
     * @param unpreparedObservable
     * @param clazz
     * @param cacheObservable
     * @param useCache
     * @return Observable ready to be subscribed to
     */
    public Observable<?> getPreparedObservable(Observable<?> unpreparedObservable, Class<?> clazz, boolean cacheObservable, boolean useCache) {
        Observable<?> preparedObservable = null;

        if (useCache)//this way we don't reset anything in the cache if this is the only instance of us not wanting to use it.
            preparedObservable = apiObservables.get(clazz);

        if (preparedObservable != null)
            return preparedObservable;

        //we are here because we have never created this observable before or we didn't want to use the cache...

        preparedObservable = unpreparedObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        if (cacheObservable) {
            preparedObservable = preparedObservable.cache();
            apiObservables.put(clazz, preparedObservable);
        }

        return preparedObservable;
    }
}
