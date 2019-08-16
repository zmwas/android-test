package de.suitepad.packagelist.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import de.suitepad.packagelist.MainActivity;

/**
 * Created by tarek on 4/6/17.
 */

public class PackageChangedBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //triggered whenever any change happens to any package
        //should inform activity
        Uri data = intent.getData();
        String pkgName = data.toString().substring(data.getScheme().length() + 1); //remove 'package:'
        if(MainActivity.instance != null) {
            if(intent.getAction() == "android.intent.action.PACKAGE_ADDED") {
                MainActivity.instance.onPackageInstalled(pkgName);
            } else if(intent.getAction() == "android.intent.action.PACKAGE_REMOVED") {
                MainActivity.instance.onPackageUninstalled(pkgName);
            } else if(intent.getAction() == "android.intent.action.PACKAGE_CHANGED") {
                MainActivity.instance.onPackageChanged(pkgName);
            }
        }
    }
}
