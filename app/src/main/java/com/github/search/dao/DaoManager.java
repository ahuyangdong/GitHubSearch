package com.github.search.dao;

import com.github.search.application.AppData;
import com.github.search.greendao.DaoMaster;
import com.github.search.greendao.DaoSession;

/**
 * 数据库层管理器
 */
public class DaoManager {
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private static DaoManager daoManager = new DaoManager();

    private DaoManager() {
        init();
    }

    public static DaoManager getInstance() {
        return daoManager;
    }

    /**
     * 初始化数据库服务
     */
    public void init() {
        // 建表
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(AppData.getInstance().getApplicationContext(), "github-search.db");
        daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
