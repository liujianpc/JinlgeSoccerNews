package com.jingle.soccernews;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class ShowNewsActivity extends AppCompatActivity {
    private static final String TAG = "ShowNewsActivity";
    private WebView mywebView = null;
    String url;
    ActionBar actionbar;
    ProgressDialog progressDialog;
    //DottedProgressBar progressBar;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        setActionBar();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
       // progressBar = (DottedProgressBar) findViewById(R.id.progress);
        mywebView = (WebView) findViewById(R.id.myWebView);
        WebSettings myWebSettings = mywebView.getSettings();
        myWebSettings.setAllowFileAccess(true);
        myWebSettings.setAppCacheEnabled(true);
        myWebSettings.setBuiltInZoomControls(true);
        myWebSettings.setDisplayZoomControls(true);
        myWebSettings.setSupportZoom(true);
        myWebSettings.setJavaScriptEnabled(true);
        myWebSettings.setAppCacheEnabled(true);
        myWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        url = getIntent().getStringExtra("url");

        mywebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
               /* progressBar.setEnabled(true);
                progressBar.setHovered(true);
                progressBar.startProgress();*/
               progressDialog.show();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
               // defaultButton.setVisibility(View.GONE);
                /*progressBar.stopProgress();
                progressBar.setVisibility(View.GONE);*/
                if (url.contains("dongqiudi.com")) view.loadUrl("javascript:"+"$('nav').remove()");
                else if (url.contains("hupu.com")) view.loadUrl("javascript:"+"$('.hupu-m-fixed').remove()");
                else if (url.contains("163.com")) view.loadUrl("javascript:"+"$('.doc-footer-wrapper').remove()");
                progressDialog.hide();
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mywebView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mywebView.canGoBack()){
            mywebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void setActionBar() {
        actionbar = getSupportActionBar();
        //显示返回箭头默认是不显示的
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);//显示左侧的返回箭头，并且返回箭头和title一直设置返回箭头才能显示
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setDisplayUseLogoEnabled(true);
            //显示标题
            actionbar.setDisplayShowTitleEnabled(true);
            //actionbar.setTitle(getString(R.string.app_name));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar,menu);*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        Log.e("liujian","切换屏幕");
    }
}
