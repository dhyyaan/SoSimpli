package com.think360.sosimpli.widgets.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class SourceSansProRegularTextView extends android.support.v7.widget.AppCompatTextView {

    public SourceSansProRegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SourceSansProRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SourceSansProRegularTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansProRegular.ttf");
            setTypeface(tf);

    }
}