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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class SchedulePTListAdapter extends BaseAdapter {

    private Context context;
    private List<PT> ptList;
    private Fragment parent;
    public int PTNum;
    public Button deleteButtonofSchedule;
    public String ptDate;

    public String ptDOTWDate;
    public SchedulePTListAdapter(Context context, List<PT> ptList, Fragment parent) {
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
        View v = View.inflate(context, R.layout.schedule, null);



        TextView ptYearText = (TextView) v.findViewById(R.id.ptYearTextofSchedule);
        TextView ptMonthText = (TextView) v.findViewById(R.id.ptMonthTextofSchedule);
        TextView ptDayText = (TextView) v.findViewById(R.id.ptDayTextofSchedule);
        TextView ptTimeText = (TextView) v.findViewById(R.id.ptTimeTextofSchedule);
        TextView ptTrainerText = (TextView) v.findViewById(R.id.ptTrainerTextofSchedule);
        TextView nowTime = (TextView) parent.getView().findViewById(R.id.nowTime);
        TextView ptDOTWTextofSchedule = (TextView) v.findViewById(R.id.ptDOTWTextofSchedule);

        ptDOTWDate = ptList.get(i).getPtYear().substring(0, ptList.get(i).getPtYear().length() - 1) + "년 " +
                ptList.get(i).getPtMonth().substring(0, ptList.get(i).getPtMonth().length() - 1) + "월 " +
                ptList.get(i).getPtDay().substring(0, ptList.get(i).getPtDay().length() - 1) + "일";

        ptYearText.setText(ptList.get(i).getPtYear());
        ptMonthText.setText(ptList.get(i).getPtMonth());
        ptDayText.setText(ptList.get(i).getPtDay());
        ptTimeText.setText("PT TIME : " + ptList.get(i).getPtTime());
        ptTrainerText.setText("트레이너 - " + ptList.get(i).getPtTrainer());
        nowTime.setText("현재시간 : " + getDateString());
        ptDOTWTextofSchedule.setText(ptList.get(i).getPtDOTW());

        v.setTag(ptList.get(i).getPtID());

        deleteButtonofSchedule = (Button) v.findViewById(R.id.deleteButtonofSchedule);
        deleteButtonofSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = UserMainActivity.userID;

                ptDate = ptList.get(i).getPtYear().substring(0, ptList.get(i).getPtYear().length() - 1) + "년 " +
                        ptList.get(i).getPtMonth().substring(0, ptList.get(i).getPtMonth().length() - 1) + "월 " +
                        ptList.get(i).getPtDay().substring(0, ptList.get(i).getPtDay().length() - 1) + "일 " +
                        ptList.get(i).getPtTime().substring(0, 2) + "시 " +
                        ptList.get(i).getPtTime().substring(3, 5) + "분";

                if (!DateUtil.compareDate(getDateString(), ptDate)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    builder.setTitle(Html.fromHtml("<strong><font color=\"#ff0000\"> " + "주의"));
                    AlertDialog dialog = builder.setMessage(Html.fromHtml("</font></strong><br>PT를 정말 취소하시겠습니까?"))
                            .setNegativeButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonResponse = new JSONObject(response);

                                                boolean success = jsonResponse.getBoolean("success");

                                                if (success) {

                                                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                                    AlertDialog dialog1 = builder.setMessage("PT가 취소되었습니다.")
                                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Response.Listener<String> responseListenerOfPTNum = new Response.Listener<String>() {
                                                                        @Override
                                                                        public void onResponse(String response) {
                                                                            try {
                                                                                JSONObject jsonResponse = new JSONObject(response);
                                                                                boolean success = jsonResponse.getBoolean("success");
                                                                                if (success) {
                                                                                    FragmentTransaction ft = (parent.getFragmentManager()).beginTransaction();
                                                                                    ft.detach(parent)
                                                                                            .attach(parent)
                                                                                            .commit();
                                                                                    ptList.remove(i);
                                                                                    notifyDataSetChanged();

                                                                                }
                                                                            } catch (Exception e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        }
                                                                    };
                                                                    int ptnum = ScheduleFragment.userPT + 1;
                                                                    PTNumUpdateRequest ptNumUpdateRequest = new PTNumUpdateRequest(UserMainActivity.userID, ptnum, responseListenerOfPTNum);
                                                                    RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                                                                    queue.add(ptNumUpdateRequest);
                                                                }
                                                            })
                                                            .create();
                                                    dialog1.show();
                                                } else {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                                    AlertDialog dialog = builder.setMessage("PT취소에 실패하였습니다.")
                                                            .setNegativeButton("다시 시도", null)
                                                            .create();
                                                    dialog.show();
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    ScheduleDeleteRequest scheduleDeleteRequest = new ScheduleDeleteRequest(UserMainActivity.userID, ptList.get(i).getPtID(), responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                                    queue.add(scheduleDeleteRequest);
                                }
                            }).setPositiveButton("아니오", null)
                            .create();
                    dialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("기간 만료")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
            }
        });
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
