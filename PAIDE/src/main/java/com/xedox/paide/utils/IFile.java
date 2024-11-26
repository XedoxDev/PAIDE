package com.xedox.paide.utils;
import java.io.File;

public interface IFile {
    
    public String TAG = "IFile";
    
    public String read();
    public boolean write(String text);
    
    public boolean mkdirs();
    public boolean mkfile();
    
    public String getPath();
    public IFile getFilePath();
    
    public String getExtension();
    public String getName();
    public String getNameNoExtension();
    
    public String getFullPath();
    public File getFullFile();
    
    public void print(String text);
    public void println(String text);
    
    public boolean exists();
}
