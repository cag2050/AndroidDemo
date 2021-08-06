package com.example.androiddemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.constant.PermissionConstants;
import com.example.androiddemo.util.PermissionHelper;

import java.util.List;

// https://blog.csdn.net/Double2hao/article/details/49846953
public class LocationManagerActivity extends AppCompatActivity {
    private static final String TAG = "LocationManagerActivity";
    private TextView positionTextView;

    private LocationManager locationManager;

    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_manager);

        // kotlin
//        PermissionHelper(this).addSuccessListener { permissionType: String? ->
//        }.addFailListener { permissionType: String?, permissionStr: String?, deny: Int ->
//        }.requestPermission(PermissionConstants.PHONE, PermissionConstants.STORAGE, PermissionConstants.LOCATION)

        Log.d(TAG, "请求权限");
        // 请求权限后，再进入页面，可以看到经度和纬度
        new PermissionHelper(this)
                .addSuccessListener(new PermissionHelper.PermissionSuccessListener() {
                    @Override
                    public void play(String permissionType) {
                    }
                })
                .addFailListener(new PermissionHelper.PermissionFailListener() {
                    @Override
                    public void deny(String permissionType, String permissionStr, int deny) {
                    }
                })
                .requestPermission(PermissionConstants.LOCATION);

        positionTextView = (TextView) findViewById(R.id.position_text_view);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //获取所有可用的位置提供器
        //如果GPS可以用就用GPS，GPS不能用则用网络
        //都不能用的情况下弹出Toast提示用户
        List<String> providerList = locationManager.getProviders(true);
        Log.d(TAG, providerList.toString());
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "No location provider to use",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //使用getLastKnownLocation就可以获取到记录当前位置信息的Location对象了
        //并且用showLocation()显示当前设备的位置信息
        //requestLocationUpdates用于设置位置监听器
        //此处监听器的时间间隔为5秒，距离间隔是5米
        //也就是说每隔5秒或者每移动5米，locationListener中会更新一下位置信息

        // 没有权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        // 有权限
        } else {
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                showLocation(location);
            }
            locationManager.requestLocationUpdates(provider, 5000, 5,
                    locationListener);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            // 关闭程序时将监听器移除
            locationManager.removeUpdates(locationListener);
        }
    }

    //locationListener中其他3个方法新手不太用得到，笔者在此也不多说了，有兴趣的可以自己去了解一下
    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {
            // 更新当前设备的位置信息
            showLocation(location);
        }
    };

    //显示经纬度信息
    private void showLocation(final Location location) {
        String currentPosition = "latitude is " + location.getLatitude() + "\n" + "longitude is "
                + location.getLongitude();
        positionTextView.setText(currentPosition);
    }
}