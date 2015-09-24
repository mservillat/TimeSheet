package br.com.mowa.timesheet.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.mowa.timesheet.entity.TaskEntity;

/**
 * Created by walky on 9/23/15.
 */
public class ProjetosDetalhesUserItemAdapter {
    private String name;
    private Long timeMillis;
    private String time;


    public  ProjetosDetalhesUserItemAdapter builderList(List<TaskEntity> listTask) {
        List<ProjetosDetalhesUserItemAdapter> list = new ArrayList<>();
        ProjetosDetalhesUserItemAdapter item = new ProjetosDetalhesUserItemAdapter();
        for (int i = 0; i < listTask.size(); i++) {
            TaskEntity taskEntity = listTask.get(i);
            if (item.timeMillis == null){
                item.timeMillis = taskEntity.getTime();
            } else {
                item.timeMillis = item.timeMillis + taskEntity.getTime();
            }

            if (item.name == null) {
                item.name = taskEntity.getUser().getName();
            }
        }

        item.time = String.format("%d hr, %d min", TimeUnit.MILLISECONDS.toHours(item.timeMillis), TimeUnit.MILLISECONDS.toMinutes(item.timeMillis));



        return item;
    }

    public String getName() {
        return name;
    }

    public Long getTimeMillis() {
        return timeMillis;
    }

    public String getTime() {
        return time;
    }
}

