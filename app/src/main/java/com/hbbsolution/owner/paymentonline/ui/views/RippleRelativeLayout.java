package com.hbbsolution.owner.paymentonline.ui.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.rey.material.widget.RippleManager;

public class RippleRelativeLayout extends RelativeLayout {
    private RippleManager mRippleManager;

    public RippleRelativeLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public RippleRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public RippleRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public RippleRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        applyStyle(context, attrs, defStyleAttr, defStyleRes);
    }

    public void applyStyle(int resId) {
        applyStyle(getContext(), null, 0, resId);
    }

    private void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        getRippleManager().onCreate(this, context, attrs, defStyleAttr, defStyleRes);
    }



    protected RippleManager getRippleManager() {
        if (mRippleManager == null) {
            synchronized (RippleManager.class) {
                if (mRippleManager == null)
                    mRippleManager = new RippleManager();
            }
        }

        return mRippleManager;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        RippleManager rippleManager = getRippleManager();
        if (l == rippleManager)
            super.setOnClickListener(l);
        else {
            rippleManager.setOnClickListener(l);
            setOnClickListener(rippleManager);
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        return getRippleManager().onTouchEvent(this, event) || result;
    }
}
