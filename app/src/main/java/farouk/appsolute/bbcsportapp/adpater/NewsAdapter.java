package farouk.appsolute.bbcsportapp.adpater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import farouk.appsolute.bbcsportapp.DetailActivity;
import farouk.appsolute.bbcsportapp.R;
import farouk.appsolute.bbcsportapp.model.NewsModel;
import farouk.appsolute.bbcsportapp.utils.OnItemClickListener;
import farouk.appsolute.bbcsportapp.utils.Utils;
import farouk.appsolute.bbcsportapp.viewHolder.NewsHolder;
import okhttp3.internal.Util;

/**
 * Created by fairuz on 20/04/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater inflater;
    private ArrayList<NewsModel> data;
    private Context context;
    private NewsHolder newsHolder;
    public NewsAdapter(Context context, ArrayList<NewsModel> data) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View news = inflater.inflate(R.layout.newscard, parent, false);
        holder = new NewsHolder(news);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        newsHolder = (NewsHolder) holder;

        newsHolder.getNewsTitle().setText(""+data.get(position).getTitle());
        newsHolder.getNewDescription().setText(""+data.get(position).getDescription());

        String [] t = data.get(position).getPublishedDate().split("T");
        System.out.println("split res: "+t[0]);

        if (!data.get(position).getPublishedDate().equals("null")){
            newsHolder.getNewsDate().setText(""+t[0].split("\\-")[2]+"/"+t[0].split("\\-")[1]+"/"+t[0].split("\\-")[0]+" •• "+t[1].split("\\+")[0].split(":")[0]+":"+t[1].split("\\+")[0].split(":")[1]);
        }else {
            newsHolder.getNewsDate().setText("");
        }

        if (context != null) {
            Glide.with(context)
                    .load(data.get(position).getImageUrl())
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
                    }).placeholder(R.mipmap.ic_launcher).centerCrop().into(newsHolder.getNewsImg());

        }

        newsHolder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onCLick(View view, int position) {
                Intent detail = new Intent(context, DetailActivity.class);

                detail.putExtra(Utils.Json_title, data.get(position).getTitle());
                detail.putExtra(Utils.Json_author, data.get(position).getAuthor());
                detail.putExtra(Utils.Json_publishDate, data.get(position).getPublishedDate());
                detail.putExtra(Utils.Json_description, data.get(position).getDescription());
                detail.putExtra(Utils.Json_fullNewUrl, data.get(position).getFullArticleUrl());
                detail.putExtra(Utils.Json_imgUrl, data.get(position).getImageUrl());

                context.startActivity(detail);
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
