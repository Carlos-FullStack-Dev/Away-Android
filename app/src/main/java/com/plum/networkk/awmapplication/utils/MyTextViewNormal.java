package com.plum.networkk.awmapplication.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class MyTextViewNormal extends androidx.appcompat.widget.AppCompatTextView {

    public MyTextViewNormal(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyTextViewNormal(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTextViewNormal(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/opensans.regular.ttf");
        setTypeface(tf);
    }
}