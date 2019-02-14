package com.example.hdh.smgproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by hwangdahyeon on 2018. 5. 21..
 */

public class PopUserInfo extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_userinfo);

        final String userID, userPassword, userName, userEmail, userGender, userHeight, userWeight, userAge, userPT;

        int textsize = 20;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.85));

        TextView userIDText = (TextView) findViewById(R.id.UserIDText);
        TextView userPassWordText = (TextView) findViewById(R.id.UserPassWordText);
        TextView UserNameText = (TextView) findViewById(R.id.UserNameText);
        TextView UserEmailText = (TextView) findViewById(R.id.UserEmailText);
        TextView UserGenderText = (TextView) findViewById(R.id.UserGenderText);
        TextView UserHeightText = (TextView) findViewById(R.id.UserHeightText);
        TextView UserWeightText = (TextView) findViewById(R.id.UserWeightText);
        TextView UserAgeText = (TextView) findViewById(R.id.UserAgeText);
        TextView userPTText = (TextView) findViewById(R.id.userPTText);

        Button FeedBackButton = (Button) findViewById(R.id.FeedBackButton);
        Button UserInfoChangeButton = (Button) findViewById(R.id.UserInfoChangeButton);

        if (!TrainerScheduleFragment.PreviouslyCheck) {
            FeedBackButton.setVisibility(View.GONE);
        } else {
            FeedBackButton.setVisibility(View.VISIBLE);
        }

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        userPassword = intent.getStringExtra("userPassword");
        userName = intent.getStringExtra("userName");
        userEmail = intent.getStringExtra("userEmail");
        userGender = intent.getStringExtra("userGender");
        userHeight = intent.getStringExtra("userHeight");
        userWeight = intent.getStringExtra("userWeight");
        userAge = intent.getStringExtra("userAge");
        userPT = intent.getStringExtra("userPT");

        FeedBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopUserInfo.this, PopFeedBack.class);
                startActivity(intent);
                TrainerMainActivity.userIDCheck = true;
                finish();
            }
        });

        if (!CEOMainActivity.CEOCHECk) {
            userIDText.setText("회원ID : " + TrainerScheduleFragment.userIDInfo);
            userIDText.setTextSize(textsize - 3);

            UserNameText.setText("회원명 : " + TrainerScheduleFragment.userNameInfo);
            UserNameText.setTextSize(textsize);

            UserEmailText.setText("이메일 : " + TrainerScheduleFragment.userEmailInfo);
            UserEmailText.setTextSize(textsize - 5);

            UserGenderText.setText("    성별     : " + TrainerScheduleFragment.userGenderInfo);
            UserGenderText.setTextSize(textsize);

            UserHeightText.setText("       키     : " + TrainerScheduleFragment.userHeightInfo + "cm");
            UserHeightText.setTextSize(textsize);

            UserWeightText.setText("   몸무게   : " + TrainerScheduleFragment.userWeightInfo + "kg");
            UserWeightText.setTextSize(textsize);

            UserAgeText.setText("     나이     : " + TrainerScheduleFragment.userAgeInfo);
            UserAgeText.setTextSize(textsize);

            FeedBackButton.setTextSize(textsize);

            userPassWordText.setVisibility(View.GONE);
            userPTText.setVisibility(View.GONE);
            UserInfoChangeButton.setVisibility(View.GONE);
        } else {
            userIDText.setText("회원ID : " + userID);
            userPassWordText.setText("비밀번호 : " + userPassword);
            UserNameText.setText("회원명 : " + userName);
            UserEmailText.setText("이메일 : " + userEmail);
            UserGenderText.setText("    성별     : " + userGender);
            UserHeightText.setText("       키     : " + userHeight + "cm");
            UserWeightText.setText("   몸무게   : " + userWeight + "kg");
            UserAgeText.setText("     나이     : " + userAge);
            userPTText.setText("     남은PT횟수     : " + userPT);
            FeedBackButton.setVisibility(View.GONE);
        }

        //정보변경버튼
        UserInfoChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            UserInfoChange.adminCheck = true;
            Intent intent = new Intent(PopUserInfo.this, UserInfoChange.class);

            intent.putExtra("userID", userID);
            intent.putExtra("userPassword", userPassword);
            intent.putExtra("userName", userName);
            intent.putExtra("userEmail",userEmail);
            intent.putExtra("userGender",userGender);
            intent.putExtra("userHeight",userHeight);
            intent.putExtra("userWeight",userWeight);
            intent.putExtra("userAge", userAge);
            intent.putExtra("userPT", Integer.parseInt(userPT));
            startActivity(intent);
            finish();

            }
        });
    }
}
