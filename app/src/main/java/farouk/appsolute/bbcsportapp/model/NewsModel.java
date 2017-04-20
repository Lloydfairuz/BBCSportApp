package farouk.appsolute.bbcsportapp.model;

/**
 * Created by fairuz on 20/04/17.
 */

public class NewsModel {

    private String author;
    private String title;
    private String description;
    private String imageUrl;
    private String fullArticleUrl;
    private String publishedDate;

    public NewsModel() {
    }

    public NewsModel(String author, String title, String description, String imageUrl, String fullArticleUrl, String publishedDate) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.fullArticleUrl = fullArticleUrl;
        this.publishedDate = publishedDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFullArticleUrl() {
        return fullArticleUrl;
    }

    public void setFullArticleUrl(String fullArticleUrl) {
        this.fullArticleUrl = fullArticleUrl;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }
}
