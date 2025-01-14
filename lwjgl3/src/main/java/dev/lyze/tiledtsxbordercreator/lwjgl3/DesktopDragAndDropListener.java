package dev.lyze.tiledtsxbordercreator.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowAdapter;
import dev.lyze.tiledtsxbordercreator.natives.DragAndDropListener;

public class DesktopDragAndDropListener extends DragAndDropListener {
    @Override
    public void setupDragAndDrop() {
        var graphics = (Lwjgl3Graphics) Gdx.graphics;
        var window = graphics.getWindow();

        window.setWindowListener(new Lwjgl3WindowAdapter() {
            @Override
            public void filesDropped(String[] files) {
                for (var file : files) {
                    if (file.endsWith(".tsx")) {
                        invokeTsxTarget(file, null);
                    } else if (file.endsWith(".png")) {
                        invokeImageTarget(file, null);
                    } else if (Gdx.files.absolute(file).isDirectory()) {
                        invokeFolderTarget(file);
                    }
                }
            }
        });
    }
}
