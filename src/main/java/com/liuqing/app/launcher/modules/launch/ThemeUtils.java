package com.liuqing.app.launcher.modules.launch;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * description
 * <p/>
 * Created by andyding on 2017/6/17.
 */

public class ThemeUtils {

    public final static String PRODUCT_COLOR_ROSE_GOLDEN = "1";
    public final static String PRODUCT_COLOR_HIGH_BLACK = "2";
    public final static String PRODUCT_COLOR_APPLE_GREEN = "3";
    public final static String PRODUCT_COLOR_G6 = "4";

    private final static int COLOR_ROSE_GOLDEN = 0xFFFF1D79;
    private final static int COLOR_HIGH_BLACK = 0xFFF2D34E;
    private final static int COLOR_APPLE_GREEN = 0xFFAFFF00;

    private final static int COLOR_G6_PLUS = 0xFFEBB553;

    public static String getProperty(String key, String defaultValue) {
        String value = defaultValue;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String)(get.invoke(c, key, value ));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return value;
        }
    }

    public static boolean isProductG7() {
        return "g7".equals(getProperty("ro.product.watch", "g6+"));
    }

    public static String getCurrentProduct() {
        if (isProductG7()) {
            return getProperty("ro.product.watch_theme_g7", PRODUCT_COLOR_APPLE_GREEN);
        } else {
            return PRODUCT_COLOR_G6;
        }
    }

    public static int getCurrentPrimaryColor() {
        if (isProductG7()) {
//        if (true) {
            String color = getProperty("ro.product.watch_theme_g7", PRODUCT_COLOR_APPLE_GREEN);
            Log.d("dingyichen", "color = " + color);
            switch (getProperty("ro.product.watch_theme_g7", PRODUCT_COLOR_APPLE_GREEN)) {
                case PRODUCT_COLOR_HIGH_BLACK:
                    return COLOR_HIGH_BLACK;
                case PRODUCT_COLOR_APPLE_GREEN:
                    return COLOR_APPLE_GREEN;
                default:
                    return COLOR_ROSE_GOLDEN;
            }
        } else {
            return COLOR_G6_PLUS;
        }
    }
}
