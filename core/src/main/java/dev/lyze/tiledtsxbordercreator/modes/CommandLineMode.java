package dev.lyze.tiledtsxbordercreator.modes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import dev.lyze.tiledtsxbordercreator.TiledTsxBorderCreator;
import dev.lyze.tiledtsxbordercreator.natives.ICommandLineNatives;

public class CommandLineMode extends ScreenAdapter {
    public CommandLineMode(String[] args, ICommandLineNatives natives) {
        if (args.length != 6) {
            throw new IllegalArgumentException("Command arguments should be: tsxFilePath imageFilePath outputFolder relativeTsxOutputPath relativeImageOutputPath border");
        }

        var tsxArg = args[0];
        var imageArg = args[1];
        var folderArg = args[2];
        var outputTsxArg = args[3];
        var outputImageArg = args[4];
        var borderArg = Integer.parseInt(args[5]);

        var tsxFile = Gdx.files.absolute(natives.getAbsolutePath(tsxArg));
        var imageFile = Gdx.files.absolute(natives.getAbsolutePath(imageArg));

        var folder = Gdx.files.absolute(natives.getAbsolutePath(folderArg));

        var borderCreator = new TiledTsxBorderCreator(tsxFile, imageFile);
        var result = borderCreator.convert(outputTsxArg, outputImageArg, borderArg);
        borderCreator.writeResult(result, folder, outputTsxArg, outputImageArg, natives);

        Gdx.app.exit();
    }
}
