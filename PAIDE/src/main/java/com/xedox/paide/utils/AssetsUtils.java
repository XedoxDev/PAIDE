package com.xedox.paide.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.xedox.paide.PAIDE.*;

public class AssetsUtils {
    private static final String TAG = "AssetsUtils";

    public static boolean copyAsset(Context context, String filePath, String toPath) {
        SketchFile path = new SketchFile(toPath);
        SketchFile file = new SketchFile(path, new File(filePath).getName());
        try {
            file.createNewFile();
            readAsset(
                    context,
                    filePath,
                    (r) -> {
                        file.write(r);
                    });
            return true;
        } catch (Exception err) {
            err.printStackTrace();
            Log.e(TAG, err.toString());
            mktest(err.toString());
            return false;
        }
    }

    public interface ReadResult {
        void result(String text);
    }

    public static void readAsset(Context context, String fileName, ReadResult rr) {
        StringBuilder buffer = new StringBuilder();
        try (InputStream is = context.getAssets().open(fileName);
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
            mktest(e.toString());
            return;
        }
    }
}
