package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class show_picActivity extends AppCompatActivity {
    private ImageView ivShowPic;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);
        ivShowPic=findViewById(R.id.iv_show_pic);
        Intent getImage=getIntent();
        Bundle bundle = getImage.getExtras();
        String Url = bundle.getString("Url");

        ivShowPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Bitmap bitmap = getBitmap(Url);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivShowPic.setImageBitmap(bitmap);
                        }
                    });
                }  catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public Bitmap getBitmap(String path) throws IOException {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}