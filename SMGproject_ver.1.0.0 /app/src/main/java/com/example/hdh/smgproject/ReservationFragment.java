package com.example.hdh.smgproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReservationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReservationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public String Year;
    public String Month;
    public String Day;

    public String tempYear;
    public String tempMonth;
    public String tempDay;


    public String ptDate;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ReservationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReservationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReservationFragment newInstance(String param1, String param2) {
        ReservationFragment fragment = new ReservationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

//    private ArrayAdapter yearApapter;
//    private Spinner yearSpinner;
//
//    private ArrayAdapter monthApapter;
//    private Spinner monthSpinner;
//
//    private ArrayAdapter dayApapter;
//    private Spinner daySpinner;

    private ArrayAdapter trainerApapter;
    private Spinner trainerSpinner;

    private ArrayAdapter timeApapter;
    private Spinner timeSpinner;

    private CalendarView calendar;
    private ScrollView scrollView;

    private ListView ptListView;
    private PTListAdapter ptListAdapter;
    private List<PT> ptList;

    private int count = 0;

    static int userPT = 0;


    public String ptDOTWDate;


    //처리를 하는 부분.
    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        getDateString();
        Year = tempYear;
        if(tempMonth.substring(0 , 1).equals("0")) {
            Month = tempMonth.substring(1, 2);
        } else {
            Month = tempMonth;
        }
        if(tempDay.substring(0 , 1).equals("0")) {
            Day = tempDay.substring(1, 2);
        } else {
            Day = tempDay;
        }

        trainerSpinner = (Spinner) getView().findViewById(R.id.trainerSpinner);
        timeSpinner = (Spinner) getView().findViewById(R.id.timeSpinner);
        calendar = (CalendarView) getView().findViewById(R.id.calendar);
        scrollView = (ScrollView) getView().findViewById(R.id.ScrollView);

        trainerApapter = ArrayAdapter.createFromResource(getActivity(), R.array.trainer, android.R.layout.simple_spinner_dropdown_item);
        trainerSpinner.setAdapter(trainerApapter);
        timeApapter = ArrayAdapter.createFromResource(getActivity(), R.array.time, android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeApapter);


        //리스트뷰를 어댑터로...
        ptListView = (ListView) getView().findViewById(R.id.ptListView);
        ptList = new ArrayList<PT>();
        ptListAdapter = new PTListAdapter(getContext().getApplicationContext(), ptList, this);
        ptListView.setAdapter(ptListAdapter);


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //getDate(getView());
                Year = String.valueOf(year);
                Month = String.valueOf(month + 1);
                Day = String.valueOf(dayOfMonth);

                String SeleteDate = Year + "년 " +
                        Month + "월 " +
                        Day + "일";

                if (DateUtil.compareDateofDay(getDateString(), SeleteDate)) {
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                    builder.setMessage("이전 날짜는 신청이 불가능합니다")
                            .setNegativeButton("다시 시도", null)
                            .create();
                    builder.show();
                }
            }
        });

        Button searchButton = (Button) getView().findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackGroundTask().execute();
            }
        });
        new BackGroundTaskForPTnum().execute();
    }

    public void setListViewHeightBasedOnItems(ListView listView) {

        // Get list adpter of listview;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;

        int numberOfItems = listAdapter.getCount();

        // Get total height of all items.
        int totalItemsHeight = 0;
        for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
            View item = listAdapter.getView(itemPos, null, listView);
            item.measure(0, 0);
            totalItemsHeight += item.getMeasuredHeight() + 10;
        }

        // Get total height of all item dividers.
        int totalDividersHeight = listView.getDividerHeight() * (numberOfItems - 1) ;

        // Set list height.
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalItemsHeight + totalDividersHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reservation, container, false);


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    class BackGroundTask extends AsyncTask<Void, Void, String> {
        String target;
        String day;
        @Override
        protected void onPreExecute() {
            try {
                if(Day.toString().length() == 1){
                    target = "http://kjg123kg.cafe24.com/PTList_SYG.php?ptYear=" +
                            URLEncoder.encode(Year.toString() + "년", "UTF-8") +
                            "&ptMonth=" + URLEncoder.encode(Month.toString() + "월", "UTF-8") +
                            "&ptDay=0" + URLEncoder.encode(Day.toString() + "일", "UTF-8") +
                            "&ptTrainer=" + URLEncoder.encode(trainerSpinner.getSelectedItem().toString(), "UTF-8") +
                            "&ptTime=" + URLEncoder.encode(timeSpinner.getSelectedItem().toString(), "UTF-8");
                } else{
                    target = "http://kjg123kg.cafe24.com/PTList_SYG.php?ptYear=" +
                            URLEncoder.encode(Year.toString() + "년", "UTF-8") +
                            "&ptMonth=" + URLEncoder.encode(Month.toString() + "월", "UTF-8") +
                            "&ptDay=" + URLEncoder.encode(Day.toString() + "일", "UTF-8") +
                            "&ptTrainer=" + URLEncoder.encode(trainerSpinner.getSelectedItem().toString(), "UTF-8") +
                            "&ptTime=" + URLEncoder.encode(timeSpinner.getSelectedItem().toString(), "UTF-8");
                }

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

                ptList.clear();

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

                while (count < jsonArray.length()) {

                    JSONObject object = jsonArray.getJSONObject(count);
                    ptID = object.getInt("ptID");
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

                    PT pt = new PT(ptID, ptYear, ptMonth, ptDay, ptTrainer, ptTime , DOTWDate);
                    if (!DateUtil.compareDate(getDateString(), ptDate)) {
                        ptList.add(pt);
                    }
                    count++;
                }

                if (count == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReservationFragment.this.getActivity());
                    builder.setMessage("조회된 PT가 없습니다. \n 다른 날짜로 조회해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    builder.show();
                }
                setListViewHeightBasedOnItems(ptListView);

                ptListAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //데이터베이스에서 user의 pt횟수 받아오는 BackGroundTask
    class BackGroundTaskForPTnum extends AsyncTask<Void, Void, String> {
        String target;
        TextView userRemainPtNumOfReservation;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://kjg123kg.cafe24.com/UserSelect_SYG.php?userID=" + URLEncoder.encode(UserMainActivity.userID, "UTF-8");

                userRemainPtNumOfReservation = (TextView) getView().findViewById((R.id.userRemainPtNumOfReservation));

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
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    userPT = object.getInt("userPT");
                    count++;
                }

                userRemainPtNumOfReservation.setText("남은 PT횟수는 " + userPT + "번 입니다.");
                //userRemainPtNumOfReservation.setText(Year + "년 " + Month + "월" + Day + "일");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분", Locale.KOREA);
        String str_date = df.format(new Date());
        tempYear = str_date.substring(0, 4);
        tempMonth = str_date.substring(6, 8);
        tempDay = str_date.substring(10, 12);
        return str_date;
    }

    public void getDate(View view) {
        long time = calendar.getDate();
        Date d1 = new Date(time);

        Calendar c = Calendar.getInstance();
        c.setTime(d1);

        Year = String.valueOf(c.get(Calendar.YEAR));
        Month = String.valueOf(c.get(Calendar.MONTH) + 1);
        Day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
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
