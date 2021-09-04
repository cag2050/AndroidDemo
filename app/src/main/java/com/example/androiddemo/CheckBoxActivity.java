package com.example.androiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class CheckBoxActivity extends AppCompatActivity {
    private static final String TAG = "CheckBoxActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);

        CheckBox checkBox = findViewById(R.id.checkbox);
        boolean isSelect = false;
        Boolean canClick = false;
        checkBox.setChecked(isSelect);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkedBefore = ((CheckBox) v).isChecked();
                Log.d(TAG, "before modify, checkedBefore:" + checkedBefore);

                if (isSelect == false && canClick == false) {
                    ((CheckBox) v).setChecked(false);
                    Toast.makeText(CheckBoxActivity.this, getString(R.string.selected_picture_exceed_limit), Toast.LENGTH_SHORT).show();
                }

                boolean checkedAfter = ((CheckBox) v).isChecked();
                Log.d(TAG, "after modify,checkedAfter:" + checkedAfter);
            }
        });
    }
}