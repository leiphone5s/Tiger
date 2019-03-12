package com.demo.winwang.tigermachine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class DBShineManager {
    private final static String dbName = "shine.db";
    private static DBShineManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBShineManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBShineManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBShineManager.class) {
                if (mInstance == null) {
                    mInstance = new DBShineManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    /**
     * 插入一条记录
     *
     * @param user
     */
    public void insertUser(UserShine user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserShineDao userShineDao = daoSession.getUserShineDao();
        userShineDao.insert(user);
    }

    /**
     * 插入用户集合
     *
     * @param users
     */
    public void insertUserList(List<UserShine> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserShineDao userShineDao = daoSession.getUserShineDao();
        userShineDao.insertInTx(users);
    }

    /**
     * 删除一条记录
     *
     * @param user
     */
    public void deleteUser(UserShine user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserShineDao userShineDao = daoSession.getUserShineDao();
        userShineDao.delete(user);
    }

    /**
     * 删除所有记录
     */
    public void deleteUser(long id) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserShineDao userShineDao = daoSession.getUserShineDao();
        userShineDao.deleteByKey(id);
    }

    /**
     * 删除所有记录
     */
    public void deleteAllUser() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserShineDao userShineDao = daoSession.getUserShineDao();
        userShineDao.deleteAll();
    }

    /**
     * 更新一条记录
     *
     * @param user
     */
    public void updateUser(UserShine user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserShineDao userShineDao = daoSession.getUserShineDao();
        userShineDao.update(user);
    }

    /**
     * 查询用户列表
     */
    public List<UserShine> queryUserList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserShineDao userShineDao = daoSession.getUserShineDao();
        QueryBuilder<UserShine> qb = userShineDao.queryBuilder();
        List<UserShine> list = qb.list();
        return list;
    }

    /**
     * 查询用户列表
     */
    public List<UserShine> queryUserList(String name) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserShineDao userShineDao = daoSession.getUserShineDao();
        QueryBuilder<UserShine> qb = userShineDao.queryBuilder();
        qb.where(UserShineDao.Properties.Name.eq(name)).orderAsc(UserShineDao.Properties.Name);
        List<UserShine> list = qb.list();
        return list;
    }

}
