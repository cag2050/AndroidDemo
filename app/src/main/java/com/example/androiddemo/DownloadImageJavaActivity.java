package com.example.androiddemo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// 参考：https://www.jianshu.com/p/eea5cc5ab9de
public class DownloadImageJavaActivity extends AppCompatActivity implements View.OnClickListener {
//    Context context = null;

    private String URLPATH = "https://ww4.sinaimg.cn/large/610dc034gw1fafmi73pomj20u00u0abr.jpg";
    private Bitmap mBitmap;
    private ProgressDialog mProgressDialog;
    private String mFileName = "test.jpg";
    private File file;
    private File dirfile;
    private Button mBtnLoading;
    private Button mBtnSaveimage;
    private Button mBtnDelete;
    private ImageView mImage;
    private Button mRxjava;
    private ImageView mImage2;
    private Button mBtnRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image_java);
        initView();
    }

    private void initView() {
        mBtnLoading = (Button) findViewById(R.id.btn_loading);
        mBtnSaveimage = (Button) findViewById(R.id.btn_saveimage);
        mBtnDelete = (Button) findViewById(R.id.btn_delete);

        mBtnLoading.setOnClickListener(this);
        mBtnSaveimage.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mImage = (ImageView) findViewById(R.id.image);

        mRxjava = (Button) findViewById(R.id.rxjava);
        mRxjava.setOnClickListener(this);
        mImage2 = (ImageView) findViewById(R.id.image2);

        mBtnRead = (Button) findViewById(R.id.btn_read);
        mBtnRead.setOnClickListener(this);
        mBtnRead.setClickable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_loading:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LoadImage2();
                    }
                }).start();
                mBtnRead.setClickable(true);
                break;
            case R.id.btn_saveimage:
                mProgressDialog = ProgressDialog.show(this, "保存图片", "图片正在保存中，请稍等...", true);
                SaveImage();
                connectHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = 2;
                        connectHandler.sendMessage(message);
                    }
                }, 1000);
                mBtnRead.setClickable(true);
                break;
            case R.id.btn_delete:
                DeleteFile(dirfile);
                mBitmap = null;
                Message message = new Message();
                message.what = 3;
                connectHandler.sendMessage(message);
                mBtnRead.setClickable(true);
                break;
            case R.id.rxjava:
//                startActivity(new Intent(this, RxActivity.class));
                mBtnRead.setClickable(true);
                break;
            case R.id.btn_read:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (file.exists()) {
                            ReadFromSDCard(file);
                            Message message = new Message();
                            message.what = 4;
                            connectHandler.sendMessage(message);
                        } else {
                            Toast.makeText(DownloadImageJavaActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
                            Message message = new Message();
                            message.what = 5;
                            connectHandler.sendMessage(message);
                        }
                    }
                }).start();
                break;
        }
    }

    /**
     * 下载图片从输入里面
     */
    private void LoadImage2() {
        try {
            URL url = new URL(URLPATH);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();
            mBitmap = BitmapFactory.decodeStream(inputStream);
            Message message = new Message();
            message.what = 1;
            connectHandler.sendMessage(message);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 主线程更新UI
     */
    private Handler connectHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mImage.setImageBitmap(mBitmap);
                    break;
                case 2:
                    mProgressDialog.dismiss();
                    Toast.makeText(DownloadImageJavaActivity.this, "图片保存完成", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    mBitmap = null;
                    mImage.setImageBitmap(mBitmap);
                    mImage2.setImageBitmap(mBitmap);
                    break;
                case 4:
                    mImage2.setImageBitmap(mBitmap);
                    break;
                case 5:
                    Toast.makeText(DownloadImageJavaActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    };

    /**
     * 保存图片到sd卡指定目录
     */
    @SuppressLint("LongLogTag")
    private void SaveImage() {
        //创建文件 在是的sd卡外部存储
        String FILEPATH = this.getExternalFilesDir(null) + "/share/";
        Log.d("DownloadImageJavaActivity", "保存文件路径：" + FILEPATH);
        dirfile = new File(FILEPATH);
        if (!dirfile.exists()) {
            dirfile.mkdir();
        }

        //指定保存文件路径
        file = new File(FILEPATH + mFileName);
        //从系统保存需要用到输出流
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            //bitmap进行解码
            if (mBitmap == null) {
                Toast.makeText(this, "Kong", Toast.LENGTH_SHORT).show();
                mProgressDialog.cancel();
            } else {
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.hashCode();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除sd卡下的图片
     */
    private void DeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
           /* for (File f : childFile) {
                DeleteFile(f);
            }
            file.delete();*/
            for (int i = 0; i < childFile.length; i++) {
                //file下面的子文件删除
                DeleteFile(childFile[i]);
            }
            //删除父文件夹
            file.delete();
        }
    }

    /**
     * 从sd卡里面读取文件
     *
     * @param file
     */
    private void ReadFromSDCard(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            // mBitmap.recycle();
            mBitmap = BitmapFactory.decodeStream(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

