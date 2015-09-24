package br.com.mowa.timesheet.parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.entity.ProjectEntity;
import br.com.mowa.timesheet.entity.UserEntity;

/**
 * Created by walky on 9/22/15.
 */
public class ParseProject {

    public List<ProjectEntity> parseJsonToProjectEntity(JSONObject response) throws JSONException {
        List<ProjectEntity> list = new ArrayList<>();
        JSONArray jsonArray = response.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject object = jsonArray.getJSONObject(i);

            ProjectEntity project = new ProjectEntity();
            project.setId(object.optString("id"));
            project.setName(object.optString("name"));
            project.setActivite(object.optBoolean("activite"));
            project.setDone(object.optBoolean("done"));


            JSONArray arrayUser = object.getJSONArray("users");
            for (int j = 0; j < arrayUser.length(); j++) {
                JSONObject objectUser = arrayUser.getJSONObject(j);
                UserEntity userEntity = new UserEntity();
                userEntity.setId(objectUser.optString("id"));
                project.getUsers().add(userEntity);
            }

            list.add(project);
        }
        return list;
    }

    public List<String> parseListProjectEntityToString(List<ProjectEntity> listProjectEntity) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < listProjectEntity.size(); i++) {
            list.add(listProjectEntity.get(i).getName());
        }
        return list;
    }


}
