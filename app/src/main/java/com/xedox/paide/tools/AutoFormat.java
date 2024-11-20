package com.xedox.paide.tools;

import com.google.googlejavaformat.java.Formatter;
import com.xedox.paide.editor.Editor;

public class AutoFormat {

    public static String formate(Editor editor) {
        Formatter formatter = new Formatter();
        String sourceCode = editor.getCode();
        String formatCode = sourceCode;
        try {
            formatCode = formatter.formatSource(sourceCode);
        } catch (Exception err) {
            err.printStackTrace();
        }
        editor.setCode(formatCode);
        return formatCode;
    }
}
