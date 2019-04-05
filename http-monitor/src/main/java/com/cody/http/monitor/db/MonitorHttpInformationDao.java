/*
 * ************************************************************
 * 文件：MonitorHttpInformationDao.java  模块：http-monitor  项目：component
 * 当前修改时间：2019年04月05日 18:55:32
 * 上次修改时间：2019年04月05日 18:53:35
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.monitor.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.cody.http.monitor.db.data.ItemMonitorData;

/**
 * Created by xu.yi. on 2019/4/5.
 * MonitorHttpInformationDao
 */
@Dao
public interface MonitorHttpInformationDao {

    @Insert
    long insert(ItemMonitorData model);

    @Update
    void update(ItemMonitorData model);

    @Query("SELECT * FROM ItemMonitorData WHERE id =:id")
    LiveData<ItemMonitorData> queryRecordObservable(long id);

    @Query("SELECT * FROM ItemMonitorData")
    List<ItemMonitorData> queryAllRecord();

    @Query("SELECT * FROM ItemMonitorData order by id desc limit :limit")
    LiveData<List<ItemMonitorData>> queryAllRecordObservable(int limit);

    @Query("SELECT * FROM ItemMonitorData order by id desc")
    LiveData<List<ItemMonitorData>> queryAllRecordObservable();

    @Query("DELETE FROM ItemMonitorData")
    void deleteAll();

}