package com.xedox.paide;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;
import com.xedox.paide.project.Project;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PAIDE extends Application {

    public static Context context;
    public static File projectsDir;

    public static final byte MANAGE_REQUEST_CODE = 1;

    static {
        projectsDir =
                new File(Environment.getExternalStorageDirectory().getPath(), "PAIDE-Projects");
        if (!projectsDir.exists()) projectsDir.mkdir();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static boolean requestManagePremission(Activity activity) {
        if (Environment.isExternalStorageManager()) return true;
        else {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                activity.startActivityForResult(intent, MANAGE_REQUEST_CODE);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                activity.startActivityForResult(intent, MANAGE_REQUEST_CODE);
                e.printStackTrace();
            }
            return false;
        }
    }

    public static Toast mktest(String text) {
        // off for release apk
        Toast toast = new Toast(context);
        toast.setText(text);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
        return toast;
    }

    public static void writeFile(File file, String text) {
        try (FileWriter writer = new FileWriter(file); ) {
            writer.write(text);
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public static String readFile(File file) {
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return buffer.toString();
    }

    public static List<Project> getProjects(Activity activity) {

        List<Project> projects = new ArrayList<>();
        if (requestManagePremission(activity)) {
            File[] files = projectsDir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    projects.add(new Project(file.getName()));
                }
            }
        }
        return projects;
    }
}
