package org.digitalsprouts.recipesearch.imageloader;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

public interface ImageLoader {

    void loadImageAsync(@NonNull String url, @NonNull ImageView target, @DrawableRes int placeHolder);
}
