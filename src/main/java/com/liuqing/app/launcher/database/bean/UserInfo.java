package com.liuqing.app.launcher.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by liuqing
 * 17-10-21.
 * Email: 1239604859@qq.com
 */

@Entity
public class UserInfo {
    @Id
    private Long id;

    private String name;

    private String number;

    @Generated(hash = 864458164)
    public UserInfo(Long id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
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

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
