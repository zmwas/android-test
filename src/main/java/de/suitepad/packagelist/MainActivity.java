package de.suitepad.packagelist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.suitepad.packagelist.models.Pkg;

public class MainActivity extends AppCompatActivity implements PackageListCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populatePackages();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        registerReceiver(packageChangedBroadcastReceiver, intentFilter);

    }

    BroadcastReceiver packageChangedBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Uri data = intent.getData();
            String pkgName = data.toString().substring(data.getScheme().length() + 1); //remove 'package:'
            switch (intent.getAction()) {
                case "android.intent.action.PACKAGE_ADDED":
                    onPackageInstalled(pkgName);
                    break;
                case "android.intent.action.PACKAGE_REMOVED":
                    onPackageUninstalled(pkgName);
                    break;
                case "android.intent.action.PACKAGE_CHANGED":
                    onPackageChanged(pkgName);
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(packageChangedBroadcastReceiver);
    }

    public void onPackageUninstalled(String pkgName) {
        Toast.makeText(this, pkgName + " is uninstalled", Toast.LENGTH_SHORT).show();
        populatePackages();
    }

    public void onPackageInstalled(String pkgName) {
        Toast.makeText(this, pkgName + " is installed", Toast.LENGTH_SHORT).show();
        populatePackages();
    }

    public void onPackageChanged(String pkgName) {
        Toast.makeText(this, pkgName + " is changed", Toast.LENGTH_SHORT).show();
        populatePackages();
    }

    public void populatePackages() {
        ArrayList<Pkg> pkgList = new ArrayList<>();
        PackageListAdapter packageListAdapter = new PackageListAdapter(this, pkgList);
        packageListAdapter.setCallback(this);
        ListView listView = (ListView) findViewById(R.id.package_list);
        listView.setAdapter(packageListAdapter);
        pkgList.clear();
        PackageManager pm = getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_META_DATA);

        for (PackageInfo packageInfo : packages) {
            pkgList.add(new Pkg(packageInfo.packageName, packageInfo.packageName, packageInfo.versionName, packageInfo.versionCode));
        }
    }


    @Override
    public void onBtn1Click(int position, Pkg pkg, View view) {
        Toast.makeText(getApplicationContext(), "Uninstalling package: " + pkg.getName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBtn2Click(int position, Pkg pkg, View view) {
        Toast.makeText(getApplicationContext(), "Launching package: " + pkg.getName(), Toast.LENGTH_SHORT).show();
    }
}
