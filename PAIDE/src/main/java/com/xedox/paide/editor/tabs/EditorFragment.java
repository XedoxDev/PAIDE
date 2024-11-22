package com.xedox.paide.editor.tabs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.xedox.paide.editor.Editor;
import com.xedox.paide.editor.editors.soraeditor.SoraEditor;
import com.xedox.paide.utils.SketchFile;

import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;

import java.util.HashMap;
import java.util.Map;

public class EditorFragment extends Fragment {

    private Editor editor;
    private byte type;
    private SketchFile sketch;
    private String lang;
    private TabLayout.Tab tab;

    public EditorFragment(byte type, SketchFile sketch) {
        this.type = type;
        this.sketch = sketch;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout parent = new RelativeLayout(requireContext());
        lang = sketch.getName().substring(sketch.getName().lastIndexOf("."));

        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);

        switch (type) {
            case Editor.SORA_EDITOR:
                {
                    editor = new SoraEditor(requireContext());
                    SoraEditor soraEditor = (SoraEditor) editor;
                    String languageScopeName =
                            "source"
                                    + sketch.getName()
                                            .substring(
                                                    sketch.getName().lastIndexOf("."),
                                                    sketch.getName().length());
                    TextMateLanguage language = null;

                    try {
                        language = TextMateLanguage.create(languageScopeName, true);
                    } catch (Exception e) {
                        Log.e(
                                "EditorFragment",
                                "Error creating TextMateLanguage: " + languageScopeName,
                                e);
                    }

                    if (language != null) {
                        soraEditor.setEditorLanguage(language);
                    }
                    editor.getView().setLayoutParams(layoutParams);
                    try {
                        editor.setCode(sketch.read());
                    } catch (Exception e) {
                        Log.e("EditorFragment", "Error reading file content", e);
                    }
                    break;
                }
        }

        parent.addView(editor.getView());

        return parent;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (editor != null && editor instanceof SoraEditor) {
            ((SoraEditor) editor).release();
        }
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

    public TabLayout.Tab getTab() {
        return this.tab;
    }

    public void setTab(TabLayout.Tab tab) {
        this.tab = tab;
    }

    public void save() {
        try {
            sketch.write(editor.getCode());
        } catch (Exception e) {
            Log.e("EditorFragment", "Error saving file", e);
        }
    }
}
