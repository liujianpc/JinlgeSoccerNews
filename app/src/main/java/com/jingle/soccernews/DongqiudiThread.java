package com.jingle.soccernews;

import android.os.Handler;
import android.os.Message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/9.
 */

public class DongqiudiThread implements Runnable {
    private static Handler mHandler;
    private static HashMap<String,String> newsMap = new HashMap<>();

    public DongqiudiThread(Handler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    public void run() {
        Message msg = Message.obtain();
        if (MainActivity.mapdDongqiudi != null){
            newsMap = MainActivity.mapdDongqiudi;
            msg.what = 0;
            msg.arg1 = 2;
            msg.obj = newsMap;
        }

       else{

            try {
                Document doc = Jsoup.connect("https://www.dongqiudi.com/").get();
                Elements elements = doc.select("div#news_list h2 > a");
                for (Element element: elements
                        ) {
                    newsMap.put(element.text(), element.absUrl("href"));

                }
                msg.what = 0;
                msg.arg1 = 2;
                msg.obj = newsMap;

            } catch (IOException e) {
                e.printStackTrace();
                msg.what = 1;
            }

        }

        mHandler.sendMessage(msg);


    }
}
