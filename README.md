### Android 项目例子

例子 | 说明
--- | ---
下载图片、保存图片、删除图片、从sd卡读取文件 | AndroidDemo/app/src/main/java/com/example/androiddemo/DownloadImageJavaActivity.java
各种dir函数区别 | app/src/main/java/com/example/androiddemo/DirDifferenceActivity.kt
微信分享 | app/src/main/java/com/example/androiddemo/ShareToWechatActivity.java
下载资源后刷新【MediaScannerConnection.scanFile()的用法】 |

#### CheckBox 注意点
1. 如果你代码中有根据数据设置checkbox选中状态，又同时监听了setOnCheckedChangeListener（）方法，setChecked()时会触发此listener，会造成页面checkbox选择错乱问题。
2. 如果有setChecked()方法，此时，用setOnClickListener代替setOnCheckedChangeListener方法。
> 原文链接：https://blog.csdn.net/zane_xiao/article/details/79044951

