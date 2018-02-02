package com.liuqing.app.launcher.modules.watchface;

import android.app.WallpaperInfo;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liuqing.app.launcher.R;

import java.util.List;

/**
 * Created by liuqing
 * 17-10-11.
 * Email: 1239604859@qq.com
 */

class WatchFaceSelectAdapter extends RecyclerView.Adapter<WatchFaceSelectAdapter.ViewHolder> {
    private Context mContext;
    private List<WallpaperInfo> wallpaperList;

    WatchFaceSelectAdapter(Context context, List<WallpaperInfo> wallpaperList) {
        mContext = context;
        this.wallpaperList = wallpaperList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_watchface, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.btWallpaper.setImageDrawable(
                wallpaperList.get(position)
                             .loadThumbnail(mContext.getPackageManager()));
    }

    @Override
    public int getItemCount() {
        return this.wallpaperList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView btWallpaper;

        ViewHolder(View itemView) {
            super(itemView);
            btWallpaper = (ImageView) itemView.findViewById(R.id.title);
        }
    }
}
