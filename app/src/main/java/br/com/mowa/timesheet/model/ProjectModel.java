package br.com.mowa.timesheet.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by walky on 9/22/15.
 */
public class ProjectModel {
    private String id;
    private String name;
    private String startDate;
    private Date endDate;
    private List<UserModel> users = new ArrayList<>();
    private boolean activite;
    private boolean done;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate.substring(0, 20);
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

    public boolean isActivite() {
        return activite;
    }

    public void setActivite(boolean activite) {
        this.activite = activite;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getId() {return id; }

    public void setId(String id) {this.id = id; }



    @Override
    public String toString() {
        return name;
    }
}