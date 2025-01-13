package dev.lyze.tiledtsxbordercreator.natives;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;

public interface ICommandLineNatives {
    String getAbsolutePath(String path);

    void savePixmap(FileHandle file, Pixmap pixmap);
}
