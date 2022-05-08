package com.dominate_orientation.subwayfootprint;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
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
    int selectPosition=-1;
    AwardAdapter mAdapter;
    Award selectAward;
    Button goBuryBtn;
    String thisPosition;
    String thisCity;
    String inputCredit;
    String inputMessage;
    AlertDialog checkAlert=null;
    AlertDialog.Builder builder = null;
    View checkView;
    Context mContext;
    EditText editText1;
    EditText editText2;
    boolean buryFlag=true;
    String key;
    String value;
    route2bury passData;
    LinkedHashMap<String,String> city_to_pid = null;
    boolean textCheckFlag1 =false;
    boolean textCheckFlag2 =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bury_treasure);
        //数据接收
        intent=getIntent();
        passData=(route2bury) intent.getSerializableExtra("MAP");
        city_to_pid=passData.getParaMap();
        thisCity=passData.getPresentCity();
        thisPosition=passData.getPresentPosition();
        Log.i("test",thisPosition);
        Log.i("test",thisCity);
        Log.i("test",city_to_pid.keySet().toString());
        Log.i("test",city_to_pid.values().toString());
        //组合成标准格式
        thisPosition=city_to_pid.get(thisCity)+thisPosition;
        Log.i("test",thisPosition);
//        thisPosition="131_西土城";
        Token app = (Token)getApplicationContext();
        token=app.getToken();
//        token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIyMDE5MjExOTk2QGJ1cHQuZWR1LmNuIiwiZXhwIjoxNjUyMjQ3NzU3fQ.GzUepl2fYoK2fIPunLD4BFaVhek36YPZboMJNiEhQGI";
        getSomeAwards(5);
        mLv1=findViewById(R.id.award_listview);
        mAdapter=new AwardAdapter(awardLinkedList,buryTreasure.this);
        mLv1.setAdapter(mAdapter);
        mLv1.setOnItemClickListener((parent, view, position, id) -> {
            Log.i("test","choiceButtonTouched");
            selectPosition=position;
            mAdapter.notifyDataSetChanged();
            selectAward=awardLinkedList.get(position);
            Log.i("test",String.valueOf(position));
        });
        setBottomNavi();
        mContext=buryTreasure.this;
        builder=new AlertDialog.Builder(mContext);

        final LayoutInflater inflater = buryTreasure.this.getLayoutInflater();
        checkView = inflater.inflate(R.layout.bury_input_dialog, null,false);
        builder.setView(checkView);
        builder.setCancelable(false);
        checkAlert=builder.create();
        editText1=checkView.findViewById(R.id.input_credit);
        editText2=checkView.findViewById(R.id.input_message);
        addListener();
        setViews();
    }


    public void setViews(){
        checkView.findViewById(R.id.btn_cancle3).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAlert.dismiss();
            }
        });

        checkView.findViewById(R.id.btn_end_tour).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAlert.dismiss();
            }
        });

        checkView.findViewById(R.id.btn_bury_check).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                intent =new Intent(buryTreasure.this, main_page.class);

                inputCredit=editText1.getText().toString();
                inputMessage=editText2.getText().toString();
                Log.i("test",inputCredit);
                Log.i("test",inputMessage);
                buryFlag=true;
//                if(!checkInput(inputCredit,inputMessage)) {
//                    return;
//                }
                if(!(textCheckFlag1&&textCheckFlag2)){
                    Toast.makeText(buryTreasure.this, "请检查输入是否合法", Toast.LENGTH_SHORT).show();
                    return;
                }
                checkBury();
                checkAlert.dismiss();
//                Toast.makeText(getApplicationContext(), "祝您一路顺风~", Toast.LENGTH_SHORT).show();
//                startActivity(intent);
            }
        });
    }

    public void addListener(){
        editText1.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0) return;
//                editText1.removeTextChangedListener(this);
                Log.i("test","onTextChangedCalled");
                Award chooseAward=awardLinkedList.get(selectPosition);
                double num_input,num_standard;
                num_input=Integer.parseInt(s.toString());
                num_standard=chooseAward.getCredit();
                if(num_input<0.6*num_standard||num_input>1.2*num_standard){
                    editText1.setError("开启宝箱所需碳积分应为兑换奖品碳积分的60%-120%");
                    textCheckFlag1=false;
                    return;
                }
                textCheckFlag1=true;
//                editText1.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("test","onTextChangedCalled");
//          do nothing
            }
        });

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length()==0){
                    editText2.setError("留言不能为空");
                    textCheckFlag2=false;
                    return;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                editText1.removeTextChangedListener(this);
                Log.i("test","onTextChangedCalled");

                if(s.length()==0){
                    editText2.setError("留言不能为空");
                    textCheckFlag2=false;
                    return;
                }
                else if(s.length()>30){
                    editText2.setError("留言不能多于30个字符");
                    textCheckFlag2=false;
                    return;
                }
                textCheckFlag2=true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });

    }


    public boolean checkInput(String a,String b){
        Award chooseAward=awardLinkedList.get(selectPosition);
        double num_input,num_standard;
        num_input=Integer.parseInt(a);
        num_standard=chooseAward.getCredit();
        if(num_input<0.6*num_standard||num_input>1.2*num_standard){
            editText1.setError("开启宝箱所需碳积分应为兑换奖品碳积分的60%-120%");
            return false;
        }
        if(b.length()==0){
            editText2.setError("留言不能为空");
            return false;
        }
        else if(b.length()>30){
            editText2.setError("留言长度应小于30个字符");
            return false;
        }
        return true;
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

    public void startBury(View view){
        if(selectPosition==-1){
            Toast.makeText(buryTreasure.this, "请先选择埋藏对象~", Toast.LENGTH_SHORT).show();
            return;
        }
        checkAlert.show();
    }

    public void checkBury(){
        buryFlag=false;
        Thread t1 = new Thread(new Runnable() {
            String message = "失败";
            Award chooseAward=awardLinkedList.get(selectPosition);
            @Override
            public void run() {
                try {
//                    FormBody.Builder params=new FormBody.Builder();
//                    params.add("num","3");
                    String json="";
                    Log.i("test","wuhu"+chooseAward.getAid());
                    FormBody formBody = new FormBody.Builder()
                            .add("aid",chooseAward.getAid())
                            .add("num", String.valueOf(1))
                            .add("credit",inputCredit)
                            .add("pid",thisPosition)
                            .add("message",inputMessage)
                            .build();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://thelittlestar.cn:8088/treasure/buryTreasure")
                            .addHeader("token",token)
                            .post(formBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    message = jsonObject.getString("message");
                    Log.i("test","wuhu"+message);
                    if (message.equals("成功")) {
                        buryFlag=true;
                        //Toast.makeText(buryTreasure.this, "藏宝成功，期待您的下一次出行~", Toast.LENGTH_SHORT).show();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(buryTreasure.this, "藏宝失败", Toast.LENGTH_SHORT).show();
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
            if(buryFlag){
                Toast.makeText(getApplicationContext(), "祝您一路顺风~", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
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
    public class AwardAdapter extends BaseAdapter {
        LinkedList<Award> awardLinkedList;
        Context mContext;
        LayoutInflater mlayoutInflater;



        public AwardAdapter(LinkedList<Award> awardLinkedList, buryTreasure mContext) {
            this.awardLinkedList = awardLinkedList;
            this.mContext = mContext;
            mlayoutInflater=LayoutInflater.from(mContext);
        }

        public class ViewHolder{
            public TextView award_variety,award_name;
            public ImageView award_content_img;
            public Button award_button;
            public RadioButton award_choice_button;
        }


        @Override
        public int getCount() {
            return awardLinkedList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AwardAdapter.ViewHolder holder=null;
            if(convertView==null){
                convertView=mlayoutInflater.inflate(R.layout.item_list_award,null);
                holder=new AwardAdapter.ViewHolder();
                holder.award_variety = convertView.findViewById(R.id.award_variety);
                holder.award_name =  convertView.findViewById(R.id.award_name);
                holder.award_content_img =  convertView.findViewById(R.id.award_img);
                holder.award_button=convertView.findViewById(R.id.check_award);
                holder.award_choice_button=convertView.findViewById(R.id.award_radio_button);
                convertView.setTag(holder);
            }else{
                holder=(AwardAdapter.ViewHolder) convertView.getTag();
            }
            String s1=String.valueOf(awardLinkedList.get(position).getVariety());
            holder.award_variety.setText(s1);
            String s2=String.valueOf(awardLinkedList.get(position).getName());
            holder.award_name.setText(s2);
            String s3=String.valueOf(awardLinkedList.get(position).getVariety());
            if(s3.equals("优惠券")){

                holder.award_content_img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.coupon));
                //   imageview.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_2));
            }else if(s3.equals("系统称号")){
                holder.award_content_img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.title));

            }else if(s3.equals("碳积分")){
                holder.award_content_img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.co2));

            }else if(s3.equals("实体物品")){
                holder.award_content_img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.things));
            }

            holder.award_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view1) {
                    Intent intent = new Intent(mContext, showAward.class);
                    //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//不是在Activity中进行跳转，需要添加这个方法
                    Award award=awardLinkedList.get(position);
                    Log.i("test",award.getAid());
                    intent.putExtra("AWARD", award);
                    mContext.startActivity(intent);
                }
            });
            if(selectPosition == position){
                holder.award_choice_button.setChecked(true);
            }
            else{
                holder.award_choice_button.setChecked(false);
            }

            return convertView;
        }
    }

}