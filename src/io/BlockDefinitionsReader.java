package io;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import blockdecorate.BasicBlock;
import blockdecorate.BlockCreator;
import blockdecorate.DecorateOption;

/**
 * Class with the intention to read BlockDefintios file (fromReader).
 * Creates BlocksFromSymbolsFactory file out of it.
 */
public class BlockDefinitionsReader {
    /**
     * Receives file (reader), reads it line by line and returns BlocksFromSymbolsFactory object.
     * BlocksFromSymbolsFactory contains Symbol to BlockCreator or symbols to Spacers widths maps.
     * for example we read that the is a block with symbol a that should be drawn bla bla way, we will
     * read it using BlockDefinitionsReader, create it and store it in our BlocksFromSymbolsFactory.
     * @param reader blocks definition file
     * @return BlocksFromSymbolsFactory object.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        BlocksFromSymbolsFactory bFactory = new BlocksFromSymbolsFactory();
        // map holds all the default value, for example "height:20" is default
        // so key = height, val = 20
        // and latter we will add the detail to each block
        Map<String, String> defaultValues = new HashMap<>();
        // maps that holds block information as string, in order to later
        // convert it to block creators
        // first map holds also symbol:a and the second omit that.
        Map<String, String> specificBlockWithSymbolMap = new HashMap<>();
        Map<String, String> specificBlockWithoutSymbolMap = new HashMap<>();
        // Maps to hold spacer information first map holds also symbol:* and the
        // second omit that.
        Map<String, String> specificSpacerWithSymbolMap = new HashMap<>();
        Map<String, String> specificSpacerWithoutSymbolMap = new HashMap<>();
        try {
            LineNumberReader readLine = new LineNumberReader(reader);
            String line;
            // read lines till the end of the stream
            while ((line = readLine.readLine()) != null) {
                // remove white spaces from the end of line
                line = line.trim();
                // if line is comment or empty skip her
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }
                if (line.startsWith("default")) {
                    line = line.replace("default", "");
                    line = line.trim();
                    // split line into words (each word is key:value form)
                    String[] split = line.split(" ");
                    for (int i = 0; i < split.length; i++) {
                        // keyAndVal[0] = key, keyAndVal[1] = val
                        String[] keyAndVal = split[i].trim().split(":");
                        defaultValues.put(keyAndVal[0], keyAndVal[1]);
                    }
                }
                if (line.startsWith("bdef")) {
                    line = line.replace("bdef", "");
                    line = line.trim();
                    // split line into words (each word is key:value form)
                    String[] split = line.split(" ");
                    for (int i = 0; i < split.length; i++) {
                        // keyAndVal[0] = key, keyAndVal[1] = val
                        String[] keyAndVal = split[i].trim().split(":");
                        specificBlockWithSymbolMap.put(keyAndVal[0], keyAndVal[1]);
                    }
                    // holds the block's symbol (a, b , t...)
                    Character symbol = specificBlockWithSymbolMap.get("symbol").charAt(0);
                    // add all the data of block + default "data" to
                    // blockWithoutSymbolMap
                    // first add defaults
                    specificBlockWithoutSymbolMap.putAll(defaultValues);
                    // add specific to block properties, in case of overlap
                    // between default to specific,
                    // prefer specific (added to map later therefore replaced
                    // the old values).
                    specificBlockWithoutSymbolMap.putAll(specificBlockWithSymbolMap);
                    // remove the symbol from blockWithoutSymbolMap
                    specificBlockWithoutSymbolMap.remove("symbol");
                    // now we will create BlockCreator to define our block
                    // and save the symbol as the key for the block creator
                    // decorate the block as needed
                    BlockCreator b = new BasicBlock();
                    for (String key : specificBlockWithoutSymbolMap.keySet()) {
                        b = DecorateOption.decorate(b, key, specificBlockWithoutSymbolMap.get(key));
                    }
                    // add the block to BlockFactory if it is a valid block
                    if (symbol != null && Integer.valueOf(specificBlockWithoutSymbolMap.get("height")) >= 0
                            && Integer.valueOf(specificBlockWithoutSymbolMap.get("width")) >= 0
                            && Integer.valueOf(specificBlockWithoutSymbolMap.get("hit_points")) >= 0
                            && checkBlockFillLegit(specificBlockWithoutSymbolMap)) {
                        bFactory.addBlockCreator(symbol, b);
                        // clear specificBlockWithSymbolMap in order to later store brand new data.
                        specificBlockWithSymbolMap.clear();
                        specificBlockWithoutSymbolMap.clear();
                    } else {
                        System.out.println(
                                "block... " + symbol + Integer.valueOf(specificBlockWithoutSymbolMap.get("height"))
                                        + Integer.valueOf(specificBlockWithoutSymbolMap.get("width"))
                                        + Integer.valueOf(specificBlockWithoutSymbolMap.get("hit_points"))
                                        + specificBlockWithoutSymbolMap.get("fill"));
                        throw new RuntimeException("couldn't read block with symbol: "
                                        + symbol + " correctly. \nMaybe do you see the problem?");
                    }
                }
                if (line.startsWith("sdef")) {
                    line = line.replace("sdef", "");
                    line = line.trim();
                    // split line into words (each word is key:value form)
                    String[] split = line.split(" ");
                    for (int i = 0; i < split.length; i++) {
                        // keyAndVal[0] = key, keyAndVal[1] = val
                        String[] keyAndVal = split[i].trim().split(":");
                        specificSpacerWithSymbolMap.put(keyAndVal[0], keyAndVal[1]);
                    }
                    // holds the Spacer's symbol (*, ^...)
                    Character symbol = specificSpacerWithSymbolMap.get("symbol").charAt(0);
                    // add all the data from spacerWithSymbolMap
                    specificSpacerWithoutSymbolMap.putAll(specificSpacerWithSymbolMap);
                    // remove the symbol from spacerWithoutSymbolMap
                    specificSpacerWithoutSymbolMap.remove("symbol");
                    // add the spacer to BlockFactory (which is also
                    // SpacerFactory...) if it is valid spacer
                    if (symbol != null && Integer.valueOf(specificSpacerWithoutSymbolMap.get("width")) != null) {
                        bFactory.addSpacer(symbol, Integer.valueOf(specificSpacerWithoutSymbolMap.get("width")));
                        specificSpacerWithoutSymbolMap.clear(); // check it
                        specificSpacerWithSymbolMap.clear(); // check it
                    } else {
                        throw new RuntimeException("couldn't read spacer with symbol: " + symbol + "correctly");
                    }
                }
            }
            return bFactory;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("some other problem while reading BlockDefinitions file...");
        }
    }
    /**
     * Checks if block's fill definition is legit,
     * if it does not have default fill and amount of hitPoints > amount of fill-k definitions returns false.
     * @param m - map holding the specific block without symbol map - contains the fill data.
     * @return true - legit or false not legit.
     */
    private static boolean checkBlockFillLegit(Map<String, String> m) {
        List<String> keyList = new ArrayList<>(m.keySet());
        int fillCounter = 0;
        for (int i = 0; i < keyList.size(); i++) {
            if (keyList.get(i).equals("fill")) {
                return true;
            }
            if (keyList.get(i).startsWith("fill")) {
                fillCounter++;
            }
        }
        if (fillCounter == Integer.valueOf(m.get("hit_points"))) {
            return true;
        }
        return false;
    }
}
