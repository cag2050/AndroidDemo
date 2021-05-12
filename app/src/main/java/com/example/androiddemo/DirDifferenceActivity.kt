package com.example.androiddemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androiddemo.databinding.ActivityDirDifferenceBinding

class DirDifferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_dir_difference)
        val binding = ActivityDirDifferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)
//      getExternalFilesDir(null).toString() 1. 模拟器输出：/storage/emulated/0/Android/data/com.example.androiddemo/files ；2.
        binding.textView2.text = getExternalFilesDir(null).toString()
    }
}