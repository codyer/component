/*
 * ************************************************************
 * 文件：HttpCatDatabase.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月06日 16:59:54
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;

import com.cody.http.cat.db.data.ItemHttpData;
import com.cody.http.cat.utils.Converters;

/**
 * Created by xu.yi. on 2019/4/5.
 * HttpCatDatabase
 */
@Database(entities = {ItemHttpData.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class HttpCatDatabase extends RoomDatabase {

    private static final String DB_NAME = "HttpCat.db";

    private static volatile HttpCatDatabase instance;

    public static HttpCatDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (HttpCatDatabase.class) {
                if (instance == null) {
                    instance = create(context);
                }
            }
        }
        return instance;
    }

    private static HttpCatDatabase create(final Context context) {
        return Room.databaseBuilder(context, HttpCatDatabase.class, DB_NAME).build();
    }

    public abstract HttpCatDao getHttpInformationDao();

}