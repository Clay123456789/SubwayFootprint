package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.dominate_orientation.subwayfootprint.R;


public class MainActivity extends AppCompatActivity {
    private Button mButton;

    public boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton =findViewById(R.id.button1);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转碳积分1页面
                Intent intent = new Intent(MainActivity.this, Tanjifen_main.class);
                startActivity(intent);
            }
        });

        //底部导航栏
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView2);
        NavController navController= Navigation.findNavController(this,R.id.fragment);
        AppBarConfiguration configuration=new AppBarConfiguration.Builder(bottomNavigationView.getMenu()).build();
        NavigationUI.setupActionBarWithNavController(this,navController,configuration);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

    }
}
    //挖宝相关代码
//        ImageView wabao= findViewById(R.id.f1);
//        AnimationDrawable anim = (AnimationDrawable) wabao.getBackground();
//        wabao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(flag){
//                    anim.start();
//                    flag=false;
//                }else{
//                    anim.stop();
//                    flag=true;
//                }
//            }
//        });


    //挖宝相关代码
//    public void click1(View view) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setIcon(R.mipmap.ic_launcher)
//                .setTitle("获得宝箱！！！")
//                .setMessage("恭喜您在XXX站获得神秘宝箱，请选择对应操作")
//                .setPositiveButton("现在打开", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                .setNegativeButton("放入我的宝箱", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                .create()
//                .show();
//    }
//}