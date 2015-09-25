package br.com.mowa.timesheet.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.mowa.timesheet.entity.ProjectEntity;
import br.com.mowa.timesheet.fragment.NavigationDrawerFragment;
import br.com.mowa.timesheet.model.Task;
import br.com.mowa.timesheet.model.User;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseProject;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.SharedPreferencesUtil;

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
    private List<String> listaDeProjetosString;
    private List<ProjectEntity> listaDeProjetosObjProject;
    private ParseProject parseProject;
    private Task task = new Task();
    private User user;
    private EditText etNomeAtividade;
    private EditText etDescricaoAtividade;
    private JSONObject requestBody;
    private TextView tvQuantidadeDeHoras;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.user = SharedPreferencesUtil.getUserFromSharedPreferences();


        this.mToolbar = (Toolbar)findViewById(R.id.activity_home_toolbar);
        if (this.mToolbar != null) {
            setSupportActionBar(this.mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_home_drawer_layout);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_home_fragment_navigation_drawer_container);
//        setUp(this.mDrawerLayout, this.mToolbar, R.id.activity_home_list_view_navigation_drawer);
        drawerFragment.setUp(mDrawerLayout, mToolbar);

        // Component TextView Quantidade de horas na semana
        this.tvQuantidadeDeHoras = (TextView) findViewById(R.id.activity_home_text_view_horas_semanais);


        // Component EditText nome e descricao atividade
        this.etNomeAtividade = (EditText) findViewById(R.id.activity_home_edit_text_nome_atividade);
        this.etDescricaoAtividade = (EditText) findViewById(R.id.activity_home_edit_text_descricao_atividade);


        // Components (DATA e HORA)
        this.btDate = (Button) findViewById(R.id.activity_home_button_data_inicio);
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

        CallJsonNetwork jsonNetwork = new CallJsonNetwork();
        jsonNetwork.callJsonObjectGet(VolleySingleton.URL_GET_TASK_USER_ID + this.user.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                        JSONObject jsonObject = data.getJSONObject(0);
                        Long time = jsonObject.optLong("time");
                        tvQuantidadeDeHoras.setText(String.format(" %d min ", TimeUnit.MILLISECONDS.toMinutes(time)));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        //Request lista de projetos e carrega na view spinner
        this.spinner = (Spinner) findViewById(R.id.activity_home_spinner_projeto);
        this.btSpinner = (ImageButton) findViewById(R.id.activity_home_button_spinner);
        this.btSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();

            }
        });

        parseProject = new ParseProject();
        CallJsonNetwork callJson = new CallJsonNetwork();
        callJson.callJsonObjectGet(VolleySingleton.URL_GET_PROJECT, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listaDeProjetosObjProject = parseProject.parseJsonToProjectEntity(response);
                    buildListaProjeto();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        //Components (Floating Button enviar)
        this.btEnviar = (FloatingActionButton) findViewById(R.id.activity_home_floating_button_enviar);
        this.btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(buildForm()) {

                        CallJsonNetwork callJson = new CallJsonNetwork();
                        snack(btEnviar, getResources().getString(R.string.activity_home_button_floating_msg_enviar));
                        callJson.callJsonObjectPost(VolleySingleton.URL_POST_CREATE_TASK, requestBody, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                    } else {
                        toast("Algum campo não foi preenchido corretamente");
                    }
                } catch (JSONException e) {

                }


            }
        });


    }


    public void buildListaProjeto() {
        listaDeProjetosString = parseProject.parseListProjectEntityToString(listaDeProjetosObjProject);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, this.listaDeProjetosString);
        this.spinner.setAdapter(adapter);
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                task.setProject(listaDeProjetosObjProject.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }








    //  CALENDÁRIO E RELÓGIO

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
            validacaoMinutos(this.btHorainicio);
            validacaoMinutos(this.btHoraFim);
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
        this.task.setDate(this.year, this.month, this.day, hour, minute);
    }


    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;

        if (btUpdateButtonHoras == btHorainicio) {
            validacaoMinutos(btUpdateButtonHoras);
            this.task.setStart_time(this.year, this.month, this.day, this.hour, this.minute);
        }
        if(btUpdateButtonHoras == btHoraFim) {
            validacaoMinutos(btUpdateButtonHoras);
            this.task.setEnd_time(this.year, this.month, this.day, this.hour, this.minute);
        }

        this.btUpdateButtonHoras = null;
    }

    private void validacaoMinutos(Button bt) {
        if (minute < 10) {
            bt.setText(this.hour + ":" + "0"+this.minute);
        } else {
            bt.setText(this.hour + ":" + this.minute);
        }
    }


    private boolean buildForm() throws JSONException {
        requestBody = new JSONObject();

        if (task.getProject() != null) {
            requestBody.put("project", task.getProject());
        } else {
            return false;
        }

        if (user.getId() != null) {
            requestBody.put("user", user.getId());
        } else {
            return false;
        }

        if (task.getDate() != null) {
            requestBody.put("date", task.getDate());
        } else {
            return false;
        }

        if (task.getStart_time() != null) {
            requestBody.put("start_time", task.getStart_time());
        } else {
            return false;
        }

        if (task.getEnd_time() != null) {
            requestBody.put("end_time", task.getEnd_time());
        } else {
            return false;
        }

        if (this.etNomeAtividade.getText().toString().length() >4 ) {
            requestBody.put("name", this.etNomeAtividade.getText().toString());
        } else {
            return false;
        }

        requestBody.put("comments", this.etDescricaoAtividade.getText().toString());
        task.calculaTime();
        requestBody.put("time", task.getTime());


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.activity_home_menu_action_exit) {
            SharedPreferencesUtil.deleteSharedPreferences();
            Intent intent = new Intent(this, LoginActivity.class);
            getActivity().finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
