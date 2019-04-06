/*
 * ************************************************************
 * 文件：MonitorHttpInformationDatabase.java  模块：http-monitor  项目：component
 * 当前修改时间：2019年04月05日 18:55:32
 * 上次修改时间：2019年04月05日 18:53:35
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;

import com.cody.http.cat.db.data.ItemMonitorData;
import com.cody.http.cat.utils.Converters;

/**
 * Created by xu.yi. on 2019/4/5.
 * MonitorHttpInformationDatabase
 */
@Database(entities = {ItemMonitorData.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MonitorHttpInformationDatabase extends RoomDatabase {

    private static final String DB_NAME = "MonitorHttpInformation.db";

    private static volatile MonitorHttpInformationDatabase instance;

    public static MonitorHttpInformationDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (MonitorHttpInformationDatabase.class) {
                if (instance == null) {
                    instance = create(context);
                }
            }
        }
        return instance;
    }

    private static MonitorHttpInformationDatabase create(final Context context) {
        return Room.databaseBuilder(context, MonitorHttpInformationDatabase.class, DB_NAME).build();
    }

    public abstract MonitorHttpInformationDao getHttpInformationDao();

}