package dev.lyze.tiledtsxbordercreator.natives.dragAndDrop;

import com.badlogic.gdx.graphics.Pixmap;

public abstract class ImageTarget {
    public abstract void onDropped(String path, Pixmap image);
}
