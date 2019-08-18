package de.suitepad.packagelist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import de.suitepad.packagelist.databinding.ListItemPackageBinding;
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
        LayoutInflater inflater = LayoutInflater.from(getContext());
        ListItemPackageBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_package, null, false);

        final Pkg pkg = getItem(position);
        if (pkg != null) {
            binding.setPkg(pkg);
            binding.pkgUninstall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onBtn1Click(position, pkg, v);
                }
            });

            binding.pkgLaunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onBtn2Click(position, pkg, v);
                }
            });

            ImageView imageView = binding.pkgIcon;
//                imageView.setImageDrawable(p.get);

        }

        return binding.getRoot();
    }


}
