package com.xedox.paide.editor.editors.soraeditor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.xedox.paide.editor.Editor;
import io.github.rosemoe.sora.widget.CodeEditor;

public class SoraEditor extends CodeEditor implements Editor {

    public SoraEditor(Context context) {
        super(context);
    }

    public SoraEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setTextSize(float newSize) {
        super.setTextSize(newSize);
    }

    @Override
    public void setCode(String newCode) {
        super.setText(newCode);
    }

    @Override
    public float getTextSize() {
        return getTextSizePx();
    }

    @Override
    public String getCode() {
        return getText().toString();
    }

    @Override
    public View getView() {
        return this;
    }
}
