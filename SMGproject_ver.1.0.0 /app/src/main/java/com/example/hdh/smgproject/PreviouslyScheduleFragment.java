package com.example.hdh.smgproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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


public class PreviouslyScheduleFragment extends Fragment {
    public PreviouslyScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_previously_schedule, container, false);
    }

    private ListView ptListView;
    private PreviouslySchedulePTListAdapter adapter;
    private List<PT> ptList;
    private TextView ptNullText;
    static int userPT = 0;

    static int ptID = 0;

    public String ptDate;
    public String ptDOTWDate;

    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);
        ptListView = (ListView) getView().findViewById(R.id.ptListView);
        ptNullText = (TextView) getView().findViewById(R.id.ptNullText);
        ptList = new ArrayList<PT>();
        adapter = new PreviouslySchedulePTListAdapter(getContext().getApplicationContext() , ptList , this);
        ptListView.setAdapter(adapter);

        //유저의 일정을 불러옴.
        new BackGroundTask().execute();

        ptListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ptID = ptList.get(position).getPtID();
                Intent intent = new Intent(getContext() ,  PopFeedBackOfUser.class);
                getContext().startActivity(intent);
            }
        });
    }

    class BackGroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://kjg123kg.cafe24.com/SchedulePTList_SYG.php?userID=" + URLEncoder.encode(UserMainActivity.userID, "UTF-8");
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
                int ptID;
                String ptYear;
                String ptMonth;
                String ptDay;
                String ptTrainer;
                String ptTime;
                String DOTWDate = "";
                int FeedBackValue;
                int a = 0;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    ptID = object.getInt("ptID");
                    ptYear = object.getString("ptYear");
                    ptMonth = object.getString("ptMonth");
                    ptDay = object.getString("ptDay");
                    ptTrainer = object.getString("ptTrainer");
                    ptTime = object.getString("ptTime");
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


                    PT pt = new PT(ptID, ptYear, ptMonth, ptDay, ptTrainer, ptTime , DOTWDate , FeedBackValue);

                    if (DateUtil.compareDate(getDateString(), ptDate)) {
                        ptList.add(pt);
                        a++;
                    }
                    count++;
                }
                if(a == 0){
                    ptListView.setVisibility(View.GONE);
                    ptNullText.setVisibility(View.VISIBLE);
                } else {
                    ptListView.setVisibility(View.VISIBLE);
                    ptNullText.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }

            catch (Exception e){
                e.printStackTrace();
            }
        }
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
                day = "(일)";
                break ;
            case 2:
                day = "(월)";
                break ;
            case 3:
                day = "(화)";
                break ;
            case 4:
                day = "(수)";
                break ;
            case 5:
                day = "(목)";
                break ;
            case 6:
                day = "(금)";
                break ;
            case 7:
                day = " (토)";
                break ;

        }
        return day ;
    }

}
