package farouk.appsolute.bbcsportapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import farouk.appsolute.bbcsportapp.utils.Utils;

/**
 * Created by fairuz on 20/04/17.
 */

public class DBmanager extends SQLiteOpenHelper {
    public DBmanager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    public static String createNewsTable = "CREATE TABLE IF NOT EXISTS " +
            Utils.Json_NewsRoot + " (" +
            Utils.id+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Utils.Json_title+ " TEXT," +
            Utils.Json_description + " TEXT, " +
            Utils.Json_author + " TEXT, " +
            Utils.Json_fullNewUrl+ " TEXT, " +
            Utils.Json_imgUrl+ " TEXT, " +
            Utils.Json_publishDate + " TEXT)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createNewsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}