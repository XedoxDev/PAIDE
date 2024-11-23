package com.xedox.paide.utils;

public class CodeBinding {
    public static String bind(SketchFile... codes) {
        StringBuilder buffer = new StringBuilder();
        SketchFile main = null;
        for (SketchFile file : codes) {
            if (file.getName() == "main.pde") {
                main = file;
                continue;
            }
            buffer.append(String.format("// file name '%s':\n%s\n", file.getName(), file.read()));
        }
        if (main != null)
            buffer.append(String.format("// file name '%s':\n%s\n", main.getName(), main.read()));
        return buffer.toString();
    }
}
