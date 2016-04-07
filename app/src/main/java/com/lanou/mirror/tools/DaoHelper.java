package com.lanou.mirror.tools;

import com.lanou.mirror.base.BaseApplication;
import com.lanou.mirror.greendao.DaoMaster;
import com.lanou.mirror.greendao.DaoSession;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by dllo on 16/4/7.
 */
public class DaoHelper {
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    QueryBuilder builder;


    public DaoHelper() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(BaseApplication.getContext(),"cache",null);
        daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        builder = daoSession.getCacheDao().queryBuilder();


    }
}
