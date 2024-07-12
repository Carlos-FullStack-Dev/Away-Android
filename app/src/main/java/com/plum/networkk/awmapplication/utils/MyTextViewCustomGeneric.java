package com.plum.networkk.awmapplication.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.plum.networkk.awmapplication.R;


public class MyTextViewCustomGeneric extends androidx.appcompat.widget.AppCompatTextView {

    /*public MyTextViewCustomGeneric(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        String fontStyleURL;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MyTextViewCus, 0, 0);
        try {
            fontStyleURL = a.getString(R.styleable.MyTextViewCus_fontStyleCus);
        } finally {
            a.recycle();
        }
        init(context, 1);
    }*/

    public MyTextViewCustomGeneric(Context context, AttributeSet attrs) {
        super(context, attrs);

        int fontType;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MyTextViewCustomGeneric, 0, 0);
        try {
//            fontStyleURL = a.getString(R.styleable.MyTextViewCus_fontStyleCus);
            fontType = a.getInteger(R.styleable.MyTextViewCustomGeneric_fontType, 1);
        } finally {
            a.recycle();
        }
        init(context, fontType);

    }

    public MyTextViewCustomGeneric(Context context) {
        super(context);
        init(context, 1);
    }

    private void init(Context context, int fontType) {
//        if (fontType != null) {
        String fontName = "opensans.regular.ttf";
        if (fontType == 1) {
            fontName = "opensans.regular.ttf";
        } else if (fontType == 2) {
            fontName = "open-sans.bold.ttf";
        } else if (fontType == 3) {
            fontName = "opensans.light.ttf";
        } else if (fontType == 4) {
            fontName = "open-sans.light-italic.ttf";
        } else if (fontType == 5) {
            fontName = "opensans.semibold.ttf";
        } else if (fontType == 6) {
            fontName = "OpenSans-SemiBoldItalic.ttf";
        }


        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/" + fontName);
        setTypeface(tf);


//        } else {
//            Typeface tf = Typeface.createFromAsset(context.getAssets(),
//                    "fonts/opensans.regular.ttf");
//            setTypeface(tf);
//        }
    }
}