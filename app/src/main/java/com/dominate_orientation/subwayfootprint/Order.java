package com.dominate_orientation.subwayfootprint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class Order extends AppCompatActivity {
    private ListView mListView;
    private ImageView imageView;
    private EditText editText;
    private LinkedList<Award> award;
    private Button cancel;

    private ImageView car;
    private   String s;
    private Intent intent;
    private  static  String TAG ="Order";
    private ImageView imageView1;
    private OkHttpClient okHttpClient;
    private LinkedList<Buy_car> mData;
    public void setS(String s) {
        this.s = s;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

cancel=findViewById(R.id.cancel);


        mListView=(ListView)findViewById(R.id.lv_2);

        okHttpClient=new OkHttpClient();
        String json= null;
        try {
            json = postSync("https://thelittlestar.cn:8088/user/getOrderAwardRecords?group=1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(json);  //接收到所有传过来的json数据
        String  data = jsonObject.getString("data");     //用String接收json数据中的data数据
        Type type=new TypeToken<LinkedList<Buy_car>>(){}.getType();
        Gson gson=new Gson();
        mData = gson.fromJson(data,type);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int headerViewsCount = mListView.getHeaderViewsCount();

                String json= null;
                try {
                    json = postSync("https://thelittlestar.cn:8088/user/finishOrderAwardRecord?arid="+mData.get(position).getArid());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = JSONObject.parseObject(json);  //接收到所有传过来的json数据
                String  data = jsonObject.getString("data");     //用String接收json数据中的data数据
                Toast.makeText(Order.this,data+"返回商品界面",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Order.MyBaseAdaPter mAdapter = new Order.MyBaseAdaPter(mData,Order.this);

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });


        mListView.setAdapter(mAdapter);


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_credit);
        bottomNavigationView.setOnItemSelectedListener((menuItem)->{
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    intent =new Intent(Order.this, main_page.class);
                    startActivity(intent);
                    break;
                case R.id.nav_credit:
                    intent =new Intent(Order.this,Tanjifen_main.class);
                    startActivity(intent);
                    break;
                case R.id.nav_shop:
                    intent =new Intent(Order.this, Shop_Main.class);
                    startActivity(intent);
                    break;
                case R.id.nav_person:
                    intent =new Intent(Order.this, PersonalcenterActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });

    }
    class MyBaseAdaPter extends BaseAdapter {
        private LinkedList<Buy_car> mData;
        private Context mContext;
        private LayoutInflater mlayoutInflater;
        public  MyBaseAdaPter(LinkedList<Buy_car> mData, Context context) {
            this.mData = mData;
            this.mContext = context;
            mlayoutInflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount( ){
            return  mData.size();
        }
        @Override
        public Object getItem(int position){
            return mData.get(position);
        }
        @Override
        public long getItemId(int position){
            return  position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
          Order.ViewHolder holder =null;
            if(convertView == null){
                convertView = View.inflate( Order.this,R.layout.record_list_item,null);
                holder= new Order.ViewHolder();
                holder.title=(TextView) convertView.findViewById(R.id.name);
                holder.price=(TextView) convertView.findViewById(R.id.cost);
                holder.num=(TextView) convertView.findViewById(R.id.num);
                holder.date=(TextView) convertView.findViewById(R.id.date);
                // holder.iv=(ImageView) convertView.findViewById(R.id.iv);
                convertView.setTag(holder);
            }
            else {
                holder = (Order.ViewHolder) convertView.getTag();
            }





            holder.title.setText("优惠卷");
            holder.price.setText(String.valueOf(mData.get(position).getCredit()));
            holder.num.setText(String.valueOf(mData.get(position).getNum()));
            holder.date.setText(String.valueOf(mData.get(position).getTime()));

            return  convertView;
        }
    }
    static class ViewHolder{
        TextView title;
        TextView price;
        TextView num;
        TextView date;
        ImageView iv;
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
