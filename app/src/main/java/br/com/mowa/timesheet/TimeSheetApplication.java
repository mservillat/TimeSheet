package br.com.mowa.timesheet;

import android.app.Application;
import android.content.Context;

/**
 * Created by walky on 9/11/15.
 */
public class TimeSheetApplication extends Application {
    private static TimeSheetApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static TimeSheetApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
