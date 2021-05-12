package com.example.androiddemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.androiddemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        旧写法
//        setContentView(R.layout.activity_main)
//        ViewBinding写法:
//        https://blog.csdn.net/guolin_blog/article/details/113089706
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.downloadImageButton.setOnClickListener {
            Log.d("MainActivity", "点击了下载图片按钮")
            val intent = Intent(this, DownloadImageJavaActivity::class.java);
            startActivity(intent)
        }

        binding.dirDifferenceButton.setOnClickListener {
            val intent = Intent(this, DirDifferenceActivity::class.java);
            startActivity(intent)
        }
    }
}