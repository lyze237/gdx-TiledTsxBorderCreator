package dev.lyze.tiledtsxbordercreator.ui;

import com.badlogic.gdx.Gdx;
import com.kotcrab.vis.ui.util.form.FormInputValidator;

public class AbsoluteFileInputValidator extends FormInputValidator {
    public AbsoluteFileInputValidator(String message) {
        super(message);
    }

    @Override
    protected boolean validate(String input) {
        return Gdx.files.absolute(input).exists();
    }
}
