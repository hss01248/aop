package com.hss01248.dpdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;

import com.morgoo.droidplugin.pm.PluginManager;
import com.morgoo.helper.compat.PackageManagerCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadApk(View view) {
        String filepath = new File(getFilesDir(),"imgmonitor-debug.apk").getAbsolutePath();
        try {
            PluginManager.getInstance().installPackage(filepath, PackageManagerCompat.INSTALL_REPLACE_EXISTING);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}