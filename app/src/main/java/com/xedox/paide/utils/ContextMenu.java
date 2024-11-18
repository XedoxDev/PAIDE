package com.xedox.paide.utils;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.PopupMenu;

public class ContextMenu {

    private PopupMenu menu;

    public void add(Item item) {
        menu.getMenu().add(0, item.CODE, Menu.NONE, item.title);
    }

    public void add(int code, String title) {
        menu.getMenu().add(0, code, Menu.NONE, title);
    }

    public void add(int code, int title) {
        menu.getMenu().add(0, code, Menu.NONE, title);
    }

    public ContextMenu(View view, OnClickListener listener) {
        menu = new PopupMenu(view.getContext(), view);
        menu.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        listener.onClick(item, menu);
                        return true;
                    }
                });
    }

    public void show() {
        menu.show();
    }

    public static class Item {
        public int CODE;
        public String title;

        public Item(int CODE, String title) {
            this.CODE = CODE;
            this.title = title;
        }
    }

    public static interface OnClickListener {
        public void onClick(MenuItem item, PopupMenu menu);
    }

    public PopupMenu getMenu() {
        return this.menu;
    }
}
