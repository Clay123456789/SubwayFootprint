package com.dominate_orientation.subwayfootprint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dominate_orientation.subwayfootprint.ui.login.LoginActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

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
    TextView  age ;
    String  credit ;
    Button cancelbtn ;
    Button confirmbtn ;
    String token;
    String message = "??????";
    public static final int CHOOSE_PHOTO = 2;
    private AlertDialog dialog;
    private String Url;
    private String ImagePath=" ";
    RoundedImageView imageView;
    String imagePath1;
    Uri uri1;
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
        imageView = findViewById(R.id.Head_imageView1);
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
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Bitmap bitmap = getBitmap(Url);
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


        //??????????????????
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
                    username.setError("?????????????????????");
                    confirmbtn.setEnabled(false);
                } else if (!isQianMingValid(qianming.getText().toString())) {
                    qianming.setError("????????????????????????18");
                    confirmbtn.setEnabled(false);
                } else if (!isSexValid(sex.getText().toString())) {
                    sex.setError("???????????????female??????male");
                    confirmbtn.setEnabled(false);
                } else if (!isTeleValid(tel.getText().toString())) {
                    tel.setError("?????????????????????");
                    confirmbtn.setEnabled(false);
                } else {
                    confirmbtn.setEnabled(true);
                }
            }
        };
        age.addTextChangedListener(afterTextChangedListener);
        username.addTextChangedListener(afterTextChangedListener);
        sex.addTextChangedListener(afterTextChangedListener);
        tel.addTextChangedListener(afterTextChangedListener);
        qianming.addTextChangedListener(afterTextChangedListener);

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfirm();
            }
        });

        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????????????????????AlertDialog??????
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalEditActivity.this);
                View view = (LinearLayout) getLayoutInflater().inflate(R.layout.date_dialog, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
                datePicker.setMaxDate(new Date().getTime());
                //???????????????????????? ?????????????????? ??????:??????\???
                datePicker.setCalendarViewShown(false);
                //??????date??????
                builder.setView(view);
                builder.setTitle("??????????????????");
                builder.setPositiveButton("??? ???", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //????????????
                        String year = String.valueOf(datePicker.getYear());
                        String month = String.valueOf(datePicker.getMonth()+1);
                        month = month.length()<2?"0"+month:month;
                        String dayOfMonth = String.valueOf(datePicker.getDayOfMonth());
                        dayOfMonth = dayOfMonth.length()<2?"0"+dayOfMonth:dayOfMonth;
                        try {
                            age.setText(year + month + dayOfMonth);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("??? ???", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });

        //??????????????????
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(PersonalEditActivity.this, PersonalcenterActivity.class);
                startActivity(intent);
            }
        });
    }
    //????????????
    public void comfirm(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = "{\n" +
                            "\"username\": "+"\""+username.getText().toString()+"\""+","+
                            "\"age\":" +age.getText().toString()+","+
                            "\"sex\": "+"\""+sex.getText().toString()+"\""+","+
                            "\"tel\": "+"\""+tel.getText().toString()+"\""+","+
                            "\"touxiang\": "+"\""+Url+"\""+","+
                            "\"qianming\": "+"\""+qianming.getText().toString()+"\""+
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
                    if(message.equals("??????")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PersonalEditActivity.this,"??????????????????",Toast.LENGTH_SHORT).show();
                            }
                        });
                        message = "??????";
                        Intent intent =new Intent(PersonalEditActivity.this, PersonalcenterActivity.class);
                        startActivity(intent);
                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PersonalEditActivity.this,"??????????????????",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }catch(Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PersonalEditActivity.this,"??????????????????",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
    //????????????
    public void chose_header_picture(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);//???????????????
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.dialog, null);//?????????????????????
        builder.setView(layout);//????????????????????????
        dialog = builder.create();//????????????????????????
        dialog.show();//???????????????
        TextView choosePhotoTV = layout.findViewById(R.id.photo);//??????????????????
        TextView cancelTV = layout.findViewById(R.id.cancel);//??????
//??????????????????
        //??????????????????
        choosePhotoTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //?????????????????????????????????
                if (ContextCompat.checkSelfPermission(PersonalEditActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PersonalEditActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                //??????????????????
                openAlbum();
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
    //??????????????????
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // ????????????
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
            String imagePath = null;
            if (DocumentsContract.isDocumentUri(this, uri)) {
                // ?????????document?????????Uri????????????document id??????
                String docId = DocumentsContract.getDocumentId(uri);
                assert uri != null;
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1]; // ????????????????????????id
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null);
                }
            } else {
                assert uri != null;
                if ("content".equalsIgnoreCase(uri.getScheme())) {
                    // ?????????content?????????Uri??????????????????????????????
                    imagePath = getImagePath(uri, null);
                } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                    // ?????????file?????????Uri?????????????????????????????????
                    imagePath = uri.getPath();
                }
            }
//????????????
            imagePath1 = imagePath;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        File file = new File(imagePath1);
                        Log.d("????????????", imagePath1);
                        RequestBody fileBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
                        MultipartBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("file", file.getName(), fileBody)
                                .build();
                        Log.d("????????????", file.getName());
                        Request request = new Request.Builder()
                                .url("https://thelittlestar.cn:8088/file/uploadOrderSignImage")
                                .addHeader("token", token)
                                .post(requestBody)
                                .build();
                        Response response = client.newCall(request).execute();

                        String responseData = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseData);
                        Log.i("code", jsonObject.getString("code"));
                        Log.i("message", jsonObject.getString("message"));
                        Log.i("data", jsonObject.getString("data"));
                        JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));

                        message = jsonObject.getString("message");

                        if (message.equals("??????")) {
                            //????????????
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PersonalEditActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                                }
                            });
                            message = "??????";
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Url = jsonArray.getString(i);

                                Log.i("data", Url);
                            }
                            new Thread(new Runnable(){
                                @Override
                                public void run() {
                                    try {
                                        Bitmap bitmap = getBitmap(Url);
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
                        } else {
                            Log.d("????????????", message);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PersonalEditActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PersonalEditActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }
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
    //??????????????????
    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // ??????Uri???selection??????????????????????????????
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

    public boolean isSexValid(String sex) {
        return sex != null && (sex.equals("male")||sex.equals("female"));
    }
    public boolean isQianMingValid(String qianming) {
        return qianming.trim().length() < 18;
    }
    public boolean isTeleValid(String tele) {
        return Patterns.PHONE.matcher(tele).matches();
    }

}