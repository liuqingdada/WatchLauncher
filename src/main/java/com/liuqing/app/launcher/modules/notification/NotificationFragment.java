package com.liuqing.app.launcher.modules.notification;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.liuqing.app.launcher.INotificationInterface;
import com.liuqing.app.launcher.R;
import com.liuqing.app.launcher.customview.utils.PsUtils;
import com.liuqing.app.launcher.modules.notification.bean.WatchNotification;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMovementListener;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemStateChangedListener;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by liuqing
 * 17-10-9.
 * Email: 1239604859@qq.com
 */

public class NotificationFragment extends Fragment implements SwipeItemClickListener {
    private static final String TAG = "NotificationFragment";
    private List<WatchNotification> mNotifications = new CopyOnWriteArrayList<>();
    private SwipeMenuRecyclerView mRecyclerView;

    private NotificationAdapter mNotificationAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performNotificationService();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.notification_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNotificationAdapter = new NotificationAdapter(getActivity(), mNotifications,
                                                       R.layout.item_menu_notification);
        mRecyclerView.addItemDecoration(new DefaultItemDecoration(
                ContextCompat.getColor(getActivity(), R.color.white)));
        mRecyclerView.setSwipeMenuCreator(mMenuCreator);
        mRecyclerView.setLongPressDragEnabled(true); // 长按拖拽，默认关闭。
        mRecyclerView.setItemViewSwipeEnabled(true); // 滑动删除，默认关闭。

        mRecyclerView.setSwipeItemClickListener(this);
        mRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        mRecyclerView.setOnItemMoveListener(onItemMoveListener);// 监听拖拽和侧滑删除，更新UI和数据源。
        mRecyclerView.setOnItemStateChangedListener(mOnItemStateChangedListener);
        mRecyclerView.setOnItemMovementListener(mOnItemMovementListener);

        mRecyclerView.addHeaderView(
                View.inflate(getActivity(), R.layout.item_menu_notification, null));
        mRecyclerView.addHeaderView(
                View.inflate(getActivity(), R.layout.item_menu_notification, null));
        mRecyclerView.addHeaderView(
                View.inflate(getActivity(), R.layout.item_menu_notification, null));

        mRecyclerView.setAdapter(mNotificationAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Toast.makeText(getActivity(), "第" + position + "个", Toast.LENGTH_SHORT)
             .show();
    }

    /**
     * Item的拖拽/侧滑删除时，手指状态发生变化监听。
     */
    private OnItemStateChangedListener mOnItemStateChangedListener = new
            OnItemStateChangedListener() {
                @Override
                public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                    if (actionState == OnItemStateChangedListener.ACTION_STATE_DRAG) {
                        // 状态：拖拽
                        // 拖拽的时候背景就透明了，这里我们可以添加一个特殊背景
                        viewHolder.itemView.setBackgroundColor(
                                ContextCompat.getColor(getActivity(), R.color.transparency));
                    } else if (actionState == OnItemStateChangedListener.ACTION_STATE_SWIPE) {
                        // 滑动删除
                    } else if (actionState == OnItemStateChangedListener.ACTION_STATE_IDLE) {
                        // 状态：手指松开
                        // 在手松开的时候还原背景
                        Drawable drawable = ContextCompat.getDrawable(getActivity(),
                                                                      android.R.color
                                                                              .holo_orange_light);
                        ViewCompat.setBackground(viewHolder.itemView, drawable);
                    }
                }
            };

    private OnItemMovementListener mOnItemMovementListener = new OnItemMovementListener() {
        @Override
        public int onDragFlags(RecyclerView recyclerView,
                               RecyclerView.ViewHolder targetViewHolder) {
            return 0;
        }

        @Override
        public int onSwipeFlags(RecyclerView recyclerView,
                                RecyclerView.ViewHolder targetViewHolder) {
            return 0;
        }
    };

    /**
     * 监听拖拽和侧滑删除，更新UI和数据源。
     */
    private OnItemMoveListener onItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder,
                                  RecyclerView.ViewHolder targetHolder) {
            // 不同的ViewType不能拖拽换位置。
            if (srcHolder.getItemViewType() != targetHolder.getItemViewType()) {
                return false;
            }

            // 添加了HeadView时，通过ViewHolder拿到的position都需要减掉HeadView的数量
            int fromPosition = srcHolder.getAdapterPosition() - mRecyclerView.getHeaderItemCount();
            int toPosition = targetHolder.getAdapterPosition() - mRecyclerView.getHeaderItemCount();
            Log.d(TAG, "onItemMove: " + fromPosition);
            Log.d(TAG, "onItemMove: " + toPosition);

            Collections.swap(mNotifications, fromPosition, toPosition);
            mNotificationAdapter.notifyItemMoved(fromPosition, toPosition);
            return true; // 返回true表示处理了并可以换位置，返回false表示你没有处理并不能换位置
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
            int adapterPosition = srcHolder.getAdapterPosition();
            int position = adapterPosition - mRecyclerView.getHeaderItemCount();

            if (mRecyclerView.getHeaderItemCount() > 0 && adapterPosition >= 0) { // HeaderView
                mRecyclerView.removeHeaderView(srcHolder.itemView);
                Toast.makeText(getActivity(), "HeaderView被删除。", Toast.LENGTH_SHORT)
                     .show();

            } else { // 普通Item
                if (position < 0) {
                    return;
                }
                mNotifications.remove(position);
                mNotificationAdapter.notifyItemRemoved(position);
                Toast.makeText(getActivity(), "现在的第" + position + "条被删除。", Toast.LENGTH_SHORT)
                     .show();
            }
        }
    };

    private SwipeMenuCreator mMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = PsUtils.dp2px(getActivity(), 70);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 左侧
            {
                SwipeMenuItem addItem = new SwipeMenuItem(getActivity())
                        .setBackground(R.drawable.selector_green)
                        .setImage(R.mipmap.ic_action_add)
                        .setWidth(width)
                        .setHeight(height);
                swipeLeftMenu.addMenuItem(addItem);

                SwipeMenuItem closeItem = new SwipeMenuItem(getActivity())
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_close)
                        .setWidth(width)
                        .setHeight(height);
                swipeLeftMenu.addMenuItem(closeItem);
            }

            // 右侧
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText(R.string.item_delete)
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);

                SwipeMenuItem addItem = new SwipeMenuItem(getActivity())
                        .setBackground(R.drawable.selector_green)
                        .setText(R.string.item_add)
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem);
            }
        }
    };

    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左右
            int adapterPosition = menuBridge.getAdapterPosition(); // recyclerview item position
            int menuPosition = menuBridge.getPosition(); // item memu position

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(getActivity(), "list第" + adapterPosition + "; 右侧菜单第" + menuPosition,
                               Toast.LENGTH_SHORT)
                     .show();

            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(getActivity(), "list第" + adapterPosition + "; 左侧菜单第" + menuPosition,
                               Toast.LENGTH_SHORT)
                     .show();
            }
        }
    };

    private INotificationInterface mNotificationInterface = new INotificationInterface.Stub() {
        @Override
        public void onNotificationPosted(StatusBarNotification sbn) throws RemoteException {
            WatchNotification wn = new WatchNotification.Builder(getActivity()).setSbn(sbn)
                                                                               .build();
            mNotifications.add(0, wn);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mNotificationAdapter.notifyDataSetHasChanged();
                }
            });
        }

        @Override
        public void onNotificationRemoved(StatusBarNotification sbn) throws RemoteException {
        }

        @Override
        public void setINotificationListener(INotificationInterface inl) throws RemoteException {}
    };

    private void performNotificationService() {
        try {
            Intent intent = new Intent(getActivity(), SystemNotificationService.class);
            getActivity().startServiceAsUser(intent, UserHandle.CURRENT_OR_SELF);
            getActivity().bindServiceAsUser(intent,
                                            mNotificationInterfaceConn,
                                            Context.BIND_AUTO_CREATE,
                                            UserHandle.CURRENT_OR_SELF);
        } catch (Exception e) {
            Log.e(TAG, "performNotificationService: ", e);
        }
    }

    private ServiceConnection mNotificationInterfaceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: INotificationInterfaceConn");
            SystemNotificationService.WatchNotificationInterface watchNotificationBinder =
                    (SystemNotificationService.WatchNotificationInterface)
                            INotificationInterface.Stub.asInterface(service);
            try {
                watchNotificationBinder.setINotificationListener(mNotificationInterface);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.w(TAG, "onServiceDisconnected: INotificationInterfaceConn");
            performNotificationService();
        }
    };
}
