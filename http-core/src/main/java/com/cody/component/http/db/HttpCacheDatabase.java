/*
 * ************************************************************
 * 文件：HttpCacheDatabase.java  模块：http-core  项目：component
 * 当前修改时间：2019年05月22日 11:01:02
 * 上次修改时间：2019年04月26日 22:46:37
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.cody.component.http.db.data.ItemCacheData;
import com.cody.component.lib.exception.NotInitializedException;

/**
 * Created by xu.yi. on 2019/5/22.
 * HttpCacheDao
 */
@Database(entities = {ItemCacheData.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class HttpCacheDatabase extends RoomDatabase {

    private static final String DB_NAME = "HttpCacheDao.db";

    private static volatile HttpCacheDatabase instance;

    public static HttpCacheDatabase getInstance() {
        if (instance == null) {
            throw new NotInitializedException("HttpCacheDatabase");
        }
        return instance;
    }

    public static void init(Context context) {
        if (instance == null) {
            synchronized (HttpCacheDatabase.class) {
                if (instance == null) {
                    instance = create(context);
                }
            }
        }
    }

    private static HttpCacheDatabase create(final Context context) {
        return Room.databaseBuilder(context, HttpCacheDatabase.class, DB_NAME).build();
    }

    public abstract HttpCacheDao getCacheDao();

}