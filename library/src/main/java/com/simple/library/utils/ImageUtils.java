package com.simple.library.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * package：    com.simple.library.utils
 * author：     XuShuai
 * date：       2017/9/20  10:00
 * version:     v1.0
 * describe：   图片处理工具类
 */
public class ImageUtils {

    private ImageUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断bitmap对象是否为空
     *
     * @param src 源图片
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private static boolean isEmptyBitmap(Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

    /**
     * 获取bitmap
     *
     * @param file 文件
     * @return bitmap
     */
    public static Bitmap getBitmap(File file) throws IOException {
        if (file == null) return null;
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        is.close();
        return bitmap;
    }

    /**
     * 计算采样大小
     *
     * @param options   选项
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 采样大小
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int maxWidth, int
            maxHeight) {
        if (maxWidth == 0 || maxHeight == 0) return 1;
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        while ((height >>= 1) > (maxHeight * 0.8) && (width >>= 1) > (maxWidth * 0.8)) {
            inSampleSize <<= 1;
        }
        return inSampleSize;
    }

    /**
     * 获取bitmap
     *
     * @param file      文件
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(File file, int maxWidth, int maxHeight) throws IOException {
        if (file == null) return null;
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        is = new BufferedInputStream(new FileInputStream(file));
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
        is.close();
        return bitmap;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity 调用方法的activity
     * @return bitmap
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = DensityUtil.getScreenWidth();
        int height = DensityUtil.getScreenHeight();
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity 调用方法的activity
     * @return bitmap
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = DensityUtil.getScreenWidth();
        int height = DensityUtil.getScreenHeight();
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width,
                height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }
}
