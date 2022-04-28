package com.dominate_orientation.subwayfootprint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dominate_orientation.subwayfootprint.ui.login.LoginActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    public static final int CHOOSE_PHOTO = 2;
    private AlertDialog dialog;
    private String Url;
    private String ImagePath=" ";
    RoundedImageView imageView;
    String imagePath1;
    Uri uri;
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
        imageView = findViewById(R.id.Head_imageView);
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
        Url = bundle.getString("url");
        uri = Uri.parse(Url);
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
                            "\"touxiang\": "+"\""+uri.toString()+"\""+","+
                            "\"qianming\": "+"\""+qianming.getText().toString()+"\""+","+
                            "\"credit\": "+"\""+credit+"\""+
                            "}";
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://thelittlestar.cn:8088/user/updateUser")
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
    //选择头像
    public void chose_header_picture(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);//创建对话框
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.dialog, null);//获取自定义布局
        builder.setView(layout);//设置对话框的布局
        dialog = builder.create();//生成最终的对话框
        dialog.show();//显示对话框
        TextView PhotoviewTV = layout.findViewById(R.id.photoview);//查看
        TextView choosePhotoTV = layout.findViewById(R.id.photo);//从相册中选择
        TextView cancelTV = layout.findViewById(R.id.cancel);//取消
//分别设置监听
        //查看
        PhotoviewTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //查看原来的头像
                if(ImagePath==" ") {
                    pic_view(uri);
                } else{
                    Uri uri= Uri.fromFile(new File(ImagePath));
                    pic_view(uri);
                }
                dialog.dismiss();//关闭对话框
            }
        });

        //从相册中选择
        choosePhotoTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //如果没有权限则申请权限
                if (ContextCompat.checkSelfPermission(PersonalEditActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PersonalEditActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                //调用打开相册
                openAlbum();
                dialog.dismiss();//关闭对话框
            }
        });
        //取消
        cancelTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();//关闭对话框
            }
        });
    }
    //打开系统相册
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    //查看图片
    public void pic_view(Uri imageUri){
        Intent it =new Intent(this,show_picActivity.class);
        it.setDataAndType(imageUri, "image/*");
        startActivity(it);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri= data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        String imagePath = null;
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            assert uri != null;
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            }
            else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }
        else {
            assert uri != null;
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                // 如果是content类型的Uri，则使用普通方式处理
                imagePath = getImagePath(uri, null);
            }
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                // 如果是file类型的Uri，直接获取图片路径即可
                imagePath = uri.getPath();
            }
        }
//发送请求
        imagePath1 = imagePath;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    File file = new File(imagePath1);
                    Log.d("文件上传",imagePath1);
                    RequestBody fileBody = RequestBody.create(file,MediaType.parse("multipart/form-data"));
                    MultipartBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("file",file.getName(),fileBody)
                            .build();
                    Log.d("文件上传",file.getName());
                    Request request = new Request.Builder()
                            .url("https://thelittlestar.cn:8088/file/uploadOrderSignImage")
                            .addHeader("token",token)
                            .post(requestBody)
                            .build();

                    client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
                        //上传超时
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            Log.d("文件上传","失败了！");
                            e.printStackTrace();
                        }

                        //上传成功
                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                try {
                                    JSONObject jsonObject = new JSONObject((response.body().string()));
                                    Log.d("文件上传成功",jsonObject.getString("code"));
                                    Log.d("文件名",jsonObject.getString("data"));
                                    message=jsonObject.getString("message");
                                    //上传成功
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(PersonalEditActivity.this,"头像上传成功",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    message = "失败";
                                    try {
                                        ImagePath=imagePath1;
                                        FileInputStream fis = new FileInputStream(ImagePath);
                                        BitmapFactory.Options options=new BitmapFactory.Options();
                                        options.inJustDecodeBounds = false;
                                        Bitmap bitmap = BitmapFactory.decodeStream(fis,null,options);
                                        imageView.setImageBitmap(bitmap);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                Log.d("文件上传",response.message()+"error:body"+response.body().string());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(PersonalEditActivity.this,"头像上传失败",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    });
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


    //获取图片路径
    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
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