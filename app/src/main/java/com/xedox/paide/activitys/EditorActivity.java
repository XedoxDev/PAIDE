package com.xedox.paide.activitys;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.xedox.paide.R;
import com.xedox.paide.project.Project;

public class EditorActivity extends AppCompatActivity {
    
    private Toolbar toolbar;
    private TabLayout tabs;
    private ViewPager2 pager;
    
    private Project project;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = findViewById(R.id.toolbar);
        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.viewPager);
        setSupportActionBar(toolbar);
        
        String name = getIntent().getStringExtra("name");
        project = new Project(name);
    }
}