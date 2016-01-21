package com.kyanro.widgettraining;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.kyanro.widgettraining.netowrk.GithubClient;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * widgetのトレーニング用アプリ
 */
public class WidgetApp extends Application {

    GithubClient githubClient;

    @Override
    public void onCreate() {
        super.onCreate();

        // okhttp3
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(Level.BODY);

        ExecutorService executorService = Executors.newScheduledThreadPool(2);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .dispatcher(new Dispatcher(executorService))
                .addInterceptor(chain -> {
                    HttpUrl url = chain.request().url().newBuilder()
                            .addQueryParameter("testquery", "testvalue")
                            .build();
                    Request request = chain.request().newBuilder().url(url).build();
                    return chain.proceed(request);
                })
                .addInterceptor(logging)
                .build();

        // retrofit のクライアントを設定
        Retrofit retrofit = new Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        githubClient = retrofit.create(GithubClient.class);
        Log.d("mydevlog", "oncreate!");
    }
}
