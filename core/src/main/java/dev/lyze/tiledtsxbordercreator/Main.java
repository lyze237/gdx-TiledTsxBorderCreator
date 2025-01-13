package dev.lyze.tiledtsxbordercreator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import dev.lyze.tiledtsxbordercreator.modes.CommandLineMode;
import dev.lyze.tiledtsxbordercreator.modes.InteractiveDesktopMode;
import dev.lyze.tiledtsxbordercreator.modes.InteractiveGwtMode;
import dev.lyze.tiledtsxbordercreator.natives.DragAndDropListener;
import dev.lyze.tiledtsxbordercreator.natives.ICommandLineNatives;
import dev.lyze.tiledtsxbordercreator.natives.IDesktopNatives;
import dev.lyze.tiledtsxbordercreator.natives.IGwtNatives;

public class Main extends Game {
    private String[] args;

    private IDesktopNatives desktopNatives;
    private ICommandLineNatives commandLineNatives;
    private IGwtNatives gwtNatives;
    private DragAndDropListener dragAndDropListener;

    @Override
    public void create() {
        switch (Gdx.app.getType()) {
            case WebGL:
                setScreen(new InteractiveGwtMode(gwtNatives));
                break;
            case Desktop:
                if (args.length > 0) {
                    setScreen(new CommandLineMode(args, commandLineNatives));
                } else {
                    setScreen(new InteractiveDesktopMode(desktopNatives, commandLineNatives, dragAndDropListener));
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

    public void setGwtNatives(IGwtNatives gwtNatives) {
        this.gwtNatives = gwtNatives;
    }

    public void setDragAndDropListener(DragAndDropListener dragAndDropListener) {
        this.dragAndDropListener = dragAndDropListener;
    }
}
