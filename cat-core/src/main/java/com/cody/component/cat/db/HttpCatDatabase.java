/*
 * ************************************************************
 * 文件：HttpCatDatabase.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.cat.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.cody.component.cat.db.data.ItemHttpData;
import com.cody.component.cat.exception.NotInitDataBaseException;
import com.cody.component.cat.utils.Converters;

/**
 * Created by xu.yi. on 2019/4/5.
 * HttpCatDatabase
 */
@Database(entities = {ItemHttpData.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class HttpCatDatabase extends RoomDatabase {

    private static final String DB_NAME = "HttpCat.db";

    private static volatile HttpCatDatabase instance;

    public static HttpCatDatabase getInstance() {
        if (instance == null) {
           throw new NotInitDataBaseException();
        }
        return instance;
    }

    public static void init(Context context) {
        if (instance == null) {
            synchronized (HttpCatDatabase.class) {
                if (instance == null) {
                    instance = create(context);
                }
            }
        }
    }

    private static HttpCatDatabase create(final Context context) {
        return Room.databaseBuilder(context, HttpCatDatabase.class, DB_NAME).build();
    }

    public abstract HttpCatDao getHttpInformationDao();

}