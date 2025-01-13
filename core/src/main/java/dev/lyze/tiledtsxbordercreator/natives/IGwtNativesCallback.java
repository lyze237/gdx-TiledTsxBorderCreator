package dev.lyze.tiledtsxbordercreator.natives;

import com.badlogic.gdx.graphics.Pixmap;

public interface IGwtNativesCallback {
    void pickedTsxFile(String name, String content);
    void pickedImageFile(String name, Pixmap image);

    void cancelled();
    void error();
}
