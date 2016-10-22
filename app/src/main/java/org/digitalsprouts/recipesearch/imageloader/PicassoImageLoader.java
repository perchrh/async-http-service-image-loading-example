package org.digitalsprouts.recipesearch.imageloader;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

public class PicassoImageLoader implements ImageLoader {

    private final Picasso picasso;
    private final OkHttp3Downloader downloader;

    public PicassoImageLoader(@NonNull Context context) {
        downloader = createDownloader();
        picasso = createPicasso(context);
    }

    private Picasso createPicasso(@NonNull Context context) {
        return new Picasso.Builder(context).downloader(downloader).build();
    }

    @NonNull
    private OkHttp3Downloader createDownloader() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        return new OkHttp3Downloader(client);
    }

    @Override
    public void loadImageAsync(@NonNull String url, @NonNull ImageView target, @DrawableRes int placeHolder) {
        picasso.load(url).
                resize(120, 60). // TODO magic constant
                placeholder(placeHolder). //TODO placeholder could be optional
                into(target);
    }
}
