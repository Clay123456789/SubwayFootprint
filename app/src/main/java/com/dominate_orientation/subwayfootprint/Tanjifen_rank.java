package com.example.zhuyihaotest1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tanjifen_rank extends AppCompatActivity {
    private ListView mLv1=null;
    private TextView mTv1;
    private OkHttpClient okHttpClient;
    private  String s;
    private  LinkedList<User_Rank> mData;
    private  static  String TAG ="Tanjifen_rank";

    public void setS(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }
    private LinkedList<User_Rank> userRanks =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanjifen_rank);
        userRanks = new LinkedList<User_Rank>();
        okHttpClient=new OkHttpClient();
        String json= null;
        try {
            json = postSync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(json);  //接收到所有传过来的json数据
        String  data = jsonObject.getString("data");     //用String接收json数据中的data数据
        Type type=new TypeToken<LinkedList<User_Rank>>(){}.getType();
        Gson gson=new Gson();
        mData = gson.fromJson(data,type);
        mLv1 =  findViewById(R.id.lv_1);
        mLv1.setAdapter(new  RankListAdapter(mData,Tanjifen_rank.this));






    }

    public String postSync() throws InterruptedException {
        final String ss=null;
        Thread threadA= new Thread(){

            @Override

            public void run(){
                FormBody formBody=new FormBody.Builder().build();

                Request request=new Request.Builder().url("http://thelittlestar.cn/user/getRankingList").post(formBody).header("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIyMDE5MjExOTk2QGJ1cHQuY24iLCJleHAiOjE2NTE1MDQzMTYsImVtYWlsIjoiMjAxOTIxMTk5NkBidXB0LmNuIn0.dOXcgd3RGZy560keD_lqZ1EewLrpC8WNi4XUO5lbpwI").build();

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