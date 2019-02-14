package com.example.hdh.smgproject;


import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.Gravity.CENTER_HORIZONTAL;


public class UserPageFragment extends Fragment {

    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view;
        Bundle bundle = getArguments();
        String ptDate = bundle.getString("ptDate");
        String ptProgram = bundle.getString("ptProgram");
        String ptTime = bundle.getString("ptTime");
        String ptTrainer = bundle.getString("ptTrainer");
        String ptMember = bundle.getString("ptMember");
        String participants = bundle.getString("participants");

        view = inflater.inflate(R.layout.fragment_user_page, container, false);
        textView = (TextView)view.findViewById(R.id.ptDate);
        textView.setText(ptDate);

        textView = (TextView)view.findViewById(R.id.ptProgram);
        textView.setText(ptProgram);
        textView.setGravity(CENTER_HORIZONTAL);

        textView = (TextView)view.findViewById(R.id.ptTime);
        textView.setText(ptTime);

        textView = (TextView)view.findViewById(R.id.ptTrainer);
        textView.setText(ptTrainer);
        textView.setGravity(CENTER_HORIZONTAL);

        textView = (TextView)view.findViewById(R.id.ptMember);
        textView.setText(ptMember);

        //가운데 가로지르는 줄 추가
        textView = (TextView)view.findViewById(R.id.Line);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        textView = (TextView)view.findViewById(R.id.participants);
        textView.setText(participants);

        return view;
    }
}