package com.xedox.paide.utils;

public class CodeBinding {
    public static String bind(FileX... codes) {
        StringBuilder buffer = new StringBuilder();
        FileX main = null;
        for (FileX file : codes) {
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
