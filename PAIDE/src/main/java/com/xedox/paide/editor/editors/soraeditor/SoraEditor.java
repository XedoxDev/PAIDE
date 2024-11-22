package com.xedox.paide.editor.editors.soraeditor;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import com.xedox.paide.editor.Editor;
import com.xedox.paide.editor.Editor.ChangesListener;
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry;
import io.github.rosemoe.sora.widget.CodeEditor;
import static com.xedox.paide.PAIDE.*;

public class SoraEditor extends CodeEditor implements Editor {

    public SoraEditor(Context context) {
        super(context);
        init();
    }

    public SoraEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setTypefaceText(
                Typeface.createFromAsset(
                        getContext().getAssets(), "general/" + defaultFont)); // default font
        try {
            setColorScheme(TextMateColorScheme.create(ThemeRegistry.getInstance()));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    
    private ChangesListener cl;

    @Override
    public void addChangesListener(ChangesListener cl) {
        this.cl = cl;
    }
}
