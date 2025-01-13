package dev.lyze.tiledtsxbordercreator.natives;

import dev.lyze.tiledtsxbordercreator.ui.StringChangeListener;

public class DragAndDropListener {
    private StringChangeListener listener;

    public void setListener(StringChangeListener listener) {
        this.listener = listener;
    }

    public void invokeListener(String value) {
        if (listener != null) {
            listener.changed(value);
        }
    }
}
