package com.demo.winwang.tigermachine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.demo.winwang.tigermachine.widget.UserCaidan;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class DBCaidanManager {
    private final static String dbName = "caidan.db";
    private static DBCaidanManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBCaidanManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBCaidanManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBCaidanManager.class) {
                if (mInstance == null) {
                    mInstance = new DBCaidanManager(context);
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
    public void insertUser(UserCaidan user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserCaidanDao userDao = daoSession.getUserCaidanDao();
        userDao.insert(user);
    }

    /**
     * 插入用户集合
     *
     * @param users
     */
    public void insertUserList(List<UserCaidan> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserCaidanDao userDao = daoSession.getUserCaidanDao();
        userDao.insertInTx(users);
    }

    /**
     * 删除一条记录
     *
     * @param user
     */
    public void deleteUser(UserCaidan user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserCaidanDao userDao = daoSession.getUserCaidanDao();
        userDao.delete(user);
    }

    /**
     * 删除所有记录
     */
    public void deleteUser(long id) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserCaidanDao userDao = daoSession.getUserCaidanDao();
        userDao.deleteByKey(id);
    }

    /**
     * 删除所有记录
     */
    public void deleteAllUser() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserCaidanDao userDao = daoSession.getUserCaidanDao();
        userDao.deleteAll();
    }

    /**
     * 更新一条记录
     *
     * @param user
     */
    public void updateUser(UserCaidan user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserCaidanDao userDao = daoSession.getUserCaidanDao();
        userDao.update(user);
    }

    /**
     * 查询用户列表
     */
    public List<UserCaidan> queryUserList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserCaidanDao userDao = daoSession.getUserCaidanDao();
        QueryBuilder<UserCaidan> qb = userDao.queryBuilder();
        List<UserCaidan> list = qb.list();
        return list;
    }

    /**
     * 查询用户列表
     */
    public List<UserCaidan> queryUserList(String name) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserCaidanDao userDao = daoSession.getUserCaidanDao();
        QueryBuilder<UserCaidan> qb = userDao.queryBuilder();
        qb.where(UserCaidanDao.Properties.Name.eq(name)).orderAsc(UserCaidanDao.Properties.Name);
        List<UserCaidan> list = qb.list();
        return list;
    }





}
