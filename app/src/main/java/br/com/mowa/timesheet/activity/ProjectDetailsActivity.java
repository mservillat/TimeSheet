package br.com.mowa.timesheet.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.model.ProjectModel;
import br.com.mowa.timesheet.model.TaskModel;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseTask;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.ListViewUtils;
import br.com.mowa.timesheet.utils.UtilsTime;

/**
 * Created by walky on 10/21/15.
 */
public class ProjectDetailsActivity extends BaseActivity {
    private ProjectModel project;
    private TextView tvTitleProject;
    private TextView tvSituation;
    private TextView tvDescription;
    private TextView tvTotalHours;
    private ListView listViewAttendees;
    private CallJsonNetwork callJson;
    private List<TaskModel> listTask;
    private List<String> listAttendees;
    private String totalHours;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        createToolbar(R.id.activity_project_details_toolar);
        Gson gson = new Gson();
        this.project = gson.fromJson(getIntent().getStringExtra(ProjectsActivity.KEY_INTENT_PUT_EXTRA_PROJECT_DETAILS), ProjectModel.class);

        this.tvTitleProject = (TextView) findViewById(R.id.activity_project_details_text_title_project);
        this.tvTitleProject.setText(this.project.getName());

        this.tvSituation = (TextView) findViewById(R.id.activity_project_details_text_situation);
        this.tvSituation.setText(this.project.isActivite()? "Ativo" : "Finalizado");

        this.tvDescription = (TextView) findViewById(R.id.activity_project_details_text_description);
//        this.tvDescription.setText(this.project.getDescription());
        this.tvDescription.setText("description description description description descriptiondescription description description description description description description description description description description ");
        this.tvTotalHours = (TextView) findViewById(R.id.activity_project_details_text_total_hours);

        this.listViewAttendees = (ListView) findViewById(R.id.activity_project_details_list_view_attendees);

        this.callJson = new CallJsonNetwork();
        this.listAttendees = new ArrayList<>();

        loadDetailsProject();
    }

    private void loadDetailsProject() {
        this.callJson.callJsonObjectGet(VolleySingleton.URL_GET_TASK_PROJECT_ID + this.project.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ParseTask parseTask = new ParseTask();
                try {
                    listTask = parseTask.jsonObjectToTaskModel(response);
                    calculateTotalHours();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void calculateTotalHours() {
        Long time =  new Long(0);
        for (TaskModel task : this.listTask) {
            time += task.getTime();
            loadListAttendees(task.getUser().getName());
        }

        this.totalHours = UtilsTime.longMillisToString(time);
        this.tvTotalHours.setText(this.totalHours);
        this.listViewAttendees.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.listAttendees));
        ListViewUtils.getListViewSize(listViewAttendees);
    }

    private void loadListAttendees(String attendees) {
        if (!this.listAttendees.contains(attendees)) {
            listAttendees.add(attendees);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

}
