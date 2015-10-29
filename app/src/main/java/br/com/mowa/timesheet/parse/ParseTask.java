package br.com.mowa.timesheet.parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.model.ProjectModel;
import br.com.mowa.timesheet.model.TaskModel;
import br.com.mowa.timesheet.model.UserModel;

/**
 * Created by walky on 9/22/15.
 */
public class ParseTask {

    public List<TaskModel> jsonObjectToTaskModel(JSONObject response) throws JSONException {
        List<TaskModel> list = new ArrayList<>();
        JSONArray array = response.getJSONArray("data");
        for (int i = 0; i < array.length(); i ++) {
            JSONObject object = array.getJSONObject(i);
            TaskModel taskModel = new TaskModel();
            taskModel.setId(object.optString("id"));
            taskModel.setName(object.optString("name"));
            taskModel.setComments(object.optString("comments"));
            taskModel.setStartTimeString(object.optString("start_time"));
            taskModel.setEndTimeString(object.optString("end_time"));
            taskModel.setTime(object.optLong("time"));
            taskModel.setDateStringAndCalendar(object.optString("date"));

            // User
            JSONObject objectUser = object.getJSONObject("user");
            UserModel userModel = new UserModel();
            userModel.setName(objectUser.optString("name"));
            taskModel.setUser(userModel);

            //Project
            JSONObject objectProject = object.getJSONObject("project");
            ProjectModel projectModel = new ProjectModel();
            projectModel.setId(objectProject.optString("id"));
            projectModel.setName(objectProject.optString("name"));
            projectModel.setDescription(objectProject.optString("description"));
            taskModel.setProject(projectModel);


            list.add(taskModel);
        }

        return list;
    }
}
