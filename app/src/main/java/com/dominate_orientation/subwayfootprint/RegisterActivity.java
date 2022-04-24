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

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Button registerButton = findViewById(R.id.register);
        final Button codeButton = findViewById(R.id.getCode);
        final EditText username = findViewById(R.id.registername);
        final EditText code = findViewById(R.id.code);
        final EditText password = findViewById(R.id.registerpassword);
        final EditText password1 = findViewById(R.id.registerpassword1);
        final TextView goLogin = findViewById(R.id.goLogin);
        registerButton.setEnabled(false);


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
                if (!isUserNameValid(username.getText().toString())) {
                    username.setError("邮箱格式不正确");
                    registerButton.setEnabled(false);
                } else if (!isPasswordValid(password.getText().toString())) {
                    password.setError("密码必须大于5个字符");
                    registerButton.setEnabled(false);
                } else if (!isPassword1Valid(password.getText().toString(), password1.getText().toString())) {
                    password1.setError(getString(R.string.invalid_password1));
                    registerButton.setEnabled(false);
                } else {
                    registerButton.setEnabled(true);
                }
            }
        };
        username.addTextChangedListener(afterTextChangedListener);
        password.addTextChangedListener(afterTextChangedListener);
        password1.addTextChangedListener(afterTextChangedListener);
        code.addTextChangedListener(afterTextChangedListener);
        //注册按钮功能实现
        registerButton.setOnClickListener(new View.OnClickListener() {
            String message = "失败";

            @Override
            public void onClick(View v) {
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String json = "{\n" +
                                    "\"username\": " + "\"" + username.getText().toString() + "\"" + "," +
                                    "\"password\": " + "\"" + password.getText().toString() + "\"" + "," +
                                    "\"email\": " + "\"" + username.getText().toString() + "\"" + "," +
                                    "\"code\": " + "\"" + code.getText().toString() + "\""  +
                                    "}";
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("http://123.56.150.89:8088/user/regist")
                                    .post(RequestBody.create(MediaType.parse("application/json"), json))
                                    .build();
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseData);
                            Log.i("code", jsonObject.getString("code"));
                            Log.i("message", jsonObject.getString("message"));
                            Log.i("data", jsonObject.getString("data"));
                            message = jsonObject.getString("message");
                            if (message.equals("成功")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                message = "失败";
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
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

        //发送验证码按钮实现
        codeButton.setOnClickListener(new View.OnClickListener() {
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
                        codeButton.setEnabled(false);
                        codeButton.setText(sDuration);
                    }

                    @Override
                    public void onFinish() {
                        codeButton.setEnabled(true);
                        codeButton.setText("获取验证码");
                    }
                }.start();

                //http向后端发送请求
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String json = "{\n" +
                                    "\"email\": " + "\"" + username.getText().toString() + "\""  +
                                    "}";
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("http://123.56.150.89:8088/user/sendRegistEmail")
                                    .post(RequestBody.create(MediaType.parse("application/json"), json))
                                    .build();
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseData);
                            Log.i("", responseData);
                            Log.i("code", jsonObject.getString("code"));
                            Log.i("message", jsonObject.getString("message"));
                            Log.i("data", jsonObject.getString("data"));
                            message = jsonObject.getString("message");
                            if (message.equals("成功")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                                        message = "失败";
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "验证码发送失败,邮箱已注册或格式不正确", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
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

        //已有帐号，立即登录
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(RegisterActivity.this, LoginActivity.class);
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

    // 密码校验
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private boolean isPassword1Valid(String password, String password1) {
        return password1.equals(password);
    }
}