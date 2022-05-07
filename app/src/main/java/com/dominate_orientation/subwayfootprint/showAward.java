package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicReference;

public class showAward extends AppCompatActivity {

    private TextView textView0;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    Award award;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_award);
        bindView();
        intent=getIntent();
        //awrad=intent.getSerializableExtra("AWARD");
        award=intent.getParcelableExtra("AWARD");
        Log.i("test",award.getAid());
        setText();
    }

    public void go_back_to_bury(View view)
    {
        finish();
    }

    public void bindView(){
        textView0=(TextView) findViewById(R.id.award_text0);
        textView1=(TextView) findViewById(R.id.award_text1);
        textView2=(TextView) findViewById(R.id.award_text2);
        textView3=(TextView) findViewById(R.id.award_text3);
        textView4=(TextView) findViewById(R.id.award_text4);
        textView5=(TextView) findViewById(R.id.award_text5);
        textView6=(TextView) findViewById(R.id.award_text6);
    }
    public void setText(){
        textView0.setText(award.getAid());
        textView1.setText(award.getVariety());
        textView2.setText(award.getName());
        textView3.setText(award.getContent());
        textView4.setText(String.valueOf(award.getCredit()));
        textView5.setText(award.getFromdate());
        textView6.setText(award.getTodate());
    }
}