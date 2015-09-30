package br.com.mowa.timesheet.adapter;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.model.TaskModel;

/**
 * Created by walky on 9/21/15.
 */
public class RegistrosTableItem {
    private String id;
    private String name;
    private String start_time;
    private String end_time;
    private Long time;
    private String user;
    private String project;


    public static List<RegistrosTableItem> builderList(List<TaskModel> listTaskModel) {
        List<RegistrosTableItem> list = new ArrayList<>();
        for (int i = 0; i < listTaskModel.size(); i ++) {
            RegistrosTableItem tableItem = new RegistrosTableItem();
            TaskModel taskModel = listTaskModel.get(i);
            tableItem.name = taskModel.getName();
            tableItem.start_time = taskModel.getStartTime();
            tableItem.end_time = taskModel.getEndTime();
            tableItem.time = taskModel.getTime();
            tableItem.project = taskModel.getProject().getName();
            list.add(tableItem);
        }
        return list;
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

    public Long getTime() {
        return time;
    }

    public String getProject() {return project; }
}
