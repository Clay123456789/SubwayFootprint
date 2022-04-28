package com.example.zhuyihaotest1;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;




import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tanjifen4 extends AppCompatActivity {
private OkHttpClient okHttpClient;
private  static final String TAG ="Tanjifen4";
private  String s;
    public void setS(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanjifen4);
        okHttpClient=new OkHttpClient();

        String s2= null;
        try {
            s2 = postSync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("323"+s2);


        JSONObject json = JSONObject.parseObject(s2);  //接收到所有传过来的json数据
       String  data = json.getString("data");     //用String接收json数据中的data数据



        Gson gson=new Gson();

        Type type=new TypeToken<List<History>>(){}.getType();
        List<History> linkedList=gson.fromJson(data,type);
        System.out.println("!!!!"+linkedList.get(2).getWay());
    }
//    public  void getSync(View view){
//new Thread(){
//    @Override
//    public void run(){
//        Request request=new Request.Builder().url("http://123.56.150.89:8088/Subway/getAllSubways?code=131").build();
//        Call call=okHttpClient.newCall(request);
//        try {
//            Response response=call.execute();
//            Log.i(TAG,"getSync"+response.body().string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}.start();
//
//    }
    public String postSync() throws InterruptedException {
        final String ss=null;
       Thread threadA= new Thread(){

            @Override

            public void run(){
                FormBody formBody=new FormBody.Builder().build();

        Request request=new Request.Builder().url("http://123.56.150.89:8088/user/getUserCreditRecords").post(formBody).header("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIyMDE5MjExOTk2QGJ1cHQuY24iLCJleHAiOjE2NTE1MDQzMTYsImVtYWlsIjoiMjAxOTIxMTk5NkBidXB0LmNuIn0.dOXcgd3RGZy560keD_lqZ1EewLrpC8WNi4XUO5lbpwI").build();

        Call call=okHttpClient.newCall(request);
        try {
            Response response=call.execute();
             setS(response.body().string());

            Log.i(TAG,"postSync"+s);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        };
       threadA.start();
       threadA.join();
        System.out.println("！"+s);
   return  s;
    }


}