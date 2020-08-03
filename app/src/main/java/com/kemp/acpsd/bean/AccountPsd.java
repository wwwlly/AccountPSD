package com.kemp.acpsd.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

@Entity
public class AccountPsd {

    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String account;
    private String passWord;
    private String remarks;

    @Generated(hash = 338358835)
    public AccountPsd(Long id, String name, String account, String passWord, String remarks) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.passWord = passWord;
        this.remarks = remarks;
    }

    @Generated(hash = 1796720099)
    public AccountPsd() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassWord() {
        return this.passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
