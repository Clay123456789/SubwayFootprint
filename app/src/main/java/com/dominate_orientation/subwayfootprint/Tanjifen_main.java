package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dominate_orientation.subwayfootprint.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Tanjifen_main extends AppCompatActivity {
    private Button mButton1 ;
    private Button mButton2 ;
    private Button mButton3;
    private TextView textView;
    private Intent intent;

    private OkHttpClient okHttpClient;
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
        setContentView(R.layout.activity_tanjifen_mian);
        okHttpClient=new OkHttpClient();

       textView=findViewById(R.id.tv_3);

        String json= null;
        try {
            json = postSync("https://thelittlestar.cn:8088/user/getUser");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(json);  //接收到所有传过来的json数据
        String  data = jsonObject.getString("data");     //用String接收json数据中的data数据
        Gson gson=new Gson();
        User user= gson.fromJson(data, User.class);



        textView.setText(user.getCredit());


        mButton1=findViewById(R.id.Tbutton_1);
        mButton1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view1) {
        //碳积分历史页面
        Intent intent= new Intent(Tanjifen_main.this, Tanjifen_history.class);
        startActivity(intent);
    }
});

        mButton2=findViewById(R.id.Tbutton_2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                //碳积分排行页面
                Intent intent= new Intent(Tanjifen_main.this,Tanjifen_rank.class);
                startActivity(intent);
            }
        });
        mButton3=findViewById(R.id.Tbutton_3);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view3) {
                //使用说明页面
                Intent intent= new Intent(Tanjifen_main.this,Tanjifen4.class);
                startActivity(intent);
            }
        });








        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_credit);
        bottomNavigationView.setOnItemSelectedListener((menuItem)->{
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    intent =new Intent(Tanjifen_main.this, main_page.class);
                    startActivity(intent);
                    break;
                case R.id.nav_credit:
                    intent =new Intent(Tanjifen_main.this,Tanjifen_main.class);
                    startActivity(intent);
                    break;
                case R.id.nav_shop:
                    intent =new Intent(Tanjifen_main.this,Shangcheng.class);
                    startActivity(intent);
                    break;
                case R.id.nav_person:
                    intent =new Intent(Tanjifen_main.this, PersonalcenterActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });


    }

    public String postSync(String url) throws InterruptedException {

        Thread threadB= new Thread(){

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
        threadB.start();
        threadB.join();

        return  s;
    }
}