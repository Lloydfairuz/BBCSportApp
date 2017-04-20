package farouk.appsolute.bbcsportapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import farouk.appsolute.bbcsportapp.model.NewsModel;
import farouk.appsolute.bbcsportapp.utils.Utils;

/**
 * Created by fairuz on 20/04/17.
 */

public class Dao  {
    private final DBmanager db;
    private final String dbName = "favNews";
    private final int version = 1;
    private final SQLiteDatabase getWDB;
    private final SQLiteDatabase getRDB;

    public Dao(Context context) {
        db = new DBmanager(context, this.dbName, null, this.version);
        getWDB = db.getWritableDatabase();
        getRDB = db.getReadableDatabase();

    }


    public Long add(NewsModel model) {
        ContentValues value = new ContentValues();

        value.put(Utils.Json_title, model.getTitle());
        value.put(Utils.Json_description, model.getDescription());
        value.put(Utils.Json_author, model.getAuthor());
        value.put(Utils.Json_fullNewUrl, model.getFullArticleUrl());
        value.put(Utils.Json_imgUrl, model.getImageUrl());
        value.put(Utils.Json_publishDate, model.getPublishedDate());


        Long status = null;
        getWDB.beginTransaction();
        try {

            status = getWDB.insert(Utils.Json_NewsRoot, null, value);

            getWDB.setTransactionSuccessful();

        } finally {
            getWDB.endTransaction();
        }

        return status;
    }

    public ArrayList<NewsModel> getAllFavNews() {
        ArrayList<NewsModel> data = new ArrayList<NewsModel>();

        getRDB.beginTransaction();
        try {
            Cursor cs = getRDB.query(true, Utils.Json_NewsRoot, new String[]{"*"}, null, null, null, null, null, null);
            if (cs != null) {
                while (cs.moveToNext()) {
                    NewsModel news = new NewsModel(cs.getString(3), cs.getString(1),cs.getString(2),cs.getString(5),cs.getString(4),cs.getString(6));
                    System.out.println(" cursor 0 cest l'id: " + cs.getString(0) + " cursor 1: " + cs.getString(1) + "");
                    data.add(news);
                }
            } else System.out.println(" le curseur est null ");
            getRDB.setTransactionSuccessful();
        } finally {
            getRDB.endTransaction();
        }
        return data;

    }
}
