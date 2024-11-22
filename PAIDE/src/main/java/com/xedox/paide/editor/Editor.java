package com.xedox.paide.editor;
import android.view.View;

public interface Editor {
    public static final byte SORA_EDITOR = 1;
    public static final byte APDE_EDITOR = 2; // SOON...
    
    public void setTextSize(float newSize);
    public void setCode(String newCode);
    
    public void undo();
    public void redo();
    
    public float getTextSize();
    public String getCode();
    
    public View getView();
    
    public void addChangesListener(ChangesListener cl); // for tab add "*"
    
    public static interface ChangesListener {
        public void change();
    }
}
