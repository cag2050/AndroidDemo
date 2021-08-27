package com.example.androiddemo.widget.customview;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class MarqueeTextView extends AppCompatTextView {
    /**
     * 滚动次数
     */
    private int marqueeNum = -1;//-1为永久循环，大于0是循环次数。`

    public void setMarqueeNum(int marqueeNum) {
        this.marqueeNum = marqueeNum;
    }

    public MarqueeTextView(Context context) {
        super(context);
        setAttr();
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttr();
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setAttr();
    }

    /**
     * 始终获取焦点
     * 跑马灯在TextView处于焦点状态的时候才会滚动
     */
    @Override
    public boolean isFocused() {
        return true;
    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (focused)
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    /*
     * Window与Window间焦点发生改变时的回调
     * */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (hasWindowFocus)
            super.onWindowFocusChanged(hasWindowFocus);
    }

    /**
     * 设置相关属性
     */
    private void setAttr() {
        this.setEllipsize(TextUtils.TruncateAt.MARQUEE);//设置跑马等效果
        this.setMarqueeRepeatLimit(marqueeNum);//设置跑马灯重复次数
        this.setSingleLine(true);//设置单行
        this.setLines(1);
    }
}