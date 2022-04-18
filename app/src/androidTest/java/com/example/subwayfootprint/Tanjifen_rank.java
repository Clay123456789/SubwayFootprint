package com.example.zhuyihaotest1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class Tanjifen_rank extends AppCompatActivity {
    private ListView mLv1;
    private TextView mTv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanjifen_rank);

        mLv1 =findViewById(R.id.lv_1);
        mLv1.setAdapter(new MyListAdapter(Tanjifen_rank.this));

    }
}