package com.example.hdh.smgproject;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class PreviouslySchedulePTListAdapter extends BaseAdapter {

    private Context context;
    private List<PT> ptList;
    private Fragment parent;
    public String ptDate;

    public TextView FeedBackValue;

    public PreviouslySchedulePTListAdapter(Context context, List<PT> ptList, Fragment parent) {
        this.context = context;
        this.ptList = ptList;
        this.parent = parent;

    }

    @Override
    public int getCount() {
        return ptList.size();
    }

    @Override
    public Object getItem(int position) {
        return ptList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.previously_schedule, null);

        ptDate = ptList.get(i).getPtYear().substring(0, ptList.get(i).getPtYear().length() - 1) + "년 " +
                ptList.get(i).getPtMonth().substring(0, ptList.get(i).getPtMonth().length() - 1) + "월 " +
                ptList.get(i).getPtDay().substring(0, ptList.get(i).getPtDay().length() - 1) + "일 " +
                ptList.get(i).getPtTime().substring(0, 2) + "시 " +
                ptList.get(i).getPtTime().substring(3, 5) + "분";

        TextView ptYearText = (TextView) v.findViewById(R.id.ptYearTextofSchedule);
        TextView ptMonthText = (TextView) v.findViewById(R.id.ptMonthTextofSchedule);
        TextView ptDayText = (TextView) v.findViewById(R.id.ptDayTextofSchedule);
        TextView ptTimeText = (TextView) v.findViewById(R.id.ptTimeTextofSchedule);
        TextView ptTrainerText = (TextView) v.findViewById(R.id.ptTrainerTextofSchedule);
        //TextView ptIDText = (TextView) v.findViewById(R.id.ptIDTextofSchedule);
        TextView nowTime = (TextView) parent.getView().findViewById(R.id.nowTime);
        TextView ptDOTWTextofSchedule = (TextView) v.findViewById(R.id.ptDOTWTextofSchedule);

        FeedBackValue = (TextView) v.findViewById(R.id.FeedBackValue);

        ptYearText.setText(ptList.get(i).getPtYear());
        ptMonthText.setText(ptList.get(i).getPtMonth());
        ptDayText.setText(ptList.get(i).getPtDay());
        ptTimeText.setText("PT TIME : " + ptList.get(i).getPtTime());
        ptTrainerText.setText("트레이너 - " + ptList.get(i).getPtTrainer());
        ptDOTWTextofSchedule.setText(ptList.get(i).getPtDOTW());

        if (ptList.get(i).getFeedBackValue() == 1) {
            FeedBackValue.setText("작성");
        } else {
            FeedBackValue.setText("미작성");
            FeedBackValue.setTextColor(Color.GRAY);
        }

        v.setTag(ptList.get(i).getPtID());

        return v;
    }

    public String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분", Locale.KOREA);
        String str_date = df.format(new Date());
        String year = str_date.substring(0, 4);
        String Month = str_date.substring(6, 8);
        String Day = str_date.substring(10, 12);
        return str_date;
    }
}
