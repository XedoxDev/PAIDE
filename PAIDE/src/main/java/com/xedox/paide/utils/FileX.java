package com.xedox.paide.utils;

import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileX extends File implements IFile {

    public FileX(String name) {
        super(name);
    }

    public FileX(File file) {
        super(file.getParentFile(), file.getName());
    }

    public FileX(String path, String name) {
        super(new File(path), name);
    }

    public FileX(File path, String name) {
        super(path, name);
    }

    public FileX(File path, File name) {
        super(path, name.getName());
    }

    @Override
    public String read() {
        StringBuilder buffer = new StringBuilder();
        try (FileReader isr = new FileReader(this);
                BufferedReader br = new BufferedReader(isr); ) {
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
        } catch (IOException err) {
            Log.e(TAG, err.getMessage());
        }
        return buffer.toString();
    }

    @Override
    public boolean write(String text) {
        try (FileWriter writer = new FileWriter(this); ) {
            writer.write(text);
            return true;
        } catch (Exception err) {
            Log.e(TAG, err.getMessage());
        }
        return false;
    }

    @Override
    public boolean mkfile() {
        try {
            return createNewFile();
        } catch (Exception err) {
            Log.e(TAG, err.getMessage());
        }
        return false;
    }

    @Override
    public IFile getFilePath() {
        return new FileX(getAbsoluteFile().getParentFile());
    }

    @Override
    public String getExtension() {
        String name = getName();
        return name.substring(name.lastIndexOf("."), name.length());
    }

    @Override
    public String getNameNoExtension() {
        String name = getName();
        return name.substring(0, name.lastIndexOf(".") - 1);
    }

    @Override
    public String getFullPath() {
        return getAbsolutePath();
    }

    @Override
    public File getFullFile() {
        return getAbsoluteFile();
    }

    @Override
    public void print(String text) {
        write(read() + text);
    }

    @Override
    public void println(String text) {
        print(text + "\n");
    }
}
