package com.deepak.projectqat.app;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Deepak Kumar on 14-03-2018.
 * utility
 */

public class Utility {

    public static void show(Context context, String data){
        Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
    }

    public static void setConcertTypeFace(Context context,TextView textView){
        Typeface concert_font = Typeface.createFromAsset(context.getAssets(), "fonts/Concert_one_regular.ttf");
        textView.setTypeface(concert_font);
    }

    public static Map<Integer, String> sortHashMap(HashMap<Integer, String> hashMap){
        Map<Integer, String> sorted = new TreeMap<Integer, String>(hashMap);
        return sorted;
    }

    public static Map<Integer, String> sortMap(Map<Integer, String> map){
        Map<Integer, String> sorted = new TreeMap<Integer, String>(map);
        return sorted;
    }
}
