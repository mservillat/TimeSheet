package br.com.mowa.timesheet.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by walky on 10/1/15.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TimeSheetDB";

    // statics da tabela project
    public static final String TIMESHEET_TABLE_PROJECT = "project";
    public static final String TIMESHEET_PROJECT_COLUMN_ID = "_id";
    public static final String TIMESHHET_PROJECT_COLUMN_NAME = "name";
    public static final String TIMESHEET_PROJECT_COLUMN_START_DATE = "start_date";
    public static final String TIMESHEET_PROJECT_COLUMN_END_DATE = "start_end";
    public static final String TIMESHEET_PROJECT_COLUMN_ACTIVITE = "activite";
    public static final String TIMESHEET_PROJECT_COLUMN_DONE = "done";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
