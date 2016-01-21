package com.kyanro.widgettraining.netowrk;

import com.kyanro.widgettraining.models.github.User;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * github api用クライアント
 */
public interface GithubClient {
    @GET("/users/{name}")
    Observable<Response<User>> getUser(@Path("name") String name);
}
