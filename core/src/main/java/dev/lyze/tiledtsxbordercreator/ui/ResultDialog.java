package dev.lyze.tiledtsxbordercreator.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.kotcrab.vis.ui.widget.VisDialog;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextArea;
import com.kotcrab.vis.ui.widget.VisTextButton;
import dev.lyze.tiledtsxbordercreator.fixer.TsxFileFixerResult;
import dev.lyze.tiledtsxbordercreator.natives.IGwtNatives;

public class ResultDialog extends VisDialog {
    public ResultDialog(TsxFileFixerResult result) {
        this(result, null);
    }

    public ResultDialog(TsxFileFixerResult result, IGwtNatives gwtNatives) {
        super("Result");

        var root = new VisTable();
        getContentTable().add(root).grow().pad(12);

        var table = new VisTable();
        table.defaults().pad(2);
        root.add(table).expand();

        var tsxTextArea = new VisTextArea(result.getTsxFileContent());
        tsxTextArea.setReadOnly(true);
        var image = new Image(new Texture(result.getImageFileContent()));
        image.setScaling(Scaling.fill);

        table.add(".tsx file").growX().left().padRight(12);
        table.add(".png file").growX().left().row();

        table.add(tsxTextArea).size(500, 400).growX().padRight(12);
        table.add(image).size(400, 400).growX().row();

        table.add().padBottom(24).row();

        var download = new VisTextButton("Download");
        download.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gwtNatives.downloadFile(result.getTsxFile(), result.getTsxFileContent());
                gwtNatives.downloadImage(result.getImageFilePath(), result.getImageFileContent());
            }
        });
        var close = new VisTextButton("Close");
        close.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                close();
            }
        });

        var buttonTable = new VisTable();
        buttonTable.defaults().pad(2);

        buttonTable.add().growX();
        if (gwtNatives != null) {
            buttonTable.add(download);
        }
        buttonTable.add(close);

        table.add(buttonTable).colspan(2).growX();
    }
}
