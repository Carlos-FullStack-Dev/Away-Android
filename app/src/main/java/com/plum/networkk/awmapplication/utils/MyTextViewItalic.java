package com.plum.networkk.awmapplication.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class MyTextViewItalic extends androidx.appcompat.widget.AppCompatTextView {

    public MyTextViewItalic(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyTextViewItalic(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTextViewItalic(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/open-sans.light-italic.ttf");
        setTypeface(tf);
    }
}