package dev.lyze.tiledtsxbordercreator.ui;

import com.badlogic.gdx.Gdx;
import com.kotcrab.vis.ui.util.form.FormInputValidator;

public class DesktopFileValidator extends FormInputValidator {
    private final String fileEnding;

    public DesktopFileValidator(String fileEnding, String message) {
        super(message);

        this.fileEnding = fileEnding;
    }

    public DesktopFileValidator(String message) {
        this(null, message);
    }

    @Override
    protected boolean validate(String input) {
        return (fileEnding == null || input.endsWith(fileEnding)) && Gdx.files.absolute(input).exists();
    }
}
