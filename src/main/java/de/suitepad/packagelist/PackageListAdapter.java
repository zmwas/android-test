package de.suitepad.packagelist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.suitepad.packagelist.models.Pkg;

class PackageListAdapter extends ArrayAdapter<Pkg> {
    PackageListCallback callback;

    public PackageListAdapter(Context context, List<Pkg> packages) {
        super(context, R.layout.list_item_package, packages);
    }

    public void setCallback(PackageListCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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
                    callback.onBtn1Click(position,p,v);
                }
            });

            Button btn2 = (Button)v.findViewById(R.id.pkg_launch);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onBtn2Click(position,p,v);

                }
            });

            ImageView imageView = (ImageView)v.findViewById(R.id.pkg_icon);
//                imageView.setImageDrawable(p.get);

        }

        return v;
    }
}
