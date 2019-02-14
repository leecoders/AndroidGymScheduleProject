package com.example.hdh.smgproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by hwangdahyeon on 2018. 5. 21..
 */

public class PopTrainerInfo extends Activity {

    TextView NameText;
    TextView EmailText;
    TextView HeightText;
    TextView WeightText;
    TextView AgeText;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_trainerinfo);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.85));

        NameText = (TextView) findViewById(R.id.trainerNameText);
        EmailText = (TextView) findViewById(R.id.trainerEmailText);
        HeightText = (TextView) findViewById(R.id.trainerHeightText);
        WeightText = (TextView) findViewById(R.id.trainerWeightText);
        AgeText = (TextView) findViewById(R.id.trainerAgeText);
        imageView = (ImageView) findViewById(R.id.imageofTrainerinfo);

        NameText.setText(PTListAdapter.TrainerName + " 트레이너");
        EmailText.setText("이메일 : " + PTListAdapter.guestEmail);
        HeightText.setText("키 : " + PTListAdapter.guestHeight + "cm");
        WeightText.setText("몸무게 : " + PTListAdapter.guestWeight + "kg");
        AgeText.setText("나이 : " + PTListAdapter.guestAge);

        if(PTListAdapter.TrainerName.equals("황다현")){
            imageView.setImageResource(R.drawable.dahyeon);
        }
        if(PTListAdapter.TrainerName.equals("이재민")){
            imageView.setImageResource(R.drawable.jeamin);
        }
        if(PTListAdapter.TrainerName.equals("김종범")){
            imageView.setImageResource(R.drawable.jongbum);
        }
        if(PTListAdapter.TrainerName.equals("한상국")){
            imageView.setImageResource(R.drawable.sangguk);
        }
        if(PTListAdapter.TrainerName.equals("김연형")){
            imageView.setImageResource(R.drawable.yunhyung);
        }
        if(PTListAdapter.TrainerName.equals("임성민")){
            imageView.setImageResource(R.drawable.sungmin);
        }


    }
}
