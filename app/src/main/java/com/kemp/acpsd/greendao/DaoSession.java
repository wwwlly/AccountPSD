package com.kemp.acpsd.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.kemp.acpsd.bean.AccountPsd;

import com.kemp.acpsd.greendao.AccountPsdDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig accountPsdDaoConfig;

    private final AccountPsdDao accountPsdDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        accountPsdDaoConfig = daoConfigMap.get(AccountPsdDao.class).clone();
        accountPsdDaoConfig.initIdentityScope(type);

        accountPsdDao = new AccountPsdDao(accountPsdDaoConfig, this);

        registerDao(AccountPsd.class, accountPsdDao);
    }
    
    public void clear() {
        accountPsdDaoConfig.clearIdentityScope();
    }

    public AccountPsdDao getAccountPsdDao() {
        return accountPsdDao;
    }

}
