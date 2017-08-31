package com.jingle.soccernews;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ActionBar actionbar;
    HashMap<String,String> map;
    static HashMap<String, String> mapHupu;
    static HashMap<String, String> mapdDongqiudi;
    static HashMap<String, String> mapZhiboba;
    static HashMap<String, String> mapWangyi;
    String[] titles;
    ListView listView;
    ArrayAdapter<String> adapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    switch (msg.arg1) {
                        case 1:
                            mapHupu = (HashMap<String, String>) msg.obj;
                            map = mapHupu;
                         //   titles = mapHupu.keySet().toArray(new String[mapHupu.size()]);
                            break;
                        case 2:
                            mapdDongqiudi = (HashMap<String, String>) msg.obj;
                            map = mapdDongqiudi;
                         //   titles = mapdDongqiudi.keySet().toArray(new String[mapdDongqiudi.size()]);

                            break;
                        case 3:
                            mapZhiboba = (HashMap<String, String>) msg.obj;
                            map = mapZhiboba;
                           // titles = mapZhiboba.keySet().toArray(new String[mapZhiboba.size()]);

                            break;

                        case 4:
                            mapWangyi = (HashMap<String,String>) msg.obj;
                            map = mapWangyi;
                          //  titles = mapWangyi.keySet().toArray(new String[mapWangyi.size()]);

                            break;
                    }
                    titles = map.keySet().toArray(new String[map.size()]);

                    adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, titles);
                    listView.setAdapter(adapter);
                   /* map = (HashMap<String, String>) msg.obj;
                    titles = map.keySet().toArray(new String[map.size()]);
                    adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, titles);
                    listView.setAdapter(adapter);*/
                    break;

                case 1:
                    Toast.makeText(MainActivity.this, "网络IO错误", Toast.LENGTH_SHORT).show();
            }
        }
    };

    // private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                        Thread hupuThread = new Thread(new HupuThread(mHandler));
                        hupuThread.start();
                        return true;


                case R.id.navigation_dashboard:
                        Thread dongqiudiThread = new Thread(new DongqiudiThread(mHandler));
                        dongqiudiThread.start();
                        return true;




                case R.id.navigation_notifications:
                        Thread zhibobaThread = new Thread(new ZhibobaThread(mHandler));
                        zhibobaThread.start();
                        return true;


                case R.id.navigation_wangyi:
                        Thread wangyiThread = new Thread(new WangYiThread(mHandler));
                        wangyiThread.start();
                        return true;

            }

            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar();
        listView = (ListView) findViewById(R.id.list_item);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
        Log.e("liujian","onCreate重现");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String title = titles[position];
                    String url = map.get(title);
                   /* Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);*/
                   Intent intent = new Intent(MainActivity.this, ShowNewsActivity.class);

                //intent.setData(Uri.parse(url));
                intent.putExtra("url",url);
                startActivity(intent);

            }
        });


       /* listView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
             //   Toast.makeText(getApplicationContext(),"出现view"+String.valueOf(v.getVerticalScrollbarPosition()), Toast.LENGTH_SHORT).show();
                Log.e("OnviewAttach","出现"+String.valueOf(v.getVerticalScrollbarPosition()));
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
               // Toast.makeText(getApplicationContext(),"消失view"+String.valueOf(v.getVerticalScrollbarPosition()), Toast.LENGTH_SHORT).show();
                Log.e("OnviewDetach","消失"+String.valueOf(v.getVerticalScrollbarPosition()));
            }
        });*/


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
                Toast.makeText(this,"退出",Toast.LENGTH_SHORT).show();
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
