package br.com.mowa.timesheet.model;

/**
 * Created by walky on 9/14/15.
 */
public class Task {
    private String name;
    private String date;
    private String start_time;
    private String end_time;
    private String comments;
    private User user;
    private String project;

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(int year, int month, int day, int hour, int minute) {
        this.date = (year+"-"+(month+1)+"-"+day+"T"+hour+":"+minute);
    }

    public String getDate(){
        return this.date;
    }

    public void setStart_time(int year, int month, int day, int hour, int minute) {
        this.start_time = (year+"-"+(month+1)+"-"+day+"T"+hour+":"+minute);
    }

    public void setEnd_time(int year, int month, int day, int hour, int minute) {
        this.end_time = (year+"-"+(month+1)+"-"+day+"T"+hour+":"+minute);
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

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
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
