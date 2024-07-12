package com.plum.networkk.awmapplication.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class MyTextViewSemiBoldItalic extends androidx.appcompat.widget.AppCompatTextView {

    public MyTextViewSemiBoldItalic(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyTextViewSemiBoldItalic(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTextViewSemiBoldItalic(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/OpenSans-SemiBoldItalic.ttf");
        setTypeface(tf);
    }
}