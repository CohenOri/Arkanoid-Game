package io;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import biuoop.GUI;
import block.Block;
import geometryprimitives.Velocity;
import level.GenericColorBackground;
import level.GenericImageBackground;
import level.LevelInformation;
import sprites.Sprite;
import utility.ColorsParser;

/**
 * The Class LevelSpecificationReader.
 * Reads level from level definition file and creates them. returns a list of the levels (fromReader method).
 */
public class LevelSpecificationReader implements LevelInformation {
    private GUI gui;

    private String levelName;
    private List<Velocity> ballVelocites;
    private java.awt.Image backgroundImage;
    private java.awt.Color backgroundColor;
    private int paddleSpeed;
    private int paddleWidth;
    private int blocksStartX;
    private int blocksStartY;
    private int rowHeight;
    private int numBlocks;

    private List<Block> blocks;
    private BlocksFromSymbolsFactory bFactory = new BlocksFromSymbolsFactory();
    private LevelSpecificationReader currentLevel;

    /**
     * Instantiates a new level specification reader.
     * @param gui the gui
     */
    public LevelSpecificationReader(GUI gui) {
        this.gui = gui;
        this.ballVelocites = new ArrayList<>();
        this.blocks = new ArrayList<>();
    }

    /**
     * Receives a reader, levelDefintion txt file, reads from it the levels
     * and returns a List<LevelInformation>. list of lvls.
     * @param reader the reader
     * @return list of lvls.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        List<LevelInformation> levelSpec = new ArrayList<>();
        try {
            LineNumberReader readLine = new LineNumberReader(reader);
            String line;
            // read lines till the end of the stream
            while ((line = readLine.readLine()) != null) {
                // remove white spaces from the end of line, if line is comment or empty skip her
                line = line.trim();
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }
                if (line.equals("START_LEVEL")) {
                    this.currentLevel = new LevelSpecificationReader(this.gui);
                }
                if (line.startsWith("level_name:")) {
                    line = line.replace("level_name:", "");
                    line = line.trim();
                    this.currentLevel.levelName = line;
                }
                if (line.startsWith("ball_velocities")) {
                    convertToVelocites(line);
                }
                if (line.startsWith("background")) {
                    // if its color background
                    if (line.startsWith("background:color")) {
                        line = line.replace("background:color(", "");
                        line = line.replace("))", ")");
                        line = line.trim();
                        try {
                            ColorsParser cParser = new ColorsParser();
                            this.currentLevel.backgroundColor = cParser.colorFromString(line);
                        } catch (Exception e) {
                            throw new RuntimeException("invalid Color was set for block: " + line);
                        }
                    }
                    // if its image background
                    if (line.startsWith("background:image")) {
                        convertToImage(line);
                    }
                }
                if (line.startsWith("paddle_speed:")) {
                    line = line.replace("paddle_speed:", "");
                    line = line.trim();
                    this.currentLevel.paddleSpeed = Integer.parseInt(line);
                }
                if (line.startsWith("paddle_width:")) {
                    line = line.replace("paddle_width:", "");
                    line = line.trim();
                    this.currentLevel.paddleWidth = Integer.parseInt(line);
                }
                if (line.startsWith("block_definitions:")) {
                    line = line.replace("block_definitions:", "");
                    line = line.trim();
                    InputStreamReader is = null;
                    try {
                        is = new InputStreamReader(ClassLoader.getSystemResourceAsStream(line));
                        Reader bd = is;
                        this.currentLevel.bFactory = BlockDefinitionsReader.fromReader(bd);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        is.close();
                    }
                }
                if (line.startsWith("blocks_start_x:")) {
                    line = line.replace("blocks_start_x:", "");
                    line = line.trim();
                    this.currentLevel.blocksStartX = Integer.parseInt(line);
                }
                if (line.startsWith("blocks_start_y:")) {
                    line = line.replace("blocks_start_y:", "");
                    line = line.trim();
                    this.currentLevel.blocksStartY = Integer.parseInt(line);
                }
                if (line.startsWith("row_height:")) {
                    line = line.replace("row_height:", "");
                    line = line.trim();
                    this.currentLevel.rowHeight = Integer.parseInt(line);
                }
                if (line.startsWith("num_blocks:")) {
                    line = line.replace("num_blocks:", "");
                    line = line.trim();
                    this.currentLevel.numBlocks = Integer.parseInt(line);
                }
                if (line.startsWith("START_BLOCKS")) {
                    // start reading block layout until END BLOCK
                    int creatorYLocation = this.currentLevel.blocksStartY;
                    while ((line = readLine.readLine()) != null) {
                        // remove white spaces from the end of line, if line is comment skip her
                        line = line.trim();
                        if (line.startsWith("#") || line.isEmpty()) {
                            continue;
                        }
                        if (line.startsWith("END_BLOCKS")) {
                            break;
                        }
                        // if its a blocks&spaceres line break it into chars, read each one of them
                        int creatorXLocation = this.currentLevel.blocksStartX;
                        for (int i = 0; i < line.length(); i++) {
                            char symbol = line.charAt(i);
                            if (this.currentLevel.bFactory.isBlockSymbol(symbol)
                                    || this.currentLevel.bFactory.isSpaceSymbol(symbol)) {
                                // if its block symbol
                                if (this.currentLevel.bFactory.isBlockSymbol(symbol)) {
                                    Block b = this.currentLevel.bFactory.getBlock(symbol, creatorXLocation,
                                            creatorYLocation);
                                    creatorXLocation = (int) (creatorXLocation + b.getRect().getWidth());
                                    currentLevel.addBlock(b);
                                } else {
                                    // if its spacer symbol
                                    creatorXLocation = creatorXLocation
                                            + this.currentLevel.bFactory.getSpaceWidth(symbol);
                                }
                            } else {
                                // if its not spacer and not symbol
                                throw new RuntimeException("symbol: " + symbol + " is not a block and not a spacer!");
                            }
                        }
                        creatorYLocation += this.currentLevel.rowHeight;
                    }
                }
                if (line.startsWith("END_LEVEL")) {
                    // check if the level contain all the necessary stuff, if it is return it
                    if (this.currentLevel.levelName != null && this.currentLevel.ballVelocites != null
                            && this.currentLevel.paddleSpeed >= 0 && this.currentLevel.paddleWidth >= 0
                            && this.currentLevel.blocksStartX >= 0 && this.currentLevel.blocksStartY >= 0
                            && this.currentLevel.rowHeight >= 0
                            && this.currentLevel.numBlocks == this.currentLevel.blocks.size()) {
                        // check if level has background color or image
                        if (this.currentLevel.backgroundColor != null || this.currentLevel.backgroundImage != null) {
                            levelSpec.add(this.currentLevel);
                        }
                    } else {
                        printLevelInfo();
                    }
                }
            }
            readLine.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return levelSpec;
    }

    /**
     * Takes line with image path and makes it backgroundImage of the lvl.
     * @param line with image path.
     * @throws IOException if something went wrong.
     */
    private void convertToImage(String line) throws IOException {
        line = line.replace("background:image(", "");
        line = line.replace(")", "");
        line = line.trim();
        InputStream is = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(line);
            BufferedImage image = ImageIO.read(is);
            this.currentLevel.backgroundImage = image;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
    }

    /**
     * Converts line of ball velocites to actuall velocity and add it to ballVelocites list.
     * @param line line of ball velocites
     */
    private void convertToVelocites(String line) {
        line = line.replace("ball_velocities:", "");
        line = line.trim();
        String[] velocityAsString = line.split(" ");
        for (String s : velocityAsString) {
            String[] singleVelocity = s.split(",");
            // unused initialization, just in order to use "fromAngleAndSpeed"
            Velocity v = new Velocity(0, 0);
            int angle = Integer.parseInt(singleVelocity[0]);
            int speed = Integer.parseInt(singleVelocity[1]);
            this.currentLevel.ballVelocites.add(v.fromAngleAndSpeed(angle, speed));
        }
    }

    /**
     * prints what's wrong with lvl.
     */
    private void printLevelInfo() {
        System.out.println("lvl name: " + this.currentLevel.levelName
                + " number of diffrent ball velocity: " + this.currentLevel.ballVelocites.size()
                + " pad speed: " + this.currentLevel.paddleSpeed + " pad width: "
                + this.currentLevel.paddleWidth + " blocks start x: "
                + this.currentLevel.blocksStartX + " blocks start y: "
                + this.currentLevel.blocksStartY + " row heigtht: " + this.currentLevel.rowHeight
                + " num blocks: " + this.currentLevel.numBlocks
                + " ammount of created blocks in level: " + this.currentLevel.blocks.size()
                + " background color: " + this.currentLevel.backgroundColor + " backgroud image: "
                + this.currentLevel.backgroundImage);
    }

    /**
     * @return number Of Balls.
     */
    public int numberOfBalls() {
        return this.ballVelocites.size();
    }

    /**
     * @return List with the initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls()
     */
    public List<Velocity> initialBallVelocities() {
        return this.ballVelocites;
    }
    /**
     * @return paddleSpeed.
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }
    /**
     * @return paddleWidth.
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }
    /**
     * @return level name as String will be displayed at the top of the screen.
     */
    public String levelName() {
        return this.levelName;
    }
    /**
     * @return a sprite with the background of the level
     */
    public Sprite getBackground() {
        try {
            if (this.backgroundColor != null) {
                GenericColorBackground gBackground = new GenericColorBackground(this.gui, this.backgroundColor);
                return gBackground;
            }
            if (this.backgroundImage != null) {
                GenericImageBackground imageBackground = new GenericImageBackground(this.gui, this.backgroundImage);
                return imageBackground;
            }
        } catch (Exception e) {
            throw new RuntimeException("couldn't create the background: " + e);
        }
        // shoudn't reach here
        System.out.println("couldn't create the background + shoulnd't reach here!!!");
        return null;
    }
    /**
     * @return List contains
     * The Blocks that make up this level, each block contains its size, color and location.
     */
    public List<Block> blocks() {
        return this.blocks;
    }

    /**
     * Adds the block to blocks list.
     * @param b the b
     */
    public void addBlock(Block b) {
        this.blocks.add(b);
    }
    /**
     * @return Number of blocks that should be removed before the level is considered to be "cleared".
     */
    public int numberOfBlocksToRemove() {
        return this.numBlocks;
    }
    /**
     * @return initial ball radius in this level.
     */
    public int ballsRadius() {
        return 5;
    }
    /**
     * @return initial ball color in this level.
     */
    public Color ballsColor() {
        return java.awt.Color.MAGENTA;
    }
}
