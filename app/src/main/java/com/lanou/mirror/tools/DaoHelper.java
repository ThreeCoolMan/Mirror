package com.lanou.mirror.tools;

import android.util.Log;

import com.lanou.mirror.base.BaseApplication;
import com.lanou.mirror.greendao.Cache;
import com.lanou.mirror.greendao.CacheDao;
import com.lanou.mirror.greendao.DaoMaster;
import com.lanou.mirror.greendao.DaoSession;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by dllo on 16/4/7.
 */
public class DaoHelper {
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private CacheDao cacheDao;
    QueryBuilder builder;


    public DaoHelper() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(BaseApplication.getContext(), "cache", null);
        daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        builder = daoSession.getCacheDao().queryBuilder();


    }

    public long addData(Cache cache) {

        return daoSession.insert(cache);

    }

    public void deleteAll() {
cacheDao.deleteAll();
    }
}
