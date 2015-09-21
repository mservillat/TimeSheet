package br.com.mowa.timesheet.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public RegistrosTableItem(String project, String name, String start_time, String end_time, Long time){
        this.project = project;
        this.name = name;
        this.start_time = start_time;
        this.end_time = end_time;
        this.time = time;
    }

    public static List<RegistrosTableItem> builderList(JSONObject response) throws JSONException {
        List<RegistrosTableItem> list = new ArrayList<>();
        JSONArray array = response.getJSONArray("data");
        for (int i = 0; i < array.length(); i ++) {
            JSONObject object = array.getJSONObject(i);

            list.add(new RegistrosTableItem(object.getJSONObject("project").optString("name"),
                    object.optString("name"),
                    object.optString("start_time"),
                    object.optString("end_time"),
                    object.optLong("time")));
        }
        return list;
    }

    public String getProject() {
        return project;
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
}
