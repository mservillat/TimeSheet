package br.com.mowa.timesheet.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by walky on 9/14/15.
 */
public class FormTaskModel {
    private String name;
    private String date;
    private String startTime;
    private String endTime;
    private String comments;
    private UserModel user;
    private String project;
    private Long time;
    private static final int tempoMaximoTask = 86300000;
    private String color;


    private Long timeIncialMilissegundos;
    private Long timeFinalMilissegundos;
    public void setName(String name) {
        this.name = name;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {this.endTime = endTime;}

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getComments() {
        return comments;
    }

    public String getProject() {
        return project;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDate(){
        return this.date;
    }

    public UserModel getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public Long getTime() { return time; }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDate(int year, int month, int day, int hour, int minute) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, 00);
        this.date = simpleDate.format(calendar.getTime());
    }


    public void setStartTime(int year, int month, int day, int hour, int minute) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, 00);
        this.startTime = simpleDate.format(calendar.getTime());
        this.timeIncialMilissegundos = calendar.getTimeInMillis();

    }

    public void setEndTime(int year, int month, int day, int hour, int minute) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, 00);
        this.endTime = simpleDate.format(calendar.getTime());
        this.timeFinalMilissegundos = calendar.getTimeInMillis();
    }

    public boolean calculaTime() {
        this.time = (this.timeFinalMilissegundos - this.timeIncialMilissegundos);
        if (this.time <= tempoMaximoTask && this.time > 180000) {
            return true;
        } else {
            this.time = null;
            return false;
        }
    }

    public boolean verificaStartAndEndTime() {
        if (this.startTime == null) {
            return false;
        }
        if (this.endTime == null) {
            return false;
        }
        if (this.date == null) {
            return false;
        }

        return true;
    }


}