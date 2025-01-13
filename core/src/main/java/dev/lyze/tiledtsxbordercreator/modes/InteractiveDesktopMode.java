package dev.lyze.tiledtsxbordercreator.modes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.util.form.SimpleFormValidator;
import com.kotcrab.vis.ui.widget.*;
import dev.lyze.tiledtsxbordercreator.TiledTsxBorderCreator;
import dev.lyze.tiledtsxbordercreator.natives.DragAndDropListener;
import dev.lyze.tiledtsxbordercreator.natives.ICommandLineNatives;
import dev.lyze.tiledtsxbordercreator.natives.IDesktopNatives;
import dev.lyze.tiledtsxbordercreator.ui.*;

public class InteractiveDesktopMode extends ScreenAdapter {
    private final IDesktopNatives desktopNatives;
    private final ICommandLineNatives commandLineNatives;
    private final DragAndDropListener dragAndDropListener;

    private final Stage stage = new Stage(new ScreenViewport());

    private final SpriteBatch batch = new SpriteBatch();
    private final Texture background = new Texture("background.png");

    public InteractiveDesktopMode(IDesktopNatives desktopNatives, ICommandLineNatives commandLineNatives, DragAndDropListener dragAndDropListener) {
        this.desktopNatives = desktopNatives;
        this.commandLineNatives = commandLineNatives;
        this.dragAndDropListener = dragAndDropListener;

        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        setupUi();
    }

    private void setupUi() {
        VisUI.load();

        var dialog = new VisDialog("Tiled Tsx Border Creator");

        var root = new VisTable();
        dialog.getContentTable().add(root).grow().pad(12);

        var table = new VisTable();
        table.defaults().pad(2);
        root.add(table).expand();

        var convertButton = new VisTextButton("Convert");
        var validator = new SimpleFormValidator(convertButton);

        var validationLabel = new VisLabel();

        var tsxFileTextField = new DesktopFileTextField(desktopNatives);
        var imageFileTextField = new DesktopFileTextField(desktopNatives);

        var outputFolderTextField = new FolderTextField(desktopNatives);
        var outputTsxFileTextField = new VisValidatableTextField();
        var outputImageFileTextField = new VisValidatableTextField();

        var borderTextField = new VisValidatableTextField("4");
        var overrideExistingFiles = new VisCheckBox("Override existing files");

        dragAndDropListener.setListener(new StringChangeListener() {
            @Override
            public void changed(String path) {
                if (path.endsWith(".tsx")) {
                    tsxFileTextField.setPath(path);
                } else if (path.endsWith(".png")) {
                    imageFileTextField.setPath(path);
                } else if (Gdx.files.absolute(path).isDirectory()) {
                    outputFolderTextField.setPath(path);
                }
            }
        });

        validator.custom(tsxFileTextField.getTextField(), new DesktopFileValidator(".tsx", "Tsx file not set or not a .tsx file"));
        validator.custom(imageFileTextField.getTextField(), new DesktopFileValidator(".png", "Image file not set or not a .png file"));
        validator.custom(outputFolderTextField.getTextField(), new DesktopFileValidator("Output folder not set"));
        validator.custom(outputTsxFileTextField, new FileEndsInValidator(".tsx", "Output .tsx file does not end in .tsx"));
        validator.custom(outputImageFileTextField, new FileEndsInValidator(".png", "Output .png file file does not end in .png"));
        validator.integerNumber(borderTextField, "Border must be an number");

        validator.setMessageLabel(validationLabel);

        var textFieldWidth = 400;
        var padBottom = 24;

        table.add(new VisLabel("Tsx file: ")).left().padRight(4);
        table.add(tsxFileTextField).width(textFieldWidth).row();
        tsxFileTextField.setChangeListener(new StringChangeListener() {
            @Override
            public void changed(String newValue) {
                if (tsxFileTextField.getTextField().isInputValid()) {
                    outputTsxFileTextField.setText(Gdx.files.absolute(newValue).name());
                }
            }
        });

        table.add(new VisLabel("Image file: ")).left().padRight(4);
        table.add(imageFileTextField).width(textFieldWidth).row();
        imageFileTextField.setChangeListener(new StringChangeListener() {
            @Override
            public void changed(String newValue) {
                if (imageFileTextField.getTextField().isInputValid()) {
                    outputImageFileTextField.setText(Gdx.files.absolute(newValue).name());
                }
            }
        });

        table.add(new VisLabel("Border Size: ")).left().padRight(4);
        table.add(borderTextField).width(textFieldWidth).row();

        table.add().padBottom(padBottom).row();

        table.add(new VisLabel("Output folder: ")).left().padRight(4);
        table.add(outputFolderTextField).width(textFieldWidth).row();

        table.add(new VisLabel("Output .tsx file: ")).left().padRight(4);
        table.add(outputTsxFileTextField).width(textFieldWidth).row();

        table.add(new VisLabel("Output .png file: ")).left().padRight(4);
        table.add(outputImageFileTextField).width(textFieldWidth).row();

        table.add().padBottom(padBottom).row();

        convertButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onConvertClicked(tsxFileTextField.getText(), imageFileTextField.getText(), outputFolderTextField.getText(), outputTsxFileTextField.getText(), outputImageFileTextField.getText(), Integer.parseInt(borderTextField.getText()), overrideExistingFiles.isChecked());
            }
        });

        var buttonTable = new VisTable();
        table.add(buttonTable).colspan(2).growX();

        buttonTable.add(validationLabel).right().expandX().padRight(12);
        buttonTable.add(overrideExistingFiles).padRight(12);
        buttonTable.add(convertButton).right();

        dialog.show(stage);
    }

    private void onConvertClicked(String tsxPath, String imagePath, String outputFolderPath, String relativeOutputTsxPath, String relativeOutputImagePath, int border, boolean overrideExistingFiles) {
        var outputFolder = Gdx.files.absolute(outputFolderPath);

        if (!overrideExistingFiles) {
            var outputTsxFile = outputFolder.child(relativeOutputTsxPath);
            var outputImageFile = outputFolder.child(relativeOutputImagePath);

            if (outputTsxFile.exists()) {
                Dialogs.showErrorDialog(stage, "Output .tsx file already exists");
                return;
            }

            if (outputImageFile.exists()) {
                Dialogs.showErrorDialog(stage, "Output .png file already exists");
                return;
            }
        }

        var borderCreator = new TiledTsxBorderCreator(Gdx.files.absolute(tsxPath), Gdx.files.absolute(imagePath));
        var result = borderCreator.convert(relativeOutputTsxPath, relativeOutputImagePath, border);
        borderCreator.writeResult(result, outputFolder, relativeOutputTsxPath, relativeOutputImagePath, commandLineNatives);

        var resultDialog = new ResultDialog(result);
        resultDialog.show(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);
        stage.getViewport().apply();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();

        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.begin();
        batch.draw(background, 0, 0, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
