package com.example.androiddemo.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.example.androiddemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermissionHelper {
    Activity mActivity;
    private int mPermissionDenied = -1;
    private int mPermissionGranted = -1;
    List<String> mPermissions = new ArrayList<String>();
    int mCurrent = 0;
    private PermissionSuccessListener mGrantedListener;
    private PermissionFailListener mDenyListener;

    public PermissionHelper(Activity activity) {
        mActivity = activity;
        //        initPermissionName();
    }

    public PermissionHelper requestPermission(@PermissionConstants.Permission String... permissions) {
        mPermissions.addAll(Arrays.asList(permissions));
        request();
        return this;
    }

    private void request() {
        if (mCurrent >= mPermissions.size()) {
            if (mGrantedListener != null) {
                if (mPermissionGranted != -1 && mPermissionDenied == -1) {
                    mGrantedListener.play(mPermissions.get(mPermissionGranted));
                }
            }
            if (mDenyListener != null) {
                if (mPermissionDenied != -1) {
//                    mDenyListener.deny(mPermissions.get(mPermissionDenied), getSimplePermissionText(mPermissionDenied), mPermissionDenied);
                }
            }
            return;
        }
        PermissionUtils.permission(mPermissions.get(mCurrent)).callback(new PermissionUtils.FullCallback() {
            @Override
            public void onGranted(List<String> permissionsGranted) {
                mPermissionGranted = mCurrent++;
                request();
            }

            @Override
            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                if (permissionsDenied.size() == permissionsDeniedForever.size()) {
                    if (mActivity != null && !mActivity.isFinishing()) {
//                        TipsDialog dialog = TipsDialog.BUILDER()
//                                .title(mActivity.getString(R.string.common_permission_request_title))
//                                .message(getPermissionText(mCurrent))
//                                .okMessage(mActivity.getString(R.string.common_text_confirm))
//                                .setCancelable(true)
//                                .onOkListener(new DialogBtnListener<TipsDialog>() {
//                                    @Override
//                                    public boolean onClick(@NonNull TipsDialog dialog, View view) {
//                                        jumpSettingActivity(dialog);
//                                        return false;
//                                    }
//                                })
//                                .build(mActivity);
//                        dialog.show();
//                        if (mDenyListener != null) {
//                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                                @Override
//                                public void onDismiss(DialogInterface dialog) {
//                                    mDenyListener.deny(mPermissions.get(mCurrent), getSimplePermissionText(mCurrent), mCurrent);
//                                }
//                            });
//                        }
                    }
                } else {
                    mPermissionDenied = mCurrent++;
                    request();
                }
            }
        }).rationale(shouldRequest -> {
            shouldRequest.again(true);
        }).request();
    }

//    public String getPermissionText(@PermissionConstants.Permission String... permissions){
//        StringBuilder sb = new StringBuilder();
//        for (String permission : permissions) {
//            switch (permission) {
//                case PermissionConstants.CALENDAR:
//                    sb.append(mActivity.getString(R.string.common_text_calendar)).append("/");
//                    break;
//                case PermissionConstants.CAMERA:
//                    sb.append(mActivity.getString(R.string.common_text_camera)).append("/");
//                    break;
//                case PermissionConstants.CONTACTS:
//                    sb.append(mActivity.getString(R.string.common_text_contacts)).append("/");
//                    break;
//                case PermissionConstants.LOCATION:
//                    sb.append(mActivity.getString(R.string.common_text_location)).append("/");
//                    break;
//                case PermissionConstants.MICROPHONE:
//                    sb.append(mActivity.getString(R.string.common_text_microphone)).append("/");
//                    break;
//                case PermissionConstants.PHONE:
//                    sb.append(mActivity.getString(R.string.common_text_phone)).append("/");
//                    break;
//                case PermissionConstants.SENSORS:
//                    sb.append(mActivity.getString(R.string.common_text_sensors)).append("/");
//                    break;
//                case PermissionConstants.SMS:
//                    sb.append(mActivity.getString(R.string.common_text_sms)).append("/");
//                    break;
//                case PermissionConstants.STORAGE:
//                    sb.append(mActivity.getString(R.string.common_text_storage)).append("/");
//                    break;
//                default:
//                    break;
//            }
//        }
//        if (sb.toString().endsWith("/")) {
//            sb = new StringBuilder(sb.subSequence(0, sb.lastIndexOf("/")));
//        }
//        sb.append(" ");
//        return String.format(mActivity.getString(R.string.common_permission_request_content),
//                sb.append(mActivity.getString(R.string.common_text_permission)).toString());
//    }

//    private String getPermissionText(int current) {
//        StringBuilder sb = new StringBuilder();
//        while (current < mPermissions.size()) {
//            switch (mPermissions.get(current)) {
//                case PermissionConstants.CALENDAR:
//                    sb.append(mActivity.getString(R.string.common_text_calendar)).append("/");
//                    break;
//                case PermissionConstants.CAMERA:
//                    sb.append(mActivity.getString(R.string.common_text_camera)).append("/");
//                    break;
//                case PermissionConstants.CONTACTS:
//                    sb.append(mActivity.getString(R.string.common_text_contacts)).append("/");
//                    break;
//                case PermissionConstants.LOCATION:
//                    sb.append(mActivity.getString(R.string.common_text_location)).append("/");
//                    break;
//                case PermissionConstants.MICROPHONE:
//                    sb.append(mActivity.getString(R.string.common_text_microphone)).append("/");
//                    break;
//                case PermissionConstants.PHONE:
//                    sb.append(mActivity.getString(R.string.common_text_phone)).append("/");
//                    break;
//                case PermissionConstants.SENSORS:
//                    sb.append(mActivity.getString(R.string.common_text_sensors)).append("/");
//                    break;
//                case PermissionConstants.SMS:
//                    sb.append(mActivity.getString(R.string.common_text_sms)).append("/");
//                    break;
//                case PermissionConstants.STORAGE:
//                    sb.append(mActivity.getString(R.string.common_text_storage)).append("/");
//                    break;
//                default:
//                    break;
//            }
//            current++;
//        }
//        if (sb.toString().endsWith("/")) {
//            sb = new StringBuilder(sb.subSequence(0, sb.lastIndexOf("/")));
//        }
//        sb.append(" ");
//        return String.format(mActivity.getString(R.string.common_permission_request_content),
//                sb.append(mActivity.getString(R.string.common_text_permission)).toString());
//    }

//    private String getSimplePermissionText(int current) {
//        String str = "";
//        switch (mPermissions.get(current)) {
//            case PermissionConstants.CALENDAR:
//                str = mActivity.getString(R.string.common_permission_calendar);
//                break;
//            case PermissionConstants.CAMERA:
//                str = mActivity.getString(R.string.common_permission_camera);
//                break;
//            case PermissionConstants.CONTACTS:
//                str = mActivity.getString(R.string.common_permission_contacts);
//                break;
//            case PermissionConstants.LOCATION:
//                str = mActivity.getString(R.string.common_permission_location);
//                break;
//            case PermissionConstants.MICROPHONE:
//                str = mActivity.getString(R.string.common_permission_microphone);
//                break;
//            case PermissionConstants.PHONE:
//                str = mActivity.getString(R.string.common_permission_phone);
//                break;
//            case PermissionConstants.SENSORS:
//                str = mActivity.getString(R.string.common_permission_sensors);
//                break;
//            case PermissionConstants.SMS:
//                str = mActivity.getString(R.string.common_permission_sms);
//                break;
//            case PermissionConstants.STORAGE:
//                str = mActivity.getString(R.string.common_permission_storage);
//                break;
//            default:
//                break;
//        }
//        return str;
//    }

    /*public String getPermissionDenied(String permission) {
        if (TextUtils.equals(permission, CALENDAR)) {
            return mActivity.getString(R.string.common_permission_calendar_denied);
        } else if (TextUtils.equals(permission, CAMERA)) {
            return mActivity.getString(R.string.common_permission_camera_denied);
        } else if (TextUtils.equals(permission, CONTACTS)) {
            return mActivity.getString(R.string.common_permission_contacts_denied);
        } else if (TextUtils.equals(permission, LOCATION)) {
            return mActivity.getString(R.string.common_permission_location_denied);
        } else if (TextUtils.equals(permission, MICROPHONE)) {
            return mActivity.getString(R.string.common_permission_microphone_denied);
        } else if (TextUtils.equals(permission, PHONE)) {
            return mActivity.getString(R.string.common_permission_phone_denied);
        } else if (TextUtils.equals(permission, SENSORS)) {
            return mActivity.getString(R.string.common_permission_sensors_denied);
        } else if (TextUtils.equals(permission, SMS)) {
            return mActivity.getString(R.string.common_permission_sms_denied);
        } else if (TextUtils.equals(permission, STORAGE)) {
            return mActivity.getString(R.string.common_permission_storage_denied);
        } else {
            return permission;
        }
    }*/

    /*public String getPermissionDesc(String permission) {
        if (TextUtils.equals(permission, CALENDAR)) {
            return mActivity.getString(R.string.common_permission_calendar_desc);
        } else if (TextUtils.equals(permission, CAMERA)) {
            return mActivity.getString(R.string.common_permission_camera_desc);
        } else if (TextUtils.equals(permission, CONTACTS)) {
            return mActivity.getString(R.string.common_permission_contacts_desc);
        } else if (TextUtils.equals(permission, LOCATION)) {
            return mActivity.getString(R.string.common_permission_location_desc);
        } else if (TextUtils.equals(permission, MICROPHONE)) {
            return mActivity.getString(R.string.common_permission_microphone_desc);
        } else if (TextUtils.equals(permission, PHONE)) {
            return mActivity.getString(R.string.common_permission_phone_desc);
        } else if (TextUtils.equals(permission, SENSORS)) {
            return mActivity.getString(R.string.common_permission_sensors_desc);
        } else if (TextUtils.equals(permission, SMS)) {
            return mActivity.getString(R.string.common_permission_sms_desc);
        } else if (TextUtils.equals(permission, STORAGE)) {
            return mActivity.getString(R.string.common_permission_storage_desc);
        } else {
            return permission;
        }
    }*/

    /*public String getPermissionRequest(String permission) {
        if (TextUtils.equals(permission, CALENDAR)) {
            return mActivity.getString(R.string.common_permission_calendar_request);
        } else if (TextUtils.equals(permission, CAMERA)) {
            return mActivity.getString(R.string.common_permission_camera_request);
        } else if (TextUtils.equals(permission, CONTACTS)) {
            return mActivity.getString(R.string.common_permission_contacts_request);
        } else if (TextUtils.equals(permission, LOCATION)) {
            return mActivity.getString(R.string.common_permission_location_request);
        } else if (TextUtils.equals(permission, MICROPHONE)) {
            return mActivity.getString(R.string.common_permission_microphone_request);
        } else if (TextUtils.equals(permission, PHONE)) {
            return mActivity.getString(R.string.common_permission_phone_request);
        } else if (TextUtils.equals(permission, SENSORS)) {
            return mActivity.getString(R.string.common_permission_sensors_request);
        } else if (TextUtils.equals(permission, SMS)) {
            return mActivity.getString(R.string.common_permission_sms_request);
        } else if (TextUtils.equals(permission, STORAGE)) {
            return mActivity.getString(R.string.common_permission_storage_request);
        } else {
            return permission;
        }
    }*/

    /*private void initPermissionName() {
        CALENDAR = mActivity.getString(R.string.common_permission_calendar);
        CAMERA = mActivity.getString(R.string.common_permission_camera);
        CONTACTS = mActivity.getString(R.string.common_permission_contacts);
        LOCATION = mActivity.getString(R.string.common_permission_location);
        MICROPHONE = mActivity.getString(R.string.common_permission_microphone);
        PHONE = mActivity.getString(R.string.common_permission_phone);
        SENSORS = mActivity.getString(R.string.common_permission_sensors);
        SMS = mActivity.getString(R.string.common_permission_sms);
        STORAGE = mActivity.getString(R.string.common_permission_storage);
    }*/

//    public void jumpSettingActivity(TipsDialog dialog) {
//        try {
//            goToSetting(mActivity);
//        } catch (Exception e) {
//            Intent intent = new Intent();
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
//            intent.setData(uri);
//            mActivity.startActivity(intent);
//        }
//        dialog.dismiss();
//    }

    public interface PermissionSuccessListener {
        void play(String permissionType);
    }

    public interface PermissionFailListener {
        void deny(String permissionType, String permissionStr, int deny);
    }

    public PermissionHelper addSuccessListener(PermissionSuccessListener listener) {
        mGrantedListener = listener;
        return this;
    }

    public PermissionHelper addFailListener(PermissionFailListener listener) {
        mDenyListener = listener;
        return this;
    }

    private final String MANUFACTURER_HUAWEI = "Huawei";//华为
    private final String MANUFACTURER_MEIZU = "Meizu";//魅族
    private final String MANUFACTURER_XIAOMI = "Xiaomi";//小米
    private final String MANUFACTURER_SONY = "Sony";//索尼
    private final String MANUFACTURER_OPPO = "OPPO";
    private final String MANUFACTURER_LG = "LG";
    private final String MANUFACTURER_VIVO = "vivo";
    private final String MANUFACTURER_SAMSUNG = "samsung";//三星
    private final String MANUFACTURER_LETV = "Letv";//乐视
    private final String MANUFACTURER_ZTE = "ZTE";//中兴
    private final String MANUFACTURER_YULONG = "YuLong";//酷派
    private final String MANUFACTURER_LENOVO = "LENOVO";//联想

    /**
     * 此函数可以自己定义
     *
     * @param activity
     */
    public void goToSetting(Activity activity) {
        try {
            switch (DeviceUtils.getManufacturer()) {
                case MANUFACTURER_HUAWEI:
                    huawei(activity);
                    break;
                case MANUFACTURER_MEIZU:
                    meizu(activity);
                    break;
                case MANUFACTURER_XIAOMI:
                    if("Redmi Note 6 Pro".equals(Build.MODEL)){
                        defaultSetting(activity);
                    }else{
                        xiaomi(activity);
                    }
                    break;
                case MANUFACTURER_SONY:
                    sony(activity);
                    break;
                case MANUFACTURER_OPPO:
                    oppo(activity);
                    break;
                case MANUFACTURER_LG:
                    lg(activity);
                    break;
                case MANUFACTURER_LETV:
                    letv(activity);
                    break;
                default:
                    defaultSetting(activity);
                    break;
            }
        } catch (Exception e) {
            defaultSetting(activity);
        }
    }

    private void huawei(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", "vchat.faceme");
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    private void meizu(Activity activity) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", activity.getPackageName());
        activity.startActivity(intent);
    }

    private void xiaomi(Activity activity) {
        try {
            // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", activity.getPackageName());
            activity.startActivity(localIntent);
        } catch (Exception e) {
            try {
                // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", activity.getPackageName());
                activity.startActivity(localIntent);
            } catch (Exception e1) {
                // 否则跳转到应用详情
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
            }
        }
    }

    private void sony(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    private void oppo(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    private void lg(Activity activity) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    private void letv(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    /**
     * 只能打开到自带安全软件
     *
     * @param activity
     */
    private void _360(Activity activity) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    /**
     * 应用信息界面
     *
     * @param activity
     */
    private void defaultSetting(Activity activity) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
        }
        activity.startActivity(localIntent);
    }
}
