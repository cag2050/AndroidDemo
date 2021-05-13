package com.example.androiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

// 参考：1. https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Access_Guide/Android.html ；
//      2. https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Share_and_Favorites/Android.html ；
public class ShareToWechatActivity extends AppCompatActivity implements View.OnClickListener {

    private Button share_to_wechat_button;
    private final String wechat_share_type = "session";
    private static IWXAPI api;
    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wx2b371b1d1a6d77fc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_to_wechat);
//      注册微信
        regToWx();
        initView();
    }

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);

        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 将该app注册到微信
                api.registerApp(APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    private void initView() {
        share_to_wechat_button = (Button) findViewById(R.id.share_to_wechat_button);
        share_to_wechat_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_to_wechat_button:
                shareImageToWechat();
                break;
        }
    }

    public void shareImageToWechat() {
        Log.d("ShareToWechatActivity", "进入方法：shareImageToWechat()");
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.icon_message_selected);

        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(bm);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bm, 150, 150, true);
        bm.recycle();
        msg.setThumbImage(thumbBmp);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = wechat_share_type + "_img"; // 这个要唯一,用于在回调中分辨是哪个分享请求
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        Log.d("ShareToWechatActivity", req.toString());
        //调用api接口，发送数据到微信；如果调用成功微信,会返回true
        boolean b = api.sendReq(req);
        Log.d("ShareToWechatActivity", "调用微信成功：" + String.valueOf(b));
    }
}