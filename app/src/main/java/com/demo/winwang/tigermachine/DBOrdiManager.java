package com.demo.winwang.tigermachine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class DBOrdiManager {
    private final static String dbName = "ordi.db";
    private static DBOrdiManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBOrdiManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBOrdiManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBOrdiManager.class) {
                if (mInstance == null) {
                    mInstance = new DBOrdiManager(context);
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
    public void insertUser(UserOrdi user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserOrdiDao userOrdiDao = daoSession.getUserOrdiDao();
        userOrdiDao.insert(user);
    }

    /**
     * 插入用户集合
     *
     * @param users
     */
    public void insertUserList(List<UserOrdi> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserOrdiDao userOrdiDao = daoSession.getUserOrdiDao();
        userOrdiDao.insertInTx(users);
    }

    /**
     * 删除一条记录
     *
     * @param user
     */
    public void deleteUser(UserOrdi user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserOrdiDao userOrdiDao = daoSession.getUserOrdiDao();
        userOrdiDao.delete(user);
    }

    /**
     * 删除所有记录
     */
    public void deleteUser(long id) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserOrdiDao userOrdiDao = daoSession.getUserOrdiDao();
        userOrdiDao.deleteByKey(id);
    }

    /**
     * 删除所有记录
     */
    public void deleteAllUser() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserOrdiDao userOrdiDao = daoSession.getUserOrdiDao();
        userOrdiDao.deleteAll();
    }

    /**
     * 更新一条记录
     *
     * @param user
     */
    public void updateUser(UserOrdi user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserOrdiDao userOrdiDao = daoSession.getUserOrdiDao();
        userOrdiDao.update(user);
    }

    /**
     * 查询用户列表
     */
    public List<UserOrdi> queryUserList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserOrdiDao userOrdiDao = daoSession.getUserOrdiDao();
        QueryBuilder<UserOrdi> qb = userOrdiDao.queryBuilder();
        List<UserOrdi> list = qb.list();
        return list;
    }

    /**
     * 查询用户列表
     */
    public List<UserOrdi> queryUserList(String name) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserOrdiDao userOrdiDao = daoSession.getUserOrdiDao();
        QueryBuilder<UserOrdi> qb = userOrdiDao.queryBuilder();
        qb.where(UserOrdiDao.Properties.Name.eq(name)).orderAsc(UserOrdiDao.Properties.Name);
        List<UserOrdi> list = qb.list();
        return list;
    }





}
