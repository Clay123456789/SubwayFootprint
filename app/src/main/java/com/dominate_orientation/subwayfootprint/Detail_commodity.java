package com.dominate_orientation.subwayfootprint;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Detail_commodity extends AppCompatActivity {
    ImageView iv;
    TextView content;
    TextView price;
    Button add_car;
    private Intent intent;
    Button buy;
    private User mData;
    private   String s;
    private OkHttpClient okHttpClient;
    public void setS(String s) {
        this.s = s;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_commodity);
        content=findViewById(R.id.xiangqing);
        price=findViewById(R.id.tv_price);
        iv=findViewById(R.id.iv_d_chanpin);
        add_car=findViewById(R.id.buy_car);
        buy=findViewById(R.id.purchase);
        Glide.with(this).load(R.drawable.poster_1).into(iv);

        AtomicReference<Intent> intent= new AtomicReference<>(this.getIntent());


        String aid= intent.get().getStringExtra("aid");
        String tanjifen= intent.get().getStringExtra("price");
        String xiangqing= intent.get().getStringExtra("content");

        System.out.println("数目为："+aid);
        price.setText("价格："+tanjifen+"碳积分");
        content.setText(xiangqing);
        add_car.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                okHttpClient=new OkHttpClient();
                String json= null;
                try {
                    json = postSync("https://thelittlestar.cn:8088/user/insertShoppingAwardRecord?aid="+aid+"&num=1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = JSONObject.parseObject(json);  //接收到所有传过来的json数据
                String  data = jsonObject.getString("data");     //用String接收json数据中的data数据

                if (data != null) {

                    Toast.makeText(Detail_commodity.this, data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        buy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                okHttpClient=new OkHttpClient();
                String json= null;
                String json1= null;
                try {
                        json = postSync("https://thelittlestar.cn:8088/user/getUser");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = JSONObject.parseObject(json);  //接收到所有传过来的json数据
                String  data = jsonObject.getString("data");     //用String接收json数据中的data数据
                Type type=new TypeToken<User>(){}.getType();
                Gson gson=new Gson();
                mData = gson.fromJson(data,type);

      if(Integer.parseInt(mData.getCredit())>Integer.parseInt(tanjifen)){
                try {
                    json1 = postSync("https://thelittlestar.cn:8088/user/createOrderAwardRecord?aid="+aid+"&num=1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject1 = JSONObject.parseObject(json1);  //接收到所有传过来的json数据
                String  data1 = jsonObject1.getString("data");     //用String接收json数据中的data数据
                System.out.println(data1);

          System.out.println("错误为："+aid);
                Toast.makeText(Detail_commodity.this,"创建订单成功",Toast.LENGTH_SHORT).show();

    Intent intent=new Intent( Detail_commodity.this,Order.class);
    startActivity(intent);

}
else{

    Toast.makeText(Detail_commodity.this,"对不起，您的碳积分不足",Toast.LENGTH_SHORT).show();}
}

        });

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_credit);
        bottomNavigationView.setOnItemSelectedListener((menuItem)->{
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    intent.set(new Intent(Detail_commodity.this, main_page.class));
                    startActivity(intent.get());
                    break;
                case R.id.nav_credit:
                    intent.set(new Intent(Detail_commodity.this, Tanjifen_main.class));
                    startActivity(intent.get());
                    break;
                case R.id.nav_shop:
                    intent.set(new Intent(Detail_commodity.this, Shop_Main.class));
                    startActivity(intent.get());
                    break;
                case R.id.nav_person:
                    intent.set(new Intent(Detail_commodity.this, PersonalcenterActivity.class));
                    startActivity(intent.get());
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
                System.out.println(token);
                Call call=okHttpClient.newCall(request);
                try {
                    Response response=call.execute();
                    setS(response.body().string());

                    Log.i("Detail_commodity","postSync"+s);

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