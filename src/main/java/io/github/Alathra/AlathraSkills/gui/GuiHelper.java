package io.github.Alathra.AlathraSkills.gui;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.guis.Gui;

public class GuiHelper {

    public enum GuiType {
        MAIN,
        SKILL
    }

    public static Gui buildGui(GuiType type) {
        Gui gui;
        switch (type) {
            case MAIN -> {
                gui = Gui.gui()
                    .rows(6)
                    .title(ColorParser.of("<dark_grey>[<green>AlathraSkills<dark_grey>]").build()) // TODO: config
                    .disableItemDrop()
                    .disableItemPlace()
                    .disableItemSwap()
                    .disableItemTake()
                    .create();
            }
            case SKILL -> {
                gui = Gui.gui()
                    .rows(3)
                    .title(ColorParser.of("<dark_grey>[<green>AlathraSkills<dark_grey>]").build()) // TODO: config
                    .disableItemDrop()
                    .disableItemPlace()
                    .disableItemSwap()
                    .disableItemTake()
                    .create();
            }
            default -> gui = null;
        }
        return gui;
    }

}
