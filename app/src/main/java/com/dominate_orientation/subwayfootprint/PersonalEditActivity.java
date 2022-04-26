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

public class PersonalEditActivity extends AppCompatActivity {

    EditText username;
    EditText  sex ;
    EditText  qianming ;
    EditText  tel ;
    EditText  age ;
    String  credit ;
    Button cancelbtn ;
    Button confirmbtn ;
    String token;
    String message = "失败";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_edit);
        Token app = (Token)getApplicationContext();
        token=app.getToken();
        username = findViewById(R.id.edit_username);
        sex = findViewById(R.id.edit_sex);
        qianming = findViewById(R.id.edit_qianming);
        tel = findViewById(R.id.edit_tele);
        age = findViewById(R.id.edit_age);

        cancelbtn = findViewById(R.id.persona_cancel);
        confirmbtn =findViewById(R.id.persona_confirm);
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        username.setText(bundle.getString("username"));
        sex.setText(bundle.getString("sex"));
        qianming.setText(bundle.getString("qianming"));
        age.setText(bundle.getString("age"));
        tel.setText(bundle.getString("tele"));
        credit =bundle.getString("credit");
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
                    username.setError("昵称格式不正确");
                    confirmbtn.setEnabled(false);
                } else if (!isQianMingValid(qianming.getText().toString())) {
                    qianming.setError("签名长度必须小于18");
                    confirmbtn.setEnabled(false);
                } else if (!isSexValid(sex.getText().toString())) {
                    sex.setError("性别必须为female或者male");
                    confirmbtn.setEnabled(false);
                } else if (!isTeleValid(tel.getText().toString())) {
                    tel.setError("电话格式不正确");
                    confirmbtn.setEnabled(false);
                } else if (!isAgeValid(age.getText().toString())) {
                    age.setError("年龄格式不正确");
                    confirmbtn.setEnabled(false);
                } else {
                    confirmbtn.setEnabled(true);
                }
            }
        };
        username.addTextChangedListener(afterTextChangedListener);
        age.addTextChangedListener(afterTextChangedListener);
        sex.addTextChangedListener(afterTextChangedListener);
        tel.addTextChangedListener(afterTextChangedListener);
        qianming.addTextChangedListener(afterTextChangedListener);

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirm();
            }
        });


        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(PersonalEditActivity.this, PersonalcenterActivity.class);
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
                            "\"username\": "+"\""+username.getText().toString()+"\""+","+
                            "\"age\": "+"\""+age.getText().toString()+"\""+","+
                            "\"sex\": "+"\""+sex.getText().toString()+"\""+","+
                            "\"tel\": "+"\""+tel.getText().toString()+"\""+","+
                            "\"touxiang\": "+"\""+"@mipmap/ic_launcher_round"+"\""+","+
                            "\"qianming\": "+"\""+qianming.getText().toString()+"\""+","+
                            "\"credit\": "+"\""+credit+"\""+
                            "}";
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://123.56.150.89:8088/user/updateUser")
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
                                Toast.makeText(PersonalEditActivity.this,"修改信息成功",Toast.LENGTH_SHORT).show();
                            }
                        });
                        message = "失败";
                        Intent intent =new Intent(PersonalEditActivity.this, PersonalcenterActivity.class);
                        startActivity(intent);
                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PersonalEditActivity.this,"修改信息失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }catch(Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PersonalEditActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    // A placeholder username validation check
    public boolean isUserNameValid(String username) {
        return username != null && username.trim().length() < 10;
    }
    public boolean isAgeValid(String age) {
        return age != null && Integer.parseInt(age)>0 && Integer.parseInt(age)<100;
    }
    public boolean isSexValid(String sex) {
        return sex != null && (sex.equals("male")||sex.equals("female"));
    }
    public boolean isQianMingValid(String qianming) {
        return qianming.trim().length() < 18;
    }
    public boolean isTeleValid(String tele) {
        return Patterns.PHONE.matcher(tele).matches();
    }
    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}