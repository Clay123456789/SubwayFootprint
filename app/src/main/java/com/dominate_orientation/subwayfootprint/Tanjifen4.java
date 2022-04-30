package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.dominate_orientation.subwayfootprint.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;




import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
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

    }
}