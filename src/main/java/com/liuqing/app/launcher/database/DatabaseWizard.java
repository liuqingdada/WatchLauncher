package com.liuqing.app.launcher.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.liuqing.app.launcher.database.greendao.DaoMaster;
import com.liuqing.app.launcher.database.greendao.DaoSession;
import com.liuqing.app.launcher.database.helper.MigrationOpenHelper;

/**
 * Created by liuqing
 * 2017/3/14.
 * Email: 1239604859@qq.com
 */

public class DatabaseWizard {
    private static DatabaseWizard sDatabaseWizard = new DatabaseWizard();
    private static SQLiteDatabase db;
    private static DaoMaster mDaoMaster;
    private static DaoSession daoSession;

    public static DatabaseWizard getInstance() {
        return sDatabaseWizard;
    }

    /**
     * 设置greenDao
     */
    public void setDatabase(Context context) {
        MigrationOpenHelper helper = new MigrationOpenHelper(context.getApplicationContext(),
                                                             "app.launcher.db", null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        daoSession = mDaoMaster.newSession();
    }

    public synchronized DaoSession getDaoSession() {
        return daoSession;
    }

    public synchronized SQLiteDatabase getDb() {
        return db;
    }

    public synchronized DaoMaster getDaoMaster() {
        return mDaoMaster;
    }
}
