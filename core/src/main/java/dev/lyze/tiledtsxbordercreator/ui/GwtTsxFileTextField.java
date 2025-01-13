package dev.lyze.tiledtsxbordercreator.ui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisValidatableTextField;
import dev.lyze.tiledtsxbordercreator.natives.IGwtNatives;
import dev.lyze.tiledtsxbordercreator.natives.IGwtNativesCallback;

public class GwtTsxFileTextField extends VisTable implements IGwtNativesCallback {
    private final IGwtNatives natives;
    private final VisValidatableTextField textField = new VisValidatableTextField();

    private StringChangeListener changeListener;

    private String content;

    public GwtTsxFileTextField(IGwtNatives natives) {
        this.natives = natives;

        textField.setReadOnly(true);

        var button = new VisTextButton("Select File");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onClicked();
            }
        });

        textField.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onClicked();
            }
        });

        add(textField).growX().padRight(5);
        add(button);
    }

    private void onClicked() {
        natives.openTsxFilePicker(this);
    }

    public VisValidatableTextField getTextField() {
        return textField;
    }

    public String getText() {
        return textField.getText();
    }

    public void setChangeListener(StringChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public String getContent() {
        return content;
    }

    @Override
    public void pickedTsxFile(String name, String content) {
        textField.setText(name);
        this.content = content;

        if (changeListener != null)
            changeListener.changed(name);
    }

    @Override
    public void pickedImageFile(String name, Pixmap image) {

    }

    @Override
    public void cancelled() {

    }

    @Override
    public void error() {
        Dialogs.showErrorDialog(getStage(), "Error while picking file");
    }
}
