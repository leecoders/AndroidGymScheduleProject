package com.example.hdh.smgproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    String guestID;
    int loginID;
    boolean loginCheck;

    public String AutoID, AutoPassword;    //아이디 , 비밀번호 자동저장 변수

    static public Boolean LogoutCheck = false;  //로그아웃인지 아닌지 확인하는 변수.

    String sfName = "LoginSaveData";    //sf네임 변수

    static public CheckBox SavecheckBox;      //체크박스 변수

    static public Context CONTEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CONTEXT = this;

        loginCheck = true;

        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);    //LoginActivity에서 RegisterActivity로 화면전환
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);

        SavecheckBox = (CheckBox) findViewById(R.id.SavecheckBox);

        // 지난번 저장해놨던 사용자 입력값을 꺼내서 보여주기
        SharedPreferences sf = getSharedPreferences(sfName, 0);
        AutoID = sf.getString("ID", ""); // 키값으로 꺼냄
        AutoPassword = sf.getString("Password", ""); // 키값으로 꺼냄
        Boolean value = sf.getBoolean("Value", false);
//        idText.setText(AutoID); // EditText에 반영함
//        passwordText.setText(AutoPassword);
        SavecheckBox.setChecked(value);

        final Button loginButton = (Button) findViewById(R.id.loginButton);

        //자동로그인 -> not logout
        if (SavecheckBox.isChecked()) {
            Response.Listener<String> responseLister = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            if (loginCheck) {
                                guestID = AutoID;

                                if (AutoID.contains("trainer")) {
                                    loginID = 1;
                                    new BackGroundTask().execute();
                                    finish();
                                } else if (AutoID.contains("admin")) {
                                    loginID = 2;

                                    new BackGroundTask().execute();
                                    finish();
                                } else {
                                    loginID = 0;
                                    new BackGroundTask().execute();
                                    finish();
                                }

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인중입니다. 잠시만 기다려주세요.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            dialog = builder.setMessage("계정을 다시 확인하세요.")
                                    .setNegativeButton("다시 시도", null)
                                    .create();
                            dialog.show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            LoginRequest loginRequest = new LoginRequest(AutoID, AutoPassword, responseLister);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        } else {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String userID = idText.getText().toString();
                    String userPassword = passwordText.getText().toString();

                    if (SavecheckBox.isChecked()) {
                        // TODO : CheckBox is checked.
                        SharedPreferences sf = getSharedPreferences(sfName, 0);
                        SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
                        String strID = idText.getText().toString(); // 사용자가 입력한 값
                        String strPassword = passwordText.getText().toString(); // 사용자가 입력한 값
                        editor.putString("ID", strID); // 입력
                        editor.putString("Password", strPassword); // 입력
                        editor.putBoolean("Value", true);  //입력
                        editor.commit(); // 파일에 최종 반영함
                    } else {
                        // TODO : CheckBox is unchecked.
                        SharedPreferences sf = getSharedPreferences(sfName, 0);
                        SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
                        editor.putString("ID", ""); // 입력
                        editor.putString("Password", ""); // 입력
                        editor.putBoolean("Value", false);  //입력
                        editor.commit(); // 파일에 최종 반영함
                    }


                    Response.Listener<String> responseLister = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    if (loginCheck) {
                                        guestID = userID;
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        dialog = builder.setMessage("로그인에 성공했습니다.")
                                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        if (userID.contains("trainer")) {
                                                            loginID = 1;
                                                            new BackGroundTask().execute();
                                                            finish();
                                                        } else if (userID.contains("admin")) {
                                                            loginID = 2;

                                                            new BackGroundTask().execute();
                                                            finish();
                                                        } else {
                                                            loginID = 0;
                                                            new BackGroundTask().execute();
                                                            finish();
                                                        }
                                                    }
                                                })
                                                .create();
                                        dialog.show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        dialog = builder.setMessage("로그인중입니다. 잠시만 기다려주세요.")
                                                .setNegativeButton("확인", null)
                                                .create();
                                        dialog.show();
                                    }
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    dialog = builder.setMessage("계정을 다시 확인하세요.")
                                            .setNegativeButton("다시 시도", null)
                                            .create();
                                    dialog.show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseLister);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }
            });
        }
        TextView information = (TextView) findViewById(R.id.appInfo);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Pop.class));
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    class BackGroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://kjg123kg.cafe24.com/UserList_SYG.php";

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

            switch (loginID) {
                case 0:
                    Intent intent = new Intent(LoginActivity.this, UserMainActivity.class);
                    intent.putExtra("userList", result);
                    intent.putExtra("keyUserID", guestID);
                    LoginActivity.this.startActivity(intent);
                    break;
                case 1:
                    Intent intent1 = new Intent(LoginActivity.this, TrainerMainActivity.class);
                    intent1.putExtra("userList", result);
                    intent1.putExtra("keyUserID", guestID);
                    LoginActivity.this.startActivity(intent1);
                    break;
                case 2:
                    Intent intent2 = new Intent(LoginActivity.this, CEOMainActivity.class);
                    intent2.putExtra("userList", result);
                    intent2.putExtra("keyUserID", guestID);
                    LoginActivity.this.startActivity(intent2);
                    break;
            }
        }
    }
}
