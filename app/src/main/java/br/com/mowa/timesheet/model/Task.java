package br.com.mowa.timesheet.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    private Long time;



    private Long timeIncialMilissegundos;
    private Long timeFinalMilissegundos;
    public void setName(String name) {
        this.name = name;
    }

    public void setDate(int year, int month, int day, int hour, int minute) {
        this.date = (year+"-"+(month+1)+"-"+day+"T"+hour+":"+minute);

        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, 00);
        this.date = simpleDate.format(calendar.getTime());

    }

    public String getDate(){
        return this.date;
    }

    public void setStart_time(int year, int month, int day, int hour, int minute) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, 00);
        this.start_time = simpleDate.format(calendar.getTime());
        this.timeIncialMilissegundos = calendar.getTimeInMillis();

    }

    public void setEnd_time(int year, int month, int day, int hour, int minute) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, 00);
        this.end_time = simpleDate.format(calendar.getTime());
        this.timeFinalMilissegundos = calendar.getTimeInMillis();
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


    public Long getTime() {
        return time;
    }

    public void calculaTime() {
        this.time = (this.timeFinalMilissegundos - this.timeIncialMilissegundos);
    }

//    public List<Task> getListTask(JSONObject response) throws JSONException {
//        List<Task> list = new ArrayList<>();
//        JSONArray array = response.getJSONArray("data");
//        for (int i = 0; i <= array.length(); i ++) {
//            JSONObject object = array.getJSONObject(i);
//
//        }
//
//        return list;
//    }


}