package farouk.appsolute.bbcsportapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import farouk.appsolute.bbcsportapp.adpater.NewsAdapter;
import farouk.appsolute.bbcsportapp.model.NewsModel;
import farouk.appsolute.bbcsportapp.utils.Utils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList <NewsModel> newsList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new BackgroundTask().execute();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(this);



    }


    private class BackgroundTask extends AsyncTask<Void, Void, JSONArray> {

        private JSONArray Jarr = null;

        @Override
        protected JSONArray doInBackground(Void... params) {


            try {
                JSONObject json = new JSONObject(syncronisation());
                Jarr = json.getJSONArray(Utils.Json_NewsRoot);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return Jarr;
        }

        @Override
        protected void onPostExecute(JSONArray aVoid) {

            super.onPostExecute(aVoid);

            NewsModel newsModel = null;
            for (int i = 0; i < aVoid.length(); i++) {
                JSONObject object = null;
                try {
                    object = aVoid.getJSONObject(i);
                    newsModel = new NewsModel(object.getString(Utils.Json_author),object.getString(Utils.Json_title),object.getString(Utils.Json_description),
                            object.getString(Utils.Json_imgUrl),object.getString(Utils.Json_fullNewUrl),object.getString(Utils.Json_publishDate));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                newsList.add(newsModel);
            }


            System.out.println("the size: "+newsList.size());
            NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this,newsList);
            mRecyclerView.setAdapter(newsAdapter);
            mRecyclerView.setLayoutManager(mLayoutManager);
        }
    }


    public String syncronisation() throws IOException{
        OkHttpClient okHttpClient = new OkHttpClient();
        String result;
        Request request = new Request
                .Builder()
                .url(Utils.Json_Url+getString(R.string.api_key))
                .build();
        Response response = okHttpClient.
                newCall(request).
                execute();
        result = response.body().string();
        System.out.println("result jsonObject: "+result);
        return result;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.my_favs) {
            startActivity(new Intent(MainActivity.this, FavoriteNewsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
