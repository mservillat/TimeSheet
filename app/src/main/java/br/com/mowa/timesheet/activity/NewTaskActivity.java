package br.com.mowa.timesheet.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import br.com.mowa.timesheet.model.FormTaskModel;
import br.com.mowa.timesheet.model.ProjectModel;
import br.com.mowa.timesheet.model.TaskModel;
import br.com.mowa.timesheet.model.UserModel;
import br.com.mowa.timesheet.network.CallJsonNetwork;
import br.com.mowa.timesheet.network.VolleySingleton;
import br.com.mowa.timesheet.parse.ParseProject;
import br.com.mowa.timesheet.timesheet.R;
import br.com.mowa.timesheet.utils.SharedPreferencesUtil;

/**
 * Created by walky on 10/8/15.
 */
public class NewTaskActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private FormTaskModel formTaskModel = new FormTaskModel();
    private List<ProjectModel> listaDeProjetosObjProject;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private List<String> listaDeProjetosString;
    private CallJsonNetwork jsonNetwork;
    private ParseProject parseProject;
    private ProgressDialog progress;
    private JSONObject requestBody;
    private UserModel user;
    private TextView etUpdateEditTextHours;
    private EditText etComment;
    private Spinner spinner;
    private EditText etNameTask;
    private FloatingActionButton btEnviar;
    private TextView etStartHours;
    private TextView etDate;
    private TextView etEndHours;
    private boolean isEditTask;
    private boolean isEditTaskDate;
    private TaskModel taskEditObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_activity);

        Gson gson = new Gson();
        taskEditObject = gson.fromJson(getIntent().getStringExtra("taskEdit"), TaskModel.class);
        if (taskEditObject != null) {
            isEditTask = true;
            isEditTaskDate = true;
        } else {
            isEditTask = false;
            isEditTaskDate = false;
        }



        this.progress = createProgressDialog("Loading", "carregando formulario", true, true);
        this.progress.show();


        this.user = SharedPreferencesUtil.getUserFromSharedPreferences();
        jsonNetwork = new CallJsonNetwork();
        parseProject = new ParseProject();

        createToolbar(R.id.activity_new_task_toolbar);


        //        // Component EditText nome e descricao atividade
        this.etNameTask = (EditText) findViewById(R.id.include_activity_new_task_edit_text_title);
        this.etComment = (EditText) findViewById(R.id.include_activity_new_task_edit_text_comment);


//         Components (DATA e HORA)
        this.etDate = (TextView) findViewById(R.id.include_activity_new_task_text_view_date);
        this.etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 11) {
                    calendarioPickerDialog();
                } else {
                    calendarioPickerDialogVersaoInferior();
                }
            }
        });

        this.etStartHours = (TextView) findViewById(R.id.include_activity_new_task_text_view_start_hours);
        this.etStartHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etUpdateEditTextHours = etStartHours;
                if (Build.VERSION.SDK_INT >= 11) {
                    relogioPickerDialog();
                } else {
                    relogioPickerDialogVersaoInferior();
                }
            }
        });

        this.etEndHours = (TextView) findViewById(R.id.include_activity_new_task_text_view_end_hours);
        this.etEndHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etUpdateEditTextHours = etEndHours;
                if (Build.VERSION.SDK_INT >= 11) {
                    relogioPickerDialog();
                } else {
                    relogioPickerDialogVersaoInferior();
                }
            }
        });


//        Request lista de projetos e carrega na view spinner
        this.spinner = (Spinner) findViewById(R.id.include_activity_new_task_spinner_project);
//        this.btSpinner = (ImageButton) findViewById(R.id.activity_home_button_spinner);
//        this.btSpinner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                spinner.performClick();
//
//            }
//        });


        loadListSpinnerProject();
        loadDateCurrent();


//        Components (Floating Button enviar)
        this.btEnviar = (FloatingActionButton) findViewById(R.id.activity_home_floating_button_enviar);
        actionFloatingButton(!isEditTask);



        if (isEditTask) {
            Calendar dateEdit;
            this.etNameTask.setText(taskEditObject.getName());
            this.formTaskModel.setName(taskEditObject.getName());

            this.etDate.setText(taskEditObject.getDateString());
            this.formTaskModel.setDate(taskEditObject.getDateString());

            this.etStartHours.setText(taskEditObject.getStartTimeString());
            this.formTaskModel.setStartTime(taskEditObject.getStartTimeString());
            this.etEndHours.setText(taskEditObject.getEndTimeString());
            this.formTaskModel.setEndTime(taskEditObject.getEndTimeString());
            this.etComment.setText(taskEditObject.getComments());
            formTaskModel.setComments(taskEditObject.getComments());
            disableAndEnableField(false);

        }



    }


    private class AsyncTask extends android.os.AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            finish();
        }
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

            validacaoMinutos(this.etStartHours);
            validacaoMinutos(this.etEndHours);
            this.etDate.setText(mDay + "/" + mMonth + "/" + mYear);
            this.formTaskModel.setDate(this.mYear, this.mMonth, this.mDay, mHour, mMinute);
            this.formTaskModel.setEndTime(mYear, mMonth, mDay, mHour, mMinute);
        }
    }



    /**
     *
     * Faz a chamada rest nas tarefas (task) do usuario logado, soma as horas trabalhadas e apresenta no TextView "QuantidadeDEHoras"
     *
     */


    /**
     * Chamada Get em todos os projetos,
     * carrega a lista de projetos em um spinner
     */
    private void loadListSpinnerProject() {
        jsonNetwork.callJsonObjectGet(VolleySingleton.URL_GET_PROJECT_USER_ID + user.getId(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listaDeProjetosObjProject = parseProject.parseJsonToProjectModel(response);
                    listaDeProjetosString = parseProject.parseListProjectEntityToString(listaDeProjetosObjProject);
                    SharedPreferencesUtil.setListProjectInSharedPreferences(listaDeProjetosObjProject);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, listaDeProjetosString);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            formTaskModel.setProject(listaDeProjetosObjProject.get(position).getId());
                            progress.dismiss();
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




    private void actionFloatingButton(boolean isWhatsAction) {
        if (isWhatsAction) {
            this.btEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(buildForm()) {
                            progress.show();


                            if (!isEditTask) {
                                jsonNetwork.callJsonObjectPost(VolleySingleton.URL_POST_CREATE_TASK, requestBody, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        clearFilderForm();
                                        snack(btEnviar, getResources().getString(R.string.activity_home_button_floating_msg_enviar));
                                        progress.dismiss();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        toast(error.getMessage());
                                    }
                                });
                            } else {
                                jsonNetwork.callJsonObjectPut(VolleySingleton.URL_PUT_TASK_ID + taskEditObject.getId(), requestBody, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        snack(btEnviar, getResources().getString(R.string.activity_home_button_floating_msg_enviar));
                                        disableAndEnableField(false);
                                        progress.dismiss();
                                        new AsyncTask().execute();

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        toast(error.getMessage());
                                    }
                                });
                            }
                        } else {
                            toast("Algum campo não foi preenchido corretamente");
                        }
                    } catch (JSONException e) {

                    }
                }
            });
        } else {
            this.btEnviar.setImageResource(R.drawable.ic_edit_white);
            this.btEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disableAndEnableField(true);
                    actionFloatingButton(isEditTask);
                    btEnviar.setImageResource(R.drawable.ic_check);
                }
            });
        }
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
        clearDateTimeEdit();
        Calendar tDefaul = Calendar.getInstance();
        tDefaul.set(year, mMonth, mDay, mHour, mMinute);

        this.mYear = year;
        this.mMonth = monthOfYear;
        this.mDay = dayOfMonth;

        this.etDate.setText(mDay + "/" + (mMonth + 1) + "/" + year);
        this.formTaskModel.setDate(this.mYear, this.mMonth, this.mDay, mHour, mMinute);
    }


    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
        clearDateTimeEdit();
        this.mHour = hourOfDay;
        this.mMinute = minute;

        if (etUpdateEditTextHours == etStartHours) {
            validacaoMinutos(etUpdateEditTextHours);
            this.formTaskModel.setStartTime(this.mYear, this.mMonth, this.mDay, this.mHour, this.mMinute);
        }
        if(etUpdateEditTextHours == etEndHours) {
            validacaoMinutos(etUpdateEditTextHours);
            this.formTaskModel.setEndTime(this.mYear, this.mMonth, this.mDay, this.mHour, this.mMinute);
        }

        this.etUpdateEditTextHours = null;
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

                etDate.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
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

                if (etUpdateEditTextHours == etStartHours) {
                    validacaoMinutos(etUpdateEditTextHours);
                    formTaskModel.setStartTime(mYear, mMonth, mDay, mHour, mMinute);
                }
                if(etUpdateEditTextHours == etEndHours) {
                    validacaoMinutos(etUpdateEditTextHours);
                    formTaskModel.setEndTime(mYear, mMonth, mDay, mHour, mMinute);
                }

                etUpdateEditTextHours = null;
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
        Boolean isOk = true;

        isOk = validDateAndTime(requestBody);

        if (formTaskModel.getProject() != null) {
            requestBody.put("project", formTaskModel.getProject());
        } else {
            isOk = false;
        }

        if (user.getId() != null) {
            requestBody.put("user", user.getId());
        } else {
            isOk = false;
        }

        if (this.etNameTask.getText().toString().length() >4 ) {
            etNameTask.setError(null);
            requestBody.put("name", this.etNameTask.getText().toString());
        } else {
            etNameTask.setError(getContext().getString(R.string.activity_home_edit_text_nome_atividade_error));
            isOk = false;
        }


        if (isEditTaskDate) {
            requestBody.put("time", taskEditObject.getTime());
        } else if (formTaskModel.verificaStartAndEndTime()){
            if (!(formTaskModel.calculaTime())) {
                isOk = false;
            } else {
                requestBody.put("time", formTaskModel.getTime());
            }
        } else {
            toast("Data ou hora invalido");
            isOk = false;
        }

        requestBody.put("comments", this.etComment.getText().toString());



        return isOk;
    }


    /**
     * Limpa os campos do formulario
     */
    private void clearFilderForm() {
        this.formTaskModel = new FormTaskModel();
        etNameTask.getText().clear();
        etComment.getText().clear();
        loadDateCurrent();
        loadListSpinnerProject();

    }

    private void validacaoMinutos(TextView bt) {
        if (mMinute < 10) {
            bt.setText(this.mHour + ":" + "0"+this.mMinute);
        } else {
            bt.setText(this.mHour + ":" + this.mMinute);
        }
    }

    private void disableAndEnableField(boolean isEnable) {
        this.etNameTask.setEnabled(isEnable);
        this.etDate.setEnabled(isEnable);
        this.etStartHours.setEnabled(isEnable);
        this.etEndHours.setEnabled(isEnable);
        this.spinner.setEnabled(isEnable);
        this.etComment.setEnabled(isEnable);
    }

    private void clearDateTimeEdit() {
        if(isEditTaskDate) {
            isEditTaskDate = false;
            etDate.setText("data");
            etStartHours.setText("inicio");
            etEndHours.setText("fim");
            formTaskModel.setDate(null);
            formTaskModel.setStartTime(null);
            formTaskModel.setEndTime(null);
        }
    }

    private boolean validDateAndTime(JSONObject requestBody) throws JSONException {
        boolean isOk = true;
        if (formTaskModel.getDate() != null) {
            requestBody.put("date", formTaskModel.getDate());
        } else {
            etDate.setError(getContext().getString(R.string.activity_home_button_date_error));
            isOk = false;
        }

        if (formTaskModel.getStartTime() != null) {
            etStartHours.setError(null);
            requestBody.put("start_time", formTaskModel.getStartTime());
        } else {
            etStartHours.setError(getContext().getString(R.string.activity_home_button_horas_inicio_error));
            isOk = false;
        }

        if (formTaskModel.getEndTime() != null) {
            requestBody.put("end_time", formTaskModel.getEndTime());
        } else {
            etEndHours.setError(getContext().getString(R.string.activity_home_button_horas_fim_error));
            isOk = false;
        }

        return isOk;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
