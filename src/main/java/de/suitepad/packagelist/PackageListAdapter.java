package de.suitepad.packagelist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import de.suitepad.packagelist.databinding.ListItemPackageBinding;
import de.suitepad.packagelist.models.Pkg;

class PackageListAdapter extends RecyclerView.Adapter<PackageListAdapter.PackageViewHolder> {
    private  Context context;
    private PackageListCallback callback;
    private List<Pkg> packages;

    public PackageListAdapter(Context context, List<Pkg> packages) {
        this.packages = packages;
        this.context = context;
    }

    public void setCallback(PackageListCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ListItemPackageBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_package, null, false);
        return new PackageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageViewHolder packageViewHolder, final int i) {
        final Pkg pkg = packages.get(i);
        if (pkg != null) {
            packageViewHolder.bind(pkg);
            packageViewHolder.binding.pkgUninstall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onBtn1Click(i, pkg, v);
                }
            });

            packageViewHolder.binding.pkgLaunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onBtn2Click(i, pkg, v);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return packages.size();
    }


    public class PackageViewHolder extends RecyclerView.ViewHolder {
        ListItemPackageBinding binding;
        PackageViewHolder(@NonNull ListItemPackageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Pkg pkg) {
            binding.setPkg(pkg);
            ImageView imageView = binding.pkgIcon;
//                imageView.setImageDrawable(p.get);


        }
    }
}
