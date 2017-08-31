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

public class ZhibobaThread implements Runnable {
    private static Handler mHandler;
    private static HashMap<String,String> newsMap = new HashMap<>();

    public ZhibobaThread(Handler mHandler) {
        this.mHandler = mHandler;

    }

    @Override
    public void run() {
        Message msg = Message.obtain();
        if (MainActivity.mapZhiboba != null){
            newsMap = MainActivity.mapZhiboba;
            msg.what = 0;
            msg.arg1 = 3;
            msg.obj = newsMap;
        }
       else{
            try {
                Document doc = Jsoup.connect("https://www.zhibo8.cc/").get();
                Element box = doc.select(".bbs_container ul").first();
                Elements elements = box.select("a");
                for (Element element: elements
                        ) {
                    newsMap.put(element.text(), element.attr("href"));

                }

                msg.what = 0;
                msg.arg1 = 3;
                msg.obj = newsMap;
            } catch (IOException e) {
                e.printStackTrace();
                msg.what = 1;
            }
        }
        mHandler.sendMessage(msg);

    }
}
