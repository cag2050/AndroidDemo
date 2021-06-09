package com.example.androiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.opensource.svgaplayer.SVGAImageView;

public class SVGAPlayActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SVGAPlayActivity";
    private Button mBtnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svga_play);
        initView();
    }

    private void initView() {
        mBtnPlay = findViewById(R.id.button_play);
//        和下面的onClick配合使用
        mBtnPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG,"click");
        switch (v.getId()) {
            case R.id.button_play:
                Log.d(TAG, "点击了");
                WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.LAST_APPLICATION_WINDOW,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                        PixelFormat.TRANSPARENT
                );
//                设置 WindowManager.LayoutParams 的透明度：配置 WindowManager.LayoutParams.FLAG_DIM_BEHIND 和 dimAmount
                mLayoutParams.dimAmount = 0.6f;
                WindowManager windowManager = getWindowManager();

                RelativeLayout mRelativeLayout = new RelativeLayout(getApplicationContext());
                Button mButton = new Button(getApplicationContext());
                mButton.setText("测试Button");
                mRelativeLayout.addView(mButton);

                Log.d(TAG, "mSVGAImageView");
                windowManager.addView(mRelativeLayout, mLayoutParams);
                break;
        }
    }
}