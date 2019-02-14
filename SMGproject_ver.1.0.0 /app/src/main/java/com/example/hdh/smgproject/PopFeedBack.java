package com.example.hdh.smgproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by hwangdahyeon on 2018. 5. 21..
 */

public class PopFeedBack extends AppCompatActivity {
    PreviouslyTrainersScheduleListAdapter adapter;
    EditText feedbackContentText;
    PreviouslyTrainerScheduleFragment fragment = new PreviouslyTrainerScheduleFragment();
   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_feedback);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.85));

        new BackGroundTask().execute();

        Button AddFeedBackButton = (Button) findViewById(R.id.AddFeedBackButton);
        feedbackContentText = (EditText) findViewById(R.id.feedbackContentText);

        AddFeedBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PopFeedBack.this);
                                builder.setMessage("피드백 등록에 성공했습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ((TrainerMainActivity)TrainerMainActivity.CONTEXT).recreate();
                                                finish();
                                            }
                                        })
                                        .create();
                                builder.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PopFeedBack.this);
                                builder.setMessage("피드백 등록에 실패했습니다.")
                                        .setNegativeButton("다시시도", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .create();
                                builder.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                FeedBackRequest feedBackRequest = new FeedBackRequest(PreviouslyTrainerScheduleFragment.ptID + "" , feedbackContentText.getText().toString() , responseListener);
                RequestQueue queue = Volley.newRequestQueue(PopFeedBack.this);
                queue.add(feedBackRequest);
            }
        });
    }

    class BackGroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://kjg123kg.cafe24.com/FeedBackContent_SYG.php?ptID=" + URLEncoder.encode(PreviouslyTrainerScheduleFragment.ptID + "", "UTF-8");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String content = "";

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    content = object.getString("FeedBack").toString();
                    count++;
                }
                feedbackContentText.setText(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}