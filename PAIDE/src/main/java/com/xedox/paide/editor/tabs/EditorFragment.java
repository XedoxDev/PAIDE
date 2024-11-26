package com.xedox.paide.editor.tabs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.xedox.paide.editor.Editor;
import com.xedox.paide.editor.editors.soraeditor.SoraEditor;
import com.xedox.paide.utils.FileX;

import com.xedox.paide.utils.TextMateLanguage;
// import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;

public class EditorFragment extends Fragment {
    
    public String TAG = "EditorFragment";

    private Editor editor;
    private final byte type;
    private final FileX sketch;
    private TabLayout.Tab tab;

    public EditorFragment(byte type, FileX sketch) {
        this.type = type;
        this.sketch = sketch;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout parent = new RelativeLayout(requireContext());
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);

        switch (type) {
            case Editor.SORA_EDITOR:
                createSoraEditor(layoutParams);
                break;
        }

        parent.addView(editor.getView());
        editor.setCode(sketch.read());
        return parent;
    }

    private void createSoraEditor(FrameLayout.LayoutParams layoutParams) {
        editor = new SoraEditor(requireContext());
        SoraEditor soraEditor = (SoraEditor) editor;
        String extension = sketch.getExtension();
        String languageScopeName = "source" + extension;
        try {
            TextMateLanguage language = new TextMateLanguage(languageScopeName);
            soraEditor.setEditorLanguage(language);
        } catch (Exception e) {
            Log.e("EditorFragment", "Error creating TextMateLanguage: " + languageScopeName, e);
        }

        soraEditor.getView().setLayoutParams(layoutParams);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (editor instanceof SoraEditor) {
            ((SoraEditor) editor).release();
        }
    }

    public Editor getEditor() {
        return editor;
    }

    public byte getType() {
        return type;
    }

    public FileX getSketch() {
        return sketch;
    }

    public String getNameForTab() {
        return sketch.getName();
    }

    public TabLayout.Tab getTab() {
        return tab;
    }

    public void setTab(TabLayout.Tab tab) {
        this.tab = tab;
    }

    public void save() {
        try {
            sketch.write(editor.getCode());
        } catch (Exception e) {
            Log.e(TAG, "Error saving file", e);
        }
    }
}
