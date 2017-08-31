package com.jingle.soccernews;

import android.os.Handler;
import android.os.Message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/16.
 */

public class WangYiThread implements Runnable {
    private static Handler mHandler;
    private static HashMap<String,String> newsMap = new HashMap<>();
    WangYiThread(Handler mHandler){
        this.mHandler = mHandler;
    }
    @Override
    public void run() {
        Message msg = Message.obtain();
        if (MainActivity.mapWangyi!= null){
            newsMap = MainActivity.mapWangyi;
            msg.what = 0;
            msg.arg1 = 4;
            msg.obj = newsMap;
        }

       else{
            try {
                Document doc = Jsoup.connect("http://sports.163.com/world").get();
                Elements elements = doc.select("h2>a");
                for (Element element: elements
                        ) {
                    newsMap.put(element.text(), element.attr("href"));

                }
                Elements elements1 = doc.select(".top_news_ul a");
                for (Element element: elements1
                        ) {
                    newsMap.put(element.text(), element.attr("href"));

                }
                msg.what = 0;
                msg.arg1 = 4;
                msg.obj = newsMap;

            } catch (IOException e) {
                e.printStackTrace();
                msg.what = 1;
            }
        }
        mHandler.sendMessage(msg);
    }
}
