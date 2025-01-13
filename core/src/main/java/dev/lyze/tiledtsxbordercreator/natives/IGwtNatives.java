package dev.lyze.tiledtsxbordercreator.natives;

import com.badlogic.gdx.graphics.Pixmap;

public interface IGwtNatives {
    void openTsxFilePicker(IGwtNativesCallback callback);
    void openImageFilePicker(IGwtNativesCallback callback);

    void downloadFile(String name, String content);
    void downloadImage(String name, Pixmap image);
}
