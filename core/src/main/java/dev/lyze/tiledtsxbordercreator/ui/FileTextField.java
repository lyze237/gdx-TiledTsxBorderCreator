package dev.lyze.tiledtsxbordercreator.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisValidatableTextField;
import dev.lyze.tiledtsxbordercreator.natives.IDesktopNatives;

public class FileTextField extends VisValidatableTextField {
    public FileTextField(IDesktopNatives natives) {
        setReadOnly(true);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                var path = natives.openFilePicker();
                if (path != null) {
                    setText(path);
                }
            }
        });
    }
}
