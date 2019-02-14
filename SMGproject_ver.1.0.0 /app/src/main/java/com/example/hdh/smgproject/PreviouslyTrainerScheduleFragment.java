package com.example.hdh.smgproject;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//회원관리

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviouslyTrainerScheduleFragment extends Fragment {

    static public ListView listView;
    static public PreviouslyTrainersScheduleListAdapter adapter;
    private List<PT> ptList;
    private TextView ptNullText;
    public String ptDOTWDate;


    public String ptDate;
    static public int ptID;

    public PreviouslyTrainerScheduleFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_previously_trainer_schedule, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);





        //리스트뷰 초기화
        listView = (ListView) getView().findViewById(R.id.TrainersScheduleListView);

        ptNullText = (TextView) getView().findViewById(R.id.ptNullText);

        ptList = new ArrayList<PT>();
        adapter = new PreviouslyTrainersScheduleListAdapter(getContext().getApplicationContext(), ptList , getActivity());
        listView.setAdapter(adapter);

        listView = (ListView) getView().findViewById(R.id.TrainersScheduleListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrainerScheduleFragment.userIDInfo = ptList.get(position).getUserID();
                ptID = ptList.get(position).ptID;
                CEOMainActivity.CEOCHECk = false;
                new BackGroundTaskOfUserInfo().execute();
            }
        });

        new BackGroundTaskForTrainersScheduleInfo().execute();
    }



    class BackGroundTaskForTrainersScheduleInfo extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://kjg123kg.cafe24.com/TrainersSchedule_SYG.php?trainerID=" + URLEncoder.encode(TrainerMainActivity.userID, "UTF-8");
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

                int a = 0;

                int count = 0;
                int ptID;
                String userID, ptYear, ptMonth ,ptDay, ptTime , ptTrainer ;
                String DOTWDate = null;
                int FeedBackValue;


                while (count < jsonArray.length()) {

                    JSONObject object = jsonArray.getJSONObject(count);

                    ptYear = object.getString("ptYear");
                    ptMonth = object.getString("ptMonth");
                    ptDay = object.getString("ptDay" );
                    ptID = object.getInt("ptID");
                    ptTime = object.getString("ptTime" );
                    ptTrainer = "트레이너 : " + object.getString("ptTrainer") ;
                    userID = object.getString("userID");
                    FeedBackValue = object.getInt("FeedBackValue");

                    ptDate = ptYear.substring(0, ptYear.length() - 1) + "년 " +
                            ptMonth.substring(0, ptMonth.length() - 1) + "월 " +
                            ptDay.substring(0, ptDay.length() - 1) + "일 " +
                            ptTime.substring(0, 2) + "시 " +
                            ptTime.substring(3, 5) + "분";

                    ptDOTWDate = ptYear.substring(0, ptYear.length() - 1) + "년 " +
                            ptMonth.substring(0, ptMonth.length() - 1) + "월 " +
                            ptDay.substring(0, ptDay.length() - 1) + "일";

                    try {
                        DOTWDate =  getDateDay(ptDOTWDate , "yyyy년 MM월 dd일");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    PT pt = new PT(userID, ptID, ptYear, ptMonth , ptDay,"PT TIME : " +  ptTime, ptTrainer , DOTWDate , FeedBackValue);
                    if (DateUtil.compareDate(getDateString(), ptDate)) {
                        ptList.add(pt);
                        a++;
                    }
                    count++;
                }
                if(a == 0){
                    listView.setVisibility(View.GONE);
                    ptNullText.setVisibility(View.VISIBLE);
                } else {
                    listView.setVisibility(View.VISIBLE);
                    ptNullText.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class BackGroundTaskOfUserInfo extends AsyncTask<Void, Void, String> {
        String target;
        @Override
        protected void onPreExecute() {
            try {
                target = "http://kjg123kg.cafe24.com/UserSelect_SYG.php?userID=" + URLEncoder.encode(TrainerScheduleFragment.userIDInfo, "UTF-8");
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

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    TrainerScheduleFragment.userNameInfo = object.getString("userName");
                    TrainerScheduleFragment.userEmailInfo = object.getString("userEmail");
                    TrainerScheduleFragment.userGenderInfo = object.getString("userGender");
                    TrainerScheduleFragment.userHeightInfo = object.getString("userHeight");
                    TrainerScheduleFragment.userWeightInfo = object.getString("userWeight");
                    TrainerScheduleFragment.userAgeInfo = object.getString("userAge") + "세";
                    count++;
                }

                TrainerScheduleFragment.PreviouslyCheck = true;
                Intent intent = new Intent(getContext() ,  PopUserInfo.class);
                getContext().startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분", Locale.KOREA);
        String str_date = df.format(new Date());
        return str_date;
    }

    public String getDateDay(String date, String dateType) throws Exception {


        String day = "" ;

        SimpleDateFormat dateFormat = new SimpleDateFormat(dateType) ;
        Date nDate = dateFormat.parse(date) ;

        Calendar cal = Calendar.getInstance() ;
        cal.setTime(nDate);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;

        switch(dayNum){
            case 1:
                day = " (일)";
                break ;
            case 2:
                day = " (월)";
                break ;
            case 3:
                day = " (화)";
                break ;
            case 4:
                day = " (수)";
                break ;
            case 5:
                day = " (목)";
                break ;
            case 6:
                day = " (금)";
                break ;
            case 7:
                day = " (토)";
                break ;

        }
        return day ;
    }
}