package com.dominate_orientation.subwayfootprint;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

<<<<<<< Updated upstream
import android.content.Intent;
import android.os.Bundle;
=======
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
>>>>>>> Stashed changes
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
<<<<<<< Updated upstream
=======
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
>>>>>>> Stashed changes

public class route extends AppCompatActivity {
    String msg="";
    Info[] infos = null;
    Intent it = null;

    TextView ls =null;
    TextView ps = null;
    TextView ns = null;
<<<<<<< Updated upstream
=======
    TextView digText1 = null;
    TextView digText2 = null;

    ImageView pre_line = null;
    ImageView next_line = null;
    ImageView last_line = null;
    ImageView last_pre = null;
    ImageView pre_next = null;

    String line_1 = "@drawable/line_1";
    String line_2 = "@drawable/line_2";
    String line_4 = "@drawable/line_4";
    String line_5 = "@drawable/line_5";
    String line_6 = "@drawable/line_6";
    String line_7 = "@drawable/line_7";
    String line_8 = "@drawable/line_8";
    String line_9 = "@drawable/line_9";
    String line_10 = "@drawable/line_10";
    String line_11 = "@drawable/line_11";
    String line_13 = "@drawable/line_13";
    String line_14 = "@drawable/line_14";
    String line_15 = "@drawable/line_15";
    String line_16 = "@drawable/line_16";
    String line_17 = "@drawable/line_17";
    String line_19 = "@drawable/line_19";
    String line_fs = "@drawable/line_fs";
    String line_cp = "@drawable/line_cp";
    String line_yz = "@drawable/line_yz";
    String line_yf = "@drawable/line_yf";
    String line_s1 = "@drawable/line_s1";
    String line_xj = "@drawable/line_xj";
    String line_yzt1 = "@drawable/line_yzt1";
    String line_capair = "@drawable/line_capair";
    String line_dxair = "@drawable/line_air";

    //地理位置
    FusedLocationProviderClient mFusedLocationClient;
    TextView latitudeTextView, longitTextView;
    int PERMISSION_ID = 44;



>>>>>>> Stashed changes

    Boolean no_next_passed_station = false;
    Boolean last_station_used = false;
    Boolean no_station_left=false;
    Integer infos_index = 0;
    Integer passed_station_index = 0;

    Button go_to_next=null;
    Button dig = null;
    Button ensconce = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        it = getIntent();
        msg = it.getStringExtra("msg");
        ls = (TextView) findViewById(R.id.last_station);
        ps = (TextView) findViewById(R.id.pre_station);
        ns = (TextView) findViewById(R.id.next_station);
        go_to_next=(Button)findViewById(R.id.go_to_next_station);
        dig =(Button)findViewById(R.id.dig);
        ensconce =(Button)findViewById(R.id.ensconce);
        ensconce.setEnabled(false);
        latitudeTextView = (TextView)findViewById(R.id.latitudeTextView);
        longitTextView = (TextView)findViewById(R.id.longitTextView);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        pre_line = (ImageView)findViewById(R.id.img_pre_station);
        next_line = (ImageView)findViewById(R.id.img_next_station);
        last_line= (ImageView)findViewById(R.id.img_last_station);
        last_pre = (ImageView)findViewById(R.id.last_pre_exchange);
        pre_next = (ImageView)findViewById(R.id.pre_next_exchange);
        last_pre.setAlpha(0);
        pre_next.setAlpha(0);


        /*new Thread()
        {
            @Override
            public void run() {
                while(!no_station_left);
                go_to_next.setEnabled(false);
            }
        }.start();*/

        Calculate();
        init_texts();

        
        new Thread(new Runnable() {
            String message = "失败";
            @Override
            public void run() {
                try{
                while(true) {
                    getLastLocation();
                }}catch (Exception e){
                    System.out.println(e);
                }
            }
        }
        ).start();
    }

<<<<<<< Updated upstream
=======

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("11","route resume!!!!");
        if(resumeFlag) {
            resumeFlag = false;
            digText1 = view_dig.findViewById(R.id.dig_text1);
            digText1.setText("宝箱类型: " + digTreasure.getVariety());
            digText2 = view_dig.findViewById(R.id.dig_text2);
            digText2.setText("打开消耗碳积分: " + digTreasure.getCredit());
            digAlert.show();
        }
        if (checkPermissions()) {
            getLastLocation();
        }
    }

>>>>>>> Stashed changes
    public void ls(String s){ ls.setText(s);}
    public void ps(String s){ ps.setText(s);}
    public void ns(String s){ ns.setText(s);}
    public void ls(CharSequence s){ ls.setText(s);}
    public void ps(CharSequence s){ ps.setText(s);}
    public void ns(CharSequence s){ ns.setText(s);}

    public void init_texts()
    {
        ps(infos[infos_index].start);
        set_line(pre_line,infos[infos_index].line);

        if(infos[infos_index].passed.length>0)
        {
            ns(infos[infos_index].passed[passed_station_index]);
            passed_station_index++;
        }else
        {
            ns(infos[infos_index].end);
            last_station_used = true;
        }
        set_line(next_line,infos[infos_index].line);
    }

    public void go_to_next_station(View view)
    {
        ls(ps.getText());
        set_line(last_line,ps.getText().toString());
        ps(ns.getText());
        set_line(pre_line,ns.getText().toString());

        last_line.setImageDrawable(pre_line.getDrawable());
        pre_line.setImageDrawable(next_line.getDrawable());

        last_pre.setAlpha(pre_next.getAlpha());



        if(no_next_passed_station)
        {
            if(last_station_used)
            {//infos_index add 1, clear two false status
                if(infos_index<infos.length-1){
                    //换乘状态！
                    ns(infos[++infos_index].start);
                    set_line(next_line,infos[infos_index].line);
                    pre_next.setAlpha(255);
                    last_station_used = false;
                    no_next_passed_station = false;
                }
                else
                {
                    pre_next.setAlpha(0);
                    go_to_next.setEnabled(false);
                    ensconce.setEnabled(true);
                    ps(ns.getText());
<<<<<<< Updated upstream
                    ns("已到达！可以挖宝啦");
=======
                    set_line(next_line,infos[infos_index].line);
                    ns("已到达！可以藏宝啦");
>>>>>>> Stashed changes
                }
            }else
            {
                pre_next.setAlpha(0);
                ns(infos[infos_index].end);
                set_line(next_line,infos[infos_index].line);
                last_station_used = true;
            }
        }else{
            pre_next.setAlpha(0);
            if(passed_station_index>=infos[infos_index].passed.length)
            {
                ns(infos[infos_index].end);
                set_line(next_line,infos[infos_index].line);
                last_station_used = true;
                no_next_passed_station = true;
            }else{
                ns(infos[infos_index].passed[passed_station_index++]);
                set_line(next_line,infos[infos_index].line);
            }
        }
    }

    public void Calculate()
    {
        String[] set = msg.split(".line.");
        infos=new Info[set.length-1];
        int index=0;
        for(int i=1;i<set.length;i++)
        {
            infos[index] = new Info();
            if(set[i].length()>0) {
                String[] tmp1 = set[i].split(".sn.");
                infos[index].line = tmp1[0];
                if(tmp1[1].indexOf(".pn.")!=-1) {
                    String[] tmp2 = tmp1[1].split(".pn.");
                    infos[index].start = tmp2[0];
                    String[] tmp3 = tmp2[1].split(".en.");
                    infos[index].passed = tmp3[0].split(" ");// 可能是空
                    infos[index].end = tmp3[1];
                }else
                {
                    String[] tmp2 = tmp1[1].split(".en.");
                    infos[index].start = tmp2[0];// 可能是空
                    infos[index].end = tmp2[1];
                }
            }
            index++;
        }
    }

    public void go_back(View view)
    {
        finish();
    }

    public void set_line(ImageView iv, String s)
    {
        String tmp = null;
        try {
            if (s.indexOf("1号") != -1) {
                tmp = line_1;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("2号") != -1) {
                tmp = line_2;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("4号") != -1) {
                tmp = line_4;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("5号") != -1) {
                tmp = line_5;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("6号") != -1) {
                tmp = line_6;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("7号") != -1) {
                tmp = line_7;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("8号") != -1) {
                tmp = line_8;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("9号") != -1) {
                tmp = line_9;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("10号") != -1) {
                tmp = line_10;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("11号") != -1) {
                tmp = line_11;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("13号") != -1) {
                tmp = line_13;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("14号") != -1) {
                tmp = line_14;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("15号") != -1) {
                tmp = line_15;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("16号") != -1) {
                tmp = line_16;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("17号") != -1) {
                tmp = line_17;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("19号") != -1) {
                tmp = line_19;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("房山") != -1) {
                tmp = line_fs;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("昌平") != -1) {
                tmp = line_cp;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("亦庄") != -1) {
                tmp = line_yz;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("s1") != -1) {
                tmp = line_s1;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("西郊") != -1) {
                tmp = line_xj;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("亦庄T1") != -1) {
                tmp = line_yzt1;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("首都") != -1) {
                tmp = line_capair;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("大兴") != -1) {
                tmp = line_dxair;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
            }else if (s.indexOf("燕房") != -1) {
                tmp = line_yf;
                int imageResource = getResources().getIdentifier(tmp, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);

            }
        }catch (RuntimeException re){
            Toast.makeText(this, "图片资源"+tmp+"找不到:(", Toast.LENGTH_SHORT).show();
        }
    }


    //挖宝
    public void dig(View view){

    }

    //藏宝
    //我已设定只有到终点此按钮才能互动
    public void ensconce(View view){

    }

    //地理位置记录器
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitudeTextView.setText(location.getLatitude() + "");
                            longitTextView.setText(location.getLongitude() + "");
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
            longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

}


class Info{
    String line="";
    String start="";
    String end="";
    String[] passed={};
}
