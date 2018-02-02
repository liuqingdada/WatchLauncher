split app whit “function/profile/module”;

alipay: just a app widget, see Alipay apk.

home: a VerticalViewPager, it contain 3 chile fragment or child "function/profile/module".
    1) see quicksettings module.
    2) black fragment.
    3) see notification module.

launch: app access point, and service.
    1) a HorizontalViewPager, it contain 3 chile fragment or child "function/profile/module".

quicksettings: a YViewPager; some system settings for android.

notification: watch notification, and phone notification (by BLE).

watchface: wallpaper for watch.