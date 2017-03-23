package com.example.zwy.plugininstalltest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;

public class InstallBroadCastReceiver extends BroadcastReceiver {

    public InstallBroadCastReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //accept a message that an apk has been installed
        if (action.equals(Intent.ACTION_PACKAGE_ADDED) || action.equals(Intent.ACTION_PACKAGE_REPLACED)) {
            if(TextUtils.isEmpty(InstallApkActivity.STORAGE_PATH)){
                return;
            }
            if (intent.getData().getSchemeSpecificPart().equals(InstallApkActivity.APK_PACKAGE_NAME)) {
                File file = new File(InstallApkActivity.STORAGE_PATH);
                if (file.exists()) {
                    if (file.delete()) {
                        Toast.makeText(context, "删除安装包成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }
}
