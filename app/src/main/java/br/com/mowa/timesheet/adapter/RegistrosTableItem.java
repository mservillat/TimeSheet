package br.com.mowa.timesheet.adapter;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.entity.TaskEntity;

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


    public static List<RegistrosTableItem> builderList(List<TaskEntity> listTaskEntity) {
        List<RegistrosTableItem> list = new ArrayList<>();
        for (int i = 0; i < listTaskEntity.size(); i ++) {
            RegistrosTableItem tableItem = new RegistrosTableItem();
            TaskEntity taskEntity = listTaskEntity.get(i);
            tableItem.name = taskEntity.getName();
            tableItem.start_time = taskEntity.getStartTime();
            tableItem.end_time = taskEntity.getEndTime();
            tableItem.time = taskEntity.getTime();
            tableItem.project = taskEntity.getProject().getName();
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
