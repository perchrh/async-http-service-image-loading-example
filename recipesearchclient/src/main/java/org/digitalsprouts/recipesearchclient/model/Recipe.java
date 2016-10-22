package org.digitalsprouts.recipesearchclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe implements Parcelable {

    @SerializedName("image_url")
    private String thumbnailUrl; // URL of the image

    @SerializedName("source_url")
    private String sourceUrl; // Original Url of the recipe on the publisher's site

    @SerializedName("f2f_url")
    private String recipeUrl; // Url of the recipe on Food2Fork.com

    @SerializedName("title")
    private String title; // Title of the recipe

    @SerializedName("publisher")
    private String publisher; // Name of the publisher

    @SerializedName("publisher_url")
    private String publisherUrl; // Base url of the publisher

    @SerializedName("social_rank")
    private Double socialRank; // The Social Ranking of the Recipe (As determined by our Ranking Algorithm), max = 100

    // The docs say there is a 'ingredients' field, but it never appears in data, and the format is not documented
    // Don't attempt to read the unknown data
    //@SerializedName("ingredients")
    //private List<String> ingredients; // The ingredients of this recipe

    // Generated getters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getRecipeUrl() {
        return recipeUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublisherUrl() {
        return publisherUrl;
    }

    public Double getSocialRank() {
        return socialRank;
    }

    // Generated Parcelable implementation

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.thumbnailUrl);
        dest.writeString(this.sourceUrl);
        dest.writeString(this.recipeUrl);
        dest.writeString(this.title);
        dest.writeString(this.publisher);
        dest.writeString(this.publisherUrl);
        dest.writeValue(this.socialRank);
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        this.thumbnailUrl = in.readString();
        this.sourceUrl = in.readString();
        this.recipeUrl = in.readString();
        this.title = in.readString();
        this.publisher = in.readString();
        this.publisherUrl = in.readString();
        this.socialRank = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

}
