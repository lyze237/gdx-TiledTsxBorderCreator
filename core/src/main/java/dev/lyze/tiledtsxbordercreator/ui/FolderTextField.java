package dev.lyze.tiledtsxbordercreator.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisValidatableTextField;
import dev.lyze.tiledtsxbordercreator.natives.IDesktopNatives;

public class FolderTextField extends VisTable {
    private final IDesktopNatives natives;
    private final VisValidatableTextField textField = new VisValidatableTextField();

    public FolderTextField(IDesktopNatives natives) {
        this.natives = natives;

        textField.setReadOnly(true);

        var button = new VisTextButton("Select Folder");
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
        var path = natives.openFolderPicker();
        if (path != null) {
            textField.setText(path);
        }
    }

    public VisValidatableTextField getTextField() {
        return textField;
    }

    public String getText() {
        return textField.getText();
    }
}
