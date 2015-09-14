package br.com.mowa.timesheet.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by walky on 9/14/15.
 */
public class Project{
    private String id;
    private String name;
    private Date start_date;
    private Date end_date;
    private boolean activite;
    private boolean done;


    public List<Project> modelBuild(JSONObject response) throws JSONException {
        List<Project> list = new ArrayList<>();
        JSONArray jsonArray = response.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++ ) {
            JSONObject object = jsonArray.getJSONObject(i);
            Project p = new Project();
            p.id = object.optString("id");
            p.name = object.optString("name");
            p.activite = object.optBoolean("activite");
            p.done = object.optBoolean("done");
            list.add(p);
        }

        return list;
    }

    public List<String> getListaProject(List<Project> listModelProject) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < listModelProject.size(); i ++) {
            Project p = listModelProject.get(i);
            list.add(p.name.toString());
        }

        return list;
    }
}
