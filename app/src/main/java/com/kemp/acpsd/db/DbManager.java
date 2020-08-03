package com.kemp.acpsd.db;

import android.content.Context;
import android.os.Environment;

import com.kemp.acpsd.bean.AccountPsd;
import com.kemp.acpsd.greendao.AccountPsdDao;
import com.kemp.acpsd.greendao.DaoMaster;
import com.kemp.acpsd.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;

import java.io.File;
import java.util.List;

public class DbManager {

    public static final String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
            "com.kemp.acpsd" + File.separator + "databases" + File.separator;
    public static final String DB_NAME = "acntPsd.db";

    private DaoSession daoSession;
    private AccountPsdDao accountPsdDao;

    private static DbManager instance;

    private DbManager() {
    }

    public static DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    }

    public void initDb(Context context) {
        if (daoSession == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);
            Database db = helper.getWritableDb();
            daoSession = new DaoMaster(db).newSession();
        }
    }

    private void initAccountPsdDao() {
        if (accountPsdDao == null) {
            accountPsdDao = daoSession.getAccountPsdDao();
        }
    }

    public List<AccountPsd> query() {
        initAccountPsdDao();
        return accountPsdDao.queryBuilder().list();
    }

    public void add(AccountPsd accountPsd) {
        initAccountPsdDao();
        accountPsdDao.insert(accountPsd);
    }

    public void delete(AccountPsd accountPsd) {
        initAccountPsdDao();
        accountPsdDao.delete(accountPsd);
    }

    public void set(AccountPsd accountPsd) {
        AccountPsd origin = accountPsdDao.queryBuilder().where(AccountPsdDao.Properties.Id.eq(accountPsd.getId())).build().unique();
        if (origin != null) {
            origin.setAccount(accountPsd.getAccount());
            origin.setName(accountPsd.getName());
            origin.setPassWord(accountPsd.getPassWord());
            origin.setRemarks(accountPsd.getRemarks());
            accountPsdDao.update(origin);
        }
    }
}
