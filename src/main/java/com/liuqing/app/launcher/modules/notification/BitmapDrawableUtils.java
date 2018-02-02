package com.liuqing.app.launcher.modules.notification;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;

/**
 * Created by liuqing
 * 17-8-14.
 * Email: 1239604859@qq.com
 */

public class BitmapDrawableUtils {
    // Decode
    public static Bitmap string2Bitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    // Encode
    public static String bitmap2String(Bitmap bitmap) {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * 通过 BitmapFactory 中的 decodeResource 方法，将资源文件中的 R.drawable.ic_drawable 转化成Bitmap
     */
    public static Bitmap drawable2Bitmap(@NonNull Context context, @DrawableRes int drawableId) {
        Resources res = context.getResources();
        return BitmapFactory.decodeResource(res, drawableId);
    }

    /**
     * 将 Drable 对象先转化成 BitmapDrawableUtils ，然后调用 getBitmap 方法 获取
     */
    public static Bitmap drawable2Bitmap2(@NonNull Context context, @DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    /**
     * 根据已有的Drawable创建一个新的Bitmap
     */
    public static Bitmap drawableToBitmap(@NonNull Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config
                .ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 使用 BitmapDrawable 对 Bitmap 进行强制转换
     */
    public static Drawable bitmap2Drawable(@NonNull Bitmap bitmap) {
        return new BitmapDrawable(null, bitmap);
    }

    /**
     * Bitmap 转换成 byte[]
     */
    public static byte[] getBytes(@NonNull Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * byte[] 转化成 Bitmap
     */
    public static Bitmap Bytes2Bimap(@NonNull byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    // ===========================================================

    /**
     * ColorStateList.valueOf(Color.RED);
     * <p>
     * ColorStateList.valueOf(Color.parseColor("#03A9F4"));
     * <p>
     * int[][] colorStates = new int[][]{
     * new int[]{android.R.attr.state_pressed},
     * new int[]{}
     * };
     * int[] colors = new int[]{ContextCompat.getColor(context, R.color.colorPrimary),
     * Color.TRANSPARENT};
     * ColorStateList colorStateList = new ColorStateList(colorStates, colors);
     */
    public static ColorStateList getStateList(@ColorInt int color) {
        return ColorStateList.valueOf(color);
    }

    /**
     * make drawable color to statelist
     */
    public static Drawable getTintDrawable(Drawable drawable, ColorStateList colorStateList) {
        Drawable dw = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(dw, colorStateList);
        // DrawableCompat.setTintMode(drawable, PorterDuff.Mode.DST_IN);
        return dw;
    }

    /*
     * 注意: getResources().getDrawable(R.mipmap.ic_account_balance_black_24dp).mutate() 方法,
     * 主要是为了防止一个屏幕里同一个图片使用了多次
     * 在操作其中一个drawable的时候会影响到其他的相同的Drawable资源文件的属性
     */

    public static void setViewTint(View view, ColorStateList colorStateList) {
        ViewCompat.setBackgroundTintList(view, colorStateList);
        ViewCompat.setBackgroundTintMode(view, PorterDuff.Mode.DST_IN);
    }
}
