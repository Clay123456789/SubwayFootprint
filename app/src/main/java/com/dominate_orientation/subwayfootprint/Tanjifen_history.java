package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dominate_orientation.subwayfootprint.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class Tanjifen_history extends AppCompatActivity {
    private LinkedList<History> mData = null;
    private Context mContext;
    private Intent intent;
    private ListView list_history;
    private Button mButton1;
    private Button mButton2;
    private TextView pageT;
    private OkHttpClient okHttpClient;
    private   String s;
    public String page="1";

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    private  static  String TAG ="Tanjifen_history";

    public void setS(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanjifen_history);
        mContext = Tanjifen_history.this;
        pageT=findViewById(R.id.page_1);
        pageT.setText("第1页");
        mButton1=findViewById(R.id.button_next);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                //碳积分历史页面
                okHttpClient=new OkHttpClient();
                String json= null;
                String p;
                p=String.valueOf(Integer.parseInt(getPage()) + 1);
                setPage(p);
                System.out.println("next!!!!!!"+getPage());
                try {
                    json = postSync("https://thelittlestar.cn:8088/user/getUserCreditRecords?group="+p);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = JSONObject.parseObject(json);  //接收到所有传过来的json数据
                String  data = jsonObject.getString("data");     //用String接收json数据中的data数据
                Type type=new TypeToken<LinkedList<History>>(){}.getType();
                Gson gson=new Gson();
                mData = gson.fromJson(data,type);
                System.out.println("?????????"+data);
                if(!data.equals("[]")){
                    pageT.setText("第"+p+"页");

                list_history =  findViewById(R.id.lv_2);
                list_history.setAdapter(new  HistoryAdapter(mData,Tanjifen_history.this));}
            }
        });


        mButton2=findViewById(R.id.button_last);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                //碳积分历史页面
                okHttpClient=new OkHttpClient();
                String json= null;
                String p;
                p = String.valueOf(Integer.parseInt(getPage()) - 1);
                if(!p.equals("0")){
                setPage(p);
                }else{
                    p = String.valueOf(Integer.parseInt(getPage()));
                }
                try {
                    json = postSync("https://thelittlestar.cn:8088/user/getUserCreditRecords?group="+p);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = JSONObject.parseObject(json);  //接收到所有传过来的json数据
                String  data = jsonObject.getString("data");     //用String接收json数据中的data数据
                Type type=new TypeToken<LinkedList<History>>(){}.getType();
                Gson gson=new Gson();
                mData = gson.fromJson(data,type);
                pageT.setText("第"+p+"页");
                list_history =  findViewById(R.id.lv_2);
                list_history.setAdapter(new  HistoryAdapter(mData,Tanjifen_history.this));
            }
        });




        okHttpClient=new OkHttpClient();
        String json= null;
        try {
            json = postSync("https://thelittlestar.cn:8088/user/getUserCreditRecords?group=1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(json);  //接收到所有传过来的json数据
        String  data = jsonObject.getString("data");     //用String接收json数据中的data数据
        Type type=new TypeToken<LinkedList<History>>(){}.getType();
       Gson gson=new Gson();
        mData = gson.fromJson(data,type);
              list_history =  findViewById(R.id.lv_2);
       list_history.setAdapter(new  HistoryAdapter(mData,Tanjifen_history.this));

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_credit);
        bottomNavigationView.setOnItemSelectedListener((menuItem)->{
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    intent =new Intent(Tanjifen_history.this, main_page.class);
                    startActivity(intent);
                    break;
                case R.id.nav_credit:
                    intent =new Intent(Tanjifen_history.this,Tanjifen_main.class);
                    startActivity(intent);
                    break;
                case R.id.nav_shop:
                    intent =new Intent(Tanjifen_history.this, PersonalcenterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_person:
                    intent =new Intent(Tanjifen_history.this, PersonalcenterActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });


    }





    public String postSync(String url) throws InterruptedException {
        final String ss=null;
        Thread threadA= new Thread(){

            @Override

            public void run(){
                FormBody formBody=new FormBody.Builder().build();
                Token app = (Token)getApplicationContext();
                String token = null;
                token =app.getToken();
                Request request=new Request.Builder().url(url).post(formBody).header("token", token).build();

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
