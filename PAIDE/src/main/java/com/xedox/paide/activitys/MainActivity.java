package com.xedox.paide.activitys;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import com.google.android.material.button.MaterialButton;
import com.xedox.paide.R;
import com.xedox.paide.dialogs.CreateProjectDialog;
import com.xedox.paide.dialogs.Dialog;
import com.xedox.paide.dialogs.ProjectsDialog;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = "MainActivity";
    
    private MaterialButton createProject, openProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createProject = findViewById(R.id.create_project);
        openProject = findViewById(R.id.open_project);

        createProject.setOnClickListener(
                (v) -> {
                    Dialog d = new CreateProjectDialog(this);
                    d.build();
                    d.show();
                });
        openProject.setOnClickListener(
                (v) -> {
                    Dialog d = new ProjectsDialog(this);
                    d.build();
                    d.show();
                });
        registerForContextMenu(openProject);
    }
}
