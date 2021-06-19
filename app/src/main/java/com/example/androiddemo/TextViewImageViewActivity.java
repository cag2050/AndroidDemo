package com.example.androiddemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class TextViewImageViewActivity extends AppCompatActivity {
    RelativeLayout mRelativeLayout;
    AppCompatImageView mAppCompatImageView;
    AppCompatTextView mAppCompatTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_image_view);

        init();
    }

    private void init() {
        mRelativeLayout = findViewById(R.id.RelativeLayout01);
        mAppCompatImageView = findViewById(R.id.image1);
        mAppCompatTextView = findViewById(R.id.text1);

        // android:background="@drawable/common_shape_pay_dialog_btn_bg"
        mRelativeLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.common_shape_pay_dialog_btn_bg));
        mAppCompatImageView.setVisibility(View.VISIBLE);

    }
}