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

public class HupuThread implements Runnable {
    private static Handler mHandler;
    private static HashMap<String, String> newsMap = new HashMap<>();

    HupuThread(Handler mHandler) {
        this.mHandler = mHandler;

    }

    @Override
    public void run() {
        Message msg = Message.obtain();
        if (MainActivity.mapHupu != null) {
            newsMap = MainActivity.mapHupu;
            msg.what = 0;
            msg.arg1 = 1;
            msg.obj = newsMap;
        }
        else {
            try {
                Document doc = Jsoup.connect("https://soccer.hupu.com/").get();
                Elements elements = doc.select("h3>a");

                for (Element element : elements
                        ) {
                    newsMap.put(element.text(), element.attr("href"));

                }
                Elements elements_h2 = doc.select("h2");
                for (Element element : elements_h2
                        ) {
                    for (Element e: element.select("a")
                         ) {

                        newsMap.put(e.text(), e.attr("href"));
                    }


                }
                msg.what = 0;
                msg.arg1 = 1;
                msg.obj = newsMap;

            } catch (IOException e) {
                e.printStackTrace();
                msg.what = 1;
            }




        }

        mHandler.sendMessage(msg);
    }
}
