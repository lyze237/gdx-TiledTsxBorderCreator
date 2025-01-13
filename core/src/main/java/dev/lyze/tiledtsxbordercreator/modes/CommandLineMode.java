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

        var tsxFile = Gdx.files.absolute(natives.getAbsolutePath(args[0]));
        var imageFile = Gdx.files.absolute(natives.getAbsolutePath(args[1]));

        var folder = Gdx.files.absolute(natives.getAbsolutePath(args[2]));

        var borderCreator = new TiledTsxBorderCreator(tsxFile, imageFile);
        borderCreator.convert(folder, args[3], args[4], Integer.parseInt(args[5]));

        Gdx.app.exit();
    }
}
