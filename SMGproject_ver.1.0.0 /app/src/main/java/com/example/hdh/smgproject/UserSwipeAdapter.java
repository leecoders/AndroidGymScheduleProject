package com.example.hdh.smgproject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

/**
 * Created by me2in on 2018. 5. 16..
 */

public class UserSwipeAdapter extends FragmentStatePagerAdapter {

    String member;
    public int countofPage = 0;

    public UserSwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    static public String year[] = new String[20];
    static public String month[] = new String[20];
    static public String day[] = new String[20];
    static public String time[] = new String[20];
    static public String trainer[] = new String[20];
    static public String DOTW[] = new String[20];

    public String ptDate;
    public String ptDOTWDate;

    public Boolean Nonpagecheck = true;
    public Boolean isZero = false;
    public String week;

    @Override
    public Fragment getItem(int position) { // 0~cnt까지 정상 실행됨.

        Fragment UserPageFragment = new UserPageFragment();
        member = UserMainActivity.userID;

        //페이지0일때 처리
        if(isZero) {

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
            String getTime = sdf.format(date);
            try {
                week = getDateDay(getTime , "yyyy년 MM월 dd일");
            } catch (Exception e) {
                e.printStackTrace();
            }

            Bundle bundle = new Bundle(50);
            bundle.putString("ptDate", "  " + getTime + " " + week + "  ");
            bundle.putString("ptProgram", "Personal Training"); // 추후 운동 프로그램 늘어나면 이름 변경
            bundle.putString("ptTime", "신청된 일정이 없습니다."); // 운동 시간대
            bundle.putString("ptTrainer", "예약하기 메뉴에서\nPT를 신청해 주세요"); // 트레이너 이름
            bundle.putString("ptMember", null);  // 회원
            bundle.putString("participants", "GUIDE");
            UserPageFragment.setArguments(bundle);

        } else {
            Bundle bundle = new Bundle(50);
            bundle.putString("ptDate", "  " + year[position] + " " + month[position] + " " + day[position] + " " + DOTW[position] + "  "); // 연월일 들어갈 부분
            bundle.putString("ptProgram", "Personal Training"); // 추후 운동 프로그램 늘어나면 이름 변경
            bundle.putString("ptTime", time[position]); // 운동 시간대
            bundle.putString("ptTrainer", trainer[position] + " 트레이너"); // 트레이너 이름
            bundle.putString("ptMember", member + " 회원님");  // 회원
            bundle.putString("participants", "PARTICIPANTS");
            UserPageFragment.setArguments(bundle);
        }

        return UserPageFragment;
    }

    @Override
    public int getCount() {
        if (Nonpagecheck) {
            new BackGroundTask().execute();
        }
        return countofPage;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
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
                Nonpagecheck = false;

                int count = 0;
                int a = 0;
                String ptYear;
                String ptMonth;
                String ptDay;
                String ptTrainer;
                String ptTime;
                String DOTWDate = "";
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    ptYear = object.getString("ptYear");
                    ptMonth = object.getString("ptMonth");
                    ptDay = object.getString("ptDay");
                    ptTrainer = object.getString("ptTrainer");
                    ptTime = object.getString("ptTime");

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

                    if (!DateUtil.compareDate(getDateString(), ptDate)) {
                        year[a] = ptYear;
                        month[a] = ptMonth;
                        day[a] = ptDay;
                        time[a] = ptTime;
                        trainer[a] = ptTrainer;
                        DOTW[a] = DOTWDate;
                        a++;
                    }
                    count++;
                }

                if(a == 0) {
                    a = 1;
                    isZero = true;
                }
                countofPage = a;

                notifyDataSetChanged();
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
                day = "(토)";
                break ;

        }
        return day ;
    }

}