package org.digitalsprouts.recipesearchclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getRecipeUrl() {
        return recipeUrl;
    }

    public String getTitle() {
        return title;
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

    // Generated setters, used for tests

    void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    void setRecipeUrl(String recipeUrl) {
        this.recipeUrl = recipeUrl;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    void setPublisherUrl(String publisherUrl) {
        this.publisherUrl = publisherUrl;
    }

    void setSocialRank(Double socialRank) {
        this.socialRank = socialRank;
    }

    // Generated HashCode and equal

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (thumbnailUrl != null ? !thumbnailUrl.equals(recipe.thumbnailUrl) : recipe.thumbnailUrl != null)
            return false;
        if (sourceUrl != null ? !sourceUrl.equals(recipe.sourceUrl) : recipe.sourceUrl != null) return false;
        if (!recipeUrl.equals(recipe.recipeUrl)) return false;
        if (!title.equals(recipe.title)) return false;
        if (publisher != null ? !publisher.equals(recipe.publisher) : recipe.publisher != null) return false;
        if (publisherUrl != null ? !publisherUrl.equals(recipe.publisherUrl) : recipe.publisherUrl != null)
            return false;
        return socialRank != null ? socialRank.equals(recipe.socialRank) : recipe.socialRank == null;

    }

    @Override
    public int hashCode() {
        return recipeUrl.hashCode();
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
