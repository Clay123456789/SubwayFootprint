package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.dominate_orientation.subwayfootprint.ui.login.LoginActivity;

public class PersonalcenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalcenter);
        String token = " ";
        Token app = (Token)getApplicationContext();
        token=app.getToken();
        Toast.makeText(PersonalcenterActivity.this,token,Toast.LENGTH_SHORT).show();
    }
}