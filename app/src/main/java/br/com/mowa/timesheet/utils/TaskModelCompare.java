package br.com.mowa.timesheet.utils;

import java.util.Comparator;

import br.com.mowa.timesheet.model.TaskModel;

/**
 * Created by walky on 10/28/15.
 */
public class TaskModelCompare implements Comparator {
    @Override
    public int compare(Object object, Object otherObject) {
        TaskModel taskModel = (TaskModel) object;
        TaskModel otherTaskModel = (TaskModel) otherObject;
        return taskModel.getTime().compareTo(otherTaskModel.getTime());
    }
}
