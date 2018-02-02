package com.liuqing.app.launcher.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.liuqing.app.launcher.database.greendao.DaoMaster;

import org.greenrobot.greendao.database.Database;

public class MigrationOpenHelper extends DaoMaster.OpenHelper {

    public MigrationOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * 数据库升级
     *
     * @param db Database
     * @param oldVersion oldVersion
     * @param newVersion newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        // 操作数据库的更新 有几个表升级都可以传入到下面
        // MigrationHelper.getInstance().migrate(db,StudentDao.class);
    }

}