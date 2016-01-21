package com.kyanro.widgettraining.netowrk;

import com.kyanro.widgettraining.models.github.User;

import retrofit.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * github api用クライアント
 */
public interface GithubClient {
    @GET("/users/{name}")
    Observable<User> getUser(@Path("name") String name);
}
