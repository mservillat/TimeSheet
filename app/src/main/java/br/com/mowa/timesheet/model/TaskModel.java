package br.com.mowa.timesheet.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by walky on 9/22/15.
 */
public class TaskModel {
    private String id;
    private String name;
    private String startTime;
    private String endTime;
    private String comments;
    private UserModel user;
    private ProjectModel project;
    private Long time;


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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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


    /**
     * Objeto Task apenas com as informações "nome do usuario" e "total de horas" trabalhadas no projeto selecionado
     * Essa classe é usada na avitivity "ProjetosActivity"
     */
    public static class ObjectDisplayUserAndTotalHours {
        private String name;
        private String timeDisplay;
        private Long timeMillis;

        public String getName() {
            return name;
        }

        public String getTimeDisplay() {
            return timeDisplay;
        }
    }

    /**
     * Converte e soma as horas de uma lista de tarefas (do mesmo user e mesmo projeto) em um model para ser usado no adapter
     * @param listTask Lista de tarefas (tasks) do mesmo projeto e do mesmo usuário
     * @return Objeto que será usado no adapter para mostrar nome do usuário e quantidade de horas em um devido projeto
     */
    public static ObjectDisplayUserAndTotalHours builderListTaskToObjectDisplay(List<TaskModel> listTask) {
        List<ObjectDisplayUserAndTotalHours> list = new ArrayList<>();
        ObjectDisplayUserAndTotalHours item = new ObjectDisplayUserAndTotalHours();
        for (int i = 0; i < listTask.size(); i++) {
            TaskModel taskModel = listTask.get(i);
            if (item.timeMillis == null){
                item.timeMillis = taskModel.getTime();
            } else {
                item.timeMillis = item.timeMillis + taskModel.getTime();
            }

            if (item.name == null) {
                item.name = taskModel.getUser().getName();
            }
        }

        item.timeDisplay = String.format("%d hr, %d min", TimeUnit.MILLISECONDS.toHours(item.timeMillis), TimeUnit.MILLISECONDS.toMinutes(item.timeMillis));

        return item;
    }


}

