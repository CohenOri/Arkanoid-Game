package io;

import java.util.HashMap;
import java.util.Map;

import block.Block;
import blockdecorate.BlockCreator;

/**
 * A BlocksFromSymbolsFactory for creating Blocks from symbols/spacers from symbols.
 */
public class BlocksFromSymbolsFactory {
    private Map<Character, Integer> spacerWidths;
    private Map<Character, BlockCreator> blocksBySymbol;
    /**
     * Instantiates a new blocks from symbols factory.
     */
    public BlocksFromSymbolsFactory() {
        this.spacerWidths = new HashMap<>();
        this.blocksBySymbol = new HashMap<>();
    }

    /**
     * Checks if it's a space symbol.
     * @param c the symbol
     * @return true, if it's a space symbol
     */
    public boolean isSpaceSymbol(Character c) {
        if (this.spacerWidths.containsKey(c)) {
            if (this.spacerWidths.get(c) >= 0) {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if it's a block symbol.
     * @param c the symbol
     * @return true, if it's a block symbol
     */
    public boolean isBlockSymbol(Character c) {
        if (this.blocksBySymbol.containsKey(c)) {
            if (this.blocksBySymbol.get(c) != null) {
                return true;
            }
        }
        return false;
    }
    /**
     * Returns Block according to the definitions associated with him.
     * The block will be located at position (xpos, ypos).
     * @param c the symbol for the block
     * @param x the x location
     * @param y the y location
     * @return the block
     */
    public Block getBlock(Character c, int x, int y) {
        return this.blocksBySymbol.get(c).create(x, y);
    }

    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     * @param c the symbol
     * @return the space width
     */
    public int getSpaceWidth(Character c) {
        return this.spacerWidths.get(c);
    }

    /**
     * Adds block creator as value to Block given symbol.
     * @param c the symbol
     * @param creator the BlockCreator
     */
    public void addBlockCreator(Character c, BlockCreator creator) {
        this.blocksBySymbol.put(c, creator);
    }

    /**
     * Adds width as value to given Spacer symbol.
     * @param c the symbol
     * @param width the width
     */
    public void addSpacer(Character c, int width) {
        this.spacerWidths.put(c, Integer.valueOf(width));
    }
}