package com.liuqing.app.launcher.modules.applist.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuqing.app.launcher.R;
import com.liuqing.app.launcher.modules.applist.bean.ApplicationInformation;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;
import org.byteam.superadapter.animation.SlideInRightAnimation;

import java.util.List;

/**
 * Created by liuqing
 * 17-10-12.
 * Email: 1239604859@qq.com
 */

public class AppListAdapter extends SuperAdapter<ApplicationInformation> {
    public AppListAdapter(Context context,
                          List<ApplicationInformation> items,
                          @LayoutRes int layoutResId) {
        super(context, items, layoutResId);
        enableLoadAnimation(200, new SlideInRightAnimation());
        setOnlyOnce(false);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition,
                       ApplicationInformation item) {
        ImageView appIcon = holder.findViewById(R.id.app_icon);
        appIcon.setImageDrawable(item.getAppIcon());

        TextView appName = holder.findViewById(R.id.app_name);
        appName.setText(item.getAppLabel());
    }
}
