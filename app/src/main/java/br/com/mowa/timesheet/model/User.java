package br.com.mowa.timesheet.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by walky on 9/14/15.
 */
public class User implements Serializable{
    private String id;
    private String username;
    private String passowrd;
    private String token;
    private Date created_at;
    private Date updated_at;
    private String name;
    private boolean activite;

    public User(String username, String id, String token ) {
        this.id = id;
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }


    public String getToken() {
        return token;
    }


    public Date getCreated_at() {
        return created_at;
    }


    public Date getUpdated_at() {
        return updated_at;
    }


    public String getName() {
        return name;
    }


    public boolean isActivite() {
        return activite;
    }

    public void setActivite(boolean activite) {
        this.activite = activite;
    }

    public String getId() {
        return this.id;
    }
}
