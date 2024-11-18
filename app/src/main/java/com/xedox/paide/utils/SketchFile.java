package com.xedox.paide.utils;
import java.io.File;

public class SketchFile extends File {
    public SketchFile(String name) {
        super(name);
    }
    
    public SketchFile(File path, String name) {
        super(path, name);
    }
    
    public SketchFile(File f) {
        super(f.getAbsolutePath(), f.getName());
    }
}
