package com.dominate_orientation.subwayfootprint;


import android.content.Intent;
import android.os.Bundle;



import android.app.Activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BuyHistory extends Activity implements CartAdapter.RefreshPriceInterface ,View.OnClickListener{

    private ListView listView;
    private CheckBox cb_check_all;
    private TextView tv_total_price;
    private TextView tv_delete;
    private TextView tv_go_to_pay;
    private OkHttpClient okHttpClient;
    private CartAdapter adapter;
    private  TextView buy_now;

    private   String s;
    private LinkedList<Buy_car> mData;
    private Award award;
    public void setS(String s) {
        this.s = s;
    }
    private double totalPrice = 0.00;
    private int totalCount = 0;
    private List<HashMap<String,String>> goodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_history);



        initDate();
    }

    //控制价格展示
    private void priceControl(Map<String, Integer> pitchOnMap){
        totalCount = 0;
        totalPrice = 0.00;
        for(int i=0;i<goodsList.size();i++){
            if(pitchOnMap.get(goodsList.get(i).get("id"))==1){
                totalCount=totalCount+Integer.valueOf(goodsList.get(i).get("count"));
                double goodsPrice=Integer.valueOf(goodsList.get(i).get("count"))*Double.valueOf(goodsList.get(i).get("price"));
                totalPrice=totalPrice+goodsPrice;
            }
        }
        tv_total_price.setText(""+totalPrice);
        tv_go_to_pay.setText("选中("+totalCount+")");
    }

    @Override
    public void refreshPrice(Map<String, Integer> pitchOnMap) {
        priceControl(pitchOnMap);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.all_chekbox:
                AllTheSelected();
                break;
            case R.id.tv_go_to_pay:
                if(totalCount<=0){
                    Toast.makeText(this,"请选择要付款的商品",Toast.LENGTH_SHORT).show();
                    return;
                }
                buy_now = (TextView) findViewById(R.id.tv_go_to_pay);

                String json= null;
                try {
                    json = postSync("https://thelittlestar.cn:8088/user/createOrderAwardRecordByShopping",null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this,"购买成功",Toast.LENGTH_SHORT).show();

                break;



            case R.id.tv_delete:
                if(totalCount<=0){
                    Toast.makeText(this,"请选择要删除的商品",Toast.LENGTH_SHORT).show();
                    return;
                }
                checkDelete(adapter.getPitchOnMap());
                break;
        }
    }
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
//同步
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
    //删除操作
    private void checkDelete(Map<String,Integer> map){
        List<HashMap<String,String>> waitDeleteList=new ArrayList<>();
        Map<String,Integer> waitDeleteMap =new HashMap<>();
        for(int i=0;i<goodsList.size();i++){
            if(map.get(goodsList.get(i).get("id"))==1){
                waitDeleteList.add(goodsList.get(i));
                waitDeleteMap.put(goodsList.get(i).get("id"),map.get(goodsList.get(i).get("id")));

                okHttpClient=new OkHttpClient();
                String json= null;
                try {
                    json = postSync("https://thelittlestar.cn:8088/user/deleteShoppingAwardRecord?arid="+goodsList.get(i).get("arid"),null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        goodsList.removeAll(waitDeleteList);
        map.remove(waitDeleteMap);

        priceControl(map);
        adapter.notifyDataSetChanged();
    }

    //全选或反选
    private void AllTheSelected(){
        Map<String,Integer> map=adapter.getPitchOnMap();
        boolean isCheck=false;
        boolean isUnCheck=false;
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            if(Integer.valueOf(entry.getValue().toString())==1)isCheck=true;
            else isUnCheck=true;
        }
        if(isCheck==true&&isUnCheck==false){//已经全选,做反选
            for(int i=0;i<goodsList.size();i++){
                map.put(goodsList.get(i).get("id"),0);
            }
            cb_check_all.setChecked(false);
        }else if(isCheck==true && isUnCheck==true){//部分选择,做全选
            for(int i=0;i<goodsList.size();i++){
                map.put(goodsList.get(i).get("id"),1);
            }
            cb_check_all.setChecked(true);
        }else if(isCheck==false && isUnCheck==true){//一个没选,做全选
            for(int i=0;i<goodsList.size();i++){
                map.put(goodsList.get(i).get("id"),1);
            }
            cb_check_all.setChecked(true);
        }
        priceControl(map);
        adapter.setPitchOnMap(map);
        adapter.notifyDataSetChanged();
    }

    private void initView(){
        listView = (ListView) findViewById(R.id.listview);
        cb_check_all = (CheckBox) findViewById(R.id.all_chekbox);
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);
        tv_delete = (TextView) findViewById(R.id.tv_delete);
        tv_go_to_pay = (TextView) findViewById(R.id.tv_go_to_pay);
        tv_go_to_pay.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        cb_check_all.setOnClickListener(this);

        adapter=new CartAdapter(this,goodsList);
        adapter.setRefreshPriceInterface(this);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initDate(){
        goodsList=new ArrayList<>();
        okHttpClient=new OkHttpClient();
        String json= null;
        try {
            json = postSync("https://thelittlestar.cn:8088/user/getShoppingAwardRecords?group=1",null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(json);  //接收到所有传过来的json数据
        String  data = jsonObject.getString("data");     //用String接收json数据中的data数据
        Type type=new TypeToken<LinkedList<Buy_car>>(){}.getType();
        Gson gson=new Gson();
        mData = gson.fromJson(data,type);


        for(int i=0;i<mData.size();i++){

            String json1= null;
            try {
                json1 = postSync("https://thelittlestar.cn:8088/award/getAward?aid="+mData.get(i).getAid(),null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject1 = JSONObject.parseObject(json1);  //接收到所有传过来的json数据
            String  data1 = jsonObject1.getString("data");     //用String接收json数据中的data数据
            Type type1=new TypeToken<Award>(){}.getType();

            award = gson.fromJson(data1,type1);
            HashMap<String,String> map=new HashMap<>();
            map.put("id",(mData.get(i).getAid()+""));
            map.put("arid",(mData.get(i).getArid()+""));
            map.put("name",award.getName());
            map.put("price",(mData.get(i).getCredit())+"");
            map.put("count",(mData.get(i).getNum())+"");
            goodsList.add(map);
        }

        initView();
    }
    private String postSync(String url,String body) throws InterruptedException {

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

                    Log.i("BuyHistory","postSync"+s);

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
