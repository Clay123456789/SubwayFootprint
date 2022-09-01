package com.dominate_orientation.subwayfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicReference;

public class code extends AppCompatActivity {
private WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        AtomicReference<Intent> intent = new AtomicReference<>(this.getIntent());

        String arid = intent.get().getStringExtra("arid");
        String date = intent.get().getStringExtra("date");
        String num = intent.get().getStringExtra("num");
        System.out.println("AAAA:"+arid);


        Integer num1 = Integer.parseInt(num);

        if (num1 >0) {

            myWebView = findViewById(R.id.web1);


            WebSettings webSettings =myWebView.getSettings();

            webSettings.setDefaultTextEncodingName("UTF-8");
            // myWebView.getSettings().setUserAgentString(arid);
            myWebView.loadUrl("http://10.128.237.76:8080/#/?arid="+arid);
            webSettings.setDefaultTextEncodingName("UTF-8");
            myWebView.getSettings().setJavaScriptEnabled(true);
           //localStorage  允许存储
            myWebView.getSettings().setDomStorageEnabled(true);
            myWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);//存储的最大容量
            String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
            myWebView.getSettings().setAppCachePath(appCachePath);
            myWebView.getSettings().setAllowFileAccess(true);
            myWebView.getSettings().setAppCacheEnabled(true);
        } else {

            Toast.makeText(this, "您的商品余额不足", Toast.LENGTH_SHORT).show();

        }


    }

    private String cnToUnicode (String cn)

    {

        char[] chars = cn.toCharArray();

        String resultStr = "";

        for (int i = 0; i < chars.length; i++)

        {

            resultStr += "%u" + Integer.toString (chars[i], 16);

        }

        return resultStr;

    }
}
