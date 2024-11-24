package com.xedox.paide.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class SketchFile extends File {
    public SketchFile(String name) {
        super(name);
    }
    
    public SketchFile(File path, String name) {
        super(path, name);
    }
    
    public SketchFile(String path, String name) {
        super(new File(path), name);
    }
    
    public SketchFile(File f) {
        super(f.getAbsoluteFile(), f.getName());
    }
    
    public String read() {
        StringBuilder buffer = new StringBuilder();
        try(var br = new BufferedReader(new FileReader(this))) {
        	String line;
            while((line=br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
        } catch(Exception err) {
        	err.printStackTrace();
        }
        return buffer.toString();
    }
    
    public void write(String text) {
        try (FileWriter fw = new FileWriter(this)) {
        	fw.write(text);
        } catch(Exception err) {
        	err.printStackTrace();
        }
    }
    
    public String getNameNoExtension() {
        String name = super.getName();
        return name.substring(0, name.lastIndexOf(".")-1);
    }
    
    public String getExtension() {
        String name = super.getName();
        return name.substring(name.lastIndexOf("."), name.length());
    }
}
