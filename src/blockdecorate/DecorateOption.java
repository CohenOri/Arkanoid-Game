package blockdecorate;

import java.io.IOException;

/**
 * The Class DecorateOption.
 * Has only one method "decorate" which Receives a default BlockCreator (of basic block) option and value,
 * chooses which decorator to trigger based on option, gives the decorator the current BlockCreator,
 * and value (for example value is width), the widthDecorator receives the block creator, update him with new
 * width and updates the block, so if we later create the block it will have the correct width.
 */
public class DecorateOption {
    /**
     * Instantiates a new decorate option.
     */
    public DecorateOption() {

    }

    /**
     * Decorate.
     * Receives a default BlockCreator (of basic block) option and value,
     * chooses which decorator to trigger based on option, gives the decorator the current BlockCreator,
     * and value
     * @param creator the BlockCreator
     * @param option the option
     * @param value the value
     * @return the updated block creator
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static BlockCreator decorate(BlockCreator creator, String option, String value) throws IOException {
        if (option.equals("height")) {
            return new BlockHeightDecorator(creator, value);
        }
        if (option.equals("width")) {
            return new BlockWidthDecorator(creator, value);
        }
        if (option.equals("hit_points")) {
            return new BlockHitPointsDecorator(creator, value);
        }
        if (option.startsWith("fill")) {
            boolean isStroke = false;
            Integer hitPointsState = hitPointsState(option);
            return new BlockDrawDecorator(creator, value, hitPointsState, isStroke);
        }
        if (option.equals("stroke")) {
            boolean isStroke = true;
            Integer hitPointsStateK = hitPointsState(option);
            return new BlockDrawDecorator(creator, value, hitPointsStateK, isStroke);
        }
        throw new RuntimeException("no such option, option: " + option + "val:" + value);
    }

    /**
     * Hit points state.
     * return null if object does not split to different coloring options, return
     * number of state (K) if it does
     * @param option the option
     * @return the integer
     */
    private static Integer hitPointsState(String option) {
        int index = option.indexOf("-");
        if (index != -1) {
            // if decorator separated into different hitPoints states
            return Integer.valueOf(option.substring(index + 1));
        }
        return null;
    }
}
