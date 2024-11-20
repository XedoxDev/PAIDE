package com.xedox.preview;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.xedox.paide.project.Project;
import processing.core.PApplet;

public class PreviewActivity extends AppCompatActivity {
    
    private PApplet sketch;
    private Project project;
    
	@Override
	protected void onCreate(Bundle b) {
	    super.onCreate(b);
        Intent intent = getIntent();
        project = intent.getStringExtra("PROJECT_NAME");
	}
	
}
