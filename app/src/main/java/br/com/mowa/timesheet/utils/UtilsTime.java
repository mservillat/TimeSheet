package br.com.mowa.timesheet.utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by walky on 10/23/15.
 */
public class UtilsTime {

    public static String longMillisToString(Long time) {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(time),
                    TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1));

    }
}
