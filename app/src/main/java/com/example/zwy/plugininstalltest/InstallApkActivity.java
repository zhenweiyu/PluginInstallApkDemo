package com.example.zwy.plugininstalltest;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class InstallApkActivity extends AppCompatActivity {

    public final static String APK_PACKAGE_NAME = "com.example.plugininstalltest.add";//需要安装的APK的包名
    private final String APK_NAME = "PluginInstallAdd.apk";
    public static String STORAGE_PATH = "";
    private boolean isDownloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void installApk(View view) {
        boolean isInstalled = false;
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> list = packageManager.getInstalledPackages(0);
        for (int index = 0; index < list.size(); index++) {
            PackageInfo info = list.get(index);
            if (info.packageName.equals(APK_PACKAGE_NAME)) {
                isInstalled = true;
                break;
            }
        }
        if (isInstalled) {
            Intent intent = new Intent();
            intent.setPackage(APK_PACKAGE_NAME);
            //在需要被安装的APK的需要启动的Activity中intent-filter中声明
            intent.setAction("android.intent.action.show.activity");
            startActivity(intent);
        } else {
            //模拟从网络下载APK的过程(实际上是放在assets)
            Toast.makeText(this,"正在下载APK",Toast.LENGTH_SHORT).show();
                if(!isDownloading) {
                    isDownloading = true;
                    downLoadApk.execute();
                }

        }

    }

    private AsyncTask<String, Integer, File> downLoadApk = new AsyncTask<String, Integer, File>() {
        @Override
        protected File doInBackground(String... params) {
            String fileName = getExternalCacheDir().getAbsolutePath()+ "/" + APK_NAME;
            STORAGE_PATH = fileName;
            File file = new File(fileName);
            try {
                if (!file.exists()) {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    InputStream inputStream = InstallApkActivity.this.getAssets().open(APK_NAME);
                    int len = -1;
                    byte[] buffer = new byte[1024];
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    inputStream.close();
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file;
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            if (file.exists()) {
                installApkFromFile(file);
            } else {
                Toast.makeText(InstallApkActivity.this, "apk下载失败", Toast.LENGTH_SHORT).show();
            }
        }

    };

    private void installApkFromFile(File file) {
        Toast.makeText(this,"准备安装APK",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }


}
