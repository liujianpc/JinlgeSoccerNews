package com.jingle.soccernews;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/5/23.
 *
 */

/**
 * 关闭BottomNavigationView的漂移动画效果
 * 使用Java的反射机制
 */
public class BottomNavigationViewHelper {
    public static void disableShiftMode(BottomNavigationView view){
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try{
            Field shiftModeField = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftModeField.setAccessible(true);
            shiftModeField.setBoolean(menuView,false);
            shiftModeField.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }
        }catch (NoSuchFieldException e) {
            Log.e("exception","NoSuchFIeldException");
        } catch (IllegalAccessException e) {
            Log.e("exception","IllegalAccessException");
        }
        {

        }
    }
}
