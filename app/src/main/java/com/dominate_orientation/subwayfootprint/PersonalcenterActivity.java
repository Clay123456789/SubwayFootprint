package com.dominate_orientation.subwayfootprint;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dominate_orientation.subwayfootprint.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PersonalcenterActivity extends AppCompatActivity {

    ImageView Edit ;
    Button logout ;
    TextView username;
    TextView sex ;
    TextView qianming ;
    TextView tel ;
    TextView credit ;
    TextView email ;
    TextView myTreasure;
    String age;
    String token;
    String url;
    RoundedImageView imageView;
    Intent intent;
    private AlertDialog dialog;

    public TextView getCredit() {
        return credit;
    }

    public void setCredit(TextView credit) {
        this.credit = credit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalcenter);
        Edit = findViewById(R.id.modify);
        logout = findViewById(R.id.logout);
        username = findViewById(R.id.nickname);
        sex = findViewById(R.id.sex);
        qianming = findViewById(R.id.qianming);
        tel = findViewById(R.id.tele);
        credit = findViewById(R.id.tpoints);
        email = findViewById(R.id.personal_email);
        imageView = findViewById(R.id.Head_imageView);
        myTreasure=findViewById(R.id.myTreasure);
        Token app = (Token)getApplicationContext();
        token=app.getToken();
        myTreasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent =new Intent(PersonalcenterActivity.this,Treasure_user.class);
                startActivity(intent);
            }
        });

        Thread t1 = new Thread(new Runnable() {
            String message = "??????";
            @Override
            public void run() {
                try {
                    String json = "";
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://thelittlestar.cn:8088/user/getUser")
                            .addHeader("token",token)
                            .post(RequestBody.create(MediaType.parse("application/json"), json))
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    message = jsonObject.getString("message");
                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));

                    if (message.equals("??????")) {
                        username.setText(jsonObject1.getString("username"));
                        sex.setText(jsonObject1.getString("sex"));
                        tel.setText(jsonObject1.getString("tel"));
                        credit.setText(jsonObject1.getString("credit"));
                        email.setText(jsonObject1.getString("email"));
                        qianming.setText(jsonObject1.getString("qianming"));
                        age=jsonObject1.getString("age");
                        url=jsonObject1.getString("touxiang");
                        new Thread(new Runnable(){
                            @Override
                            public void run() {
                                try {
                                    Bitmap bitmap = getBitmap(url);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            imageView.setImageBitmap(bitmap);
                                        }
                                    });
                                }  catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                message = "??????";
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PersonalcenterActivity.this, "?????????????????????,?????????????????????????????????", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PersonalcenterActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //2.????????????????????????????????????????????????
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalcenterActivity.this);
                builder.setIcon(null);
                builder.setTitle("???????????????");
                builder.setMessage("???????????????????????????");
                //3.???????????????????????????????????????????????????
                builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(PersonalcenterActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(PersonalcenterActivity.this, LoginActivity.class);
                        app.setToken(" ");
                        PersonalcenterActivity.this.startActivity(intent);
                        PersonalcenterActivity.this.finish();
                    }
                });
                //4.???????????????????????????????????????????????????
                builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(PersonalcenterActivity.this, "??????", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_person);
        bottomNavigationView.setOnItemSelectedListener((menuItem)->{
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    intent =new Intent(PersonalcenterActivity.this, main_page.class);
                    startActivity(intent);
                    break;
                case R.id.nav_credit:
                    intent =new Intent(PersonalcenterActivity.this,Tanjifen_main.class);
                    startActivity(intent);
                    break;
                case R.id.nav_shop:
                    intent =new Intent(PersonalcenterActivity.this, Shangcheng.class);
                    startActivity(intent);
                    break;
                case R.id.nav_person:
                    intent =new Intent(PersonalcenterActivity.this, PersonalcenterActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });
    }




    public void check_header_picture(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);//???????????????
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.dialog1, null);//?????????????????????
        builder.setView(layout);//????????????????????????
        dialog = builder.create();//????????????????????????
        dialog.show();//???????????????
        TextView PhotoviewTV = layout.findViewById(R.id.photoview);//??????
        TextView cancelTV = layout.findViewById(R.id.cancel);//??????
//??????????????????
        //??????
        PhotoviewTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pic_view(url);

                dialog.dismiss();//???????????????
            }
        });

        //??????
        cancelTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();//???????????????
            }
        });
    }
    //????????????
    public void pic_view(String url){
        Bundle bundle= new Bundle();
        bundle.putString("Url",url);
        Intent it =new Intent(this,show_picActivity.class);
        it.putExtras(bundle);
        PersonalcenterActivity.this.startActivity(it);
    }

    public void alert_edit2(View view){
        Bundle bundle = new Bundle();
        bundle.putString("email", email.getText().toString());
        Intent intent =new Intent(PersonalcenterActivity.this, Password_EditActivity.class);
        intent.putExtras(bundle);
        PersonalcenterActivity.this.startActivity(intent);
    }
    public void onClick(View view) {
        //???????????????1??????
        Intent intent = new Intent(PersonalcenterActivity.this, Tanjifen_main.class);
        startActivity(intent);
    }
    public void onClick1(View view) {
        //??????????????????
        Intent intent = new Intent(PersonalcenterActivity.this, Treasure_user.class);
        startActivity(intent);
    }
    public void alert_edit1(View view){
        Bundle bundle = new Bundle();
        bundle.putString("username", username.getText().toString());
        bundle.putString("age", age);
        bundle.putString("tele", tel.getText().toString());
        bundle.putString("qianming", qianming.getText().toString());
        bundle.putString("sex", sex.getText().toString());
        bundle.putString("url", url);
        Intent intent =new Intent(PersonalcenterActivity.this, PersonalEditActivity.class);
        intent.putExtras(bundle);
        PersonalcenterActivity.this.startActivity(intent);
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