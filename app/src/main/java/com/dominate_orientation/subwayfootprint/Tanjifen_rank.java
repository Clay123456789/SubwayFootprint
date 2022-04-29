package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dominate_orientation.subwayfootprint.R;

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
    private TextView mTv2;
    private OkHttpClient okHttpClient;
    private  String s;
    private  LinkedList<User_Rank> mData;
    private  static  String TAG ="Tanjifen_rank";

    public TextView getmTv1() {
        return mTv1;
    }

    public void setmTv1(TextView mTv1) {
        this.mTv1 = mTv1;
    }
    public void setmTv2(TextView mTv2) {
        this.mTv2 = mTv1;
    }

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
        String json1= null;
        try {
            json1 = postSync("https://thelittlestar.cn:8088/user/getRankingList");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(json1);  //接收到所有传过来的json数据
        String  data = jsonObject.getString("data");     //用String接收json数据中的data数据
        Type type=new TypeToken<LinkedList<User_Rank>>(){}.getType();
        Gson gson=new Gson();
        mData = gson.fromJson(data,type);
        mLv1 =  findViewById(R.id.lv_1);
        mLv1.setAdapter(new  RankListAdapter(mData,Tanjifen_rank.this));



        String json2= null;
        try {
            json2 = postSync("https://thelittlestar.cn:8088/user/getUser");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject2 = JSONObject.parseObject(json2);  //接收到所有传过来的json数据
        String  data2 = jsonObject2.getString("data");     //用String接收json数据中的data数据
        Gson gson2=new Gson();
        User user= gson2.fromJson(data2, User.class);
        //System.out.println("fuck"+data2);

        mTv1=findViewById(R.id.textView4);
        mTv1.setText(user.getCredit());
        setmTv1(mTv1);


        String json3= null;
        try {
            json3 = postSync("https://thelittlestar.cn:8088/user/getPersonalCreditRank");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject3 = JSONObject.parseObject(json3);  //接收到所有传过来的json数据
        String  data3 = jsonObject3.getString("data");     //用String接收json数据中的data数据
        Gson gson3=new Gson();
     //   User user= gson2.fromJson(data2, User.class);
        //System.out.println("fuck"+data2);

        mTv2=findViewById(R.id.tv_2);

        mTv2.setText(data3);


    }

    public String postSync(String url) throws InterruptedException {
        final String ss=null;
        Thread threadA= new Thread(){

            @Override

            public void run(){
                FormBody formBody=new FormBody.Builder().build();

                Request request=new Request.Builder().url(url).post(formBody).header("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIyMDE5MjExOTk2QGJ1cHQuY24iLCJleHAiOjE2NTE3MTI5MjksImVtYWlsIjoiMjAxOTIxMTk5NkBidXB0LmNuIn0.AYskxhwgQei8aRYUve5EYpPfnWUdJBFmULmnKARy_ds").build();

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