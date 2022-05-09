package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
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

public class Treasure_user extends AppCompatActivity {
    private ListView mLv1=null;
    private OkHttpClient okHttpClient;
    private Intent intent;
    private  String s;
    private  LinkedList<User_treasure> mData;

    public void setS(String s) {
        this.s = s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure_user);

        okHttpClient=new OkHttpClient();

        String json1= null;
        try {
            json1 = postSync("https://thelittlestar.cn:8088/treasure/getUserTreasure");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(json1);  //接收到所有传过来的json数据
        String  data = jsonObject.getString("data");     //用String接收json数据中的data数据
        Type type=new TypeToken<LinkedList<User_treasure>>(){}.getType();
        Gson gson=new Gson();

        mData = gson.fromJson(data,type);

        mLv1 =  findViewById(R.id.lv_2);
        mLv1.setAdapter(new TreasureUserAdapter(mData,Treasure_user.this));



        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_person);
        bottomNavigationView.setOnItemSelectedListener((menuItem)->{
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    intent =new Intent(Treasure_user.this, main_page.class);
                    startActivity(intent);
                    break;
                case R.id.nav_credit:
                    intent =new Intent(Treasure_user.this,Tanjifen_main.class);
                    startActivity(intent);
                    break;
                case R.id.nav_shop:
                    intent =new Intent(Treasure_user.this, PersonalcenterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_person:
                    intent =new Intent(Treasure_user.this, PersonalcenterActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_treasure_user);

        okHttpClient=new OkHttpClient();

        String json1= null;
        try {
            json1 = postSync("https://thelittlestar.cn:8088/treasure/getUserTreasure");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(json1);  //接收到所有传过来的json数据
        String  data = jsonObject.getString("data");     //用String接收json数据中的data数据
        Type type=new TypeToken<LinkedList<User_treasure>>(){}.getType();
        Gson gson=new Gson();

        mData = gson.fromJson(data,type);

        mLv1 =  findViewById(R.id.lv_2);
        mLv1.setAdapter(new TreasureUserAdapter(mData,Treasure_user.this));



        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_person);
        bottomNavigationView.setOnItemSelectedListener((menuItem)->{
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    intent =new Intent(Treasure_user.this, main_page.class);
                    startActivity(intent);
                    break;
                case R.id.nav_credit:
                    intent =new Intent(Treasure_user.this,Tanjifen_main.class);
                    startActivity(intent);
                    break;
                case R.id.nav_shop:
                    intent =new Intent(Treasure_user.this, PersonalcenterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_person:
                    intent =new Intent(Treasure_user.this, PersonalcenterActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });
    }
    public String postSync(String url) throws InterruptedException {

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



                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
        threadA.start();
        threadA.join();


        return  s;
    }

}