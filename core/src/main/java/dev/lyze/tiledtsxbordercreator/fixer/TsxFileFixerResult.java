package dev.lyze.tiledtsxbordercreator.fixer;

import com.badlogic.gdx.graphics.Pixmap;

public class TsxFileFixerResult {
    private final String tsxFilePath;
    private final String tsxFileContent;
    private final String imageFilePath;
    private final Pixmap imageFile;

    public TsxFileFixerResult(String tsxFilePath, String tsxFileContent, String imageFilePath, Pixmap imageFileContent) {
        this.tsxFilePath = tsxFilePath;
        this.tsxFileContent = tsxFileContent;
        this.imageFilePath = imageFilePath;
        this.imageFile = imageFileContent;
    }

    public String getTsxFile() {
        return tsxFilePath;
    }

    public String getTsxFileContent() {
        return tsxFileContent;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public Pixmap getImageFileContent() {
        return imageFile;
    }
}
