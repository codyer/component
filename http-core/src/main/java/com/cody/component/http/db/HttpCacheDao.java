/*
 * ************************************************************
 * 文件：HttpCacheDao.java  模块：http-core  项目：component
 * 当前修改时间：2019年05月22日 10:49:05
 * 上次修改时间：2019年04月26日 22:46:37
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.cody.component.http.db.data.ItemCacheData;

import java.util.List;

/**
 * Created by xu.yi. on 2019/5/22.
 * HttpCacheDao
 */
@Dao
public interface HttpCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ItemCacheData item);

    @Update
    void update(ItemCacheData item);

    @Query("SELECT * FROM http_cache_table WHERE mVersion=:version and mKey=:key order by mRequestDate DESC limit 1")
    ItemCacheData queryCacheByVersionAndKey(String version, String key);

    @Query("SELECT * FROM http_cache_table WHERE mVersion=:version and  mKey =:key order by mRequestDate DESC limit 1")
    LiveData<ItemCacheData> queryCacheByVersionAndKeyObservable(String version, String key);

    @Query("SELECT * FROM http_cache_table")
    List<ItemCacheData> queryAllCache();

    @Query("SELECT * FROM http_cache_table order by mRequestDate ASC limit :limit")
    LiveData<List<ItemCacheData>> queryAllCacheObservable(int limit);

    @Query("SELECT * FROM http_cache_table order by mRequestDate ASC")
    LiveData<List<ItemCacheData>> queryAllCacheObservable();

    @Query("DELETE FROM http_cache_table")
    void deleteAll();
}