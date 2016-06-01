package pl.edu.radomski.tapptictestapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by radomskia on 01/06/2016.
 */
public class Utils {
    public static boolean isNetworkConnectionAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        NetworkInfo.State network = info.getState();
        return network == NetworkInfo.State.CONNECTED
                || network == NetworkInfo.State.CONNECTING;
    }
}
