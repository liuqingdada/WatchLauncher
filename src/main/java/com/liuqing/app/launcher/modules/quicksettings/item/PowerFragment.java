package com.liuqing.app.launcher.modules.quicksettings.item;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuqing.app.launcher.R;
import com.liuqing.app.launcher.test.TestActivity;
import com.liuqing.app.launcher.modules.mvptest.view.LoginActivity;

/**
 * Created by liuqing
 * 17-10-10.
 * Email: 1239604859@qq.com
 */

public class PowerFragment extends Fragment implements View.OnClickListener {
    private static final int NOTIFICATION_ADD_ID = 1000;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.qs_item_power, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.send_notification)
            .setOnClickListener(this);

        view.findViewById(R.id.os_api_setbklight)
            .setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_notification:
                sendNotification();
                break;

            case R.id.os_api_setbklight:
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }

    private void sendNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getActivity(), 0, new Intent(getActivity(), TestActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.mipmap.defaultnotification)
                .setContentTitle("Mstarc Notification")
                .setContentText(getResources().getString(R.string.long_hello))
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getActivity()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(NOTIFICATION_ADD_ID, notification);
    }
}
