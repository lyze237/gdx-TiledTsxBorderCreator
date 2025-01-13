package dev.lyze.tiledtsxbordercreator.lwjgl3;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import dev.lyze.tiledtsxbordercreator.natives.ICommandLineNatives;

import java.io.File;

public class CommandLineNatives implements ICommandLineNatives {
    @Override
    public String getAbsolutePath(String path) {
        return new File(path).getAbsolutePath();
    }

    @Override
    public void savePixmap(FileHandle file, Pixmap pixmap) {
        PixmapIO.writePNG(file, pixmap);
    }
}
