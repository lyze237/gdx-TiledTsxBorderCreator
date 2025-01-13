package dev.lyze.tiledtsxbordercreator.ui;

import com.kotcrab.vis.ui.util.form.FormInputValidator;

public class FileEndsInValidator extends FormInputValidator {
    private final String fileEnding;

    public FileEndsInValidator(String fileEnding, String message) {
        super(message);

        this.fileEnding = fileEnding;
    }

    @Override
    protected boolean validate(String input) {
        return input.endsWith(fileEnding);
    }
}
