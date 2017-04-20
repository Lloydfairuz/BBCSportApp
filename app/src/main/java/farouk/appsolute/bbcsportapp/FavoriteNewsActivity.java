package farouk.appsolute.bbcsportapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import farouk.appsolute.bbcsportapp.adpater.NewsAdapter;
import farouk.appsolute.bbcsportapp.database.Dao;
import farouk.appsolute.bbcsportapp.model.NewsModel;
/**
 * Created by fairuz on 20/04/17.
 */

public class FavoriteNewsActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<NewsModel> newsList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(this);

        Dao dao = new Dao(FavoriteNewsActivity.this);

        NewsModel newsModel = null;
        for (int i = 0; i < dao.getAllFavNews().size(); i++) {
            NewsModel object = null;

                object = dao.getAllFavNews().get(i);
                newsModel = new NewsModel(object.getAuthor(),object.getTitle(),object.getDescription(),
                        object.getImageUrl(),object.getFullArticleUrl(),object.getPublishedDate());

            newsList.add(newsModel);
        }


        System.out.println("the size: "+newsList.size());
        NewsAdapter newsAdapter = new NewsAdapter(FavoriteNewsActivity.this,newsList);
        mRecyclerView.setAdapter(newsAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }
}
