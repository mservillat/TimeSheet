package br.com.mowa.timesheet.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

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

import br.com.mowa.timesheet.dialog.HomeExitDialogFragment;
import br.com.mowa.timesheet.fragment.NavigationDrawerFragment;
import br.com.mowa.timesheet.model.FormTaskModel;
import br.com.mowa.timesheet.model.ProjectModel;
import br.com.mowa.timesheet.model.UserModel;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseProject;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.SharedPreferencesUtil;

public class HomeActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private FormTaskModel formTaskModel = new FormTaskModel();
    private List<ProjectModel> listaDeProjetosObjProject;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private List<String> listaDeProjetosString;
    private TextView tvQuantidadeDeHoras;
    private CallJsonNetwork jsonNetwork;
    private DrawerLayout mDrawerLayout;
    private ParseProject parseProject;
    private ProgressDialog progress;
    private JSONObject requestBody;
    private UserModel user;
    private Button btUpdateButtonHoras;
    private EditText etDescricaoAtividade;
    private ImageButton btSpinner;
    private Spinner spinner;
    private EditText etNomeAtividade;
    private FloatingActionButton btEnviar;
    private Button btHorainicio;
    private Button btDate;
    private Button btHoraFim;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.progress = createProgressDialog("Loading", "calculando horas trabalhadas", true, true);
        this.progress.show();


        this.user = SharedPreferencesUtil.getUserFromSharedPreferences();
        jsonNetwork = new CallJsonNetwork();
        parseProject = new ParseProject();


        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_home_drawer_layout);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_home_fragment_navigation_drawer_container);
        drawerFragment.setUp(mDrawerLayout, createToolbar(R.id.activity_home_toolbar));

//        // Component TextView Quantidade de horas na semana
        this.tvQuantidadeDeHoras = (TextView) findViewById(R.id.activity_home_text_view_horas_semanais);
//
//
//        // Component EditText nome e descricao atividade
//        this.etNomeAtividade = (EditText) findViewById(R.id.activity_home_edit_text_nome_atividade);
//        this.etDescricaoAtividade = (EditText) findViewById(R.id.activity_home_edit_text_descricao_atividade);
//
//
//        // Components (DATA e HORA)
//        this.btDate = (Button) findViewById(R.id.activity_home_button_data_inicio);
//        this.btDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= 11) {
//                    calendarioPickerDialog();
//                } else {
//                    calendarioPickerDialogVersaoInferior();
//                }
//            }
//        });
//
//        this.btHorainicio = (Button) findViewById(R.id.activity_home_button_horas_inicio);
//        this.btHorainicio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btUpdateButtonHoras = btHorainicio;
//                if (Build.VERSION.SDK_INT >= 11) {
//                    relogioPickerDialog();
//                } else {
//                    relogioPickerDialogVersaoInferior();
//                }
//            }
//        });
//
//        this.btHoraFim = (Button) findViewById(R.id.activity_home_button_horas_fim);
//        this.btHoraFim.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btUpdateButtonHoras = btHoraFim;
//                if (Build.VERSION.SDK_INT >= 11) {
//                    relogioPickerDialog();
//                } else {
//                    relogioPickerDialogVersaoInferior();
//                }
//            }
//        });
//
//
//        //Request lista de projetos e carrega na view spinner
//        this.spinner = (Spinner) findViewById(R.id.activity_home_spinner_projeto);
//        this.btSpinner = (ImageButton) findViewById(R.id.activity_home_button_spinner);
//        this.btSpinner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                spinner.performClick();
//
//            }
//        });


//        loadListSpinnerProject();
//        loadDateCurrent();
        loadDisplayAllHoursWork();


        //Components (Floating Button enviar)
//        this.btEnviar = (FloatingActionButton) findViewById(R.id.activity_home_floating_button_enviar);
//        this.btEnviar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    if(buildForm()) {
//
//
//                        progress.show();
//                        jsonNetwork.callJsonObjectPost(VolleySingleton.URL_POST_CREATE_TASK, requestBody, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                clearFilderForm();
//                                snack(btEnviar, getResources().getString(R.string.activity_home_button_floating_msg_enviar));
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                toast(error.getMessage());
//                            }
//                        });
//                    } else {
//                        toast("Algum campo não foi preenchido corretamente");
//                    }
//                } catch (JSONException e) {
//
//                }
//
//
//            }
//        });

    }


    /**
     *
     * Carrega as variaveis int de data e hora para a hora atual, e seta nos buttons da activity e formulario.
     */
    private void loadDateCurrent() {
        if (mYear == 0) {
            Calendar c = Calendar.getInstance();
            this.mYear = c.get(Calendar.YEAR);
            this.mMonth = c.get(Calendar.MONTH);
            this.mDay = c.get(Calendar.DAY_OF_MONTH);
            this.mHour = c.get(Calendar.HOUR_OF_DAY);
            this.mMinute = c.get(Calendar.MINUTE);

            validacaoMinutos(this.btHorainicio);
            validacaoMinutos(this.btHoraFim);
            this.btDate.setText(mDay + "/" + mMonth + "/" + mYear);
            this.formTaskModel.setDate(this.mYear, this.mMonth, this.mDay, mHour, mMinute);
            this.formTaskModel.setEnd_time(mYear, mMonth, mDay, mHour, mMinute);
        }
    }



    /**
     *
     * Faz a chamada rest nas tarefas (task) do usuario logado, soma as horas trabalhadas e apresenta no TextView "QuantidadeDEHoras"
     *
     */
    private void loadDisplayAllHoursWork() {
        jsonNetwork.callJsonObjectGet(VolleySingleton.URL_GET_TASK_USER_ID + this.user.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                    Long time = null;
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        if (time == null) {
                            time = jsonObject.optLong("time");
                        } else {
                            time = time + jsonObject.optLong("time");
                        }
                    }
                    tvQuantidadeDeHoras.setText(String.format(" %d min ", TimeUnit.MILLISECONDS.toMinutes(time)));
                    tvQuantidadeDeHoras.refreshDrawableState();
                    progress.dismiss();

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


    /**
     * Chamada Get em todos os projetos,
     * carrega a lista de projetos em um spinner
     */
    private void loadListSpinnerProject() {
        jsonNetwork.callJsonObjectGet(VolleySingleton.URL_GET_PROJECT_USER_ID + user.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listaDeProjetosObjProject = parseProject.parseJsonToProjectEntity(response);
                    listaDeProjetosString = parseProject.parseListProjectEntityToString(listaDeProjetosObjProject);
                    SharedPreferencesUtil.setListProjectInSharedPreferences(listaDeProjetosObjProject);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, listaDeProjetosString);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            formTaskModel.setProject(listaDeProjetosObjProject.get(position).getId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
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






    /**
     * DATE PICKER PARA VERSÕES SUPERIORES A HONEYCOMB (11)
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void calendarioPickerDialog() {
        Calendar calendarDefaul = Calendar.getInstance();
        calendarDefaul.set(this.mYear, this.mMonth, this.mDay);


        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                calendarDefaul.get(Calendar.YEAR),
                calendarDefaul.get(Calendar.MONTH),
                calendarDefaul.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(getActivity().getFragmentManager(), "Calendario");
        datePickerDialog.setAccentColor(getResources().getColor(R.color.primary));
    }

    /**
     * TIME PICKER PARA VERSÕES SUPERIORES A HONEYCOMB (11)
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void relogioPickerDialog() {
        Calendar relogioDefaul = Calendar.getInstance();
        relogioDefaul.set(this.mYear, this.mMonth, this.mDay, this.mHour, this.mMinute);

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                this,
                relogioDefaul.get(Calendar.HOUR_OF_DAY),
                relogioDefaul.get(Calendar.MINUTE),
                true
        );

        timePickerDialog.show(getActivity().getFragmentManager(), "Relogio");
        timePickerDialog.setAccentColor(getActivity().getResources().getColor(R.color.primary));
    }


    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar tDefaul = Calendar.getInstance();
        tDefaul.set(year, mMonth, mDay, mHour, mMinute);

        this.mYear = year;
        this.mMonth = monthOfYear;
        this.mDay = dayOfMonth;

        this.btDate.setText(mDay + "/" + (mMonth + 1) + "/" + year);
        this.formTaskModel.setDate(this.mYear, this.mMonth, this.mDay, mHour, mMinute);
    }


    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
        this.mHour = hourOfDay;
        this.mMinute = minute;

        if (btUpdateButtonHoras == btHorainicio) {
            validacaoMinutos(btUpdateButtonHoras);
            this.formTaskModel.setStart_time(this.mYear, this.mMonth, this.mDay, this.mHour, this.mMinute);
        }
        if(btUpdateButtonHoras == btHoraFim) {
            validacaoMinutos(btUpdateButtonHoras);
            this.formTaskModel.setEnd_time(this.mYear, this.mMonth, this.mDay, this.mHour, this.mMinute);
        }

        this.btUpdateButtonHoras = null;
    }









    /**
     * DATE PICKER PARA VERSÕES INFERIORES A HONEYCOMB (11)
     */
    private void calendarioPickerDialogVersaoInferior() {
        Calendar calendarDefault = Calendar.getInstance();
        calendarDefault.set(this.mYear, this.mMonth, this.mDay);

        android.app.DatePickerDialog datePicker = new android.app.DatePickerDialog(getContext(), new android.app.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar tCalendar = Calendar.getInstance();
                tCalendar.set(year, monthOfYear, dayOfMonth);

                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;

                btDate.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                formTaskModel.setDate(year, mMonth, mDay, mHour, mMinute);
            }
        }, calendarDefault.get(Calendar.YEAR), calendarDefault.get(Calendar.MONTH), calendarDefault.get(Calendar.DAY_OF_MONTH));

        datePicker.show();
    }

    /**
     * TIME PICKER PARA VERSÕES INFERIORES A HONEYCOMB (11)
     */
    private void relogioPickerDialogVersaoInferior() {
        Calendar calendarDefault = Calendar.getInstance();
        calendarDefault.set(this.mYear, this.mMonth, this.mDay, this.mHour, this.mMinute);

        android.app.TimePickerDialog timePicker = new android.app.TimePickerDialog(getContext(), new android.app.TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;

                if (btUpdateButtonHoras == btHorainicio) {
                    validacaoMinutos(btUpdateButtonHoras);
                    formTaskModel.setStart_time(mYear, mMonth, mDay, mHour, mMinute);
                }
                if(btUpdateButtonHoras == btHoraFim) {
                    validacaoMinutos(btUpdateButtonHoras);
                    formTaskModel.setEnd_time(mYear, mMonth, mDay, mHour, mMinute);
                }

                btUpdateButtonHoras = null;
            }
        }, calendarDefault.get(Calendar.HOUR_OF_DAY), calendarDefault.get(Calendar.MINUTE), true);

        timePicker.show();
    }



    /**
     * Validação e preenchimento do formulario
     * @return true se estiver tudo ok....false se algum campo estiver incorreto
     * @throws JSONException
     */
    private boolean buildForm() throws JSONException {
        requestBody = new JSONObject();

        if (formTaskModel.getProject() != null) {
            requestBody.put("project", formTaskModel.getProject());
        } else {
            return false;
        }

        if (user.getId() != null) {
            requestBody.put("user", user.getId());
        } else {
            return false;
        }

        if (formTaskModel.getDate() != null) {
            requestBody.put("date", formTaskModel.getDate());
        } else {
            return false;
        }

        if (formTaskModel.getStartTime() != null) {
            btHorainicio.setError(null);
            requestBody.put("start_time", formTaskModel.getStartTime());
        } else {
            btHorainicio.setError(getContext().getString(R.string.activity_home_button_horas_inicio_error));
            return false;
        }

        if (formTaskModel.getEndTime() != null) {
            requestBody.put("end_time", formTaskModel.getEndTime());
        } else {
            return false;
        }

        if (this.etNomeAtividade.getText().toString().length() >4 ) {
            etNomeAtividade.setError(null);
            requestBody.put("name", this.etNomeAtividade.getText().toString());
        } else {
            etNomeAtividade.setError(getContext().getString(R.string.activity_home_edit_text_nome_atividade_error));
            return false;
        }

        requestBody.put("comments", this.etDescricaoAtividade.getText().toString());
        formTaskModel.calculaTime();
        requestBody.put("time", formTaskModel.getTime());


        return true;
    }


    /**
     * Limpa os campos do formulario
     */
    private void clearFilderForm() {
        this.formTaskModel = new FormTaskModel();
        etNomeAtividade.getText().clear();
        etDescricaoAtividade.getText().clear();
        loadDateCurrent();
        loadDisplayAllHoursWork();
        loadListSpinnerProject();

    }

    private void validacaoMinutos(Button bt) {
        if (mMinute < 10) {
            bt.setText(this.mHour + ":" + "0"+this.mMinute);
        } else {
            bt.setText(this.mHour + ":" + this.mMinute);
        }
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
            SharedPreferencesUtil.deleteSharedPreferencesUser(SharedPreferencesUtil.KEY_USER_LOGIN_PREFERENCE_USERNAME);
            Intent intent = new Intent(this, LoginActivity.class);
            getActivity().finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        HomeExitDialogFragment exitDialogFragment = new HomeExitDialogFragment();
        FragmentManager fm = getSupportFragmentManager();
        exitDialogFragment.show(fm, "HomeExitDialogFragment");
    }
}
