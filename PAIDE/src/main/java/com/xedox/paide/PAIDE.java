package com.xedox.paide;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;
import com.xedox.paide.project.Project;
import com.xedox.paide.utils.FastTask;
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.model.ThemeModel;
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.tm4e.core.registry.IThemeSource;
import xedox.assetspp.Assets;

public class PAIDE extends Application {

    public static PAIDE context;
    public static File projectsDir;
    public static String defaultFont = "JetBrainsMono-Regular.ttf";

    public static final byte MANAGE_REQUEST_CODE = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        projectsDir =
                new File(Environment.getExternalStorageDirectory().getPath(), "PAIDE-Projects");
        if (!projectsDir.exists()) projectsDir.mkdir();
        initSchemes();
        copyD8_Jar();
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

    public static void mktest(String text) {
        // off for release apk
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
    
    public static void mkToast(String txt) {
        Toast.makeText(context, txt, Toast.LENGTH_SHORT).show();
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

    public static void initSchemes() {
        FastTask.execute(
                () -> {
                    try {
                        FileProviderRegistry.getInstance()
                                .addFileProvider(new AssetsFileResolver(context.getAssets()));
                        var themeRegistry = ThemeRegistry.getInstance();
                        var name = "processing";
                        var themeAssetsPath = "soraeditor/themes/" + name + ".json";
                        var model =
                                new ThemeModel(
                                        IThemeSource.fromInputStream(
                                                FileProviderRegistry.getInstance()
                                                        .tryGetInputStream(themeAssetsPath),
                                                themeAssetsPath,
                                                null),
                                        name);
                        try {
                            themeRegistry.loadTheme(model);
                        } catch (Exception err) {
                            err.printStackTrace();
                        }
                        ThemeRegistry.getInstance().setTheme("XProcessing");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    GrammarRegistry.getInstance().loadGrammars("soraeditor/languages.json");
                });
    }

    public static void copyD8_Jar() {
        try {
            File d8 = new File(projectsDir, "d8.jar");
            if (!d8.exists()) {
                Assets.from(context).asset("d8.jar").toPath(projectsDir.getAbsolutePath()).copy();
                mktest("d8 successfull copyed");
            }
        } catch (Exception e) {
            mktest(e.toString());
            mktest("d8 unsuccessfull copyed");
        }
    }
}
