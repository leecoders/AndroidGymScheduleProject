package com.example.hdh.smgproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PTListAdapter extends BaseAdapter {

    private Context context;
    private List<PT> ptList;
    private Fragment parent;

    public String ptDate;

    static public String TrainerName;
    static String guestName = null, guestEmail = null, guestHeight = null, guestWeight = null, guestAge = null;




    public PTListAdapter(Context context, List<PT> ptList, Fragment parent) {
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
        View v = View.inflate(context, R.layout.pt, null);


        ptDate = ptList.get(i).getPtYear().substring(0, ptList.get(i).getPtYear().length() - 1) + "년 " +
                ptList.get(i).getPtMonth().substring(0, ptList.get(i).getPtMonth().length() - 1) + "월 " +
                ptList.get(i).getPtDay().substring(0, ptList.get(i).getPtDay().length() - 1) + "일 " +
                ptList.get(i).getPtTime().substring(0, 2) + "시 " +
                ptList.get(i).getPtTime().substring(3, 5) + "분";

        TextView ptYearText = (TextView) v.findViewById(R.id.ptYearText);
        TextView ptMonthText = (TextView) v.findViewById(R.id.ptMonthText);
        TextView ptDayText = (TextView) v.findViewById(R.id.ptDayText);
        TextView ptTimeText = (TextView) v.findViewById(R.id.ptTimeText);
        TextView ptTrainerText = (TextView) v.findViewById(R.id.ptTrainerText);
        TextView ptDOTWTextofSchedule = (TextView) v.findViewById(R.id.ptDOTWTextofSchedule);

        //TextView ptIDText = (TextView) v.findViewById(R.id.ptIDText);

        ptYearText.setText(ptList.get(i).getPtYear());
        ptMonthText.setText(ptList.get(i).getPtMonth());
        ptDayText.setText(ptList.get(i).getPtDay());
        ptTimeText.setText("PT TIME : " + ptList.get(i).getPtTime());
        ptTrainerText.setText("트레이너 - " + ptList.get(i).getPtTrainer());
        ptDOTWTextofSchedule.setText(ptList.get(i).getPtDOTW());


        v.setTag(ptList.get(i).getPtID());

        final ImageButton trainerinfoButton = (ImageButton) v.findViewById(R.id.trainerinfoButton);

        trainerinfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainerName = ptList.get(i).getPtTrainer();
                new BackGroundTaskofTrainerInfo().execute();

            }
        });

        final Button addButton = (Button) v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = UserMainActivity.userID;

                ptDate = ptList.get(i).getPtYear().substring(0, ptList.get(i).getPtYear().length() - 1) + "년 " +
                        ptList.get(i).getPtMonth().substring(0, ptList.get(i).getPtMonth().length() - 1) + "월 " +
                        ptList.get(i).getPtDay().substring(0, ptList.get(i).getPtDay().length() - 1) + "일 " +
                        ptList.get(i).getPtTime().substring(0, 2) + "시 " +
                        ptList.get(i).getPtTime().substring(3, 5) + "분";

                if (!DateUtil.compareDate(getDateString(), ptDate)) {
                    if (ReservationFragment.userPT > 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                        builder.setTitle("PT횟수가 차감됩니다.");
                        builder.setMessage("정말로 신청하시겠습니까?");
                        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
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
                                                AlertDialog dialog1 = builder.setMessage("PT가 신청되었습니다.")
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
                                                                            }
                                                                        } catch (Exception e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                };
                                                                int ptnum = ReservationFragment.userPT - 1;
                                                                PTNumUpdateRequest ptNumUpdateRequest = new PTNumUpdateRequest(UserMainActivity.userID, ptnum, responseListenerOfPTNum);
                                                                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                                                                queue.add(ptNumUpdateRequest);

                                                            }
                                                        })
                                                        .create();
                                                dialog1.show();
                                            } else {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                                AlertDialog dialog = builder.setMessage("PT신청에 실패하였습니다.")
                                                        .setNegativeButton("확인", null)
                                                        .create();
                                                dialog.show();

                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                PTAddRequest ptAddRequest = new PTAddRequest(UserMainActivity.userID, ptList.get(i).getPtID() + "", responseListener);
                                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                                queue.add(ptAddRequest);
                            }
                        });
                        builder.setPositiveButton("아니오", null);
                        builder.create();
                        builder.show();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                        AlertDialog dialog = builder.setMessage("PT횟수가 부족합니다.")
                                .setNegativeButton("확인", null)
                                .create();
                        dialog.show();
                    }
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

        return str_date;
    }

    class BackGroundTaskofTrainerInfo extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://kjg123kg.cafe24.com/TrainerInfo_SYG.php?trainerName=" + URLEncoder.encode(TrainerName , "UTF-8");
            } catch (UnsupportedEncodingException e) {
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

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);

                    guestName = object.getString("userName");
                    guestEmail = object.getString("userEmail");
                    guestHeight = object.getString("userHeight");
                    guestWeight = object.getString("userWeight");
                    guestAge = object.getString("userAge") + "세";

                    count++;
                }

                Intent intent = new Intent(parent.getActivity(), PopTrainerInfo.class);
                parent.getActivity().startActivity(intent);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
