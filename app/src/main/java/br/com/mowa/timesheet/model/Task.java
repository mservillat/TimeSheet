package br.com.mowa.timesheet.model;

import java.util.Date;

/**
 * Created by walky on 9/14/15.
 */
public class Task {
    private String name;
    private Date date;
    private Date start_time;
    private Date end_time;
    private String comments;
    private User user;
    private String project;

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }


    public Date getStart_time() {
        return start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public String getComments() {
        return comments;
    }

    public User getUser() {
        return user;
    }

    public String getProject() {
        return project;
    }
}
