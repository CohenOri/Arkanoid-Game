package io;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import biuoop.GUI;
import level.LevelInformation;

/**
 * The Class LevelSetReader. reads levelset file and chooses which one to run.
 * e - to easy level set
 * h - to hard level set
 */
public class LevelSetReader {
    // key is the key to level set, (a, b, c...) and the value is the
    // description (hard, easy)
    private Map<Character, String> levelSetDescription = new HashMap<>();
    // key is the key to level set, (a, b, c...) and the value is the path to
    // level-specification file
    private Map<Character, String> levelSetPath = new HashMap<>();
    private Map<Character, List<LevelInformation>> keyToLevelDefinitios = new HashMap<>();
    private GUI gui;

    /**
     * Instantiates a new level set reader.
     * @param g the gui
     */
    public LevelSetReader(GUI g) {
        this.gui = g;
    }

    /**
     * Receives level_sets.txt file and reads him. return a map between lvlset symbol to list of lvls
     * for example a: hardlvls = list of hard lvls.
     * @param reader the level_sets.txt
     * @return a map between lvlset symbol to list of lvls
     */
    public Map<Character, List<LevelInformation>> fromReader(java.io.Reader reader) {
        try {
            LineNumberReader readLine = new LineNumberReader(reader);
            String line;
            // read lines till the end of the stream
            int lineCounter = 1;
            Character levelsetSymbol = null;
            while ((line = readLine.readLine()) != null) {
                // remove white spaces from the end of line
                line = line.trim();
                // if line is odd line (1,3,5..)
                if (lineCounter % 2 == 1) {
                    line = line.trim();
                    // split line into key:value form
                    String[] split = line.split(":");
                    levelsetSymbol = split[0].charAt(0);
                    String description = split[1];
                    addLevelSetDescription(levelsetSymbol, description);
                } else {
                    // line is even line (2,4,6,8..)
                    line = line.trim();
                    if (levelsetSymbol != null) {
                        addLevelSetPath(levelsetSymbol, line);
                    }
                }
                lineCounter++;
            }
            // if something went wrong while reading
            if (this.levelSetDescription.keySet().size() != this.levelSetPath.keySet().size()) {
                throw new RuntimeException(
                        "Error reading levelSet file. \n levelset descriptions & path are not equal");
            }
            for (Character k : levelSetPath.keySet()) {
                InputStreamReader lvlReader = new java.io.InputStreamReader(
                        ClassLoader.getSystemResourceAsStream(levelSetPath.get(k)));
                LevelSpecificationReader lvLevelSpecificationReader = new LevelSpecificationReader(gui);
                keyToLevelDefinitios.put(k, lvLevelSpecificationReader.fromReader(lvlReader));
            }
            return this.keyToLevelDefinitios;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyToLevelDefinitios;
    }

    /**
     * return Levelset path.
     * @param key levelset symbol, eg. a,b,c
     * @return path to level-specification file as String
     */
    public String levelsetPath(Character key) {
        if (this.levelSetPath.containsKey(key)) {
            return this.levelSetPath.get(key);
        }
        return null;
    }

    /**
     * return Levelset description.
     * @param key levelset symbol, eg. a,b,c
     * @return path to level-specification file as String
     */
    public String levelsetDescription(Character key) {
        if (this.levelSetDescription.containsKey(key)) {
            return this.levelSetDescription.get(key);
        }
        return null;
    }

    /**
     * Adds the level set description to given symbol of levelset.
     * @param key the symbol
     * @param description the description to add
     */
    private void addLevelSetDescription(Character key, String description) {
        this.levelSetDescription.put(key, description);
    }

    /**
     * Adds the level set path to given symbol of levelset.
     * @param key the symbol
     * @param path the path to add
     */
    private void addLevelSetPath(Character key, String path) {
        this.levelSetPath.put(key, path);
    }

    /**
     * @return Levelset path keys list.
     */
    public List<Character> levelsetPathKeys() {
        return new ArrayList<>(this.levelSetPath.keySet());
    }

    /**
     * @return Levelset description keys list.
     */
    public List<Character> levelsetDescriptionKeys() {
        return new ArrayList<>(this.levelSetDescription.keySet());
    }
    /**
     * Gets the level set description.
     * //IM PRETTEY DAMN SURE IT SHOULDN'T BE THIS WAY, BUT I WON't CHANGE WORKING CODE!  = new HashMap<>()
     * @return the levelSetDescription
     */
    public Map<Character, String> getLevelSetDescription() {
        return levelSetDescription;
    }

    /**
     * Gets the level set path.
     * @return the levelSetPath
     */
    public Map<Character, String> getLevelSetPath() {
        return levelSetPath;
    }

}
