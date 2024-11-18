package com.xedox.paide.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xedox.paide.R;
import com.xedox.paide.editor.tabs.TabsAdapter;
import com.xedox.paide.project.Project;
import com.xedox.paide.utils.SketchFile;
import java.io.File;

public class EditorActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TabLayout tabs;
    private TabsAdapter adapter;
    private ViewPager2 pager;

    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        toolbar = findViewById(R.id.toolbar);
        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.viewPager);
        setSupportActionBar(toolbar);

        pager.setMinimumWidth(getWindowManager().getDefaultDisplay().getWidth());
        pager.setMinimumHeight(getWindowManager().getDefaultDisplay().getHeight());

        String name = getIntent().getStringExtra("name");
        project = new Project(name);

        adapter = new TabsAdapter(this);
        pager.setAdapter(adapter);
        new TabLayoutMediator(
                tabs,
                pager,
                (tab, pos) -> {
                    tab.setText(adapter.getNameForTab(pos));
                }).attach();

        for (File f : project.getSrc().listFiles()) {
            if (f.isFile()) {
                adapter.add(new SketchFile(project.getSrc(), f.getName()));
                Log.e("EditorActivtity", f.getName()); // debug
            }
        }
    }
}
