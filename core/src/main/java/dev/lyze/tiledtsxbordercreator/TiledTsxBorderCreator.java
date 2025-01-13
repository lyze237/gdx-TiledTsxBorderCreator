package dev.lyze.tiledtsxbordercreator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import dev.lyze.tiledtsxbordercreator.fixer.TsxFileFixer;
import dev.lyze.tiledtsxbordercreator.fixer.TsxFileFixerResult;

public class TiledTsxBorderCreator {
    private final FileHandle tsxFile;
    private final FileHandle imageFile;

    public TiledTsxBorderCreator(FileHandle tsxFile, FileHandle imageFile) {
        this.tsxFile = tsxFile;
        this.imageFile = imageFile;
    }

    public TsxFileFixerResult convert(FileHandle outputFolder, String relativeTsxOutputPath, String relativeImageOutputPath, int border) {
        var outputTsxFile = outputFolder.child(relativeTsxOutputPath);
        var outputImageFile = outputFolder.child(relativeImageOutputPath);

        Gdx.app.log("TiledTsxBorderCreator", "Loading tsx " + tsxFile.path() + " with image " + imageFile.path());
        var tsxFileParser = new TsxFileFixer(tsxFile.readString(), new Pixmap(imageFile));

        Gdx.app.log("TiledTsxBorderCreator", "Converting");
        var result = tsxFileParser.convert(relativeImageOutputPath, border);

        Gdx.app.log("TiledTsxBorderCreator", "Saving tsx to " + outputTsxFile.path() + " and image to " + outputImageFile.path());
        outputTsxFile.writeString(result.getTsxFile(), false);
        PixmapIO.writePNG(outputImageFile, result.getImageFile());

        return result;
    }
}
