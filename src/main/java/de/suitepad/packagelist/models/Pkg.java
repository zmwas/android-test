package de.suitepad.packagelist.models;

import android.content.ComponentName;

/**
 * Created by tarek on 4/6/17.
 */

public class Pkg {
    String versionName;
    int versionCode;
    String name;
    String label;

    public Pkg(String name, String label, String versionName, int versionCode) {
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.name = name;
        this.label = label;
    }

    public String getVersionName() {
        return versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }


}
