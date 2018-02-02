package com.liuqing.app.launcher.modules.notification;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.Log;

import com.liuqing.app.launcher.R;
import com.liuqing.app.launcher.modules.notification.bean.WatchNotification;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by liuqing
 * 17-10-11.
 * Email: 1239604859@qq.com
 */

public class NotificationAdapter extends SuperAdapter<WatchNotification> {
    private static final String TAG = "NotificationAdapter";

    public NotificationAdapter(Context context,
                               List<WatchNotification> items,
                               @LayoutRes int layoutResId) {
        super(context, items, layoutResId);
        enableLoadAnimation();
        setOnlyOnce(false);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition,
                       WatchNotification item) {
        try {
            holder.setText(R.id.tv_title, item.getTitle());
            holder.setText(R.id.tv_content, item.getContent());
            holder.setImageDrawable(R.id.iv_icon, item.getIconDrawable());
        } catch (Exception e) {
            Log.e(TAG, "onBind: ", e);
        }
    }
}
