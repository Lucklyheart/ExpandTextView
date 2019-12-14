package com.luckdu.expandtext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.luckdu.library.ExpandTextView;

public class MainActivity extends AppCompatActivity {

    ExpandTextView textView;

    String text = "1.由爱故生忧，由爱故生怖，若离于爱者，无忧亦无怖。2.福幡立中庭，果尔降荣幸，名姝设华筵，召我伊家饮。3.黑字已书成，水滴即可灭，心字不成书，欲拭安可得。4.侯门有娇女，空欲窥颜色，譬彼琼树花，鲜艳自高立。5.工布有少年，性如蜂在网，随我三日游，又作皈依想。6.世间事，除了生死，哪一件事不是闲事，谁的隐私不被回光返照？7.爱别离，怨憎会，撒手西归，全无是类。不过是满眼空花，一片虚幻。8.故园迢迢忆双亲，每对卿卿泪满襟。千山万水相追寻，始信卿心胜娘心。";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);

        WindowManager w = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);


        textView.initWidth(metrics.widthPixels);
        textView.setMaxLines(3);
        textView.setAppendText(true);
        textView.setCloseText(text);

//        textView.initWidth(metrics.widthPixels);
//        textView.setMaxLines(Integer.MAX_VALUE);
//        textView.setAppendText(false);
//        textView.setRes(R.mipmap.ic_launcher);
//        textView.setExpandText(text);


    }
}
