package br.com.mowa.timesheet.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.mowa.timesheet.model.ProjectModel;
import br.com.mowa.timesheet.model.TaskModel;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseTask;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.ImageDownload;
import br.com.mowa.timesheet.utils.ImageStorage;
import br.com.mowa.timesheet.utils.ImageUtils;
import br.com.mowa.timesheet.utils.ListViewUtils;
import br.com.mowa.timesheet.utils.UtilsTime;

/**
 * Created by walky on 10/21/15.
 */
public class ProjectDetailsActivity extends BaseActivity implements ImageDownload.OnImageDownloadListener{
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
    private ImageView imageView;
    private ProgressDialog progress;

    private List<Long> hoursInProjectGraphic = new ArrayList<>();;
    private PieChart mChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_information);


        this.progress = createProgressDialog("Loading", "loading information", true, true);
        this.progress.show();
//        createToolbar(R.id.activity_project_details_toolar);
        Gson gson = new Gson();
        this.project = gson.fromJson(getIntent().getStringExtra(ProjectsActivity.KEY_INTENT_PUT_EXTRA_PROJECT_DETAILS), ProjectModel.class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        this.tvTitleProject = (TextView) findViewById(R.id.activity_project_details_text_title_project);
        this.tvTitleProject.setText(this.project.getName());

        this.imageView = (ImageView) findViewById(R.id.activity_project_details_img);

        this.tvSituation = (TextView) findViewById(R.id.activity_project_details_text_situation);
        this.tvSituation.setText(this.project.isActivite()? "Ativo" : "Finalizado");

        this.tvDescription = (TextView) findViewById(R.id.activity_project_details_text_description);
//        this.tvDescription.setText(this.project.getDescription());
        this.tvDescription.setText(this.project.getDescription());
        this.tvTotalHours = (TextView) findViewById(R.id.activity_project_details_text_total_hours);

        this.listViewAttendees = (ListView) findViewById(R.id.activity_project_details_list_view_attendees);

        this.callJson = new CallJsonNetwork();
        this.listAttendees = new ArrayList<>();

//        this.rlGraphic = (RelativeLayout) findViewById(R.id.relative_graphic);
//        this.mChart = new PieChart(this);
        mChart = (PieChart) findViewById(R.id.piechart);


        mChart.setUsePercentValues(true);
        mChart.setDescription("");


        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);


        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;

                Toast.makeText(ProjectDetailsActivity.this,
                        listAttendees.get(e.getXIndex()) + " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });


        // customize legends
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);


        loadDetailsProject();
        loadImageCacheStorage();

    }


    @Override
    public void onImageDownloadFinishListener(Bitmap imageDownload, String url) {
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        imageView.setImageBitmap(ImageStorage.getImage(ProjectDetailsActivity.this, url, params.width, params.height));
        progress.dismiss();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < hoursInProjectGraphic.size(); i++)
            yVals1.add(new Entry(hoursInProjectGraphic.get(i), i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < (listAttendees.size()) ; i++)
            xVals.add(listAttendees.get(i));

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
    }



    private void loadImageCacheStorage() {
        File file = ImageStorage.getImage(this, project.getImage());
        if (file != null) {
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            Bitmap bitmap = ImageUtils.decodeSampledBitmapFromResource(file, params.width, params.height);
            imageView.setImageBitmap(bitmap);
            progress.dismiss();

        } else {
            loadImageDownload();
        }

    }

    private void loadImageDownload() {
        new ImageDownload(this, project.getImage(), project.getImage(), this ).execute();
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
        builderListGraphic();
    }

    private void loadListAttendees(String attendees) {
        if (!this.listAttendees.contains(attendees)) {
            listAttendees.add(attendees);
        }
    }

    private void builderListGraphic() {
        for(int i = 0; i < listAttendees.size(); i ++) {
            Long time = new Long(0);
            for (TaskModel task : listTask) {
                if (listAttendees.get(i).equals(task.getUserName())) {
                    time += task.getTime();
                }
            }
            hoursInProjectGraphic.add(time);
        }
        addData();
    }

}
