package vttp2022.ssf.aug12_assessment.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class NewsArticle {

    private String id;
    private Integer publishedOn;
    private String title;
    private String url;
    private String imageUrl;
    private String body;
    private String tags;
    private String categories;
    private String contents;
    private String row;

    public NewsArticle (JsonObject jsonObject) {
        this.id = jsonObject.getString("id");
        this.publishedOn = jsonObject.getInt("published_on");
        this.title = jsonObject.getString("title");
        this.url = jsonObject.getString("url");
        this.imageUrl = jsonObject.getString("imageurl");
        this.body = jsonObject.getString("body");
        this.tags = jsonObject.getString("tags");
        this.categories = jsonObject.getString("categories");
        this.contents = jsonObject.toString();
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", this.id)
                .add("published_on", this.publishedOn)
                .add("title", this.title)
                .add("url", this.url)
                .add("imageurl", this.imageUrl)
                .add("body", this.body)
                .add("tags", this.tags)
                .add("categories", this.categories)
                .build();
    }

    public String getId() {
        return this.id;
    }

    public Integer getPublishedOn() {
        return this.publishedOn;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getBody() {
        return this.body;
    }

    public String getTags() {
        return this.tags;
    }

    public String getCategories() {
        return this.categories;
    }

    public String getContents() {
        return this.contents;
    }

    public void setRow(String row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "NewsArticle [body=" + body + ", categories=" + categories + ", id=" + id + ", imageUrl=" + imageUrl
                + ", publishedOn=" + publishedOn + ", tags=" + tags + ", title=" + title + ", url=" + url + "]";
    }

    public String getRow() {
        return row;
    }

}
