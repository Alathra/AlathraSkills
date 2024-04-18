package io.github.alathra.alathraskills.gui;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.guis.Gui;
import org.bukkit.entity.Player;

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
                    .rows(1)
                    .title(ColorParser.of("<dark_grey>[<gradient:#ffff80:#00ff00>AlathraSkills</gradient><dark_grey>]").build()) // TODO: config
                    .disableItemDrop()
                    .disableItemPlace()
                    .disableItemSwap()
                    .disableItemTake()
                    .create();
            }
            case SKILL -> {
                gui = Gui.gui()
                    .rows(3)
                    .title(ColorParser.of("<dark_grey>[<gradient:#ffff80:#00ff00>AlathraSkills</gradient><dark_grey>]").build()) // TODO: config
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

    public static void populateMainGui(Gui gui, Player player) {
        io.github.alathra.alathraskills.gui.main.PopulateContent.populateContent(gui, player);
    }

    public static void populateSkillGui(Gui gui) {
        io.github.alathra.alathraskills.gui.skill.PopulateBorders.populateBorders(gui);
        io.github.alathra.alathraskills.gui.skill.PopulateButtons.populateButtons(gui);
        io.github.alathra.alathraskills.gui.skill.PopulateContent.populateContent(gui);
    }

}
