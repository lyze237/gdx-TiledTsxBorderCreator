package dev.lyze.tiledtsxbordercreator.headless;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import dev.lyze.tiledtsxbordercreator.Main;

/** Launches the headless application. Can be converted into a utilities project or a server application. */
public class HeadlessLauncher {
    public static void main(String[] args) {
        createApplication(args);
    }

    private static Application createApplication(String[] args) {
        // Note: you can use a custom ApplicationListener implementation for the headless project instead of Main.
        var main = new Main();
        main.setArgs(args);
        main.setCommandLineNatives(new CommandLineNatives());
        return new HeadlessApplication(main, getDefaultConfiguration());
    }

    private static HeadlessApplicationConfiguration getDefaultConfiguration() {
        HeadlessApplicationConfiguration configuration = new HeadlessApplicationConfiguration();
        configuration.updatesPerSecond = -1; // When this value is negative, Main#render() is never called.
        //// If the above line doesn't compile, it is probably because the project libGDX version is older.
        //// In that case, uncomment and use the below line.
        //configuration.renderInterval = -1f; // When this value is negative, Main#render() is never called.
        return configuration;
    }
}
