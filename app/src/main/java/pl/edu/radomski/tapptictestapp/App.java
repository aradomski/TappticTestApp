package pl.edu.radomski.tapptictestapp;

import android.app.Application;

import okhttp3.OkHttpClient;

/**
 * Created by radomskia on 01/06/2016.
 */
public class App extends Application {

    private OkHttpClient okHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        okHttpClient = new OkHttpClient();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
