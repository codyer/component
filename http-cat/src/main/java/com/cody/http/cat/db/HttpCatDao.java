/*
 * ************************************************************
 * 文件：HttpCatDao.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月05日 18:55:32
 * 上次修改时间：2019年04月05日 18:53:35
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.cody.http.cat.db.data.ItemHttpData;

/**
 * Created by xu.yi. on 2019/4/5.
 * HttpCatDao
 */
@Dao
public interface HttpCatDao {

    @Insert
    long insert(ItemHttpData model);

    @Update
    void update(ItemHttpData model);

    @Query("SELECT * FROM http_cat_table WHERE id =:id")
    LiveData<ItemHttpData> queryRecordObservable(long id);

    @Query("SELECT * FROM http_cat_table")
    List<ItemHttpData> queryAllRecord();

    @Query("SELECT * FROM http_cat_table order by id desc limit :limit")
    LiveData<List<ItemHttpData>> queryAllRecordObservable(int limit);

    @Query("SELECT * FROM http_cat_table order by id desc")
    LiveData<List<ItemHttpData>> queryAllRecordObservable();

    @Query("DELETE FROM http_cat_table")
    void deleteAll();

}