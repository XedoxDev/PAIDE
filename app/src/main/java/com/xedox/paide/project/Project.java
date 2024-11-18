package com.xedox.paide.project;

import java.io.File;
import static com.xedox.paide.PAIDE.*;

public class Project {
    
    private String name;
    private File src;
    private File projectDir;

    public Project(String name) {
        this.name = name;
        projectDir = new File(projectsDir, name);
        src = new File(projectDir, "src");
    }

    public void create() {
        projectDir.mkdir();
        src.mkdir();
        File main = new File(projectDir, "src/main.pde");
        try {
            main.createNewFile();
        } catch (Exception err) {
            err.printStackTrace();
        }
        writeFile(
                main,
                """
                void setup() {
                    size(400, 400);
                    // setup programm
                }

                void draw() {
                    fill(#FF0000); // RED
                    rect(0, 0, 100, 100);
                    // render graphics
                }
                """);
    }
    
    public void delete() {
        for(File file : src.listFiles()) {
        	file.delete();
        }
        new File(projectDir, "src").delete();
        projectDir.delete();
    }

    public boolean exists() {
        return projectDir.exists();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getProjectDir() {
        return this.projectDir;
    }

    public void setProjectDir(File projectDir) {
        this.projectDir = projectDir;
    }
}
