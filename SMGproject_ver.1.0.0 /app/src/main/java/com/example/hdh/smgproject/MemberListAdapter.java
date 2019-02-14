package com.example.hdh.smgproject;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MemberListAdapter extends BaseAdapter {

    private Context context;
    private List<Member> mList;
    private Activity parent;

    public String ptDate;

    private TextView nameText;
    private TextView majorText;
    private TextView emailText;
    private ImageView imageView;


    public MemberListAdapter(Context context, List<Member> mList, Activity parent) {
        this.context = context;
        this.mList = mList;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.member, null);

        nameText = (TextView) v.findViewById(R.id.memberName);
        majorText = (TextView) v.findViewById(R.id.memberMajor);
        emailText = (TextView) v.findViewById(R.id.memberEmail);
        imageView = (ImageView) v.findViewById(R.id.image);

        if(mList.get(i).getImage().equals("황")){
            imageView.setImageResource(R.drawable.dahyeon);
        }
        if(mList.get(i).getImage().equals("이")){
            imageView.setImageResource(R.drawable.jeamin);
        }
        if(mList.get(i).getImage().equals("김종")){
            imageView.setImageResource(R.drawable.jongbum);
        }
        if(mList.get(i).getImage().equals("한")){
            imageView.setImageResource(R.drawable.sangguk);
        }
        if(mList.get(i).getImage().equals("임")){
            imageView.setImageResource(R.drawable.sungmin);
        }
        if(mList.get(i).getImage().equals("김연")){
            imageView.setImageResource(R.drawable.yunhyung);
        }

        nameText.setText(mList.get(i).getMemberName());
        majorText.setText(mList.get(i).getMemberMajor());
        emailText.setText(mList.get(i).getMemberEmail());

        return v;
    }

}
