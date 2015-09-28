package br.com.mowa.timesheet.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by walky on 9/28/15.
 */
public class IsConnectionNetworkAvailable {
    private static String TAG = "TIMESHEET - IsConnectionNetworkAvailable";

    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                return false;
            } else {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }

            }
        } catch (SecurityException e) {
            Log.d(TAG , "Conexão inexistente ");

        }
        return false;
    }
}
