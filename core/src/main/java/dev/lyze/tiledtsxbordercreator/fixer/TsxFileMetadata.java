package dev.lyze.tiledtsxbordercreator.fixer;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.XmlReader.Element;

public class TsxFileMetadata {
    private final int width, height;
    private final int tileWidth, tileHeight;
    private final int spacing, margin;
    private final int tileCount, columns;

    private final Element tsxXml;
    private final Element imageXml;

    public TsxFileMetadata(Element tsxXml) {
        this.tsxXml = tsxXml;
        this.imageXml = tsxXml.getChildByName("image");

        width = imageXml.getInt("width");
        height = imageXml.getInt("height");

        tileWidth = tsxXml.getInt("tilewidth");
        tileHeight = tsxXml.getInt("tilewidth");

        spacing = tsxXml.getInt("spacing", 0);
        margin = tsxXml.getInt("margin", 0);

        tileCount = tsxXml.getInt("tilecount");
        columns = tsxXml.getInt("columns");
    }

    public String generateNewTsxFile(String relativeImagePath, Pixmap image, int border) {
        tsxXml.setAttribute("spacing", String.valueOf(border * 2));
        tsxXml.setAttribute("margin", String.valueOf(border));

        imageXml.setAttribute("source", relativeImagePath);
        imageXml.setAttribute("width", String.valueOf(image.getWidth()));
        imageXml.setAttribute("width", String.valueOf(image.getHeight()));

        return tsxXml.toString();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getSpacing() {
        return spacing;
    }

    public int getMargin() {
        return margin;
    }

    public int getTileCount() {
        return tileCount;
    }

    public int getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        return "TsxFileMetadata [width=" + width + ", height=" + height + ", tileWidth=" + tileWidth + ", tileHeight="
                + tileHeight + ", spacing=" + spacing + ", margin=" + margin + ", tileCount=" + tileCount + ", columns="
                + columns + "]";
    }
}
