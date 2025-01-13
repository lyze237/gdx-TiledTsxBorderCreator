package dev.lyze.tiledtsxbordercreator.modes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.form.FormValidator;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisValidatableTextField;
import dev.lyze.tiledtsxbordercreator.TiledTsxBorderCreator;
import dev.lyze.tiledtsxbordercreator.natives.IDesktopNatives;
import dev.lyze.tiledtsxbordercreator.ui.*;

public class InteractiveDesktopMode extends ScreenAdapter {
    private final IDesktopNatives desktopNatives;

    private final Stage stage = new Stage(new ScreenViewport());

    public InteractiveDesktopMode(IDesktopNatives desktopNatives) {
        this.desktopNatives = desktopNatives;

        setupUi();
    }

    private void setupUi() {
        VisUI.load();

        var root = new VisTable();
        root.defaults().padBottom(8);
        root.setFillParent(true);
        stage.addActor(root);

        var inner = new VisTable();
        inner.setBackground("window-bg");
        root.add(inner).fill();

        var table = new VisTable();
        inner.add(table).pad(12).expand().fill();

        var convertButton = new VisTextButton("Convert");
        var validator = new FormValidator(convertButton);

        var validationLabel = new VisLabel();

        var tsxFileTextField = new FileTextField(desktopNatives);
        var imageFileTextField = new FileTextField(desktopNatives);

        var outputFolderTextField = new FolderTextField(desktopNatives);
        var outputTsxFileTextField = new VisValidatableTextField();
        var outputImageFileTextField = new VisValidatableTextField();

        var borderTextField = new VisValidatableTextField("4");

        validator.custom(tsxFileTextField, new AbsoluteFileInputValidator("Tsx file not set"));
        validator.custom(imageFileTextField, new AbsoluteFileInputValidator("Image file not set"));
        validator.custom(outputFolderTextField, new AbsoluteFileInputValidator("Output folder not set"));
        validator.custom(outputTsxFileTextField, new FileEndsInValidator(".tsx", "Output tsx file does not end in .tsx"));
        validator.custom(outputImageFileTextField, new FileEndsInValidator(".png", "Output file file does not end in .png"));
        validator.integerNumber(borderTextField, "Border must be an number");

        validator.setMessageLabel(validationLabel);

        table.add(new VisLabel("Tsx File: ")).left().padRight(4);
        table.add(tsxFileTextField).width(600).row();

        table.add(new VisLabel("Image File: ")).left().padRight(4);
        table.add(imageFileTextField).width(600).row();

        table.add().padBottom(24).row();

        table.add(new VisLabel("Border Size: ")).left().padRight(4);
        table.add(borderTextField).width(600).row();

        table.add().padBottom(24).row();

        table.add(new VisLabel("Output Folder: ")).left().padRight(4);
        table.add(outputFolderTextField).width(600).row();

        table.add(new VisLabel("Relative output .tsx file: ")).left().padRight(4);
        table.add(outputTsxFileTextField).width(600).row();

        table.add(new VisLabel("Relative output image file: ")).left().padRight(4);
        table.add(outputImageFileTextField).width(600).row();

        table.add().padBottom(24).row();

        convertButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onConvertClicked(tsxFileTextField.getText(), imageFileTextField.getText(), outputFolderTextField.getText(), outputTsxFileTextField.getText(), outputImageFileTextField.getText(), Integer.parseInt(borderTextField.getText()));
            }
        });

        var buttonTable = new VisTable();
        table.add(buttonTable).colspan(2).growX();

        buttonTable.add(validationLabel).right().expandX().padRight(12);
        buttonTable.add(convertButton).right();
    }

    private void onConvertClicked(String tsxPath, String imagePath, String outputFolderPath, String relativeOutputTsxPath, String relativeOutputImagePath, int border) {
        var borderCreator = new TiledTsxBorderCreator(Gdx.files.absolute(tsxPath), Gdx.files.absolute(imagePath));
        var result = borderCreator.convert(Gdx.files.absolute(outputFolderPath), relativeOutputTsxPath, relativeOutputImagePath, border);

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

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
