package dev.lyze.tiledtsxbordercreator.natives;

import com.badlogic.gdx.graphics.Pixmap;
import dev.lyze.tiledtsxbordercreator.natives.dragAndDrop.FolderTarget;
import dev.lyze.tiledtsxbordercreator.natives.dragAndDrop.ImageTarget;
import dev.lyze.tiledtsxbordercreator.natives.dragAndDrop.TsxTarget;
import dev.lyze.tiledtsxbordercreator.ui.StringChangeListener;

import java.util.Set;

public abstract class DragAndDropListener {

    private FolderTarget folderTarget;
    private ImageTarget imageTarget;
    private TsxTarget tsxTarget;

    public abstract void setupDragAndDrop();

    public void setFolderTarget(FolderTarget folderTarget) {
        this.folderTarget = folderTarget;
    }

    public void setImageTarget(ImageTarget imageTarget) {
        this.imageTarget = imageTarget;
    }

    public void setTsxTarget(TsxTarget tsxTarget) {
        this.tsxTarget = tsxTarget;
    }

    public void invokeFolderTarget(String path) {
        if (folderTarget != null) {
            folderTarget.onDropped(path);
        }
    }

    public void invokeImageTarget(String path, Pixmap pixmap) {
        if (imageTarget != null) {
            imageTarget.onDropped(path, pixmap);
        }
    }

    public void invokeTsxTarget(String path, String content) {
        if (tsxTarget != null) {
            tsxTarget.onDropped(path, content);
        }
    }
}
