package com.lanou.mirror.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lanou.mirror.base.BaseApplication;
import com.lanou.mirror.greendao.Cache;
import com.lanou.mirror.greendao.CacheDao;
import com.lanou.mirror.greendao.DaoMaster;
import com.lanou.mirror.greendao.DaoSession;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by dllo on 16/4/13.
 */
public class DaoSingleton {
    private static final String DB_NAME = "cache";

    private volatile static DaoSingleton daoSingleton;//静态类对象
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Context context;
    private DaoMaster.DevOpenHelper helper;
    private CacheDao cacheDao;

    /**
     * 私有构造方法,因为只在当前类会使用.
     * Context使用的是Application的,防止单例类长时间持有其他Activity的Context,影响性能.
     */
    private DaoSingleton() {
        context = BaseApplication.getContext();
    }

    /**
     * 单利
     * 对外提供一个方法,可以获得这个DaoSingleton实例
     * @param context
     * @return
     */
    public static DaoSingleton getInstance(Context context) {
        if (daoSingleton == null) {
            synchronized (DaoSingleton.class) {
                if (daoSingleton == null) {
                    daoSingleton = new DaoSingleton();
                }
            }
        }
        return daoSingleton;
    }

    /**
     * @return DevOpenHelper对象, 类似于SQLiteOpenHelper
     */
    public DaoMaster.DevOpenHelper getHelper() {
        if (helper == null) {
            helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        return helper;
    }

    private SQLiteDatabase getDb() {
        if (db == null) {
            db = getHelper().getWritableDatabase();
        }

        return db;

    }

    private DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            daoMaster = new DaoMaster(getDb());
        }
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        if (daoSession == null) {
            daoSession = getDaoMaster().newSession();
        }
        return daoSession;
    }

    /**
     * 通过CacheDao对象来操作数据库  即增删改查
     * 上方的所有方法除了getInstance方法都是为了此方法服务
     */
    public CacheDao getCacheDao() {
        if (cacheDao == null) {
            cacheDao = getDaoSession().getCacheDao();
        }
        return cacheDao;
    }

    /**
     * 插入数据 插入实体类集合
     * @param entities 要插入的集合
     */
    public void insertList(List<Cache> entities){
        getCacheDao().insertOrReplaceInTx(entities);
    }

    /**
     * 插入数据 插入但个对象
     * @param entity 要插入的当个对象
     */
    public void insertOne(Cache entity){
        getCacheDao().insertOrReplace(entity);
    }

    /**
     * 删除某条数据 因为当前项目没用到 暂时写一个简单实例
     * @param entity
     */
    public void delete(Cache entity){
        getCacheDao().delete(entity);
        QueryBuilder queryBuilder = null;
        queryBuilder.where(CacheDao.Properties.Brand.eq(""));
    }

    /**
     * 按品牌查询
     * @param brand
     * @return
     */
    public List<Cache> queryList(String brand){
        QueryBuilder<Cache> qb = getCacheDao().queryBuilder();
        qb.where(CacheDao.Properties.Brand.eq(brand));
        return qb.list();
    }

    /**
     * ?
     * @return
     */
    public List<Cache> queryAll(){
        return getCacheDao().loadAll();
    }




}


