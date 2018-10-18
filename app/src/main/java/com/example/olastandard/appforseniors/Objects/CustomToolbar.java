package com.example.olastandard.appforseniors.Objects;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import com.example.olastandard.appforseniors.R;

public class CustomToolbar extends Toolbar {
    TypedArray typedArray;

    public CustomToolbar(Context context) {
        super(context);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomToolbar);

    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}