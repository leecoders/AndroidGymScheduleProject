package com.example.hdh.smgproject;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FeedBackRequest extends StringRequest{

    final static private String URL = "http://kjg123kg.cafe24.com/TrainerFeedBack_SYG.php";
    private Map<String , String> parameters;

    //일반회원으로 접속했을때
    public FeedBackRequest(String ptID , String FeedBack, Response.Listener<String> listener){
        super(Method.POST , URL , listener , null); //해당 URL의 parameters들을 POST방식으로 해당 요청을 숨겨서 보여주어라
        parameters = new HashMap<>();
        parameters.put("ptID" , ptID);
        parameters.put("FeedBack" , FeedBack);
        parameters.put("Value" , String.valueOf(1));
}
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
