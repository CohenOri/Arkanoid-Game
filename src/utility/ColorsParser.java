package utility;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Class ColorsParser.
 */
public class ColorsParser {

    /**
     * Instantiates a new colors parser.
     */
    public ColorsParser() {

    }

    /**
     * Convert to Color from string.
     * @param s the String eg. "red" or "RGB(10,15,20)"
     * @return the java.awt. color
     */
    // parse color definition and return the specified color.
    public java.awt.Color colorFromString(String s) {
        s = s.trim();
        if (s.indexOf("RGB") >= 0) {
            try {
                s = s.replace("RGB(", "");
                s = s.replace(")", "");
                s = s.trim();
                List<String> colorRGB = new ArrayList<String>(Arrays.asList(s.split(",")));
                return new java.awt.Color(Integer.parseInt(colorRGB.get(0).trim()),
                        Integer.parseInt(colorRGB.get(1).trim()), Integer.parseInt(colorRGB.get(2).trim()));
            } catch (Exception e) {
                throw new RuntimeException("invalid Color was set for block: " + s);
            }
        } else {
            s = s.replace("color(", "");
            s = s.replace(")", "");
            s = s.trim();
            try {
                Field field = Color.class.getField(s);
                return (Color) field.get(null);
            } catch (Exception e) {
                throw new RuntimeException("invalid Color was set for block: " + s);
            }
        }
    }
}