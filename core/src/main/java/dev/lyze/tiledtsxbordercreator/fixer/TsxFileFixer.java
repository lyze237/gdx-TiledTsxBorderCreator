package dev.lyze.tiledtsxbordercreator.fixer;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class TsxFileFixer {
    private final Pixmap imageFile;

    private final Element tsx;
    private final TsxFileMetadata metadata;

    public TsxFileFixer(String tsxFile, Pixmap imageFile) {
        this.imageFile = imageFile;

        var xml = new XmlReader();
        tsx = xml.parse(tsxFile);

        metadata = new TsxFileMetadata(tsx);
    }

    public TsxFileFixerResult convert(String relativeTsxOutputPath, String relativeImageOutputPath, int border) {
        var tiles = unpackImage(metadata, imageFile);
        var newTiles = addBorderToTiles(tiles, border);
        disposeTiles(tiles);

        var newImage = repackImage(newTiles);
        disposeTiles(newTiles);

        var newTsxFile = metadata.generateNewTsxFile(relativeImageOutputPath, newImage, border);
        return new TsxFileFixerResult(relativeTsxOutputPath, newTsxFile, relativeImageOutputPath, newImage);
    }

    public Pixmap repackImage(Array<Pixmap> tiles) {
        var tile = tiles.get(0);
        var pixmap = new Pixmap(tile.getWidth() * metadata.getColumns(), tile.getHeight() * (tiles.size / metadata.getColumns()), tiles.get(0).getFormat());

        for (int i = 0; i < tiles.size; i++) {
            var x = i % metadata.getColumns();
            var y = i / metadata.getColumns();

            pixmap.drawPixmap(tiles.get(i), tile.getWidth() * x, tile.getHeight() * y);
        }

        return pixmap;
    }

    private Array<Pixmap> unpackImage(TsxFileMetadata metadata, Pixmap image) {
        var tiles = new Array<Pixmap>();

        for (int i = 0; i < metadata.getTileCount(); i++) {
            var tile = new Pixmap(metadata.getTileWidth(), metadata.getTileHeight(), image.getFormat());

            var x = i % metadata.getColumns();
            var y = i / metadata.getColumns();

            tile.drawPixmap(image, 0, 0, metadata.getMargin() + metadata.getSpacing() * x + metadata.getTileWidth() * x, metadata.getMargin() + metadata.getSpacing() * y + metadata.getTileHeight() * y, metadata.getTileWidth(), metadata.getTileHeight());
            tiles.add(tile);
        }

        return tiles;
    }

    private Array<Pixmap> addBorderToTiles(Array<Pixmap> tiles, int border) {
        var newTiles = new Array<Pixmap>();

        for (var tile : tiles) {
            var newTile = new Pixmap(tile.getWidth() + 2 * border, tile.getHeight() + 2 * border, tile.getFormat());
            newTile.drawPixmap(tile, border, border);

            for (int x = 0; x < tile.getWidth(); x++) {
                int colorTop = tile.getPixel(x, 0);
                int colorBottom = tile.getPixel(x, tile.getHeight() - 1);
                for (int y = 0; y < border; y++) {
                    newTile.drawPixel(x + border, y, colorTop); // Top border
                    newTile.drawPixel(x + border, tile.getHeight() + border + y, colorBottom); // Bottom border
                }
            }

            for (var y = 0; y < tile.getHeight(); y++) {
                var colorLeft = tile.getPixel(0, y);
                var colorRight = tile.getPixel(tile.getWidth() - 1, y);
                for (int x = 0; x < border; x++) {
                    newTile.drawPixel(x, y + border, colorLeft); // Left border
                    newTile.drawPixel(tile.getWidth() + border + x, y + border, colorRight); // Right border
                }
            }

            int colorTopLeft = tile.getPixel(0, 0);
            int colorTopRight = tile.getPixel(tile.getWidth() - 1, 0);
            int colorBottomLeft = tile.getPixel(0, tile.getHeight() - 1);
            int colorBottomRight = tile.getPixel(tile.getWidth() - 1, tile.getHeight() - 1);

            for (int x = 0; x < border; x++) {
                for (int y = 0; y < border; y++) {
                    newTile.drawPixel(x, y, colorTopLeft); // Top-left corner
                    newTile.drawPixel(tile.getWidth() + border + x, y, colorTopRight); // Top-right corner
                    newTile.drawPixel(x, tile.getHeight() + border + y, colorBottomLeft); // Bottom-left corner
                    newTile.drawPixel(tile.getWidth() + border + x, tile.getHeight() + border + y, colorBottomRight); // Bottom-right corner
                }
            }

            newTiles.add(newTile);
        }

        return newTiles;
    }

    private void disposeTiles(Array<Pixmap> tiles) {
        for (var tile : tiles)
            tile.dispose();
    }
}
