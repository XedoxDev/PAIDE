package com.xedox.paide.editor.tabs;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.xedox.paide.editor.Editor;
import com.xedox.paide.utils.SketchFile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TabsAdapter extends FragmentStateAdapter {

    private List<EditorFragment> list;
    private Context context;

    public TabsAdapter(FragmentActivity context) {
        super(context);
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Fragment createFragment(int pos) {
        return list.get(pos);
    }

    public void add(SketchFile file) {
        var ef = new EditorFragment(Editor.SORA_EDITOR, file);
        ef.getEditor().setCode(file.read());
        list.add(ef);
        
        notifyItemInserted(list.size() - 1);
    }

    public void remove(int pos) {
        list.remove(pos);
        notifyItemRemoved(pos);
    }

    public EditorFragment getEditorFragment(int pos) {
        return list.get(pos);
    }

    public List<EditorFragment> getEditors() {
        return this.list;
    }

    public void setList(List<EditorFragment> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    
    public String getNameForTab(int pos) {
        return list.get(pos).getNameForTab();
    }
}
