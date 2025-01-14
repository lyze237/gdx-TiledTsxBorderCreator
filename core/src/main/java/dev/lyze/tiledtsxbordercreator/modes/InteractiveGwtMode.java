package dev.lyze.tiledtsxbordercreator.modes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.form.SimpleFormValidator;
import com.kotcrab.vis.ui.widget.*;
import dev.lyze.tiledtsxbordercreator.fixer.TsxFileFixer;
import dev.lyze.tiledtsxbordercreator.natives.DragAndDropListener;
import dev.lyze.tiledtsxbordercreator.natives.IGwtNatives;
import dev.lyze.tiledtsxbordercreator.natives.dragAndDrop.ImageTarget;
import dev.lyze.tiledtsxbordercreator.natives.dragAndDrop.TsxTarget;
import dev.lyze.tiledtsxbordercreator.ui.*;

public class InteractiveGwtMode extends ScreenAdapter {
    private final IGwtNatives gwtNatives;
    private final DragAndDropListener dragAndDropListener;

    private final Stage stage = new Stage(new ScreenViewport());

    private final SpriteBatch batch = new SpriteBatch();
    private final Texture background = new Texture("background.png");

    public InteractiveGwtMode(IGwtNatives gwtNatives, DragAndDropListener dragAndDropListener) {
        this.gwtNatives = gwtNatives;
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

        var tsxFileTextField = new GwtTsxFileTextField(gwtNatives);
        var imageFileTextField = new GwtImageFileTextField(gwtNatives);

        dragAndDropListener.setTsxTarget(new TsxTarget() {
            @Override
            public void onDropped(String path, String content) {
                tsxFileTextField.pickedTsxFile(path, content);
            }
        });
        dragAndDropListener.setImageTarget(new ImageTarget() {
            @Override
            public void onDropped(String path, Pixmap image) {
                imageFileTextField.pickedImageFile(path, image);
            }
        });

        var outputTsxFileTextField = new VisValidatableTextField();
        var outputImageFileTextField = new VisValidatableTextField();

        var borderTextField = new VisValidatableTextField("4");

        validator.custom(tsxFileTextField.getTextField(), new FileEndsInValidator(".tsx", "Tsx file does not end in .tsx"));
        validator.custom(imageFileTextField.getTextField(), new FileEndsInValidator(".png", "Image file does not end in .png"));

        validator.custom(outputTsxFileTextField, new FileEndsInValidator(".tsx", "Output .tsx file file does not end in .tsx"));
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
                    outputTsxFileTextField.setText(newValue);
                }
            }
        });

        table.add(new VisLabel("Image file: ")).left().padRight(4);
        table.add(imageFileTextField).width(textFieldWidth).row();
        imageFileTextField.setChangeListener(new StringChangeListener() {
            @Override
            public void changed(String newValue) {
                if (imageFileTextField.getTextField().isInputValid()) {
                    outputImageFileTextField.setText(newValue);
                }
            }
        });

        table.add(new VisLabel("Border Size: ")).left().padRight(4);
        table.add(borderTextField).width(textFieldWidth).row();

        table.add().padBottom(padBottom).row();

        table.add(new VisLabel("Output .tsx file: ")).left().padRight(4);
        table.add(outputTsxFileTextField).width(textFieldWidth).row();

        table.add(new VisLabel("Output .png file: ")).left().padRight(4);
        table.add(outputImageFileTextField).width(textFieldWidth).row();

        table.add().padBottom(padBottom).row();

        convertButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onConvertClicked(outputTsxFileTextField.getText(), tsxFileTextField.getContent(), imageFileTextField.getImage(), outputImageFileTextField.getText(), Integer.parseInt(borderTextField.getText()));
            }
        });

        var buttonTable = new VisTable();
        table.add(buttonTable).colspan(2).growX();

        buttonTable.add(validationLabel).right().expandX().padRight(12);
        buttonTable.add(convertButton).right();

        dialog.show(stage);
    }

    private void onConvertClicked(String outputTsxFileName, String tsxFileContent, Pixmap imageFileContent, String relativeOutputImagePath, int border) {
        var tsxFileParser = new TsxFileFixer(tsxFileContent, imageFileContent);
        var result = tsxFileParser.convert(outputTsxFileName, relativeOutputImagePath, border);

        var resultDialog = new ResultDialog(result, gwtNatives);
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
