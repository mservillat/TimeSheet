package br.com.mowa.timesheet.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by walky on 9/22/15.
 */
public class TaskModel implements Comparable<TaskModel>{
    private String id;
    private String name;
    private String startTimeString;
    private String endTimeString;
    private String comments;
    private UserModel user;
    private ProjectModel project;
    private Long time;
    private String timeDisplay;
    private String dateString;
    public boolean selectd = false;


    public TaskModel(){

    }

    public TaskModel(String name, Long time) {
        this.name = name;
        this.time = time;
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

    public String getStartTimeString() {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    public String getEndTimeString() {
        return endTimeString;
    }

    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public ProjectModel getProject() {
        return project;
    }

    public void setProject(ProjectModel project) {
        this.project = project;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


    public String getTimeDisplay() {
        return timeDisplay;
    }

    public String getDateString() {
        return dateString.substring(0,10);
    }


    public void setDateStringAndCalendar(String dateString) {
        this.dateString = dateString;
    }





    /**
     *  Calcula as horas de uma lista de tarefas (do mesmo user e mesmo projeto) para ser usado no adapter
     * @param listTask Lista de tarefas (tasks) do mesmo projeto e do mesmo usuário
     * @return taskModel com o atributo timeDisplay contendo a quantidade de horas de todas as task de um usuário especifico
     */
    public TaskModel calculateHoursWorks(List<TaskModel> listTask) {
        List<TaskModel> list = new ArrayList<>();
        TaskModel item = new TaskModel();
        for (int i = 0; i < listTask.size(); i++) {
            TaskModel taskModel = listTask.get(i);
            if (item.time == null){
                item.time = taskModel.getTime();
            } else {
                item.time = item.time + taskModel.getTime();
            }

            if (item.name == null) {
                item.name = taskModel.getUser().getName();
            }
        }

        item.timeDisplay = String.format("%d hr, %d min", TimeUnit.MILLISECONDS.toHours(item.time), TimeUnit.MILLISECONDS.toMinutes(item.time));

        return item;
    }

    public String calculateHoursWorks() {
        if (time != null) {
            this.timeDisplay = String.format("%d min", TimeUnit.MILLISECONDS.toMinutes(this.time));
            return this.timeDisplay;
        } else {
            return null;
        }
    }

    @Override
    public int compareTo(TaskModel another) {
        return getName().compareTo(another.getName());
    }

}

