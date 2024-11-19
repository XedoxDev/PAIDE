package com.xedox.paide.drawer.files;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import com.xedox.paide.R;

public class FilesListAdapter extends RecyclerView.Adapter<FilesListAdapter.VH> {

    private Context context;
    private File[] list;
    private OnItemClickListener onItemClickListener;

    public FilesListAdapter(Context context) {
        this.context = context;
        this.list = list;
    }

    public class VH extends RecyclerView.ViewHolder {
        public View parent;
        public ImageView icon;
        public TextView name;
        public int pos;

        public VH(View parent, int pos) {
            super(parent);
            this.parent = parent;
            this.pos = pos;
            icon = parent.findViewById(R.id.icon);
            name = parent.findViewById(R.id.fileName);
            parent.setOnClickListener(
                    (v) -> {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, list[pos], pos);
                        }
                    });
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup vg, int pos) {
        if (vg == null) {
            vg = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.file_item, null, false);
        }
        VH vh = new VH(vg, pos);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH vh, int pos) {
        File f = list[pos];
        vh.name.setText(f.getName());
        if (f.getName().equals("..")) {
            vh.icon.setImageResource(R.drawable.ic_back_arrow);
        } else if (f.isFile()) {
            vh.icon.setImageResource(R.drawable.ic_file);
        } else if (f.isDirectory()) {
            vh.icon.setImageResource(R.drawable.ic_folder);
        }
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public File[] getList() {
        return this.list;
    }

    public void setList(File[] list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static interface OnItemClickListener {
        public void onItemClick(View view, File file, int pos);
    }

    public OnItemClickListener getOnItemClickListener() {
        return this.onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
