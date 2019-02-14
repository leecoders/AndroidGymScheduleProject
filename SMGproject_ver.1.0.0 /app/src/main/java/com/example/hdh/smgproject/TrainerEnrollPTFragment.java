package com.example.hdh.smgproject;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrainerEnrollPTFragment extends Fragment {

    private ArrayAdapter timeApapter;
    private Spinner timeSpinner;


    public String Year;
    public String Month;
    public String Day;

    public String ptDate;
    private CalendarView calendar;

    public TrainerEnrollPTFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trainer_enroll_pt, container, false);
    }

    //처리를 하는 부분.
    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        timeSpinner = (Spinner) getView().findViewById(R.id.timeSpinnerOfTrainer);
        calendar = (CalendarView) getView().findViewById(R.id.calendar);


        timeApapter = ArrayAdapter.createFromResource(getActivity(), R.array.timeoftrainer, android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeApapter);


        final Button ptAddButton = (Button) getView().findViewById(R.id.ptAddButton);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //getDate(getView());
                Year = String.valueOf(year);
                Month = String.valueOf(month + 1);
                Day = String.valueOf(dayOfMonth);

                ptDate = Year + "년 " +
                        Month + "월 " +
                        Day + "일";

                if (DateUtil.compareDateofDay(getDateString(), ptDate)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("이전 날짜는 신청이 불가능합니다")
                            .setNegativeButton("다시 시도", null)
                            .create();
                    builder.show();
                    ptAddButton.setEnabled(false);
                    ptAddButton.setBackground(ContextCompat.getDrawable(getContext() , R.drawable.round_button_enabled));
                }
                else{
                    ptAddButton.setEnabled(true);
                    ptAddButton.setBackground(ContextCompat.getDrawable(getContext() , R.drawable.round_button));
                }
            }
        });



        ptAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("PT 수업을 등록하시겠습니까")
                                        .setNegativeButton("예", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //응답받기
                                                Response.Listener<String> responseListener = new Response.Listener<String>() {

                                                    @Override
                                                    public void onResponse(String response) {
                                                        JSONObject jsonResponse = null;
                                                        try {
                                                            jsonResponse = new JSONObject(response);
                                                            boolean success = jsonResponse.getBoolean("success");
                                                            if (success) {
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                                builder.setMessage("PT수업 등록에 성공했습니다.")
                                                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                            }
                                                                        })
                                                                        .create();
                                                                builder.show();
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                };
                                                if(Day.length() == 1) {
                                                    TrainerPTEnrollRequest trainerPTEnrollRequest = new TrainerPTEnrollRequest(Year + "년", Month + "월", "0" + Day + "일", TrainerMainActivity.tempguestName, timeSpinner.getSelectedItem().toString(), TrainerMainActivity.userID, responseListener);
                                                    RequestQueue queue = Volley.newRequestQueue(getContext());
                                                    queue.add(trainerPTEnrollRequest);
                                                } else {
                                                    TrainerPTEnrollRequest trainerPTEnrollRequest = new TrainerPTEnrollRequest(Year + "년", Month + "월", Day + "일", TrainerMainActivity.tempguestName, timeSpinner.getSelectedItem().toString(), TrainerMainActivity.userID, responseListener);
                                                    RequestQueue queue = Volley.newRequestQueue(getContext());
                                                    queue.add(trainerPTEnrollRequest);
                                                }
                                            }
                                        });
                                builder.setPositiveButton("아니오", null)
                                        .create();
                                builder.show();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("해당시간에 이미 수업이 등록되어 있습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create();
                                builder.show();
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                if(Day.length() == 1) {
                    TrainerPTValidateRequest trainerPTValidateRequest = new TrainerPTValidateRequest(Year + "년", Month + "월", "0" + Day + "일", timeSpinner.getSelectedItem().toString(), TrainerMainActivity.userID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    queue.add(trainerPTValidateRequest);
                } else {
                    TrainerPTValidateRequest trainerPTValidateRequest = new TrainerPTValidateRequest(Year + "년", Month + "월", Day + "일", timeSpinner.getSelectedItem().toString(), TrainerMainActivity.userID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    queue.add(trainerPTValidateRequest);
                }
            }
        });
    }

    public String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA);
        String str_date = df.format(new Date());
        String year = str_date.substring(0, 4);
        String Month = str_date.substring(6, 8);
        String Day = str_date.substring(10, 12);
        return str_date;
    }
}
