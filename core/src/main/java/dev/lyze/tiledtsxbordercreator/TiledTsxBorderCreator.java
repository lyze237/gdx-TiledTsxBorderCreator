package dev.lyze.tiledtsxbordercreator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import dev.lyze.tiledtsxbordercreator.fixer.TsxFileFixer;
import dev.lyze.tiledtsxbordercreator.fixer.TsxFileFixerResult;
import dev.lyze.tiledtsxbordercreator.natives.ICommandLineNatives;

public class TiledTsxBorderCreator {
    private final FileHandle tsxFile;
    private final FileHandle imageFile;

    public TiledTsxBorderCreator(FileHandle tsxFile, FileHandle imageFile) {
        this.tsxFile = tsxFile;
        this.imageFile = imageFile;
    }

    public TsxFileFixerResult convert(String relativeTsxOutputPath, String relativeImageOutputPath, int border) {

        Gdx.app.log("TiledTsxBorderCreator", "Loading tsx " + tsxFile.path() + " with image " + imageFile.path());
        var tsxFileParser = new TsxFileFixer(tsxFile.readString(), new Pixmap(imageFile));

        Gdx.app.log("TiledTsxBorderCreator", "Converting");
        return tsxFileParser.convert(relativeTsxOutputPath, relativeImageOutputPath, border);
    }

    public void writeResult(TsxFileFixerResult result, FileHandle outputFolder, String relativeTsxOutputPath, String relativeImageOutputPath, ICommandLineNatives natives) {
        var outputTsxFile = outputFolder.child(relativeTsxOutputPath);
        var outputImageFile = outputFolder.child(relativeImageOutputPath);

        Gdx.app.log("TiledTsxBorderCreator", "Saving tsx to " + outputTsxFile.path() + " and image to " + outputImageFile.path());
        outputTsxFile.writeString(result.getTsxFileContent(), false);
        natives.savePixmap(outputImageFile, result.getImageFileContent());
    }
}
