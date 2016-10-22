package org.digitalsprouts.recipesearch.imageloader;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

public interface ImageLoader {

    void loadImageAsync(@NonNull String url, @NonNull ImageView target, @DrawableRes int placeHolder);

    void loadImageAsync(@NonNull String url, @NonNull ImageView target, @DrawableRes int placeHolder, int targetWidth, int targetHeight);
}
