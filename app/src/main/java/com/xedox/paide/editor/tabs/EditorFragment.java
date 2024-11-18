package com.xedox.paide.editor.tabs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xedox.paide.editor.Editor;
import com.xedox.paide.editor.editors.soraeditor.SoraEditor;
import com.xedox.paide.utils.SketchFile;

import static android.view.ViewGroup.*;

public class EditorFragment extends Fragment {

    private Editor editor;
    private final byte type;
    private final SketchFile sketch;

    public EditorFragment(byte type, SketchFile sketch) {
        this.type = type;
        this.sketch = sketch;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout parent = (RelativeLayout) (container != null ? container : new RelativeLayout(getActivity()));
        switch (type) {
            case Editor.SORA_EDITOR:
                editor = new SoraEditor(parent.getContext());
                break;
            default:
                throw new IllegalArgumentException("Unknown editor type");
        }

        parent.addView(editor.getView());
        return parent;
    }

    public Editor getEditor() {
        return editor;
    }

    public byte getType() {
        return type;
    }

    public SketchFile getSketch() {
        return sketch;
    }

    public String getNameForTab() {
        return sketch.getName();
    }
}
