package br.com.mowa.timesheet.activity;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.mowa.timesheet.fragment.NavigationDrawerFragment;
import br.com.mowa.timesheet.network.CustomJsonObjectRequest;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.timesheet.R;

public class HomeActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private ActionBarDrawerToggle drawerToggle;
    private int year, month, day, hour, minute;
    private Button btDate;
    private Button btHorainicio;
    private Button btHoraFim;
    private Button btUpdateButtonHoras;
    private ImageButton btSpinner;
    private FloatingActionButton btEnviar;
    private Spinner spinner;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private final String url = "http://walkyteste.goldarkapi.com/project";
    private RequestQueue mRequestQueue;
    private List<String> listaDeProjetos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        this.mToolbar = (Toolbar)findViewById(R.id.activity_home_toolbar);
        if (this.mToolbar != null) {
            setSupportActionBar(this.mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_home_drawer_layout);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_home_fragment_container);
        drawerFragment.setUp(this.mDrawerLayout, this.mToolbar);



        // Components (DATA e HORA)
        this.btDate = (Button) findViewById(R.id.activity_home_button_data);
        this.btDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarioPickerDialog();
            }
        });

        this.btHorainicio = (Button) findViewById(R.id.activity_home_button_horas_inicio);
        this.btHorainicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btUpdateButtonHoras = btHorainicio;
                relogioPickerDialog();
            }
        });

        this.btHoraFim = (Button) findViewById(R.id.activity_home_button_horas_fim);
        this.btHoraFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btUpdateButtonHoras = btHoraFim;
                relogioPickerDialog();
            }
        });
        loadDateCurrent();


        //Request lista de projetos e carrega na view spinner
        this.mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        callJsonObject();

        //Components (Button enviar)
        this.btEnviar = (FloatingActionButton) findViewById(R.id.fab);
        this.btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snack(btEnviar, getResources().getString(R.string.activity_home_button_floating_msg_enviar));
            }
        });

    }

    private void callJsonObject() {
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.GET, this.url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    montaListaProjeto(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toast(error.getMessage());
            }
        });

        this.mRequestQueue.add(request);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void calendarioPickerDialog() {
        Calendar calendarDefaul = Calendar.getInstance();
        calendarDefaul.set(this.year, this.month, this.day);


        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                calendarDefaul.get(Calendar.YEAR),
                calendarDefaul.get(Calendar.MONTH),
                calendarDefaul.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(getActivity().getFragmentManager(), "Calendario");
        datePickerDialog.setAccentColor(getResources().getColor(R.color.primary));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void relogioPickerDialog() {
        Calendar relogioDefaul = Calendar.getInstance();
        relogioDefaul.set(this.year, this.month, this.day, this.hour, this.minute);

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                this,
                relogioDefaul.get(Calendar.HOUR_OF_DAY),
                relogioDefaul.get(Calendar.MINUTE),
                true
        );

        timePickerDialog.show(getActivity().getFragmentManager(), "Relogio");
        timePickerDialog.setAccentColor(getActivity().getResources().getColor(R.color.primary));
    }

    private void loadDateCurrent() {
        if (year == 0) {
            Calendar c = Calendar.getInstance();
            this.year = c.get(Calendar.YEAR);
            this.month = c.get(Calendar.MONTH);
            this.day = c.get(Calendar.DAY_OF_MONTH);
            this.hour = c.get(Calendar.HOUR_OF_DAY);
            this.minute = c.get(Calendar.MINUTE);

            this.btDate.setText(day + "/" + month + "/" + year);
            this.btHorainicio.setText(this.hour + ":" + this.minute);
            this.btHoraFim.setText(this.hour + ":" + this.minute);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar tDefaul = Calendar.getInstance();
        tDefaul.set(year, month, day, hour, minute);
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;

        this.btDate.setText(day + "/" + (month + 1) + "/" + year);
    }


    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;

        if (minute < 10) {
            this.btUpdateButtonHoras.setText(this.hour + ":" + "0"+this.minute);
        } else {
            this.btUpdateButtonHoras.setText(this.hour + ":" + this.minute);
        }
        this.btUpdateButtonHoras = null;

    }
    public void montaListaProjeto(JSONObject jsonObject) throws JSONException {
        this.listaDeProjetos = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            this.listaDeProjetos.add(obj.optString("name"));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, this.listaDeProjetos);
        this.spinner = (Spinner) findViewById(R.id.activity_home_spinner_projeto);
        this.spinner.setAdapter(adapter);
        this.btSpinner = (ImageButton) findViewById(R.id.activity_home_button_spinner);
        this.btSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });

    }

}
