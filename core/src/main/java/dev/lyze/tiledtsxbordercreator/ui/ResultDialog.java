package dev.lyze.tiledtsxbordercreator.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.kotcrab.vis.ui.widget.VisDialog;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextArea;
import dev.lyze.tiledtsxbordercreator.fixer.TsxFileFixerResult;

public class ResultDialog extends VisDialog {
    public ResultDialog(TsxFileFixerResult result) {
        super("Result");

        var table = new VisTable();
        table.setFillParent(true);

        var tsxTextArea = new VisTextArea(result.getTsxFile());
        var image = new Image(new Texture(result.getImageFile()));
        image.setScaling(Scaling.fill);

        table.add("Tsx File").growX().left().padRight(12);
        table.add("Image File").growX().left().row();

        table.add(tsxTextArea).size(500, 400).growX().padRight(12);
        table.add(image).size(400, 400).growX();

        getContentTable().add(table).grow();

        button("Ok");
    }
}
