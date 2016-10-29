package ru.mustakimov.irknet.api;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by misha on 29.10.2016.
 */

public interface IrknetApi {

    @FormUrlEncoded
    @POST("/auth.php")
    Observable<Response<ResponseBody>> loginObservable(@Field("user_id") String login, @Field("hash") String passHash);
}
