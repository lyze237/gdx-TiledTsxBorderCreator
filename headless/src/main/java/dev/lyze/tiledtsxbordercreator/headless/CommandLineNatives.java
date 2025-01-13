package dev.lyze.tiledtsxbordercreator.headless;

import dev.lyze.tiledtsxbordercreator.natives.ICommandLineNatives;

import java.io.File;

public class CommandLineNatives implements ICommandLineNatives {
    @Override
    public String getAbsolutePath(String path) {
        return new File(path).getAbsolutePath();
    }
}
