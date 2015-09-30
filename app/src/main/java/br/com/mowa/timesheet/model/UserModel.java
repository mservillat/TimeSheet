package br.com.mowa.timesheet.model;

import java.util.Date;

/**
 * Created by walky on 9/22/15.
 */
public class UserModel {
    private String id;
    private String name;
    private String userName;
    private String password;
    private String token;
    private Date createdAt;
    private Date updateAt;
    private boolean activite;


    public UserModel() {

    }

    public UserModel(String id, String username, String token) {
        this.id = id;
        this.userName = username;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public boolean isActivite() {
        return activite;
    }

    public void setActivite(boolean activite) {
        this.activite = activite;
    }
}