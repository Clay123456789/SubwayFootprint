package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class route extends AppCompatActivity {
    String msg="";
    Info[] infos = null;
    Intent it = null;

    TextView ls =null;
    TextView ps = null;
    TextView ns = null;

    Boolean no_next_passed_station = false;
    Boolean last_station_used = false;
    Boolean no_station_left=false;
    Integer infos_index = 0;
    Integer passed_station_index = 0;

    Button go_to_next=null;
    Button dig = null;
    Button ensconce = null;
    Button closeBury=null;

    View view_bury;

    Dialog dialog;

    AlertDialog buryAlert = null;
    AlertDialog.Builder builder = null;

    Context mContext;

    Intent intent;

    ImageView img_show;
    AnimationDrawable anim;

    String token;

    ArrayList<Treasure> treasureList;

    static  String TAG ="route";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        it = getIntent();
        msg = it.getStringExtra("msg");
        ls = (TextView) findViewById(R.id.last_station);
        ps = (TextView) findViewById(R.id.pre_station);
        ns = (TextView) findViewById(R.id.next_station);
        go_to_next=(Button)findViewById(R.id.go_to_next_station);
        dig =(Button)findViewById(R.id.dig);
        ensconce =(Button)findViewById(R.id.ensconce);
        ensconce.setEnabled(false);

        Token app = (Token)getApplicationContext();
        token=app.getToken();

        /*new Thread()
        {
            @Override
            public void run() {
                while(!no_station_left);
                go_to_next.setEnabled(false);
            }
        }.start();*/

        mContext=route.this;
        builder=new AlertDialog.Builder(mContext);

        final LayoutInflater inflater = route.this.getLayoutInflater();
        view_bury = inflater.inflate(R.layout.bottom_sheet_bury, null,false);
        builder.setView(view_bury);
        builder.setCancelable(false);
        buryAlert = builder.create();

        view_bury.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                buryAlert.dismiss();
            }
        });

        view_bury.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "祝您一路顺风~", Toast.LENGTH_SHORT).show();
                buryAlert.dismiss();
                intent =new Intent(route.this, main_page.class);
                startActivity(intent);
            }
        });

        view_bury.findViewById(R.id.btn_bury).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                buryAlert.dismiss();
            }
        });


        //anim = (AnimationDrawable) img_show.getBackground();

        Calculate();
        init_texts();
    }

    public void ls(String s){ ls.setText(s);}
    public void ps(String s){ ps.setText(s);}
    public void ns(String s){ ns.setText(s);}
    public void ls(CharSequence s){ ls.setText(s);}
    public void ps(CharSequence s){ ps.setText(s);}
    public void ns(CharSequence s){ ns.setText(s);}

    public void init_texts()
    {
        ps(infos[infos_index].start);
        if(infos[infos_index].passed.length>0)
        {
            ns(infos[infos_index].passed[passed_station_index]);
            passed_station_index++;
        }else
        {
            ns(infos[infos_index].end);
            last_station_used = true;
        }
    }

    public void go_to_next_station(View view)
    {
        ls(ps.getText());
        ps(ns.getText());

        if(no_next_passed_station)
        {
            if(ns.getText().toString().indexOf("广")!=-1)
            {
                System.out.println("lmao");
            }
            if(last_station_used)
            {//infos_index add 1, clear two false status
                if(infos_index<infos.length-1){
                    ns(infos[++infos_index].start);
                    last_station_used = false;
                    no_next_passed_station = false;
                }
                else
                {
                    go_to_next.setEnabled(false);
                    ensconce.setEnabled(true);
                    ps(ns.getText());
                    ns("已到达！可以藏宝啦");
                }
            }else
            {
                ns(infos[infos_index].end);
                last_station_used = true;
            }
        }else{
            if(passed_station_index>=infos[infos_index].passed.length)
            {
                ns(infos[infos_index].end);
                last_station_used = true;
                no_next_passed_station = true;
            }else{
                ns(infos[infos_index].passed[passed_station_index++]);
            }
        }
    }

    public void Calculate()
    {
        String[] set = msg.split(".line.");
        infos=new Info[set.length-1];
        int index=0;
        for(int i=1;i<set.length;i++)
        {
            infos[index] = new Info();
            if(set[i].length()>0) {
                String[] tmp1 = set[i].split(".sn.");
                infos[index].line = tmp1[0];
                if(tmp1[1].indexOf(".pn.")!=-1) {
                    String[] tmp2 = tmp1[1].split(".pn.");
                    infos[index].start = tmp2[0];
                    String[] tmp3 = tmp2[1].split(".en.");
                    infos[index].passed = tmp3[0].split(" ");// 可能是空
                    infos[index].end = tmp3[1];
                }else
                {
                    String[] tmp2 = tmp1[1].split(".en.");
                    infos[index].start = tmp2[0];// 可能是空
                    infos[index].end = tmp2[1];
                }
            }
            index++;
        }
    }

    public void go_back(View view)
    {
        finish();
    }

    //挖宝
    //获取当前站点的一个宝箱
    public void dig(View view){
        Thread t1 = new Thread(new Runnable() {
            String message = "失败";
            @Override
            public void run() {
                try {
                    //String json = "";
                    String json = "{\n" +
                            "\"pid\": "+"\""+"第二站"+"\""+
                            "}";
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://thelittlestar.cn:8088/treasure/getPositionTreasure")
 //                           .addHeader("token",token)
                            .post(RequestBody.create(MediaType.parse("application/json"), json))
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    message = jsonObject.getString("message");
                    Log.i("message",jsonObject.getString("message"));
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    if (jsonArray.length()!=0) {
                        int ranPick=(int) (Math.random()*jsonArray.length());
                        JSONObject pickTreasure=jsonArray.getJSONObject(ranPick);
                        String tid=pickTreasure.getString("tid");
                        Log.i("test",tid);
                        //播放动画
                        intent =new Intent(route.this, AnimOpenTreasure.class);
                        startActivity(intent);
                        //checkTreasure(tid);
                        //getTreasure(tid);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(route.this, "很遗憾并未获得任何宝藏，下一站再试试吧~", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(route.this, "网络连接失败", Toast.LENGTH_SHORT).show();
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

    //挖宝 查看获得宝箱
    //传入tid
    public void checkTreasure(String tid){
        Treasure treasure;
        Thread t1 = new Thread(new Runnable() {
            String message = "失败";
            @Override
            public void run() {
                try {
                    //String json = "";
                    String json = "{\n" +
                            "\"tid\": "+"\""+tid+"\""+
                            "}";
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://thelittlestar.cn:8088/treasure/getTreasure")
                            //                           .addHeader("token",token)
                            .post(RequestBody.create(MediaType.parse("application/json"), json))
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    message = jsonObject.getString("message");
                    Log.i("message",jsonObject.getString("message"));
                    JSONObject tre=new JSONObject(jsonObject.getString("data"));
//                    Log.i("check",tre.getString("content"));
//                    Log.i("check",tre.getString("tid"));
//                    Log.i("check",tre.getString("fromdate"));
//                    Log.i("check",tre.getString("todate"));
//                    treasure.setTid(tre.getString("tid"));
//                    treasure.setTid(tre.getString(""));
//                    treasure.setTid(tre.getString(""));
//                    treasure.setTid(tre.getString(""));
//                    treasure.setTid(tre.getString(""));
//                    treasure.setTid(tre.getString(""));
//                    treasure.setTid(tre.getString(""));

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(route.this, "网络连接失败", Toast.LENGTH_SHORT).show();
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


    //挖宝
    //传入tid
    public void getTreasure(String tid){
        Thread t1 = new Thread(new Runnable() {
            String message = "失败";
            @Override
            public void run() {
                try {
                    //String json = "";
                    String json = "{\n" +
                            "\"tid\": "+"\""+tid+"\""+
                            "}";
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://thelittlestar.cn:8088/treasure/getPositionTreasure")
                            //                           .addHeader("token",token)
                            .post(RequestBody.create(MediaType.parse("application/json"), json))
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    message = jsonObject.getString("message");
                    Log.i("message",jsonObject.getString("message"));
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    if (message.equals("成功")) {
                        int ranPick=(int) (Math.random()*jsonArray.length());
                        JSONObject pickTreasure=jsonArray.getJSONObject(ranPick);
                        String s=pickTreasure.getString("tid");
                        Log.i("test",s);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(route.this, "很遗憾并未获得任何宝藏，下一站再试试吧~", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(route.this, "网络连接失败", Toast.LENGTH_SHORT).show();
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


    //藏宝
    //我已设定只有到终点此按钮才能互动
    public void ensconce(View view){
        buryAlert.show();
    }
}



class Info{
    String line="";
    String start="";
    String end="";
    String[] passed={};
}
