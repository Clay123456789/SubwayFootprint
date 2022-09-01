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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
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

public class Shop_Main extends AppCompatActivity {
    private ListView mListView;
    private ImageView imageView;
    private EditText editText;
    private ImageView car;
    private   String s;
    private Intent intent;
    private  static  String TAG ="Shop_Main";
    private ImageView imageView1;
    private OkHttpClient okHttpClient;
    private LinkedList<Award> mData;

    public void setS(String s) {
        this.s = s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_main);

        editText=findViewById(R.id.select01);
        System.out.println(editText.getText().toString());


        mListView=(ListView)findViewById(R.id.lv);

        ViewFlipper flipper = findViewById(R.id.flipper);
        flipper.startFlipping();

        car= (ImageView) findViewById(R.id.car);

        car.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent( Shop_Main.this,BuyHistory.class);
                startActivity(intent);}
        });





        okHttpClient=new OkHttpClient();
        String json= null;
        try {
            json = postSync("https://thelittlestar.cn:8088/award/getAllAwards");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(json);  //接收到所有传过来的json数据
        String  data = jsonObject.getString("data");     //用String接收json数据中的data数据
        Type type=new TypeToken<LinkedList<Award>>(){}.getType();
        Gson gson=new Gson();

       LinkedList<Award> mData1=new LinkedList<>();
       mData = gson.fromJson(data,type);
        for(int i=0;i<mData.size();i++){
            if(mData.get(i).getStatus()!=0){
                 mData.remove(i);
            }
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int headerViewsCount = mListView.getHeaderViewsCount();



                Intent intent=new Intent( Shop_Main.this,Detail_commodity.class);


                Bundle bundle=new Bundle();
                bundle.putString("price",String.valueOf(mData.get(position).getCredit()));
                bundle.putString("content",mData.get(position).getContent());
                bundle.putString("aid",mData.get(position).getAid());

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        MyBaseAdaPter mAdapter = new MyBaseAdaPter(mData,Shop_Main.this);

        mListView.setAdapter(mAdapter);


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_credit);
        bottomNavigationView.setOnItemSelectedListener((menuItem)->{
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    intent =new Intent(Shop_Main.this, main_page.class);
                    startActivity(intent);
                    break;
                case R.id.nav_credit:
                    intent =new Intent(Shop_Main.this,Tanjifen_main.class);
                    startActivity(intent);
                    break;
                case R.id.nav_shop:
                    intent =new Intent(Shop_Main.this, Shop_Main.class);
                    startActivity(intent);
                    break;
                case R.id.nav_person:
                    intent =new Intent(Shop_Main.this, PersonalcenterActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });

    }
    class MyBaseAdaPter extends BaseAdapter {
        private LinkedList<Award> mData;
        private Context mContext;
        private LayoutInflater mlayoutInflater;
        public  MyBaseAdaPter(LinkedList<Award> mData, Context context) {
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
            ViewHolder holder =null;
            if(convertView == null){
                convertView = View.inflate( Shop_Main.this,R.layout.list_item,null);
                holder= new ViewHolder();
                holder.title=(TextView) convertView.findViewById(R.id.title);
                holder.price=(TextView) convertView.findViewById(R.id.price);
                holder.iv=(ImageView) convertView.findViewById(R.id.iv);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            System.out.println(mData.get(position).getName());
            holder.title.setText(mData.get(position).getName());
            holder.price.setText(String.valueOf(mData.get(position).getCredit()));

            return  convertView;
        }
    }
    static class ViewHolder{
        TextView title;
        TextView price;
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