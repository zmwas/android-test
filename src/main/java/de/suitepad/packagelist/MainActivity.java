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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    RecyclerView listView;
    ArrayList<Pkg> pkgList;
    PackageListAdapter packageListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.package_list);
        pkgList = new ArrayList<>();
        packageListAdapter = new PackageListAdapter(this, pkgList);
        packageListAdapter.setCallback(this);
        listView.setAdapter(packageListAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
        pkgList.clear();
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
    protected void onPause() {
        super.onPause();
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
        PackageManager pm = getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_META_DATA);
        for (PackageInfo packageInfo : packages) {
            pkgList.add(new Pkg(packageInfo.packageName, packageInfo.packageName, packageInfo.versionName, packageInfo.versionCode));
        }
        packageListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBtn1Click(int position, Pkg pkg, View view) {
        Toast.makeText(this, "Uninstalling package: " + pkg.getName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBtn2Click(int position, Pkg pkg, View view) {
        Toast.makeText(this, "Launching package: " + pkg.getName(), Toast.LENGTH_SHORT).show();
    }
}
