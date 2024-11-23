package com.xedox.paide.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.xedox.paide.project.Project;
import com.xedox.paide.utils.CodeBinding;
import com.xedox.paide.utils.PClassLoader;
import com.xedox.paide.utils.ProcessingCompiler;
import com.xedox.paide.utils.SketchFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import processing.android.PFragment;
import processing.core.PApplet;

public class PreviewActivity extends AppCompatActivity {

    private static final String TAG = "PreviewActivity";
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        project = new Project(getIntent().getStringExtra("name"));
        String code = generateProcessingCode();

        try {
            File buildDir = new File(project.getProjectDir(), "build/");
            buildDir.mkdirs();
            File buildFile = new File(buildDir, "MySketch.class");
            byte[] byteCode = ProcessingCompiler.compileProcessingCode(code);
            try (FileOutputStream fos = new FileOutputStream(buildFile)) {
                fos.write(byeCode);
            } catch(Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            back();
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private String generateProcessingCode() {
        List<SketchFile> codes = new ArrayList<>();
        for (File file : project.getSrc().listFiles()) {
            if (file.getName().endsWith(".pde")) {
                codes.add(new SketchFile(file.getAbsolutePath(), file.getName()));
            }
        }
        return """
                public class MySketch extends processing.core.PApplet {
                    public void settings() { size(400, 400); }
                    public void setup() {
                        """
                + CodeBinding.bind(codes.toArray(new SketchFile[0]))
                + """
                    }
                    public void draw() {

                    }
                }
                """;
    }

    private void back() {
        Intent i = new Intent(this, EditorActivity.class);
        i.putExtra("name", project.getName());
        startActivity(i);
        finish();
    }
}

