package com.xedox.paide.project;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.xedox.paide.activitys.EditorActivity;
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
                        String open = context.getString(R.string.open_project);
                        String rename = context.getString(R.string.rename);
                        String delete = context.getString(R.string.delete_project);
                        ContextMenu menu =
                                new ContextMenu(
                                        v,
                                        (item, m) -> {
                                            String title = item.getTitle().toString();
                                            if (title.equals(open)) {
                                                Intent i =
                                                        new Intent(context, EditorActivity.class);
                                                i.putExtra("name", name.getText());
                                                context.startActivity(i);
                                                context.finish();
                                            }
                                            if (title.equals(delete)) {
                                                Project project =
                                                        new Project(name.getText().toString());
                                                project.deleteProject();
                                                remove(project.getName());
                                            }
                                        });
                        menu.add(0, open);
                        menu.add(0, rename);
                        menu.add(0, delete);
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

    public void add(Project project) {
        list.add(project);
        notifyItemInserted(list.size() - 1);
    }

    public void remove(String name) {
        for (int i = 0; i < list.size(); i++) {
            String n = list.get(i).getName();
            if (n == name) {
                list.remove(i);
                notifyItemRemoved(i);
                return;
            }
        }
    }
}
