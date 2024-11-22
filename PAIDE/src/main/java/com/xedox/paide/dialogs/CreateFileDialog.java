package com.xedox.paide.dialogs;

import android.app.Activity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import static com.xedox.paide.PAIDE.*;
import com.xedox.paide.R;
import com.xedox.paide.activitys.EditorActivity;
import java.io.File;

public class CreateFileDialog implements Dialog {

    private Activity context;
    private MaterialAlertDialogBuilder builder;
    private AlertDialog dialog;
    private File path;

    public CreateFileDialog(Activity context, File path) {
        this.context = context;
        this.path = path;
    }

    public void build() {
        builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle(R.string.create_file);
        TextInputLayout layout = new TextInputLayout(context);
        TextInputEditText fileName = new TextInputEditText(context);
        layout.addView(fileName);
        fileName.setHint(R.string.file_name);
        builder.setView(layout);

        builder.setPositiveButton(
                R.string.create,
                (i, p) -> {
                    if (requestManagePremission(context)) {
                        File file = new File(path, fileName.getText().toString());
                        try {
                            file.createNewFile();
                            i.dismiss();
                            ((EditorActivity) context).refreshFileList();
                        } catch (Exception err) {
                            err.printStackTrace();
                        }
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
