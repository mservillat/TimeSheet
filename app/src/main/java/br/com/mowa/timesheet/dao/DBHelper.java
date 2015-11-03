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
    public static final String TABLE_PROJECT = "project";
    public static final String PROJECT_COLUMN_ID = "_id";
    public static final String PROJECT_COLUMN_NAME = "name";
    public static final String PROJECT_COLUMN_START_DATE = "start_date";
    public static final String PROJECT_COLUMN_END_DATE = "end_date";
    public static final String PROJECT_COLUMN_ACTIVITE = "activite";
    public static final String PROJECT_COLUMN_DONE = "done";
    public static final String PROJECT_COLUMN_USERS = "users";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table if not exists " + TABLE_PROJECT + " ( " + PROJECT_COLUMN_ID + " text primary key, " + PROJECT_COLUMN_NAME + " text not null, " +
            PROJECT_COLUMN_START_DATE + " text not null, " + PROJECT_COLUMN_END_DATE + " text not null, " + PROJECT_COLUMN_ACTIVITE + " integer not null, " +
            PROJECT_COLUMN_DONE + " integer not null, " + PROJECT_COLUMN_USERS + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
