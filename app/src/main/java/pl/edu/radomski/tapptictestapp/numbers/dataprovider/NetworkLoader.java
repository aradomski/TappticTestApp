package pl.edu.radomski.tapptictestapp.numbers.dataprovider;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import pl.edu.radomski.tapptictestapp.App;

/**
 * Created by radomskia on 01/06/2016.
 */
public abstract class NetworkLoader<T> extends AsyncTaskLoader<Response<T>> {
    public NetworkLoader(Context context) {
        super(context);
    }

    @Override
    public Response<T> loadInBackground() {
        Response<T> tResponse = new Response<>();
        OkHttpClient client = ((App) getContext().getApplicationContext()).getOkHttpClient();
        Request request = new Request.Builder()
                .url(getUrl())
                .build();
        try {
            okhttp3.Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);


            String stringResponse = response.body().string();

            tResponse.setData(parse(stringResponse));

        } catch (Exception e) {
            e.printStackTrace();
            tResponse.setException(e);
        }

        return tResponse;
    }

    protected abstract String getUrl();

    protected abstract T parse(String s) throws Exception;
}
