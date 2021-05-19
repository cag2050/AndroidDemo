package com.example.androiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.example.androiddemo.util.Uri2PathUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MediaScannerConnectionActivity extends AppCompatActivity {
    private static final String TAG = "MediaScannerConnection";
    private static final String netImgPath = "https://porsche-prod.oss-cn-beijing.aliyuncs.com/share/m_junmanshangmao_com.png?x-oss-process=image/watermark,text_YTlmNDk3ZTk,type_ZmFuZ3poZW5naGVpdGk,color_FFFFFF,size_36,g_se,x_181,y_102";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_scanner_connection);
        execute(netImgPath);
    }

    public String execute(String net_img_path) {
        Log.d(TAG, "execute：" + net_img_path);
        if (!TextUtils.isEmpty(net_img_path)) {
            if (!TextUtils.isEmpty(net_img_path)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LoadImage(net_img_path);
                        Log.d(TAG, "LoadImage");
                    }
                }).start();
            }
        }
        return null;
    }

    /**
     * 下载网络图片
     */
    private void LoadImage(String netImgPath) {
        try {
            URL url = new URL(netImgPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();
            String displayName = System.currentTimeMillis() + ".png";
            String mimeType = "image/png";
            writeInputStreamToAlbum(inputStream, displayName, mimeType);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeInputStreamToAlbum(InputStream inputStream, String displayName, String mimeType) {
        Log.d(TAG, "writeInputStreamToAlbum() start");
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
        } else {
            values.put(MediaStore.MediaColumns.DATA, Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_DCIM + "/" + displayName);
        }
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Log.d(TAG, "uri.toString(): " + uri.toString());
        Log.d(TAG, "uri.toString(): " + uri.toString());
        if (uri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = contentResolver.openOutputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (outputStream != null) {
                BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                Log.d(TAG, "bos.toString(): " + bos.toString());
                byte[] buffer = new byte[1024];
                int bytes = 0;
                try {
                    bytes = bis.read(buffer);
                    while (bytes >= 0) {
                        bos.write(buffer, 0 , bytes);
                        bos.flush();
                        bytes = bis.read(buffer);
                    }
                    bos.close();
//                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
//                        //把图片保存后声明这个广播事件通知系统相册有新图片到来
//                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                        intent.setData(uri);
//                        getApplicationContext().sendBroadcast(intent);
//                        Log.d(TAG, "Build.VERSION.SDK_INT < Build.VERSION_CODES.Q uri: " + uri);
//                    }
                    // uri转为绝对路径
                    String realPath = Uri2PathUtil.getRealPathFromUri(getApplicationContext(), uri);
                    Log.d(TAG, "realPath: " + realPath);
                    Log.d(TAG, "uri.getPath(): " + uri.getPath());
                    Log.d(TAG, "uri.getEncodedPath(): " + uri.getEncodedPath());
                    MediaScannerConnection.scanFile(this,
                            new String[] { realPath }, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.d(TAG, "Scanned " + path);
                                    Log.d(TAG, "-> uri=" + uri);
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}