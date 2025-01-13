# gdx-TiledTsxBorderCreator

A project to add "padding" to tiled tilesets.

This prevents opengl "artifacts" like those ugly "lines" when zooming in or out.

### Screenshots

| Desktop Interface                      | Browser Interface                      | Result Interface                     | Console Interface                      |
|----------------------------------------|----------------------------------------|--------------------------------------|----------------------------------------|
| ![Desktop Interface](docs/desktop.png) | ![Browser Interface](docs/browser.png) | ![Result Interface](docs/result.png) | ![Console Interface](docs/console.png) |

### How to run:

#### Make sure that you have [java jdk 17+](https://bell-sw.com/pages/downloads/￥) installed

There are four ways to run the app:

1. Interactive desktop mode (TODO):
    1. Download the desktop jar from the [releases](https://github.com/lyze237/gdx-TiledTsxBorderCreator/releases) page
    2. Run it via `java -jar filename.jar`
2. Commandline mode (TODO):
    1. Download the desktop jar or headless jar (for when your computer doesn't have a graphics card. For example a vm,
       github actions or docker) from the [releases](https://github.com/lyze237/gdx-TiledTsxBorderCreator/releases) page
    2. Run `java -jar filename.jar --help` to see a help screen with what parameters you need to use.
    3. Run it via `java -jar filename.jar input.tsx input.png outputfolder out.tsx out.png bordersize`
3. Website (TODO):
    1. Open https://lyze237.github.io/gdx-TiledTsxBorderCreator/
4. Gradle task:
    1. TODO

### Run from source:

1. Clone the repo
2. Run `./gradlew.bat jar` to create jar files in `lwjgl3/build/libs` or `headless/build/libs`
3. Then refer to "How to run" with either one of the two jar files
