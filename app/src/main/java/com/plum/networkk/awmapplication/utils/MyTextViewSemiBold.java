package com.plum.networkk.awmapplication.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class MyTextViewSemiBold extends androidx.appcompat.widget.AppCompatTextView {

    public MyTextViewSemiBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyTextViewSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTextViewSemiBold(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/opensans.semibold.ttf");
        setTypeface(tf);
    }
}