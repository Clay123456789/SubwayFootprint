package com.example.zhuyihaotest1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tanjifen1 extends AppCompatActivity {
private Button mButton1 ;
    private Button mButton2 ;
    private Button mButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanjifen1);
    mButton1=findViewById(R.id.Tbutton_1);
mButton1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view1) {
        //碳积分历史页面
        Intent intent= new Intent(Tanjifen1.this,Tanjifen2.class);
        startActivity(intent);
    }
});

        mButton2=findViewById(R.id.Tbutton_2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                //碳积分排行页面
                Intent intent= new Intent(Tanjifen1.this,Tanjifen_rank.class);
                startActivity(intent);
            }
        });
        mButton3=findViewById(R.id.Tbutton_3);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view3) {
                //使用说明页面
                Intent intent= new Intent(Tanjifen1.this,Tanjifen4.class);
                startActivity(intent);
            }
        });
    }

}