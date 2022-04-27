package com.dominate_orientation.subwayfootprint;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dominate_orientation.subwayfootprint.databinding.ActivityMainPageBinding;

public class main_page extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainPageBinding binding;
    WebView wv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        wv = (WebView) findViewById(R.id.metro_overview);
        wv.setWebViewClient(new WebViewClient());
        //改设置很重要！
        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setAllowContentAccess(true);
        wv.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setDomStorageEnabled(true);
        wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv.getSettings().setGeolocationEnabled(true);

        wv.setHorizontalScrollBarEnabled(false);
        wv.setVerticalScrollBarEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        //mlgb这文件路径名是真的逆天的批爆
        wv.loadUrl("file:///android_asset/geotest.html");

    }

    public void forward(View view)
    {
        Intent myIntent = new Intent(this, null);
        myIntent.putExtra("start","FirstKeyValue");
        myIntent.putExtra("secondKeyName","SecondKeyValue");
        startActivity(myIntent);
    }
}