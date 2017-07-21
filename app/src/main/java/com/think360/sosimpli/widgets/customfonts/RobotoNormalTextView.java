package com.think360.sosimpli.widgets.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class RobotoNormalTextView extends android.support.v7.widget.AppCompatTextView {

    public RobotoNormalTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RobotoNormalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoNormalTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/robotoregular.ttf");
            setTypeface(tf);

    }
}