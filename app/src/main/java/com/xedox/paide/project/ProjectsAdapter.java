package com.xedox.paide.project;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.xedox.paide.R;
import com.xedox.paide.utils.ContextMenu;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.VH> {

    private Activity context;
    private List<Project> list;

    public ProjectsAdapter(Activity context, List<Project> list) {
        this.context = context;
        this.list = list;
    }

    public class VH extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView more;

        public VH(View parent) {
            super(parent);
            parent.setBackgroundResource(R.drawable.ic_ripple);
            name = parent.findViewById(R.id.name);
            more = parent.findViewById(R.id.more);
            more.setOnClickListener(
                    (v) -> {
                        ContextMenu menu = new ContextMenu(v, (i) -> {});
                        menu.add(0, context.getString(R.string.open_project));
                        menu.add(0, context.getString(R.string.rename));
                        menu.add(0, context.getString(R.string.delete_project));
                        menu.show();
                    });
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup vg, int pos) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_item, vg, false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH vh, int pos) {
        Project project = list.get(pos);
        vh.name.setText(project.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
