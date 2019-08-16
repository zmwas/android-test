package de.suitepad.packagelist;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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

public class MainActivity extends AppCompatActivity {

    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);

        populatePackages();
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
        ListView listView = (ListView) findViewById(R.id.package_list);
        listView.setAdapter(packageListAdapter);
        pkgList.clear();
        PackageManager pm = getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_META_DATA);

        for (PackageInfo packageInfo : packages) {
            pkgList.add(new Pkg(packageInfo.packageName, packageInfo.packageName, packageInfo.versionName, packageInfo.versionCode));
        }
    }

    class PackageListAdapter extends ArrayAdapter<Pkg> {

        public PackageListAdapter(Context context, List<Pkg> packages) {
            super(context, R.layout.list_item_package, packages);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.list_item_package, null);
            }
            final Pkg p = getItem(position);
            if (p != null) {
                TextView tt1 = (TextView)v.findViewById(R.id.pkg_label);
                tt1.setText(p.getLabel());
                TextView tt2 = (TextView)v.findViewById(R.id.pkg_name);
                tt2.setText(p.getName());
                TextView tt3 = (TextView)v.findViewById(R.id.pkg_version);
                tt3.setText(p.getVersionName());
                Button btn1 = (Button)v.findViewById(R.id.pkg_uninstall);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Uninstalling package: " + p.getName(), Toast.LENGTH_SHORT).show();
                    }
                });

                Button btn2 = (Button)v.findViewById(R.id.pkg_launch);
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Launching package: " + p.getName(), Toast.LENGTH_SHORT).show();
                    }
                });

                ImageView imageView = (ImageView)v.findViewById(R.id.pkg_icon);
//                imageView.setImageDrawable(p.get);

            }

            return v;
        }
    }

}
