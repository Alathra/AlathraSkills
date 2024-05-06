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

    public static Gui buildSkillGui() {
        Gui gui;
        gui = Gui.gui()
            .rows(6)
            .title(ColorParser.of("<dark_grey>[<gradient:#ffff80:#00ff00>AlathraSkills</gradient><dark_grey>]").build())
            .disableItemDrop()
            .disableItemPlace()
            .disableItemSwap()
            .disableItemTake()
            .create();
        return gui;
    }

    public static Gui buildConfirmGui() {
        Gui gui;
        gui = Gui.gui()
            .rows(1)
            .title(ColorParser.of("<dark_grey>[<gradient:#ffff80:#00ff00>AlathraSkills</gradient><dark_grey>]").build())
            .disableItemDrop()
            .disableItemPlace()
            .disableItemSwap()
            .disableItemTake()
            .create();
        return gui;
    }

    public static void openSkillCategoryGui(Player player) {
        Gui gui = buildGui(GuiHelper.GuiType.SKILL);
        populateSkillCategoryGui(gui, player);
        gui.open(player);
    }

    public static void openSkillGui(Player player, int skillCategoryId, int page) {
        Gui gui = buildSkillGui();
        populateSkillGui(gui, player, skillCategoryId, page);
        gui.open(player);
    }

    public static void openConfirmGui(Player player, int skill, int skillCategoryId, int page) {
        Gui gui = buildConfirmGui();
        populateConfirmGui(gui, player, skill, skillCategoryId, page);
        gui.open(player);
    }

    public static void populateMainGui(Gui gui, Player player) {
        io.github.alathra.alathraskills.gui.main.PopulateContent.populateContent(gui, player);
    }

    public static void populateSkillCategoryGui(Gui gui, Player player) {
        io.github.alathra.alathraskills.gui.skillcategory.PopulateButtons.populateButtons(gui, player);
        io.github.alathra.alathraskills.gui.skillcategory.PopulateContent.populateContent(gui, player);
    }

    public static void populateSkillGui(Gui gui, Player player, int skillCategoryId, int page) {
        io.github.alathra.alathraskills.gui.skill.PopulateButtons.populateButtons(gui, player, skillCategoryId, page);
        io.github.alathra.alathraskills.gui.skill.PopulateContent.populateContent(gui, player, skillCategoryId, page);
    }

    public static void populateConfirmGui(Gui gui, Player player, int skill, int skillCategoryId, int page) {
        io.github.alathra.alathraskills.gui.confirm.PopulateContent.populateContent(gui, player, skill, skillCategoryId, page);
    }

}
