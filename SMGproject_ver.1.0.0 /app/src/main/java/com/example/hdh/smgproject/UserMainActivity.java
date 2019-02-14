package com.example.hdh.smgproject;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public Bundle bundle;
    public static String userID;
    public Intent intent;
    public String getExtra;
    public static boolean userIDCheck = true;

    static public int countofPage;

    public static String tempguestName;

    public static Context CONTEXT;


    //뷰페이저를 위한 생성
    ViewPager viewPager;
    UserSwipeAdapter swipeAdapter;

    PreviouslyTrainersScheduleListAdapter adapter;

    FragmentManager manager = getSupportFragmentManager();
    //도트를 위한 생성
    LinearLayout sliderDotspanel;
    int dotCounts;
    ImageView[] dots;

    public String ptDate;

    AlertDialog.Builder builder;
    private AlertDialog dialog;

    static public int countPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CONTEXT = this;

        setContentView(R.layout.activity_user_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //현재 스마트폰의 화면을 세로로고정
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //뷰페이저
        viewPager = findViewById(R.id.userViewPager);
        viewPager.setOffscreenPageLimit(1);
        swipeAdapter = new UserSwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);
        viewPager.setCurrentItem(0, true);

        intent = getIntent();


        //로그인화면을 통해 인텐트 되면 true , 회원정보변경을 통해서 인텐트되면 false
        if (userIDCheck) {
            bundle = getIntent().getExtras(); // 로그인 액티비티로 부터 유저 아이디 가져오기
            userID = bundle.getString("keyUserID");
            getExtra = intent.getStringExtra("userList");
        } else {
            userID = intent.getStringExtra("userID");
            getExtra = intent.getStringExtra("userListofChange");
        }
        getGuestData();
        new BackGroundTaskofPage().execute();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 네비게이션 헤더 이름 변경
        View nav_header_view = navigationView.getHeaderView(0);

        TextView nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.idTextfromNav);
        nav_header_id_text.setText(tempguestName + "님 안녕하세요.");

        ImageView logoImageView = (ImageView) nav_header_view.findViewById(R.id.logoImageView);

        logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    private long lastTimeBackPressed;

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        RelativeLayout l = (RelativeLayout) findViewById(R.id.relativelayout);
        l.setVisibility(View.VISIBLE);


        this.recreate();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish();
            return;
        }
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT);
        lastTimeBackPressed = System.currentTimeMillis();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // 로그아웃 시 모든 스택을 비운다.
            if (id == R.id.action_settings) {
                //LoginActivity.LogoutCheck = true;
                SharedPreferences sf = getSharedPreferences("LoginSaveData", 0);
                SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
                editor.putBoolean("Value", false);
                editor.commit();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //내정보
        if (id == R.id.nav_myinfo) {
            RelativeLayout l = (RelativeLayout) findViewById(R.id.relativelayout);
            l.setVisibility(View.GONE);
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().replace(R.id.content_user_main, new MyinfoFragment()).addToBackStack(null).commit();
        }
        //예약하기
        else if (id == R.id.nav_reservation) {
            RelativeLayout l = (RelativeLayout) findViewById(R.id.relativelayout);
            l.setVisibility(View.GONE);
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().replace(R.id.content_user_main, new ReservationFragment()).addToBackStack(null).commit();
        }
        //일정확인
        else if (id == R.id.nav_schdulefix) {
            RelativeLayout l = (RelativeLayout) findViewById(R.id.relativelayout);
            l.setVisibility(View.GONE);
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().replace(R.id.content_user_main, new ScheduleFragment()).addToBackStack(null).commit();
        }

        //지난일정확인
        else if (id == R.id.nav_schdulefix_previously) {
            RelativeLayout l = (RelativeLayout) findViewById(R.id.relativelayout);
            l.setVisibility(View.GONE);
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().replace(R.id.content_user_main, new PreviouslyScheduleFragment()).addToBackStack(null).commit();
        }

        //추천운동
        else if (id == R.id.Recommend) {
            RelativeLayout l = (RelativeLayout) findViewById(R.id.relativelayout);
            l.setVisibility(View.GONE);
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().replace(R.id.content_user_main, new RecommendFragment()).addToBackStack(null).commit();
        }

        //어플리케이션 정보
        else if (id == R.id.appinfo) {
            startActivity(new Intent(this, Pop.class));
        }

        //로그아웃
        else if (id == R.id.logout) {
            // 로그아웃 시 모든 스택을 비운다.
            SharedPreferences sf = getSharedPreferences("LoginSaveData", 0);
            SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
            editor.putBoolean("Value", false);
            editor.commit();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class BackGroundTaskofPage extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://kjg123kg.cafe24.com/SchedulePTList_SYG.php?userID=" + URLEncoder.encode(userID, "UTF-8");

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
                int a = 0;
                String ptYear;
                String ptMonth;
                String ptDay;
                String ptTrainer;
                String ptTime;

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

                    if (!DateUtil.compareDate(getDateString(), ptDate)) {
                        a++;
                    }
                    count++;
                }
                countofPage = a;
                //도트
                sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
                dotCounts = countofPage;
                dots = new ImageView[dotCounts];


                for (int i = 0; i < dotCounts; i++) {

                    dots[i] = new ImageView(UserMainActivity.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(10, 0, 10, 0);

                    sliderDotspanel.addView(dots[i], params);
                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                        for (int i = 0; i < dotCounts; i++) {
                            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                        }

                        dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

            } catch (Exception e) {
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

    public void getGuestData() {
        try {
            JSONObject jsonObject;
            if (userIDCheck) {
                jsonObject = new JSONObject(intent.getStringExtra("userList"));
            } else {
                jsonObject = new JSONObject(intent.getStringExtra("userListofChange"));
            }
            JSONArray jsonArray = jsonObject.getJSONArray("response");

            int count = 0;

            String guestID, guestName;

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                guestID = object.getString("userID");
                guestName = object.getString("userName");

                if (userID.equals(guestID)) {
                    tempguestName = guestName;
                }
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
