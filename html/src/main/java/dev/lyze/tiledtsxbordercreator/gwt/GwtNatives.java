package dev.lyze.tiledtsxbordercreator.gwt;

import com.badlogic.gdx.graphics.Pixmap;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.ImageElement;
import dev.lyze.tiledtsxbordercreator.natives.IGwtNatives;
import dev.lyze.tiledtsxbordercreator.natives.IGwtNativesCallback;

interface InternalGwtCallback {
    void picked(String name, ImageElement image, IGwtNativesCallback callback);
}

public class GwtNatives implements IGwtNatives, InternalGwtCallback {
    @Override
    public void openTsxFilePicker(IGwtNativesCallback callback) {
        openTsxFile(callback);
    }

    @Override
    public void openImageFilePicker(IGwtNativesCallback callback) {
        openImageFile(this, callback);
    }

    @Override
    public void downloadFile(String name, String content) {
        saveText(name, content);
    }

    @Override
    public void downloadImage(String name, Pixmap image) {
        saveImage(name, image.getCanvasElement());
    }

    public static native void saveImage(String filename, CanvasElement canvas) /*-{
        var link = document.createElement("a");
        link["href"] = canvas.toDataURL("image/png");
        link["download"] = filename;
        link.click();
    }-*/;

    public static native void saveText(String filename, String content) /*-{
        var blob = new Blob([content], {type: 'text/plain'});

        var link = document.createElement("a");
        link["href"] = $wnd.URL.createObjectURL(blob);
        link["download"] = filename;
        link.click();
    }-*/;

    public static native void openTsxFile(IGwtNativesCallback result) /*-{
        var filePicker = document.createElement("input");
        filePicker["type"] = "file";
        filePicker["hidden"] = true;

        filePicker.addEventListener('change', function () {
            var file = this.files[0];
            if (file === undefined) {
                result.@IGwtNativesCallback::cancelled()();
                return;
            }
            var reader = new FileReader();
            reader.onload = function () {
                result.@IGwtNativesCallback::pickedTsxFile(*)(file.name, this.result);
            }
            reader.onerror = function () {
                result.@IGwtNativesCallback::error()();
            }

            reader.readAsText(file);
        }, false);
        filePicker.click();
    }-*/;

    public static native void openImageFile(InternalGwtCallback callback, IGwtNativesCallback result) /*-{
        var filePicker = document.createElement("input");
        filePicker["type"] = "file";
        filePicker["hidden"] = true;
        //filePicker["accept"] = "image/*";

        filePicker.addEventListener('change', function () {
            var file = this.files[0];
            if (file === undefined) {
                result.@IGwtNativesCallback::cancelled()();
                return;
            }
            var reader = new FileReader();
            reader.onload = function () {
                var img = $doc.createElement("img");
                img.crossOrigin = "Anonymous";
                img.src = this.result;

                img.addEventListener('load', function () {
                    callback.@InternalGwtCallback::picked(*)(file.name, img, result);
                });
            }
            reader.onerror = function () {
                result.@IGwtNativesCallback::error()();
            }

            reader.readAsDataURL(file);
        }, false);
        filePicker.click();
    }-*/;

    @Override
    public void picked(String name, ImageElement image, IGwtNativesCallback callback) {
        callback.pickedImageFile(name, new Pixmap(image));
    }
}
