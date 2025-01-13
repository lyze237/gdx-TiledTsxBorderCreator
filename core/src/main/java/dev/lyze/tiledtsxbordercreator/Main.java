package dev.lyze.tiledtsxbordercreator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import dev.lyze.tiledtsxbordercreator.modes.CommandLineMode;
import dev.lyze.tiledtsxbordercreator.modes.InteractiveDesktopMode;
import dev.lyze.tiledtsxbordercreator.modes.InteractiveGwtMode;
import dev.lyze.tiledtsxbordercreator.natives.ICommandLineNatives;
import dev.lyze.tiledtsxbordercreator.natives.IDesktopNatives;

public class Main extends Game {
    private String[] args;

    private IDesktopNatives desktopNatives;
    private ICommandLineNatives commandLineNatives;

    @Override
    public void create() {
        switch (Gdx.app.getType()) {
            case WebGL:
                setScreen(new InteractiveGwtMode());
                break;
            case Desktop:
                if (args.length > 0) {
                    setScreen(new CommandLineMode(args, commandLineNatives));
                } else {
                    setScreen(new InteractiveDesktopMode(desktopNatives));
                }
                break;
            case HeadlessDesktop:
                setScreen(new CommandLineMode(args, commandLineNatives));
                break;
            default:
                throw new IllegalArgumentException("Not a valid app type for this app");
        }
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public void setDesktopNatives(IDesktopNatives desktopNatives) {
        this.desktopNatives = desktopNatives;
    }

    public void setCommandLineNatives(ICommandLineNatives commandLineNatives) {
        this.commandLineNatives = commandLineNatives;
    }
}
