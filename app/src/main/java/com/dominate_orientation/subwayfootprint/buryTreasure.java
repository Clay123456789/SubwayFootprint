package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.LinkedList;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class buryTreasure extends AppCompatActivity {

    String token;
    Intent intent;
    String[] arr_variety=new String[]{"xxx","xxx","xxx"};
    String[] arr_name=new String[]{"xxx","xxx","xxx"};
    LinkedList<Award> awardLinkedList;
    ListView mLv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bury_treasure);
        //获取token
        //        Token app = (Token)getApplicationContext();
//        token=app.getToken();
        token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIyMDE5MjExOTk2QGJ1cHQuZWR1LmNuIiwiZXhwIjoxNjUyMjQ3NzU3fQ.GzUepl2fYoK2fIPunLD4BFaVhek36YPZboMJNiEhQGI";
        getSomeAwards(5);
        mLv1=findViewById(R.id.award_listview);
        mLv1.setAdapter(new AwardAdapter(awardLinkedList,buryTreasure.this));
        setBottomNavi();
    }


    public void getSomeAwards(int num){
        Thread t1 = new Thread(new Runnable() {
            String message = "失败";
            @Override
            public void run() {
                try {
//                    FormBody.Builder params=new FormBody.Builder();
//                    params.add("num","3");
                    String json="";
                    FormBody formBody = new FormBody.Builder()
                            .add("num", num+"")
                            .build();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://thelittlestar.cn:8088/award/getSomeAwards")
                            .addHeader("token",token)
                            .post(formBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    message = jsonObject.getString("message");
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    if (jsonArray.length()!=0) {
//                        for(int i=0;i<num;i++){
//                            Award tempAward=new Award();
//                            JSONObject pickAward=jsonArray.getJSONObject(i);
//                            String aid=pickAward.getString("aid");
//                            Log.i("test",aid);
//                            Log.i("test","kafhvauoifvbahjfubika");
//                            tempAward.setName(pickAward.getString("name"));
//                            tempAward.setVariety(pickAward.getString("variety"));
//                        }
                        String data=jsonObject.getString("data");
                        Type type=new TypeToken<LinkedList<Award>>(){}.getType();
                        Gson gson=new Gson();
                        awardLinkedList=gson.fromJson(data,type);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(buryTreasure.this, "奖池为空", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(buryTreasure.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setBottomNavi(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener((menuItem)->{
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    intent =new Intent(buryTreasure.this, main_page.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.nav_credit:
                    intent =new Intent(buryTreasure.this,Tanjifen_main.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.nav_shop:
                    intent =new Intent(buryTreasure.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.nav_person:
                    intent =new Intent(buryTreasure.this, PersonalcenterActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
            return true;
        });

    }
}