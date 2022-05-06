package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AnimOpenTreasure extends AppCompatActivity {
    ImageView treasure_anim_open;
    AnimationDrawable anim;
    Intent intent;
    int duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_open_treasure);
        treasure_anim_open = (ImageView) findViewById(R.id.treasure_open_show);
        anim=(AnimationDrawable) treasure_anim_open.getBackground();
        duration = 0;
        for(int i=0;i<anim.getNumberOfFrames();i++){
            duration += anim.getDuration(i);
        }
        anim.start();
        Log.i("tiem",duration+"");
        goBack();
    }

    public void goBack(){
        new Thread(){
            @Override
            public void run() {
                try{
                    sleep(duration);
                }catch (Exception e){
                    e.printStackTrace();
                }
//                Intent intent = new Intent();
//                intent.setClass(AnimOpenTreasure.this, route.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳到的界面
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activity
//                startActivity(intent);
                finish();
            }
        }.start();
    }

}