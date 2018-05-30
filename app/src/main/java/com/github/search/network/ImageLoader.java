package com.github.search.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * 网络图片加载器
 */
public class ImageLoader {
    /**
     * 加载网络图片
     * @param context
     * @param imageView 图片控件
     * @param url       图片地址
     * @param defaultId 默认图资源
     */
    public static void loadImage(Context context, ImageView imageView, String url, int defaultId) {
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .error(defaultId)
                .placeholder(defaultId)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     * @param context
     * @param imageView 图片控件
     * @param url       图片地址
     * @param defaultId 默认图资源
     */
    public static void loadRoundImage(final Context context, final ImageView imageView, String url, int defaultId) {
        Glide.with(context).load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .error(defaultId)
                .placeholder(defaultId)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

}
