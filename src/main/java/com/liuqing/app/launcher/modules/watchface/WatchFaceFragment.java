package com.liuqing.app.launcher.modules.watchface;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuqing.app.launcher.R;

/**
 * Created by liuqing
 * 17-10-9.
 * Email: 1239604859@qq.com
 */

public class WatchFaceFragment extends Fragment implements View.OnLongClickListener {
    private static final String TAG = "WatchFaceFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_empty, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.watchface_fragment)
            .setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), WatchFaceSelectActivity.class));
        return false;
    }
}
