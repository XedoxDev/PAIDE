package com.xedox.paide.activitys;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xedox.paide.R;
import com.xedox.paide.drawer.files.FilesListAdapter;
import com.xedox.paide.editor.Editor;
import com.xedox.paide.editor.editors.soraeditor.SoraEditor;
import com.xedox.paide.editor.tabs.EditorFragment;
import com.xedox.paide.editor.tabs.TabsAdapter;
import com.xedox.paide.project.Project;
import com.xedox.paide.tools.AutoFormat;
import com.xedox.paide.utils.ContextMenu;
import com.xedox.paide.utils.SketchFile;
import io.github.rosemoe.sora.util.ArrayList;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class EditorActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;
    private TabLayout tabs;
    private TabsAdapter adapter;
    private ViewPager2 pager;
    private Project project;
    public File currentDir;
    private FilesListAdapter filesAdapter;

    private ImageButton backDir;

    private ImageButton undo, redo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        String name = getIntent().getStringExtra("name");

        toolbar = findViewById(R.id.toolbar);
        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.viewPager);
        drawer = findViewById(R.id.drawer_layout);
        backDir = findViewById(R.id.backDir);
        undo = findViewById(R.id.undo);
        redo = findViewById(R.id.redo);
        toolbar.setTitle(name);
        backDir.setOnClickListener(
                (v) -> {
                    toParentDirectory();
                });

        undo.setOnClickListener(
                (v) -> {
                    adapter.getEditorFragment(tabs.getSelectedTabPosition()).getEditor().undo();
                });
        redo.setOnClickListener(
                (v) -> {
                    adapter.getEditorFragment(tabs.getSelectedTabPosition()).getEditor().redo();
                });

        project = new Project(name);
        adapter = new TabsAdapter(this);
        pager.setAdapter(adapter);
        currentDir = project.getProjectDir();

        setSupportActionBar(toolbar);

        new TabLayoutMediator(
                        tabs,
                        pager,
                        (tab, pos) -> {
                            tab.setText(adapter.getNameForTab(pos));
                            tab.view.setOnClickListener(
                                    v -> {
                                        if (tab.getPosition() == tabs.getSelectedTabPosition()) {
                                            ContextMenu tabMenu =
                                                    new ContextMenu(
                                                            v,
                                                            (item, menu) -> {
                                                                adapter.remove(pos);
                                                            });
                                            tabMenu.add(0, R.string.delete);
                                            tabMenu.show();
                                        }
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
        files.setLayoutManager(new LinearLayoutManager(this));

        filesAdapter = new FilesListAdapter(this);

        filesAdapter.setOnItemClickListener(
                (v, file) -> {
                    String fileName = file.getName();
                    if (file.isDirectory()) {
                        toDirectory(file);
                        Toast.makeText(
                                        EditorActivity.this,
                                        "Opened directory: " + fileName,
                                        Toast.LENGTH_SHORT)
                                .show();
                    } else if (file.isFile()) {
                        openFile(file);
                        Toast.makeText(
                                        EditorActivity.this,
                                        "Opened file: " + fileName,
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        filesAdapter.setList(Arrays.asList(project.getProjectDir().listFiles()));
        refreshFileList();
        files.setAdapter(filesAdapter);

        drawerToggle =
                new ActionBarDrawerToggle(
                        this, drawer, toolbar, R.drawable.ic_look, R.drawable.ic_plus);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void toParentDirectory() {
        File cd = currentDir;
        currentDir = new File(cd.getParent());
        refreshFileList();
    }

    private void toDirectory(File directory) {
        File cd = currentDir;
        currentDir = new File(cd, directory.getName());
        refreshFileList();
    }

    private void openFile(File file) {
        try {
            SketchFile sf = new SketchFile(file);
            for (int i = 0; i < tabs.getTabCount(); i++) {
                if (tabs.getTabAt(i).getText().toString().equals(sf.getName())) {
                    return;
                }
            }
            adapter.add(sf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshFileList() {
        List<File> f = Arrays.asList(currentDir.listFiles());
        if (f != null && f.size() > 0) {
            filesAdapter.setList(f);
        } else {
            filesAdapter.setList(new ArrayList<File>());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (EditorFragment ef : adapter.getEditors()) {
            Editor ed = (SoraEditor) ef.getEditor();
            if (ed instanceof SoraEditor) {
                SoraEditor editor = (SoraEditor) ed;
                editor.release();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.save) {
            for (EditorFragment ef : adapter.getEditors()) {
                ef.save();
            }
        }
        if (item.getItemId() == R.id.launch) {
            for (EditorFragment ef : adapter.getEditors()) {
                ef.save();
            }
            /*Intent i = new Intent(this, PreviewActivity.class);
            i.putExtra("name", project.getName());
            startActivity(i);
            finish();*/
        }
        if (item.getItemId() == R.id.format_code) {
            AutoFormat.formate(adapter.getEditorFragment(tabs.getSelectedTabPosition()).getEditor());
        }
        return super.onOptionsItemSelected(item);
    }
}
