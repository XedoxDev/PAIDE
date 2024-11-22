package com.xedox.paide.dialogs;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.xedox.paide.project.ProjectsAdapter;
import static com.xedox.paide.PAIDE.*;
import com.xedox.paide.R;

public class ProjectsDialog implements Dialog {

    private Activity context;
    private MaterialAlertDialogBuilder builder;
    private AlertDialog dialog;
    private ProjectsAdapter adapter;

    public ProjectsDialog(Activity context) {
        this.context = context;
    }

    @Override
    public void build() {
        builder = new MaterialAlertDialogBuilder(context);
        adapter = new ProjectsAdapter(context, getProjects(context));
        builder.setTitle(R.string.projects_menu);
        
        View view = LayoutInflater.from(context).inflate(R.layout.projects_layout, null, false);
        RecyclerView rv = view.findViewById(R.id.projects);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(adapter);
        
        builder.setView(view);
    }

    @Override
    public void show() {
        dialog = builder.create();
        dialog.show();
    }
}
