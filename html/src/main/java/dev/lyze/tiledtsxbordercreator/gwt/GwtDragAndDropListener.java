package dev.lyze.tiledtsxbordercreator.gwt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.google.gwt.dom.client.ImageElement;
import dev.lyze.tiledtsxbordercreator.natives.DragAndDropListener;

public class GwtDragAndDropListener extends DragAndDropListener {
    @Override
    public void setupDragAndDrop() {
        exportInstance(this);
    }

    public void dragAndDropTsxFile(String name, String content) {
        Gdx.app.log("DragAndDrop", "Got tsx file: " + name + " with content: " + content);
        invokeTsxTarget(name, content);
    }

    public void dragAndDropImageFile(String name, ImageElement content) {
        Gdx.app.log("DragAndDrop", "Got image file: " + name);
        invokeImageTarget(name, new Pixmap(content));
    }

    private native void exportInstance(GwtDragAndDropListener callback) /*-{
        var canvas = $doc.getElementById('embed-html');
        ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(function(event) {
            canvas.addEventListener(event, function(e) {
                e.preventDefault();
                e.stopPropagation();
            }, false);
        });
        canvas.addEventListener('drop', function(e) {
            Array.from(e.dataTransfer.files).forEach(function(file) {
                var reader = new FileReader();
                if (file.name.endsWith(".tsx")) {
                    reader.onload = function () {
                        callback.@GwtDragAndDropListener::dragAndDropTsxFile(*)(file.name, this.result);
                    }

                    reader.readAsText(file);
                } else if (file.name.endsWith(".png")) {
                    reader.onload = function () {
                        var img = document.createElement("img");
                        img.crossOrigin = "Anonymous";
                        img.src = this.result;

                        img.addEventListener('load', function () {
                            callback.@GwtDragAndDropListener::dragAndDropImageFile(*)(file.name, img);
                        });
                    }

                    reader.readAsDataURL(file);
                }
            });
        }, false);
    }-*/;
}
