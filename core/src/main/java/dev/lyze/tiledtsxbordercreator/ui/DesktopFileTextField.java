package dev.lyze.tiledtsxbordercreator.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisValidatableTextField;
import dev.lyze.tiledtsxbordercreator.natives.IDesktopNatives;
import dev.lyze.tiledtsxbordercreator.utils.StringUtils;

public class DesktopFileTextField extends VisTable {
    private final IDesktopNatives natives;
    private final VisValidatableTextField textField = new VisValidatableTextField();

    private final VisValidatableTextField displayTextField = new VisValidatableTextField();

    private StringChangeListener changeListener;

    public DesktopFileTextField(IDesktopNatives natives) {
        this.natives = natives;

        textField.setReadOnly(true);

        var button = new VisTextButton("Select File");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onClicked();
            }
        });

        displayTextField.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onClicked();
            }
        });

        add(displayTextField).growX().padRight(5);
        add(button);
    }

    private void onClicked() {
        var path = natives.openFilePicker();
        if (path != null) {
            textField.setText(path);
            displayTextField.setText(StringUtils.substringFromRight(path, 42));

            if (changeListener != null)
                changeListener.changed(path);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        displayTextField.setInputValid(textField.isInputValid());
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
}
