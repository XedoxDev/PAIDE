package com.xedox.paide.activitys;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.android.tools.r8.D8;
import static com.xedox.paide.PAIDE.*;
import com.xedox.paide.project.Project;
import com.xedox.paide.utils.CodeBinding;
import com.xedox.paide.utils.IFile;
import com.xedox.paide.utils.ProcessingCompiler;
import com.xedox.paide.utils.FileX;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            mktest(err.toString());
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private String generateProcessingCode() {
        List<FileX> codes = new ArrayList<>();
        for (File file : project.getSrc().listFiles()) {
            FileX sf = new FileX(file);
            if (sf.getExtension().equals(".pde")) {
                codes.add(sf);
            }
        }
        return """
                public class MySketch extends processing.core.PApplet {
                        """
                + CodeBinding.bind(codes.toArray(new FileX[0]))
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

    private void dexingClass() {
        /* not work, premission denied - execulatble jar files :(
        File build = new File(project.getProjectDir(), "build/");

        IFile classDir = new FileX(build, "MySketch.class");
        IFile dexDir = new FileX(build, "MySketch.dex");
        IFile debug = new FileX(build, "debug.txt");
        debug.mkfile();

        IFile d8Path = new FileX(projectsDir.getAbsolutePath(), "d8.jar");
        if (!d8Path.exists()) {
            return;
        }

        try {
            String[] command = {
                d8Path.toString(),
                "--output=",
                dexDir.getFullPath(),
                classDir.getFullPath()
            };

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                debug.println(line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Dex compilation successful!");
                mkToast("dexing successful");
            } else {
                System.err.println("Dex compilation exit code: " + exitCode);
                mkToast("dexing unsuccessful");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            mkToast(e.toString());
        }
        */
    }
}
