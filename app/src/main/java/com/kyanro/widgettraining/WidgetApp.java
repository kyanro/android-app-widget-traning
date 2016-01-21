package com.kyanro.widgettraining;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.kyanro.widgettraining.netowrk.GithubClient;
import com.squareup.okhttp.Dispatcher;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.squareup.okhttp.logging.HttpLoggingInterceptor.Level;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.Retrofit.Builder;
import retrofit.RxJavaCallAdapterFactory;
import rx.android.schedulers.AndroidSchedulers;

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
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setDispatcher(new Dispatcher(executorService));

        // retrofit のクライアントを設定
        Retrofit retrofit = new Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        githubClient = retrofit.create(GithubClient.class);
        Log.d("mydevlog", "oncreate!");

        githubClient.getUser("kyanro")
//                    .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userResponse -> {
                    Toast.makeText(
                            this, userResponse.login, Toast.LENGTH_LONG).show();
                }, Throwable::printStackTrace);

    }
}
