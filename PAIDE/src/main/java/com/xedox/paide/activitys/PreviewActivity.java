package com.xedox.paide.activitys;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import static com.xedox.paide.PAIDE.*;
import com.xedox.paide.project.Project;
import com.xedox.paide.utils.CodeBinding;
import com.xedox.paide.utils.ProcessingCompiler;
import com.xedox.paide.utils.SketchFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.android.tools.r8.D8;

public class PreviewActivity extends AppCompatActivity {

    private static final String TAG = "PreviewActivity";
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        project = new Project(getIntent().getStringExtra("name"));
        compileClass();
        try {
            dexingClass();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private String generateProcessingCode() {
        List<SketchFile> codes = new ArrayList<>();
        for (File file : project.getSrc().listFiles()) {
            SketchFile sf = new SketchFile(file);
            if (sf.getExtension().equals(".pde")) {
                codes.add(sf);
            }
        }
        return """
                public class MySketch extends processing.core.PApplet {
                        """
                + CodeBinding.bind(codes.toArray(new SketchFile[0]))
                + """
                }
                """;
    }

    private void back() {
        Intent i = new Intent(this, EditorActivity.class);
        i.putExtra("name", project.getName());
        startActivity(i);
        finish();
    }

    private void compileClass() {
        String code = generateProcessingCode();
        try {
            File buildDir = new File(project.getProjectDir(), "build/");
            buildDir.mkdirs();
            File buildFile = new File(buildDir, "MySketch.class");
            byte[] byteCode = ProcessingCompiler.compileProcessingCode(code);
            try (FileOutputStream fos = new FileOutputStream(buildFile)) {
                fos.write(byteCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            back();
        }
    }

    private void dexingClass() throws IOException, InterruptedException {
        File classDir = new File(project.getProjectDir(), "build/MySketch.class");
        File dexDir = new File(project.getProjectDir(), "build/MySketch.dex");
        List<String> command =
                Arrays.asList(
                        "d8",
                        "--min-api",
                        String.valueOf(30),
                        "--output",
                        dexDir.getAbsolutePath(),
                        classDir.getAbsolutePath());

        new ProcessBuilder(command).start().waitFor();
    }
}
