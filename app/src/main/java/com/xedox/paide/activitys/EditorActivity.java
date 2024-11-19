package com.xedox.paide.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xedox.paide.R;
import com.xedox.paide.drawer.DrawerFilesManager;
import com.xedox.paide.drawer.files.FilesListAdapter;
import com.xedox.paide.editor.tabs.TabsAdapter;
import com.xedox.paide.project.Project;
import com.xedox.paide.utils.ContextMenu;
import com.xedox.paide.utils.SketchFile;
import java.io.File;

public class EditorActivity extends AppCompatActivity {

    private Toolbar toolbar;
    

    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;
    
    private TabLayout tabs;
    private TabsAdapter adapter;
    private ViewPager2 pager;

    private Project project;
    
    public File currentDir = project.getProjectDir();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        String name = getIntent().getStringExtra("name");

        toolbar = findViewById(R.id.toolbar);
        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.viewPager);
        drawer = findViewById(R.id.drawer_layout);

        project = new Project(name);
        adapter = new TabsAdapter(this);
        pager.setAdapter(adapter);

        setSupportActionBar(toolbar);

        new TabLayoutMediator(
                        tabs,
                        pager,
                        (tab, pos) -> {
                            tab.setText(adapter.getNameForTab(pos));
                            tab.view.setOnClickListener(
                                    v -> {
                                        ContextMenu tabMenu =
                                                new ContextMenu(
                                                        v,
                                                        (item, menu) -> {
                                                            adapter.remove(pos);
                                                        });
                                        tabMenu.add(0, R.string.delete);
                                        tabMenu.show();
                                    });
                        })
                .attach();

        for (File f : project.getSrc().listFiles()) {
            if (f.isFile()) {
                adapter.add(new SketchFile(project.getSrc(), f.getName()));
                Log.e("EditorActivtity", f.getName()); // debug
            }
        }

        NavigationView drawerContent = findViewById(R.id.drawer_content);
        RecyclerView files = drawerContent.findViewById(R.id.files_list);
        FilesListAdapter filesAdapter = new FilesListAdapter(this);
        files.setAdapter(filesAdapter);

        filesAdapter.setList(project.getProjectDir().listFiles());
        
        filesAdapter.setOnItemClickListener(
                (v, file, pos) -> {
                    String fileName = file.getName();
                    if (fileName == "..") {
                        currentDir = new File(currentDir.getParent());
                        filesAdapter.setList(currentDir.listFiles());
                    } else if (file.isDirectory()) {
                        currentDir = new File(currentDir, file.getName());
                    } else if (file.isFile()) {
                        SketchFile sf = new SketchFile(file);
                        adapter.add(sf);
                    }
                });

        drawerToggle =
                new ActionBarDrawerToggle(
                        this, drawer, toolbar, R.drawable.ic_look, R.drawable.ic_plus);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
