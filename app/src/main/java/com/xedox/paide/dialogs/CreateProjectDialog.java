package com.xedox.paide.dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import static com.xedox.paide.PAIDE.*;
import com.xedox.paide.R;
import com.xedox.paide.project.Project;

public class CreateProjectDialog implements Dialog {

    private Activity context;
    private MaterialAlertDialogBuilder builder;
    private AlertDialog dialog;

    public CreateProjectDialog(Activity context) {
        this.context = context;
    }

    public void build() {
        builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle(R.string.create_project);
        TextInputLayout layout = new TextInputLayout(context);
        TextInputEditText projectName = new TextInputEditText(context);
        layout.addView(projectName);
        projectName.setHint(R.string.project_name);
        builder.setView(layout);

        builder.setPositiveButton(
                R.string.create_project,
                (i, p) -> {
                    if (requestManagePremission(context)) {
                        String name = projectName.getText().toString();
                        Project project = new Project(name);
                        project.create();
                        i.dismiss();
                    }
                });

        builder.setNegativeButton(
                R.string.cancel,
                (i, p) -> {
                    i.dismiss();
                });
    }

    @Override
    public void show() {
        dialog = builder.create();
        dialog.show();
    }
}
