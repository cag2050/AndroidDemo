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
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSPARENT
                );
                WindowManager windowManager = getWindowManager();
                RelativeLayout mRelativeLayout = new RelativeLayout(getApplicationContext());
                mRelativeLayout.setBackgroundColor(Color.parseColor("#66000000"));
//            mRelativeLayout.setBackgroundColor(Color.BLACK)
//          十六进制透明度66，转为十进制是102
//            mRelativeLayout.background.mutate().alpha = 102
//            mRelativeLayout.addView(container)

//                Button mButton = new Button(getApplicationContext());
//                mButton.setText("测试Button");
//                mRelativeLayout.addView(mButton);

                SVGAImageView mSVGAImageView = new SVGAImageView(this);
                mRelativeLayout.addView(mSVGAImageView);
                Log.d(TAG, "mSVGAImageView");
                windowManager.addView(mRelativeLayout, mLayoutParams);
                break;
        }
    }
}