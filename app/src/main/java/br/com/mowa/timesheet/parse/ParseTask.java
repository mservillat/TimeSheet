package br.com.mowa.timesheet.parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.entity.ProjectEntity;
import br.com.mowa.timesheet.entity.TaskEntity;
import br.com.mowa.timesheet.entity.UserEntity;

/**
 * Created by walky on 9/22/15.
 */
public class ParseTask {

    public List<TaskEntity> jsonObjectToTaskEntity(JSONObject response) throws JSONException {
        List<TaskEntity> list = new ArrayList<>();
        JSONArray array = response.getJSONArray("data");
        for (int i = 0; i < array.length(); i ++) {
            JSONObject object = array.getJSONObject(i);
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setId(object.optString("id"));
            taskEntity.setName(object.optString("name"));
            taskEntity.setComments(object.optString("comments"));
            taskEntity.setStartTime(object.optString("start_time"));
            taskEntity.setEndTime(object.optString("end_time"));
            taskEntity.setTime(object.optLong("time"));

            // User
            JSONObject objectUser = object.getJSONObject("user");
            UserEntity userEntity = new UserEntity();
            userEntity.setName(objectUser.optString("name"));
            taskEntity.setUser(userEntity);

            //Project
            JSONObject objectProject = object.getJSONObject("project");
            ProjectEntity projectEntity = new ProjectEntity();
            projectEntity.setName(objectProject.optString("name"));
            taskEntity.setProject(projectEntity);


            list.add(taskEntity);
        }

        return list;
    }
}
