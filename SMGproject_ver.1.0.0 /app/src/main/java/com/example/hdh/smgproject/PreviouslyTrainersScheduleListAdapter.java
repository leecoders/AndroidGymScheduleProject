package com.example.hdh.smgproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class PreviouslyTrainersScheduleListAdapter extends BaseAdapter {

    private Context context;
    private List<PT> ptList;
    private Activity parentActivity;

    public TextView FeedBackValue;

    public String ptDate;


    public PreviouslyTrainersScheduleListAdapter(Context context , List<PT> ptList , Activity parentActivity ){
        this.context = context;
        this.ptList = ptList;
        this.parentActivity = parentActivity;
    }

    //현재 사용자의 개수
    @Override
    public int getCount() {
        return ptList.size();
    }

    //유저리스트의 특정 사용자를 반환
    @Override
    public Object getItem(int i) {
        return ptList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.trainers_previously_schedule, null);
            //new BackGroundTask().execute();

            TextView userID = (TextView) v.findViewById(R.id.ptUserTextofTrainersSchedule);
            TextView ptYear = (TextView) v.findViewById(R.id.ptYearTextofTrainersSchedule);
            TextView ptMonth = (TextView) v.findViewById(R.id.ptMonthTextofTrainersSchedule);
            TextView ptDay = (TextView) v.findViewById(R.id.ptDayTextofTrainersSchedule);
            TextView ptTime = (TextView) v.findViewById(R.id.ptTimeTextofTrainersSchedule);
            TextView ptTrainer = (TextView) v.findViewById(R.id.ptTrainerTextofTrainersSchedule);
            TextView ptDOTWTextofSchedule = (TextView) v.findViewById(R.id.ptDOTWTextofSchedule);

            FeedBackValue = (TextView) v.findViewById(R.id.FeedBackValue);

            userID.setText(ptList.get(i).getUserID());
            ptYear.setText(ptList.get(i).getPtYear());
            ptMonth.setText(ptList.get(i).getPtMonth());
            ptDay.setText(ptList.get(i).getPtDay());
            ptTime.setText(ptList.get(i).getPtTime());
            ptTrainer.setText(ptList.get(i).getPtTrainer());
            ptDOTWTextofSchedule.setText(ptList.get(i).getPtDOTW());

            if (ptList.get(i).getFeedBackValue() == 1) {
                FeedBackValue.setText("작성");
            } else {
                FeedBackValue.setText("미작성");
                FeedBackValue.setTextColor(Color.GRAY);
            }


            //특정유저의 아이디값을 반환
            v.setTag(ptList.get(i).getUserID());

        return v;
    }

}
