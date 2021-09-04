package com.example.androiddemo

import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.androiddemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

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

        binding.wechatShareButton.setOnClickListener {
            val intent = Intent(this, ShareToWechatActivity::class.java);
            startActivity(intent)
        }

        binding.refresh.setOnClickListener {
            val intent = Intent(this, MediaScannerConnectionActivity::class.java);
            startActivity(intent)
        }

        binding.svga.setOnClickListener {
            Log.d(TAG, "svga click")
            val intent = Intent(this, SVGAPlayActivity::class.java);
            startActivity(intent)
        }

        binding.MaterialButton.setOnClickListener {
            val intent = Intent(this, MaterialButtonActivity::class.java);
            startActivity(intent)
        }

        binding.textImageCenter.setOnClickListener {
            val intent = Intent(this, TextViewImageViewActivity::class.java);
            startActivity(intent)
        }

        binding.locationManager.setOnClickListener {
            val intent = Intent(this, LocationManagerActivity::class.java);
            startActivity(intent)
        }

        binding.textViewMarquee.setOnClickListener {
            val intent = Intent(this, TextViewMarqueeActivity::class.java);
            startActivity(intent)
        }
        binding.checkBoxDemo.setOnClickListener {
            val intent = Intent(this, CheckBoxActivity::class.java);
            startActivity(intent)
        }
    }
}