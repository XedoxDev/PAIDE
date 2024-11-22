package com.xedox.paide.tools;

import com.xedox.paide.editor.Editor;
import processing.app.Formatter;
import processing.app.Preferences;
import processing.mode.java.AutoFormat;

public class CodeFormat {

    public static String formate(Editor editor) {
        String sourceCode = editor.getCode();
        String formatCode = sourceCode;
        Preferences.setInteger("editor.tabs.size", 2);
        AutoFormat af = new AutoFormat();
        formatCode = af.format(sourceCode);
        editor.setCode(formatCode);
        return formatCode;
    }
}
