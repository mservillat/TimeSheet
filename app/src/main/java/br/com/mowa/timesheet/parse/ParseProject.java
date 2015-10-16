package br.com.mowa.timesheet.parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.model.ProjectModel;
import br.com.mowa.timesheet.model.UserModel;

/**
 * Created by walky on 9/22/15.
 */
public class ParseProject {

    public List<ProjectModel> parseJsonToProjectEntity(JSONObject response) throws JSONException {
        List<ProjectModel> list = new ArrayList<>();
        JSONArray jsonArray = response.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject object = jsonArray.getJSONObject(i);

            ProjectModel project = new ProjectModel();
            project.setId(object.optString("id"));
            project.setName(object.optString("name"));
            project.setActivite(object.optBoolean("activite"));
            project.setDone(object.optBoolean("done"));
            project.setStartDate(object.optString("start_date"));


            JSONArray arrayUser = object.getJSONArray("users");
            for (int j = 0; j < arrayUser.length(); j++) {
                JSONObject objectUser = arrayUser.getJSONObject(j);
                UserModel userModel = new UserModel();
                userModel.setId(objectUser.optString("id"));
                project.getUsers().add(userModel);
            }

            list.add(project);
        }
        return list;
    }

    public List<String> parseListProjectEntityToString(List<ProjectModel> listProjectModel) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < listProjectModel.size(); i++) {
            list.add(listProjectModel.get(i).getName());
        }
        return list;
    }


}
