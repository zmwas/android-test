package de.suitepad.packagelist;

import android.view.View;

import de.suitepad.packagelist.models.Pkg;

public interface PackageListCallback {
    void onBtn1Click(int position, Pkg pkg, View view);
    void onBtn2Click(int position, Pkg pkg, View view);

}
