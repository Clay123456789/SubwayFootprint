package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dominate_orientation.subwayfootprint.ui.login.LoginActivity;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Password_EditActivity extends AppCompatActivity {

    String email;
    EditText  oldpassword ;
    EditText  newpassword ;
    EditText  newpassword1 ;
    Button cancelbtn ;
    Button confirmbtn ;
    String token;
    String message = "失败";
    Token app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_edit);
        app = (Token)getApplicationContext();
        token=app.getToken();
        oldpassword = findViewById(R.id.edit_oldpassword);
        newpassword = findViewById(R.id.edit_newpassword);
        newpassword1 = findViewById(R.id.edit_newpassword1);
        cancelbtn = findViewById(R.id.password_cancel);
        confirmbtn =findViewById(R.id.password_confirm);
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        email = bundle.getString("email");
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
                if (!isPasswordValid(newpassword.getText().toString())) {
                    newpassword.setError("密码必须大于5位数");
                    confirmbtn.setEnabled(false);
                } else if (!isPassword1Valid(newpassword.getText().toString(),newpassword1.getText().toString())) {
                    newpassword1.setError("两次密码输入不一致");
                    confirmbtn.setEnabled(false);
                }  else {
                    confirmbtn.setEnabled(true);
                }
            }
        };
        newpassword.addTextChangedListener(afterTextChangedListener);
        newpassword1.addTextChangedListener(afterTextChangedListener);

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirm();
            }
        });


        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Password_EditActivity.this, PersonalcenterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void comfirm(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = "{\n" +
                            "\"email\": "+"\""+email+"\""+","+
                            "\"password\": "+"\""+oldpassword.getText().toString()+"\""+","+
                            "\"newPassword\": "+"\""+newpassword.getText().toString()+"\""+
                            "}";
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://123.56.150.89:8088/user/changePassword")
                            .addHeader("token",token)
                            .post(RequestBody.create(MediaType.parse("application/json"),json))
                            .build();
                    Response response =client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    Log.i("code",jsonObject.getString("code"));
                    Log.i("message",jsonObject.getString("message"));
                    Log.i("data",jsonObject.getString("data"));
                    message = jsonObject.getString("message");
                    if(message.equals("成功")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Password_EditActivity.this,"修改密码成功",Toast.LENGTH_SHORT).show();
                            }
                        });
                        message = "失败";
                        Intent intent =new Intent(Password_EditActivity.this, LoginActivity.class);
                        app.setToken("");
                        startActivity(intent);
                        Password_EditActivity.this.finish();
                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Password_EditActivity.this,"修改密码失败，旧密码错误",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }catch(Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Password_EditActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }


    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
    private boolean isPassword1Valid(String password, String password1) {
        return password1.equals(password);
    }
}