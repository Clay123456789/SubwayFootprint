package com.dominate_orientation.subwayfootprint;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;



import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationView;


//发数据取决于route_enabled和msg内容！！！
public class main_page extends AppCompatActivity {

    //variable
    private AppBarConfiguration appBarConfiguration;
    WebView wv = null;
    String msg = "";
    Info[] infos = null;
    Button voyage = null;
    Boolean route_exist = false;
    Button swap_city = null;

    //city list
    String[] web_files = null;
    Integer web_files_index = 0;

    //layout
    //final LinearLayout container = findViewById(R.id.container);
    ClearEditText cet1;
    ClearEditText cet2;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        //cet1 = (ClearEditText) findViewById(R.id.start_point);
        //cet2 = (ClearEditText) findViewById(R.id.end_point);
        voyage = (Button) findViewById(R.id.voyage);
        swap_city = (Button)findViewById(R.id.swap_city);
        wv = (WebView) findViewById(R.id.metro_overview);
        init_web();

        route_exist = false;

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener((menuItem)->{
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    intent =new Intent(main_page.this, main_page.class);
                    startActivity(intent);
                    break;
                case R.id.nav_credit:
                    intent =new Intent(main_page.this,Tanjifen_main.class);
                    startActivity(intent);
                    break;
                case R.id.nav_shop:
                    intent =new Intent(main_page.this, PersonalcenterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_person:
                    intent =new Intent(main_page.this, PersonalcenterActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });

    }

    @SuppressLint("SetJavaScriptEnabled")
    public void testJS(View view) {
        wv.loadUrl("javascript:wtf()");
    }

    @JavascriptInterface
    public void get_station(String msg) {
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        this.msg = msg;
        //Toast.makeText(this, info[1].start, Toast.LENGTH_SHORT).show();
    }
    @JavascriptInterface
    public void voyage_enable() {
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        route_exist = true;
        //Toast.makeText(this, info[1].start, Toast.LENGTH_SHORT).show();
    }


    /*//从前端获取路线起始站和终点站
    //传参数给下一个activity
    public void forward(View view)
    {
        Intent it = new Intent(this, Routing.class);
        String start_station = "", end_station = "";
        it.putExtra("msg",msg);
        startActivity(it);
    }*/

    //1.只显示webview, 去掉其他控件
    //2.新生成下部的一个窗口用于寻宝挖宝以及返回选择站点的界面
    public void letsgo(View view) {
        //cet1.setVisibility(View.GONE);
        //cet2.setVisibility(View.GONE);
        if(route_exist) {
            Intent it = new Intent(this, route.class);
            it.putExtra("msg", msg);
            startActivity(it);
        }else
        {
            Toast.makeText(this, "请先选择路线哦", Toast.LENGTH_SHORT).show();
        }

        /*LinearLayout l=new LinearLayout(getApplicationContext());
        final ViewGroup.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        l.setLayoutParams(params);
        l.setOrientation(LinearLayout.HORIZONTAL);

        TextView tv1 = new TextView(getApplicationContext());
        LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsText.weight = 1;
        tv1.setLayoutParams(paramsText);
        tv1.setText("当前所在站点：");

        l.addView(tv1);

        container.addView(l);*/
    }
    public void init_web()
    {
        load_webs();

        //改设置很重要！
        wv.setWebViewClient(new WebViewClient());
        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setAllowContentAccess(true);
        wv.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setDomStorageEnabled(true);
        wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv.getSettings().setGeolocationEnabled(true);
        wv.addJavascriptInterface(this, "passStations");
        wv.setHorizontalScrollBarEnabled(false);
        wv.setVerticalScrollBarEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //mlgb这文件路径名是真的逆天的批爆
        wv.loadUrl(web_files[web_files_index]);
    }

    public void load_webs()
    {
        web_files = new String[2];
        web_files[0] = "file:///android_asset/map_overview.html";
        web_files[1] = "file:///android_asset/map_overview_sh.html";
    }

    public void swap_city(View view)
    {
        if(web_files_index>=web_files.length-1)
        {
            web_files_index=0;
        }else
        {
            web_files_index++;
        }
        wv.loadUrl(web_files[web_files_index]);
    }

}