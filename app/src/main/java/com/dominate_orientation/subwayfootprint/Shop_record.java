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

public class Shop_record extends AppCompatActivity {
    private ListView mListView;

    private TextView pageT;
    private Award award;
    private ImageView car;
    private   String s;
    private Intent intent;
    private  static  String TAG ="Shop_record";
    private ImageView imageView1;
    private OkHttpClient okHttpClient;
    private LinkedList<Buy_car> mData;
    private Button mButton1;
    private Button mButton2;
    public String page="1";
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    public void setS(String s) {
        this.s = s;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_record);
        pageT=findViewById(R.id.page_1);
        pageT.setText("第1页");



        mListView=(ListView)findViewById(R.id.lv_2);

        mButton1=findViewById(R.id.button_next);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

                okHttpClient=new OkHttpClient();
                String json= null;
                String p;
                p=String.valueOf(Integer.parseInt(getPage()) + 1);


                try {
                    json = postSync("https://thelittlestar.cn:8088/user/getAllOrderAwardRecords?group="+p);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = JSONObject.parseObject(json);  //接收到所有传过来的json数据
                String  data = jsonObject.getString("data");     //用String接收json数据中的data数据
                Type type=new TypeToken<LinkedList<Buy_car>>(){}.getType();
                Gson gson=new Gson();

                mData = gson.fromJson(data,type);

                for(int i=0;i<mData.size();i++){
                    if(Integer.parseInt(mData.get(i).getOperation())!=2){
                        mData.remove(i);
                    }
                }
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int headerViewsCount = mListView.getHeaderViewsCount();



                        Intent intent=new Intent( Shop_record.this,code.class);


                        Bundle bundle=new Bundle();
                        bundle.putString("price",String.valueOf(mData.get(position).getCredit()));
                        bundle.putString("content",mData.get(position).getTime());
                        bundle.putString("aid",mData.get(position).getAid());
                        bundle.putString("num",mData.get(position).getRemaining_count());
                        bundle.putString("arid",mData.get(position).getArid());
                        bundle.putString("date",mData.get(position).getTime());

                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                }); if(!data.equals("[]")){
                    pageT.setText("第"+p+"页");
                    setPage(p);
                Shop_record.MyBaseAdaPter mAdapter = new Shop_record.MyBaseAdaPter(mData,Shop_record.this);

                mListView.setAdapter(mAdapter);}
            }
        });


        mButton2=findViewById(R.id.button_last);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

                okHttpClient=new OkHttpClient();
                String json= null;
                String p;
                p = String.valueOf(Integer.parseInt(getPage()) - 1);
                if(!p.equals("0")){
                    setPage(p);
                }else{
                    p = String.valueOf(Integer.parseInt(getPage()));
                }
                try {
                    json = postSync("https://thelittlestar.cn:8088/user/getAllOrderAwardRecords?group="+p);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = JSONObject.parseObject(json);  //接收到所有传过来的json数据
                String  data = jsonObject.getString("data");     //用String接收json数据中的data数据
                Type type=new TypeToken<LinkedList<Buy_car>>(){}.getType();
                Gson gson=new Gson();
                mData = gson.fromJson(data,type);
                for(int i=0;i<mData.size();i++){
                    if(Integer.parseInt(mData.get(i).getOperation())!=2){
                        mData.remove(i);
                    }
                }
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int headerViewsCount = mListView.getHeaderViewsCount();



                        Intent intent=new Intent( Shop_record.this,code.class);


                        Bundle bundle=new Bundle();
                        bundle.putString("price",String.valueOf(mData.get(position).getCredit()));
                        bundle.putString("content",mData.get(position).getTime());
                        bundle.putString("aid",mData.get(position).getAid());
                        bundle.putString("num",mData.get(position).getNum());
                        bundle.putString("arid",mData.get(position).getArid());
                        bundle.putString("date",mData.get(position).getTime());

                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
                pageT.setText("第"+p+"页");
                Shop_record.MyBaseAdaPter mAdapter = new Shop_record.MyBaseAdaPter(mData,Shop_record.this);

                mListView.setAdapter(mAdapter);
            }
        });






        okHttpClient=new OkHttpClient();
        String json= null;
        try {
            json = postSync("https://thelittlestar.cn:8088/user/getAllOrderAwardRecords?group=1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(json);  //接收到所有传过来的json数据
        String  data = jsonObject.getString("data");     //用String接收json数据中的data数据
        Type type=new TypeToken<LinkedList<Buy_car>>(){}.getType();
        Gson gson=new Gson();
        mData = gson.fromJson(data,type);
        for(int i=0;i<mData.size();i++){
            if(Integer.parseInt(mData.get(i).getOperation())!=2){
                mData.remove(i);
            }
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int headerViewsCount = mListView.getHeaderViewsCount();



                Intent intent=new Intent( Shop_record.this,code.class);


                Bundle bundle=new Bundle();
                bundle.putString("price",String.valueOf(mData.get(position).getCredit()));
                bundle.putString("content",mData.get(position).getTime());
                bundle.putString("aid",mData.get(position).getAid());
                bundle.putString("num",mData.get(position).getNum());
                bundle.putString("arid",mData.get(position).getArid());
                bundle.putString("date",mData.get(position).getTime());

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        Shop_record.MyBaseAdaPter mAdapter = new Shop_record.MyBaseAdaPter(mData,Shop_record.this);

        mListView.setAdapter(mAdapter);


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_credit);
        bottomNavigationView.setOnItemSelectedListener((menuItem)->{
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    intent =new Intent(Shop_record.this, main_page.class);
                    startActivity(intent);
                    break;
                case R.id.nav_credit:
                    intent =new Intent(Shop_record.this,Tanjifen_main.class);
                    startActivity(intent);
                    break;
                case R.id.nav_shop:
                    intent =new Intent(Shop_record.this, Shop_Main.class);
                    startActivity(intent);
                    break;
                case R.id.nav_person:
                    intent =new Intent(Shop_record.this, PersonalcenterActivity.class);
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
            Shop_record.ViewHolder holder =null;
            if(convertView == null){
                convertView = View.inflate( Shop_record.this,R.layout.record_list_item,null);
                holder= new Shop_record.ViewHolder();
                holder.title=(TextView) convertView.findViewById(R.id.name);
                holder.price=(TextView) convertView.findViewById(R.id.cost);
                holder.num=(TextView) convertView.findViewById(R.id.num);
                holder.date=(TextView) convertView.findViewById(R.id.date);
               // holder.iv=(ImageView) convertView.findViewById(R.id.iv);
                convertView.setTag(holder);
            }
            else {
                holder = (Shop_record.ViewHolder) convertView.getTag();
            }

            String json1= null;
            try {
                json1 = postSync("https://thelittlestar.cn:8088/award/getAward?aid="+mData.get(position).getAid());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject1 = JSONObject.parseObject(json1);  //接收到所有传过来的json数据
            String  data1 = jsonObject1.getString("data");     //用String接收json数据中的data数据
            Type type1=new TypeToken<Award>(){}.getType();
            Gson gson=new Gson();
            award = gson.fromJson(data1,type1);
            holder.title.setText(award.getName());
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
