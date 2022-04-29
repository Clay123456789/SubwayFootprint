package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.dominate_orientation.subwayfootprint.R;
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
    private OkHttpClient okHttpClient;
    private  String s;
    public void setS(String s) {
        this.s = s;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanjifen_mian);

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




    }

}