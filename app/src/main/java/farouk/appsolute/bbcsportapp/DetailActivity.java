package farouk.appsolute.bbcsportapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import farouk.appsolute.bbcsportapp.database.Dao;
import farouk.appsolute.bbcsportapp.model.NewsModel;
import farouk.appsolute.bbcsportapp.utils.Utils;

/**
 * Created by fairuz on 20/04/17.
 */

public class DetailActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_WRITE_STORAGE = 1;
    private Intent intent;
    private FloatingActionButton favNews;
    private TextView newsTitle, newsFull, newDescription, newAuthors;
    private ImageView newsImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        intent = getIntent();

        checkPermissionIsGrantOrNot();

        favNews = (FloatingActionButton) findViewById(R.id.fav_news);
        disableFavBtn();

        newsTitle = (TextView) findViewById(R.id.news_title);
        newDescription = (TextView) findViewById(R.id.news_description);
        newAuthors = (TextView) findViewById(R.id.news_author);
        newsFull = (TextView) findViewById(R.id.news_full);
        newsImg = (ImageView) findViewById(R.id.news_img);

        newsTitle.setText(""+intent.getStringExtra(Utils.Json_title));
        newDescription.setText(""+intent.getStringExtra(Utils.Json_description));
        newAuthors.setText(newAuthors.getText()+" "+intent.getStringExtra(Utils.Json_author));
        newsFull.setText(newsFull.getText()+" : "+intent.getStringExtra(Utils.Json_fullNewUrl));


        favNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFav();
                favNews.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart));
                favNews.setEnabled(false);
                Toast.makeText(DetailActivity.this,"Article enrégistré dans les favoris",Toast.LENGTH_SHORT).show();
            }
        });

            Glide.with(DetailActivity.this)
                    .load(intent.getStringExtra(Utils.Json_imgUrl))
                    .asBitmap()
                    .thumbnail(0.1f)
                    .listener(new RequestListener<String, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    }).centerCrop().into(newsImg);

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

            if (id == R.id.action_share) {

                String ShareText = intent.getStringExtra(Utils.Json_title)+" On BBC sport read here: "+intent.getStringExtra(Utils.Json_fullNewUrl);

                Intent textShareIntent = new Intent(Intent.ACTION_SEND);
                textShareIntent.putExtra(Intent.EXTRA_TEXT, ShareText);
                textShareIntent.setType("text/plain");
                startActivity(textShareIntent);
            }

        return super.onOptionsItemSelected(item);
    }

    public void checkPermissionIsGrantOrNot(){
        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) &&
                ((ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_WRITE_STORAGE);
            }
        } else {
           // addToFav();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_WRITE_STORAGE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //addToFav();
                } else {
                }
                return;
            }
        }
    }

    public void disableFavBtn(){
        Dao dao = new Dao(DetailActivity.this);
        if (dao.getAllFavNews().size() > 0){
            for (int i = 0;i<dao.getAllFavNews().size();i++){
                if ((dao.getAllFavNews().get(i).getFullArticleUrl()).equals(""+intent.getStringExtra(Utils.Json_fullNewUrl))){
                    favNews.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart));
                    favNews.setEnabled(false);
                }
            }
        }
    }
    public void addToFav(){
        Dao dao = new Dao(DetailActivity.this);
        if (dao.getAllFavNews().size() > 0){
            for (int i = 0;i<dao.getAllFavNews().size();i++){
                if ((dao.getAllFavNews().get(i).getFullArticleUrl()).equals(""+intent.getStringExtra(Utils.Json_fullNewUrl))){
                    favNews.setVisibility(View.GONE);
                }else {
                    NewsModel newsModel = new NewsModel(intent.getStringExtra(Utils.Json_author),intent.getStringExtra(Utils.Json_title),intent.getStringExtra(Utils.Json_description),
                            intent.getStringExtra(Utils.Json_imgUrl),intent.getStringExtra(Utils.Json_fullNewUrl),intent.getStringExtra(Utils.Json_publishDate));

                    dao.add(newsModel);
                    break;
                }
            }
        }else {
            NewsModel newsModel = new NewsModel(intent.getStringExtra(Utils.Json_author),intent.getStringExtra(Utils.Json_title),intent.getStringExtra(Utils.Json_description),
                    intent.getStringExtra(Utils.Json_imgUrl),intent.getStringExtra(Utils.Json_fullNewUrl),intent.getStringExtra(Utils.Json_publishDate));

            dao.add(newsModel) ;
        }
    }
}
