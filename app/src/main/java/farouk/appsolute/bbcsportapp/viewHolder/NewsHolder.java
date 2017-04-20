package farouk.appsolute.bbcsportapp.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import farouk.appsolute.bbcsportapp.R;
import farouk.appsolute.bbcsportapp.utils.OnItemClickListener;

/**
 * Created by fairuz on 20/04/17.
 */

public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView newsTitle, newsDate, newDescription;
    private ImageView newsImg;
    private OnItemClickListener mItemClickListener;

    public NewsHolder(View itemView) {
        super(itemView);

        newsTitle = (TextView) itemView.findViewById(R.id.news_title);
        newsDate = (TextView) itemView.findViewById(R.id.news_time);
        newDescription = (TextView) itemView.findViewById(R.id.description);
        newsImg = (ImageView) itemView.findViewById(R.id.news_img);
        itemView.setOnClickListener(this);
    }

    public TextView getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(TextView newsTitle) {
        this.newsTitle = newsTitle;
    }

    public TextView getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(TextView newsDate) {
        this.newsDate = newsDate;
    }

    public TextView getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(TextView newDescription) {
        this.newDescription = newDescription;
    }

    public ImageView getNewsImg() {
        return newsImg;
    }

    public void setNewsImg(ImageView newsImg) {
        this.newsImg = newsImg;
    }

    @Override
    public void onClick(View view) {
        if(view != null && mItemClickListener != null ) {
            mItemClickListener.onCLick(view, getPosition());
        }
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
