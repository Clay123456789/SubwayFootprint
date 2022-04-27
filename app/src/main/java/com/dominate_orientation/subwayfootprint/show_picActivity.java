package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
public class show_picActivity extends AppCompatActivity {
    private ImageView ivShowPic;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);
        //状态栏透明
        ivShowPic=findViewById(R.id.iv_show_pic);
        Intent getImage=getIntent();
        Uri data=getImage.getData();
        ivShowPic.setImageURI(data);
        ivShowPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}