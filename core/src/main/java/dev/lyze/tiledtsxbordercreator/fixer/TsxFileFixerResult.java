package dev.lyze.tiledtsxbordercreator.fixer;

import com.badlogic.gdx.graphics.Pixmap;

public class TsxFileFixerResult {
    private final String tsxFile;
    private final Pixmap imageFile;

    public TsxFileFixerResult(String tsxFile, Pixmap imageFile) {
        this.tsxFile = tsxFile;
        this.imageFile = imageFile;
    }

    public String getTsxFile() {
        return tsxFile;
    }

    public Pixmap getImageFile() {
        return imageFile;
    }
}
