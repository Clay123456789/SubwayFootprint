package com.dominate_orientation.subwayfootprint;
import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dominate_orientation.subwayfootprint.ui.login.LoginActivity;

import org.json.JSONObject;

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
    String age;
    String token;
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
        Token app = (Token)getApplicationContext();
        token=app.getToken();
        Thread t1 = new Thread(new Runnable() {
            String message = "失败";
            @Override
            public void run() {
                try {
                    String json = "";
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://123.56.150.89:8088/user/getUser")
                            .addHeader("token",token)
                            .post(RequestBody.create(MediaType.parse("application/json"), json))
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                    message = jsonObject.getString("message");
                    if (message.equals("成功")) {
                        username.setText(jsonObject1.getString("username"));
                        sex.setText(jsonObject1.getString("sex"));
                        tel.setText(jsonObject1.getString("tel"));
                        credit.setText(jsonObject1.getString("credit"));
                        email.setText(jsonObject1.getString("email"));
                        qianming.setText(jsonObject1.getString("qianming"));
                        age=jsonObject1.getString("age");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                message = "失败";
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PersonalcenterActivity.this, "验证码发送失败,邮箱已注册或格式不正确", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PersonalcenterActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
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
            public void onClick(View v) {
                Intent intent =new Intent(PersonalcenterActivity.this, LoginActivity.class);
                app.setToken(" ");
                PersonalcenterActivity.this.startActivity(intent);
            }
        });
    }
    public void alert_edit1(View view){
        Bundle bundle = new Bundle();
        bundle.putString("username", username.getText().toString());
        bundle.putString("age", age);
        bundle.putString("tele", tel.getText().toString());
        bundle.putString("qianming", qianming.getText().toString());
        bundle.putString("sex", sex.getText().toString());
        bundle.putString("credit", credit.getText().toString());
        Intent intent =new Intent(PersonalcenterActivity.this, PersonalEditActivity.class);
        intent.putExtras(bundle);
        PersonalcenterActivity.this.startActivity(intent);
    }


}