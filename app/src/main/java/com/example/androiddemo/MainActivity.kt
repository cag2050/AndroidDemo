package com.example.androiddemo

import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
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

        binding.wechatShareButton.setOnClickListener {
            val intent = Intent(this, ShareToWechatActivity::class.java);
            startActivity(intent)
        }

        binding.refresh.setOnClickListener {
            val intent = Intent(this, MediaScannerConnectionActivity::class.java);
            startActivity(intent)
        }

        binding.layoutParams.setOnClickListener {
            val backgroundLP = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.LAST_APPLICATION_WINDOW,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT
            )
            val windowManager = windowManager
            var backgroundRL = RelativeLayout(applicationContext)
//            backgroundRL.setBackgroundColor(Color.parseColor("#66000000"))
            backgroundRL.setBackgroundColor(Color.BLACK)
//          十六进制透明度66，转为十进制是102
            backgroundRL.background.mutate().alpha = 102
//            backgroundRL.addView(container)
            windowManager.addView(backgroundRL, backgroundLP)
        }
    }
}