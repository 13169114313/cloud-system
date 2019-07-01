package com.yi.cloud.system.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户持久化类
 *
 * @author chenguoyi
 */
@Entity
//@TableName("sys_user")
@Table(name = "sys_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "userName", name = "idx_user_name")
})
public class SysUser implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "userName", length = 64)
    private String userName;

    @Column(name = "password", length = 64)
    private String password;

    @Column(name = "nickName", length = 64)
    private String nickName;

    @Column(name = "mail", length = 64)
    private String mail;

    @Column(name = "phone", length = 64)
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
