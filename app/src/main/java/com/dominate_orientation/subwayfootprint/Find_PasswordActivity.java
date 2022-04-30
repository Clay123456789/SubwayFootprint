package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dominate_orientation.subwayfootprint.ui.login.LoginActivity;

import org.json.JSONObject;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Find_PasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        final Button find_PW_Button = findViewById(R.id.getPassword);
        final EditText email = findViewById(R.id.find_email);
        final TextView goLogin = findViewById(R.id.goLogin1);

        //输入格式校验
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isUserNameValid(email.getText().toString())) {
                    email.setError("邮箱格式不正确");
                }
            }
        };
        email.addTextChangedListener(afterTextChangedListener);


        //发送密码按钮实现
        find_PW_Button.setOnClickListener(new View.OnClickListener() {
            String message = "失败";

            @Override
            public void onClick(View v) {
                //设置倒计时重发
                long duration = TimeUnit.MINUTES.toMillis(1);
                new CountDownTimer(duration, 1000){
                    @Override
                    public void onTick(long l) {
                        String sDuration = String.format(Locale.ENGLISH,"%02ds后可重发",
                                TimeUnit.MILLISECONDS.toSeconds(l) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                        find_PW_Button.setEnabled(false);
                        find_PW_Button.setText(sDuration);
                    }

                    @Override
                    public void onFinish() {
                        find_PW_Button.setEnabled(true);
                        find_PW_Button.setText("找  回  密  码");
                    }
                }.start();

                //http向后端发送请求
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String json = "{\n" +
                                    "\"email\": " + "\"" + email.getText().toString() + "\""  +
                                    "}";
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("https://thelittlestar.cn:8088/user/findPassword")
                                    .post(RequestBody.create(MediaType.parse("application/json"), json))
                                    .build();
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseData);
                            Log.i("", responseData);
                            Log.i("code", jsonObject.getString("code"));
                            Log.i("message", jsonObject.getString("message"));
                            Log.i("data", jsonObject.getString("data"));
                            String data=jsonObject.getString("data");
                            message = jsonObject.getString("message");
                            if (message.equals("成功")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Find_PasswordActivity.this, data, Toast.LENGTH_SHORT).show();
                                        message = "失败";
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Find_PasswordActivity.this, data, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Find_PasswordActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
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
            }
        });

        //立即登录
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Find_PasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    // 用户名校验
    private boolean isUserNameValid(String username) {
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return false;
        }
    }

}