package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.atomic.AtomicReference;

public class ShowAllMessage extends AppCompatActivity {
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private TextView textView8;
    private TextView textView9;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_message);
        User_treasure cc=new User_treasure();
        AtomicReference<Intent> intent = new AtomicReference<>(getIntent());
         cc =  (User_treasure) intent.get().getSerializableExtra("PERSON_INFO");

        textView1=findViewById(R.id.text0);
        textView1.setText(cc.getTid());
        textView2=findViewById(R.id.text02);
        textView2.setText(cc.getVariety());
        textView3=findViewById(R.id.text03);
        textView3.setText(cc.getCredit());
        textView4=findViewById(R.id.text04);
        textView4.setText(cc.getFromdate());
        textView5=findViewById(R.id.text05);
        textView5.setText(cc.getGetdate());
        textView6=findViewById(R.id.text06);
        textView6.setText(cc.getContent());
        textView7=findViewById(R.id.text07);
        textView7.setText(cc.getMid());
        textView8=findViewById(R.id.text08);
        textView8.setText(cc.getMessage());
        textView9=findViewById(R.id.text09);
        if(cc.getStatus().equals("1")){
            textView9.setText("已打开宝箱");
        }else{
            textView9.setText("未打开宝箱");
        }





        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_credit);
        bottomNavigationView.setOnItemSelectedListener((menuItem)->{
           Intent intent1=new Intent();
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    intent1 =new Intent(ShowAllMessage.this, main_page.class);
                    startActivity(intent1);
                    break;
                case R.id.nav_credit:
                    intent1 =new Intent(ShowAllMessage.this,Tanjifen_main.class);
                    startActivity(intent1);
                    break;
                case R.id.nav_shop:
                    intent1 =new Intent(ShowAllMessage.this, PersonalcenterActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.nav_person:
                    intent1 =new Intent(ShowAllMessage.this, PersonalcenterActivity.class);
                    startActivity(intent1);
                    break;
            }
            return true;
        });



    }
}