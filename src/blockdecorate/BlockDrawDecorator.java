package blockdecorate;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import block.Block;
import blockdrawers.BlockDrawer;
import blockdrawers.ColoredBlockDrawer;
import blockdrawers.ImagedBlockDrawer;
import blockdrawers.StrokedBlockDrawer;
import utility.ColorsParser;

/**
 * The Class BlockDrawDecorator.
 */
public class BlockDrawDecorator extends BlockCreatorDecorator {
    private Integer hitPointsStateK;
    /** The drawer. */
    private BlockDrawer drawer;
    /** if block has stroke. */
    private boolean isStroke;

     /**
     * Instantiates a new block draw decorator.
     * Receives resource, hitPointsStateK, and isStroke and determine how to draw the block
     * if its stroke draw stroke, if it is resource to fill, determine which fill, default fill or fill-k.
     * @param setDecoratedBlock the decorated block
     * @param resource the resource is a string with color or image
     * @param hitPointsStateK the hit points state K
     * @param isStroke the is stroke
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public BlockDrawDecorator(BlockCreator setDecoratedBlock, String resource, Integer hitPointsStateK,
            boolean isStroke) throws IOException {
        super(setDecoratedBlock);
        this.hitPointsStateK = hitPointsStateK;
        this.isStroke = isStroke;

        // if it is a Stroke Drawer
        if (isStroke) {
            // stroke block is always defined by color (not image)
            try {
                ColorsParser cParser = new ColorsParser();
                this.drawer = new StrokedBlockDrawer(cParser.colorFromString(resource));
            } catch (Exception e) {
                throw new RuntimeException(
                        "Couldn't create StrokedBlockDrawer for some reason resource is: " + resource);
            }
        } else {
            // if not Stroke Drawer = if it a "fill drawer"
            // if resource is the resource to "fill-k" = the is no hitPointsStateK since there is no K.
            if (hitPointsStateK != null) {
                if (resource.startsWith("color(")) {
                    resource = resource.replace("color(", "");
                    resource = resource.replace("))", ")");
                    resource = resource.trim();
                    ColorsParser cParser = new ColorsParser();
                    this.drawer = new ColoredBlockDrawer(cParser.colorFromString(resource));
                } else {
                    // if resource is an image file
                    resource = resource.replace("image(", "");
                    resource = resource.replace(")", "");
                    resource = resource.trim();
                    InputStream is;
                    try {
                        is = ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
                        BufferedImage image = ImageIO.read(is);
                        this.drawer = new ImagedBlockDrawer(image);
                    } catch (Exception e) {
                        throw new RuntimeException("Couldn't read/locate image: " + resource);
                    }
                    is.close();
                }
            } else {
                // if resource is the resource to "fill"
                if (resource.startsWith("color(")) {
                    resource = resource.replace("color(", "");
                    resource = resource.replace("))", ")");
                    resource = resource.trim();
                    ColorsParser cParser = new ColorsParser();
                    this.drawer = new ColoredBlockDrawer(cParser.colorFromString(resource));
                } else {
                    // if resource is an image file
                    resource = resource.replace("image(", "");
                    resource = resource.replace(")", "");
                    resource = resource.trim();
                    InputStream is;
                    try {
                        is = ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
                        BufferedImage image = ImageIO.read(is);
                        this.drawer = new ImagedBlockDrawer(image);
                    } catch (Exception e) {
                        throw new RuntimeException("Couldn't read/locate image: " + resource);
                    }
                    is.close();
                }
            }
        }
    }

    /**
     * Create a block at the specified location.
     * Add drawers in order to later draw the block for each hit point.
     * @param x the x position
     * @param y the y position
     * @return the block
     */
    public Block create(int x, int y) {
        Block b = super.create(x, y);
        // if its stroke drawer, add to as default stroke drawer of block
        if (this.isStroke) {
            b.addStrokeDrawer(this.drawer);
        } else {
            // if its a fill or fill-k drawer
            // if its fill drawer
            if (this.hitPointsStateK == null) {
                b.setDefaultColoredBlockDrawer(this.drawer);
            } else {
                // if its fill-k drawer
                b.addFillDrawer(this.drawer, (int) this.hitPointsStateK);
            }
        }
        return b;
    }
}
