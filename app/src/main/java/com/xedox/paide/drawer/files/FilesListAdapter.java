package com.xedox.paide.drawer.files;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.xedox.paide.R;

public class FilesListAdapter extends RecyclerView.Adapter<FilesListAdapter.VH> {

    private Context context;
    private List<File> list;
    private OnItemClickListener onItemClickListener;

    public FilesListAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public class VH extends RecyclerView.ViewHolder {
        public View parent;
        public ImageView icon;
        public TextView name;

        public VH(@NonNull View parent) {
            super(parent);
            this.parent = parent;
            icon = parent.findViewById(R.id.icon);
            name = parent.findViewById(R.id.fileName);

            parent.setOnClickListener(
                    v -> {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, list.get(position));
                        }
                    });
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.file_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int pos) {
        File f = list.get(pos);
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
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, File file);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<File> getList() {
        return list;
    }

    public void setList(List<File> list) {
        if (list == null) {
            Log.e("FilesListAdapter", "Attempt to set a null list");
            return;
        }
        this.list = list;
        notifyDataSetChanged();
    }

    public void add(int pos, File file) {
        list.add(pos, file);
        notifyItemInserted(pos);
    }

    public void add(int pos, String path) {
        add(pos, new File(path));
        notifyItemInserted(pos);
    }

    public void remove(int pos) {
        if (pos >= 0 && pos < list.size()) {
            list.remove(pos);
            notifyItemRemoved(pos);
        }
    }
}
